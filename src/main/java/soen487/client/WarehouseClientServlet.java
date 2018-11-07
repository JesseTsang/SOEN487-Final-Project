package soen487.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import soen487.entity.Customer;
import soen487.entity.Item;
import soen487.entity.ItemList;
import soen487.entity.ItemShippingStatusList;
import soen487.service.WarehouseService;

/**
 *
 * @author Moh
 */
@WebServlet(name = "WarehouseClientServlet", urlPatterns = {"/WarehouseClientServlet"})
public class WarehouseClientServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Map<String,String> productsMap = new HashMap<>();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                for (String paramValue : paramValues) {
                    if (!"".equals(paramValue)) {
                        String productSplit[] = paramName.split("-");
                        if (productSplit[0].equals("product")) {
                            productsMap.put(productSplit[1], paramValue);
                        }
                    }
                }
            }
    
            // create itemList
            ItemList itemList = new ItemList();
            
            // add items to itemList
            for (String key : productsMap.keySet()) {
                Item item = new Item();
                item.setWarehouseId("");
                item.setManufacturerName("");
                item.setProductType("");
                item.setUnitPrice(0);
                item.setProductId(key);
                item.setQuantity(Integer.parseInt(productsMap.get(key)));
                item.setItemStatus("");
                itemList.setItemList(item);
            }
            
            // get customer information from submitted parameters
            int customerReferenceNumber = Integer.parseInt(request.getParameter("customerReferenceNumber"));
            String name = request.getParameter("name");
            String street1 = request.getParameter("street1");
            String street2 = request.getParameter("street2");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");
            String country = request.getParameter("country");
            
            // create customer info object
            Customer customer = new Customer();
            customer.setCustomerReferenceNumber(customerReferenceNumber);
            customer.setName(name);
            customer.setStreet1(street1);
            customer.setStreet2(street2);
            customer.setCity(city);
            customer.setState(state);
            customer.setZip(zip);
            customer.setCountry(country);

            // call service operation "shipsGood"
            WarehouseService ws = new WarehouseService();
            ItemShippingStatusList shippingInformation = ws.shipGoods(itemList,customer);    
            
            // print out result
            Map<ItemList, Customer> map = shippingInformation.getItemShippingStatusList();

            for (Map.Entry<ItemList,Customer> shippingListType : map.entrySet()) {
                ItemList iL = shippingListType.getKey();
                Customer c = shippingListType.getValue();    
                out.println(c.getName() + "<br/>");
                // print out item list
                for (int i = 0; i < iL.getItemList().size(); i++) {    
                    Item item = (Item) iL.getItemList().get(i);
                    out.println("Product ID: " + item.getProductId() + "<br/>");
                    out.println("Warehouse ID: " + item.getWarehouseId() + "<br/>");
                    out.println("Manufacturer: " + item.getManufacturerName() + "<br/>");
                    out.println("Product: " + item.getProductType() + "<br/>");
                    out.println("Unit Price: " + item.getUnitPrice() + "<br/>");
                    out.println("Quantity: " + item.getQuantity() + "<br/>");
                    out.println("Item Status: " + item.getStatus() + "<br><br/>");
                }
                out.println("<hr>");
            }  
            out.println("<a href='http://localhost:8080/soen487-w18-team07/#home'><button>Go back to Home</button></a>");
        }
    }

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
        } catch (Exception ex) {
            Logger.getLogger(WarehouseClientServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
