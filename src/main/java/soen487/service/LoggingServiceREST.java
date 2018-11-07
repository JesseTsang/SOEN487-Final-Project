/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.service;

import java.net.URISyntaxException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import soen487.logging.Log;
import soen487.logging.Logger;
import soen487.util.DBUtils.LoggingDAO;

/**
 *
 * @author ethanshen
 */
@Path("/logging")
public class LoggingServiceREST {
    
    private final static Logger logger = Logger.getLogger(LoggingServiceREST.class.getName());
    
    @Context
    private UriInfo context;

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
     public List<Log> getLogs() throws JAXBException, URISyntaxException {
         return LoggingDAO.getInstance().fetchAll();
   }
     
     @GET
     @Path("/inteval")
     @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
     public List<Log> getIntevalLogs(
             @QueryParam("lower_limit") long lower,
             @QueryParam("upper_limit") long upper) 
             throws JAXBException, URISyntaxException {
         return LoggingDAO.getInstance().fetchAll(lower, upper);
   }

}
