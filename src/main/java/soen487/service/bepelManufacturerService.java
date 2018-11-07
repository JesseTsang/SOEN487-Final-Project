package soen487.service;

import java.io.File;
import java.text.ParseException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import soen487.entity.Product;
import soen487.entity.PurchaseOrder;
import soen487.logging.LogLevel;
import soen487.logging.Logger;
import soen487.util.FileLocator;
import soen487.util.ProductValidator;
import soen487.util.Terms;
import soen487.util.XMLUtils.XMLUtils;

/**
 *
 * @author Moh
 */
@WebService(serviceName = "bepelManufacturerService")
public class bepelManufacturerService {

    private final static Logger logger = Logger.getLogger(bepelManufacturerService.class.getName());
    
    @WebMethod(operationName = "processPurchaseOrders")
    public boolean processPurchaseOrders(@WebParam(name="warehouseId") int warehouseId,
            @WebParam(name="quantity") int quantity, 
            @WebParam(name="productType") String productType) throws ParseException, Exception{
        
        logger.log(LogLevel.INFO, "Processing purchase order.");
        
        PurchaseOrder aPO = new PurchaseOrder();
        long orderNum = (int) (System.currentTimeMillis() & 0xfffffff);
        int customerRef = (int) (System.currentTimeMillis() & 0xfffffff);
        aPO.setOrderNum(orderNum);
        aPO.setCustomerRef(Integer.toString(customerRef));
        aPO.setWarehouseId(warehouseId);
        aPO.setManufacturerId(warehouseId);
        Product p = new Product();
        p.setId(1);
        p.setManufacturerId(Integer.toString(warehouseId));
        p.setModel("");
        p.setProductType(productType);
        p.setQuantity(quantity);
        p.setUnitPrice(0);
        p.setWarehouseId(Integer.toString(warehouseId));
        aPO.setProduct(p);
        aPO.setQuantity(quantity);
        aPO.setUnitPrice(0);
       	long millis=System.currentTimeMillis();  
        java.sql.Date date  =new java.sql.Date(millis);  
        aPO.setOrderDate(date);
        
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
        }
        
        logger.log(LogLevel.INFO, "Finished processing purchase order.");
        
        return status;
    }
    
    private boolean produce(String productName, int quantity){
        return ProductValidator.validateProductName(productName) 
                && quantity <= 100;
    }   
}
