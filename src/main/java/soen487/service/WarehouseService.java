package soen487.service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import soen487.entity.ItemShippingStatusList;
import soen487.entity.ItemList;
import soen487.entity.Customer;
import soen487.entity.Item;
import soen487.util.XMLUtils.XMLUtils;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import soen487.entity.Catalog;
import soen487.entity.Invoice;
import soen487.logging.LogLevel;
import soen487.logging.Logger;
import soen487.util.DBUtils.InvoiceDAO;
import soen487.util.FileLocator;
import soen487.util.NumberFormatter;
/**
 *
 * @author Moh
 */
@WebService(serviceName = "warehouseService")
public class WarehouseService  {
    
    private static Catalog catalog;
    private final static Logger logger = Logger.getLogger(WarehouseService.class.getName());
    
    @WebMethod(operationName = "shipGoods")
    public ItemShippingStatusList shipGoods(@WebParam(name = "itemList")ItemList itemList,
            @WebParam(name = "info")Customer info) throws JAXBException, Exception {
        logger.log(LogLevel.INFO, "Start shipping items to customer.");

        Invoice invoice = new Invoice();
        invoice.setCustomerRef(info.getCustomerReferenceNumber());
        invoice.setPayment_status(false);
        
        double amount_due = 0;
        
        //load catalog
        loadCatalog();
        List <Item> productList= new ArrayList();
        for(Item product: WarehouseService.catalog.getProducts()){
            productList.add(product);
        }
        
        System.out.println(productList.size());
        
        // to store shipped and not shipped items
        ItemList shippedItems = new ItemList();
        ItemList notShippedItems = new ItemList();

        
        
        // foreach item in the item list
        for (int i = 0; i < itemList.getItemList().size(); i++) {            
            Item item = (Item) itemList.getItemList().get(i);    
            
            // check the catalog if product quantity is avalaible                    
            for (Item catalogProduct : WarehouseService.catalog.getProducts()) {    
                
                // compare purchased item with catalog product
                if (item.getProductId().equals(catalogProduct.getProductId())) {     
                    
                    // item is available
                    if (item.getQuantity() <= catalogProduct.getQuantity()) {
                        logger.log(LogLevel.INFO, "Item #" + item.getProductId() +" is avaliable");
                      
                        // subtract shipped items quantity from catalog 
                        String filepath = System.getProperty("user.home") + "/soen487-w18-team07/src/main/resources/catalog.xml";
                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document doc = docBuilder.parse(filepath);
                        NodeList products = doc.getElementsByTagName("product");
                        
                        for (int j = 0; j < products.getLength(); j++) {
                            Node nodeProduct = doc.getElementsByTagName("product").item(j);
                            // loop the product child node
                            NodeList list = nodeProduct.getChildNodes();
                            boolean selectedItem = false;
                            for (int k = 0; k < list.getLength(); k++) {
                               Node node = list.item(k);
                               // update only for selected Item
                               if (node.getNodeName().equals("productId") && node.getTextContent().equals(item.getProductId())) {
                                    selectedItem = true;
                               }
                               // get the quantity element, and update the value
                               if (selectedItem == true && "quantity".equals(node.getNodeName())) {
                                    node.setTextContent(Integer.toString(catalogProduct.getQuantity() - item.getQuantity()));
                                    selectedItem = false;
                               }
                            }
                            
                            // write the content into xml file
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            DOMSource source = new DOMSource(doc);
                            StreamResult result = new StreamResult(new File(filepath));
                            transformer.transform(source, result);            
                        }
                        
                        // add to shipping list
                        
                        amount_due += item.getQuantity() * catalogProduct.getUnitPrice();
                        
                        Item iTem = new Item();
                        iTem.setManufacturerName(catalogProduct.getManufacturerName());
                        iTem.setProductId(catalogProduct.getProductId());
                        iTem.setProductType(catalogProduct.getProductType());
                        iTem.setQuantity(item.getQuantity());
                        iTem.setUnitPrice(catalogProduct.getUnitPrice());
                        iTem.setWarehouseId(catalogProduct.getWarehouseId());  
                        iTem.setItemStatus("Shipped");
                        shippedItems.setItemList(iTem);
                        
                        
                    }
                    // item is not available
                    else {
                        logger.log(LogLevel.INFO, "Item #" + item.getProductId() +" is not avaliable");
                        // not shipped items list, make them them as an ItemShippingStatusList and return it to the caller
                        Item iTemNotShipping = new Item();
                        iTemNotShipping.setManufacturerName(catalogProduct.getManufacturerName());
                        iTemNotShipping.setProductId(item.getProductId());
                        iTemNotShipping.setProductType(catalogProduct.getProductType());
                        iTemNotShipping.setQuantity(item.getQuantity());
                        iTemNotShipping.setUnitPrice(catalogProduct.getUnitPrice());
                        iTemNotShipping.setWarehouseId(catalogProduct.getWarehouseId());  
                        iTemNotShipping.setItemStatus("Not shipped");
                        notShippedItems.setItemList(iTemNotShipping);
                    }
                }
            }
        }
   
        // ship out the items that are available to the "Customer"
        ship(info,shippedItems);
        
        invoice.setAmount_due(NumberFormatter.round(amount_due, 2));
        InvoiceDAO.getInstance().insert(invoice);
        
        // restock check
        replenish();
        
        Map<ItemList,Customer> customerOrderSummary = new HashMap();
        customerOrderSummary.put(shippedItems, info);
        customerOrderSummary.put(notShippedItems, info);
        ItemShippingStatusList shippingInformation = new ItemShippingStatusList(customerOrderSummary);
        return shippingInformation;
    }
    
