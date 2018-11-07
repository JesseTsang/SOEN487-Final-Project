package soen487.service;

import java.io.File;
import java.net.URISyntaxException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.JAXBException;
import soen487.entity.Catalog;
import soen487.entity.Item;
import soen487.logging.LogLevel;
import soen487.logging.Logger;
import soen487.util.FileLocator;
import soen487.util.XMLUtils.XMLUtils;

/**
 *
 * @author Moh
 */
@WebService(serviceName = "bepelWarehouseService")
public class bepelWarehouseService {

    private static Catalog catalog;
    
    private final static Logger logger = Logger.getLogger(bepelWarehouseService.class.getName());

    /**
     * @param warehouseId
     * @param productType
     * @param quantity
     * @return 
     * @throws java.lang.Exception 
     */
    @WebMethod(operationName = "shipGood")
    public boolean shipGood(@WebParam(name = "warehouseId") int warehouseId,
            @WebParam(name = "productType") String productType,
            @WebParam(name = "quantity") int quantity) throws Exception {
        
        logger.log(LogLevel.INFO, "ShipGood Request Received.");
        
         //load catalog
        loadCatalog();
        
        // check the catalog if product quantity is avalaible                    
        for (Item catalogProduct : bepelWarehouseService.catalog.getProducts()) { 
            System.out.println(warehouseId);
            // compare purchased item with catalog product
            if (productType.equalsIgnoreCase(catalogProduct.getProductType()) &&
                    warehouseId == Integer.parseInt(catalogProduct.getWarehouseId()) &&
                    quantity <= catalogProduct.getQuantity()) {   
                logger.log(LogLevel.INFO, "TRUE");
                return true;
            }
        }
        logger.log(LogLevel.INFO, "FALSE");
        return false;
    }
    
    private void loadCatalog() throws JAXBException, URISyntaxException{
        File file = FileLocator.getFile("catalog.xml");
        bepelWarehouseService.catalog = (Catalog) XMLUtils.unmarshalXML(file, Catalog.class);
    }
}