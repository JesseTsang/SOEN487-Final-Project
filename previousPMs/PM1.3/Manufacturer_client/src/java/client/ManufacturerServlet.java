/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import soen487.service.JAXBException_Exception;
import soen487.service.ManufacturerService_Service;
import soen487.service.Product;
import soen487.service.PurchaseOrder;
import soen487.service.URISyntaxException_Exception;

/**
 *
 * @author Ethan Shen
 */
@WebServlet(name = "ManufacturerServlet", urlPatterns = {"/ManufacturerServlet"})
public class ManufacturerServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/soen487-w18-team07/manufacturerService.wsdl")
    private ManufacturerService_Service service;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws soen487.service.JAXBException_Exception
     * @throws soen487.service.URISyntaxException_Exception
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JAXBException_Exception, URISyntaxException_Exception {
        String responseMessage="";
        String s = request.getParameter("requestType");
        if(request.getParameter("requestType").equalsIgnoreCase("purchaseorder")){
            Product product = new Product();
            product.setManufacturerName(request.getParameter("manufacturerName"));
            product.setProductType(request.getParameter("productType"));
            product.setUnitPrice(Float.valueOf(request.getParameter("unitPrice")));
            
            PurchaseOrder aPO = new PurchaseOrder();
            aPO.setCustomerRef(request.getParameter("customerRef"));
            aPO.setOrderNum(Long.valueOf(request.getParameter("orderNum")));
            aPO.setProduct(product);
            aPO.setQuantity(Integer.valueOf(request.getParameter("quantity")));
            aPO.setUnitPrice(Float.valueOf(request.getParameter("unitPrice")));
            
            if(processPurchaseOrder(aPO)){
               responseMessage = "Purchase Order Created, Order number:" + request.getParameter("orderNum");
            }
        } else if(request.getParameter("requestType").equalsIgnoreCase("productInfo")){
            Product returnObj = getProductInfo(request.getParameter("productType"));
            if(null != returnObj){
                responseMessage = "Product Information:</br>" + "Manufacturer: " + returnObj.getManufacturerName() 
                        + "</br>Type: " + returnObj.getProductType()
                        + "</br>Unit Price: " + returnObj.getUnitPrice();
            } else {
                responseMessage = "Product doesn't exist.";
            }
        } else if (request.getParameter("requestType").equalsIgnoreCase("paymentReceived")){
            if(receivePayment(request.getParameter("orderNum"), Float.valueOf(request.getParameter("totalPrice")))){
                responseMessage = "Order: "+ request.getParameter("orderNum") + " is paid.";
            }else{
                responseMessage = "Error Occured, please verify order number and payment amount.";
            }
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Confirmation</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>"+ responseMessage +"</h2>");
            out.println("</body>");
            out.println("</html>");
            out.println("</br>");
            out.println("<form action="+"index.jsp"+">");
            out.println("<input type="+"submit"+" action="+"index.jsp"+" Value="+"Home"+">");
            out.println("</form>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JAXBException_Exception | URISyntaxException_Exception ex) {
            Logger.getLogger(ManufacturerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JAXBException_Exception | URISyntaxException_Exception ex) {
            Logger.getLogger(ManufacturerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Product getProductInfo(java.lang.String prodName) throws JAXBException_Exception, URISyntaxException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        soen487.service.ManufacturerService port = service.getManufacturerServicePort();
        return port.getProductInfo(prodName);
    }

    private boolean processPurchaseOrder(soen487.service.PurchaseOrder purchaceOrder) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        soen487.service.ManufacturerService port = service.getManufacturerServicePort();
        return port.processPurchaseOrder(purchaceOrder);
    }

    private boolean receivePayment(java.lang.String orderNum, float totalPrice) throws JAXBException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        soen487.service.ManufacturerService port = service.getManufacturerServicePort();
        return port.receivePayment(orderNum, totalPrice);
    }

}
