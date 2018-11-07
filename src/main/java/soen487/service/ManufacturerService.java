package soen487.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import soen487.entity.Catalog;
import soen487.entity.Item;
import soen487.entity.PurchaseOrder;
import soen487.logging.LogLevel;
import soen487.logging.Logger;
import soen487.util.FileLocator;
import soen487.util.ProductValidator;
import soen487.util.Terms;
import soen487.util.XMLUtils.DOMUtils;
import soen487.util.XMLUtils.XMLUtils;

/**
 *
 * @author ethanshen
 */
@WebService(serviceName = "manufacturerService")
public class ManufacturerService {
    
    private static Catalog catalog;
    
    private final static Logger logger = Logger.getLogger(ManufacturerService.class.getName());
    
    @WebMethod(operationName = "processPurchaseOrder")
    public boolean processPurchaseOrder(@WebParam(name="purchaceOrder")PurchaseOrder aPO){
        logger.log(LogLevel.INFO, "Received purchase order.");
        boolean status = false;
        if(aPO.getQuantity() > Terms.MAX_QUANTITY){
            int offset = aPO.getQuantity() % Terms.MAX_QUANTITY;
            for(int i = 0; i < Math.floor(aPO.getQuantity()/100); i++){
                if (!produce(aPO.getProduct().getProductType(), Terms.MAX_QUANTITY)){
                    return status;
                }
            }
            status = produce(aPO.getProduct().getProductType(), offset);
            
        }else{
            status = produce(aPO.getProduct().getProductType(), aPO.getQuantity());
        }
        //Writing purchase order into an xml file.
        if(status){
            try {
                String filePath = "purchaseOrder_unpaid/"
                        + Terms.PURCHASE_ORDER
                        + "_" + aPO.getOrderNum()
                        + "_"+ Terms.UNPAID
                        + Terms.XML_SUFFIX;
                
                File unpaidOrder = FileLocator.getFile(filePath);
                status = XMLUtils.marshalObject(unpaidOrder, aPO);
                if(status){
                    logger.log(LogLevel.DEBUG, "Purchase order #" + aPO.getOrderNum() + " created.");
                }
            } catch (JAXBException ex) {
                logger.log(LogLevel.ERROR, ex.getMessage());
            }
        }
        return status;
    }
    
    @WebMethod(operationName = "getProductInfo")
    public Item getProductInfo(@WebParam(name="prodName")String aProdName) 
            throws JAXBException, URISyntaxException{
        if(null == ManufacturerService.catalog){
            loadCatalog();
        }
        for(Item product: ManufacturerService.catalog.getProducts()){
            if(aProdName.equalsIgnoreCase(product.getProductType())){
                return product;
            }
        }
        return null;
    }
    
    @WebMethod(operationName = "receivePayment")
    public boolean receivePayment(@WebParam(name="orderNum")String orderNum, 
            @WebParam(name="totalPrice")float totalPrice) throws JAXBException{
        
        String filePath = "purchaseOrder_unpaid/"
                + Terms.PURCHASE_ORDER 
                + "_" + orderNum
                + "_"+ Terms.UNPAID
                + Terms.XML_SUFFIX;
        
        File purchaseOrderFile = FileLocator.getFile(filePath);
        
        PurchaseOrder aPO = (PurchaseOrder) XMLUtils.unmarshalXML(purchaseOrderFile, PurchaseOrder.class);
        
        if(null == aPO) return false;
        
        if(totalPrice >= aPO.getUnitPrice() * aPO.getQuantity()){
            
            try {
                String outputFilePath = "purchaseOrder_paid/"
                        +Terms.PURCHASE_ORDER
                        + "_"+ orderNum
                        + "_"+ Terms.PAID
                        + Terms.XML_SUFFIX;
                
                File purchaseOrder_paid = FileLocator.getFile(outputFilePath);
                
                DOMUtils.appendNode(purchaseOrderFile, purchaseOrder_paid,
                        Terms.PURCHASE_ORDER, Terms.PAYMENT, String.valueOf(totalPrice));
                
                purchaseOrderFile.delete();
                logger.log(LogLevel.DEBUG, "Order #" + orderNum + " payment received.");
                return true;
            } catch (ParserConfigurationException | IOException | SAXException | TransformerException ex) {
                logger.log(LogLevel.ERROR, ex.getMessage());
                purchaseOrderFile.delete();
            }
        }
        return false;
    }
    
    private boolean produce(String productName, int quantity){
        return ProductValidator.validateProductName(productName) 
                && quantity <= 100;
    }
    
    private void loadCatalog() throws JAXBException, URISyntaxException{
        File file = FileLocator.getFile("catalog.xml");
        ManufacturerService.catalog = (Catalog) XMLUtils.unmarshalXML(file, Catalog.class);
    }
    
}
