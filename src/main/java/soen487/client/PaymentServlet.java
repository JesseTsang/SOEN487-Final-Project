/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.client;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import soen487.entity.Invoice;
import soen487.logging.LogLevel;
import soen487.logging.Logger;
import soen487.util.DBUtils.InvoiceDAO;

/**
 *
 * @author Ethan Shen
 */
@Path("/invoice")
public class PaymentServlet {

    private final static Logger logger = Logger.getLogger(PaymentServlet.class.getName());
    
    @Context
    private UriInfo context;

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
     public List<Invoice> getInvoices() throws JAXBException, URISyntaxException {
       return InvoiceDAO.getInstance().fetchAll();
   }

    @POST
    @Path("/pay")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response pay(
                        @QueryParam("customerRef") int cusRef,
                        @QueryParam("payment") double amount) {
        try {
            Invoice invoice;
            invoice = InvoiceDAO.getInstance().fetch(cusRef);
            
            if(null != invoice.getCustomerRef()){
                amount = (invoice.getAmount_due() - amount) 
                        < 0 ? 0 : invoice.getAmount_due() - amount;
                invoice.setAmount_due(amount);
                boolean paid = 0 == invoice.getAmount_due();
                invoice.setPayment_status(paid);
                InvoiceDAO.getInstance().update(invoice);
            }
            
            return Response
                    .status(Response.Status.ACCEPTED)
                    .build();
                
        }catch (SQLException ex) {
            logger.log(LogLevel.DEBUG, ex.getMessage());
            
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ex.getCause())
                    .build();
        }
        
    }
}
