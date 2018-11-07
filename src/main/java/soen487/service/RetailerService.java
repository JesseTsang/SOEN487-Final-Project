/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.service;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.xml.bind.JAXBException;
import soen487.entity.Catalog;
import soen487.entity.Item;
import soen487.util.FileLocator;
import soen487.util.XMLUtils.XMLUtils;

/**
 * REST Web Service
 *
 * @author Moh
 */
@Path("/retailer")
public class RetailerService {

    @Context
    private UriInfo context;
    private static Catalog catalog;

    public RetailerService() {
    }

    @GET
    @Produces("application/json")
     public static List<Item> getCatalog() throws JAXBException, URISyntaxException {
       loadCatalog();
       List <Item> productList= new ArrayList();
       for(Item product: RetailerService.catalog.getProducts()){
           productList.add(product);
       }
       return productList;
   }


    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
    
    private static void loadCatalog() throws JAXBException, URISyntaxException{
        File file = FileLocator.getFile("catalog.xml");
        RetailerService.catalog = (Catalog) XMLUtils.unmarshalXML(file, Catalog.class);
   }
}