    public void ship(Customer customer, ItemList shippedItems) throws JAXBException, Exception {                
        // cutomer shopping item list XML file
        File file = FileLocator.getFile("/shippingRecords/" + customer.getCustomerReferenceNumber() + ".xml");
        // write out customer info to XML file
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(customer, new FileOutputStream(file, true));
            } 
        catch (JAXBException e) {
            logger.log(LogLevel.ERROR, e.getMessage());
        }  
        // write out shipped items to XML file
        for(int i = 0; i < shippedItems.getItemList().size(); i++) {
            logger.log(LogLevel.INFO, "Shipment size: "+ shippedItems.getItemList().size());
            Item item = (Item) shippedItems.getItemList().get(i);
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Item.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                // output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.marshal(item, new FileOutputStream(file, true));
//                jaxbMarshaller.marshal(item, System.out);
            }
            catch (JAXBException e) {
              logger.log(LogLevel.ERROR, e.getMessage());
            }  
        }
    }
    
    public void replenish() throws JAXBException, Exception {
        final  int INVENTORY_MIN_THRESHOLD = 50;
        final  int INVENTORY_MAX_THRESHOLD = 200;
        // load the catalog
        loadCatalog();
        
        //  Check the catalog products. If the product quantity is lower than the threshold
        for(Item catalogProduct : WarehouseService.catalog.getProducts()){ 
            int currentInventoryItemQuantity = catalogProduct.getQuantity();
            if (currentInventoryItemQuantity < INVENTORY_MIN_THRESHOLD) {
                logger.log(LogLevel.INFO, "Warehouse need to replenish.");
                int maxThresholdDifference = (INVENTORY_MAX_THRESHOLD - currentInventoryItemQuantity);
                // update Inventory
                String filepath = System.getProperty("user.home") + "/soen487-w18-team07/src/main/resources/catalog.xml";
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(filepath);
                NodeList products = doc.getElementsByTagName("product");
                for (int j = 0; j < products.getLength(); j++) {
                    Node product = doc.getElementsByTagName("product").item(j);
                    // loop the item child node
                    NodeList list = product.getChildNodes();
                    boolean selectedItem = false;
                    for (int i = 0; i < list.getLength(); i++) {
                       Node node = list.item(i);
                       // update only for selected Item
                       if (node.getNodeName().equals("productId") &&
                               node.getTextContent().equals(catalogProduct.getProductId())) {

                           selectedItem = true;
                       }
                       // get the quantity element, and update the value
                       if (selectedItem == true && "quantity".equals(node.getNodeName())) {
                            node.setTextContent(Integer.toString(maxThresholdDifference + currentInventoryItemQuantity));
                            selectedItem = false;
                       }
                    }
                }
                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(filepath));
                transformer.transform(source, result);                
                // order more from Manufacturer : amount = maxThresholdDifference         
            }
        }
    }
    
    private void loadCatalog() throws JAXBException, URISyntaxException{
        File file = FileLocator.getFile("catalog.xml");
        WarehouseService.catalog = (Catalog) XMLUtils.unmarshalXML(file, Catalog.class);
    }
}