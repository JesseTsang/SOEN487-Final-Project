import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import soen487.service.Customer;
import soen487.service.Exception_Exception;
import soen487.service.Item;
import soen487.service.ItemList;
import soen487.service.ItemShippingStatusList;
import soen487.service.JAXBException_Exception;
import soen487.service.WarehouseService;
import soen487.service.WarehouseService_Service;

/**
 *
 * @author Moh
 */
@WebServlet(urlPatterns = {"/WarehouseServlet"})
public class WarehouseServlet extends HttpServlet {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/soen487-w18-team07/warehouseService.wsdl")
    private WarehouseService_Service service;

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
            throws ServletException, IOException, JAXBException_Exception, Exception_Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //            out.println("<strong>" + request.getQueryString() + "</strong><br/>");
            
            // get item information from submitted paramters
            String[] itemCollection = request.getParameterValues("item");
            String[] rawQuantityCollection  =  request.getParameterValues("quantity");
            List<String> allQuantityCollection = Arrays.asList(rawQuantityCollection);
            List<String> quantityCollection = new ArrayList();
            for (String str : allQuantityCollection) {  
              if (!(str.equals(""))) {
                    quantityCollection.add(str);
              }
            }  
            ItemList itemList = new ItemList();
            
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
            
            // create item object and add it to itemList object
            for (int i = 0; i < itemCollection.length; i++) {
                String[] itemInfo = itemCollection[i].split("_");
                Item item = new Item();
                item.setManufacturerName(itemInfo[0]);
                item.setProductType(itemInfo[1]);
                item.setUnitPrice(Float.parseFloat(itemInfo[2]));
                item.setQuantity(Integer.parseInt(quantityCollection.get(i)));
                itemList.getItemList().add(item);
            }
            out.println("hello");
            // call service operation "shipsGood"
            shipGoods(itemList,customer);
            out.println("hello1");
            response.sendRedirect("orderForm.jsp");
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
        } catch (JAXBException_Exception | Exception_Exception ex) {
            Logger.getLogger(WarehouseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ItemShippingStatusList shipGoods(soen487.service.ItemList itemList, soen487.service.Customer info) throws Exception_Exception, JAXBException_Exception {
        soen487.service.WarehouseService port = service.getWarehouseServicePort();
        return port.shipGoods(itemList, info);
    }
}