<%-- 
    Document   : orderForm
    Created on : Feb 17, 2018, 6:58:16 PM
    Author     : Moh
--%>

<%@page import="javax.xml.bind.Unmarshaller"%>
<%@page import="javax.xml.bind.JAXBContext"%>
<%@page import="soen487.entity.Item"%>
<%@page import="soen487.entity.Inventory"%>
<%@page import="soen487.util.XMLUtils.XMLUtils"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Warehouse Client</title>
    </head>
    <body>
        <h1>Available items in the warehouse</h1>
        <!--load inventory items from XML file-->
        <%
            String fileName = System.getProperty("user.home") + "/soen487-w18-team07/src/main/resources/inventory.xml";
            File file = new File(fileName);
            Inventory inventory = (Inventory) XMLUtils.unmarshalXML(file, Inventory.class);      
            for(Item inventoryItem: inventory.getItems()){   
                out.println("Manufacturer Name: " + inventoryItem.getManufacturerName() + "<br/>");
                out.println("Product Type: " + inventoryItem.getProductType() + "<br/>");
                out.println("Unit Price: " + inventoryItem.getUnitPrice() + "<br/>");
                out.println("Remaining Quantity: " + inventoryItem.getQuantity()+ "<br/>");
                out.println("<br/>");                
            }            
        %>
        
        <hr>
        
        <form action="WarehouseServlet">
            <h1>Select available items and enter the quantity you wish to purchase</h1><br/>
            <%                
              for(Item inventoryItem: inventory.getItems()){   
                out.println("<input type='checkbox' value='" + inventoryItem.getManufacturerName() + "_" +
                        inventoryItem.getProductType() + "_" +
                        inventoryItem.getUnitPrice() + 
                        "' name='item'>" +
                        inventoryItem.getProductType());                
                out.println("<input type='text' placeholder='quanity' size='5' name='quantity'/><br><br/>");
              }                
            %>  
            
            <hr>
            
            <h1>Enter your information</h1>
            
            <% int customerReferenceNumber = (int) (System.currentTimeMillis() & 0xfffffff);%>
            
            <input type="text" size="20" hidden="hidden" name="customerReferenceNumber" value='<%=customerReferenceNumber%>'/>
            
            <label>Name</label>
            <input type="text" size="20" name="name"/><br><br/>
            
            <label>Street 1</label>
            <input type="text" size="20" name="street1"/><br><br/>
            
            <label>Street 2</label>
            <input type="text" size="20" name="street2"/><br><br/>
            
            <label>City</label>
            <input type="text" size="20" name="city"/><br><br/>
            
            <label>State</label>
            <input type="text" size="20" name="state"/><br><br/>
            
            <label>Zip</label>
            <input type="text" size="20" name="zip"/><br><br/>
            
            <label>Country</label>
            <input type="text" size="20" name="country"/><br><br/>
            
            <input type="submit" name="submit" value="Submit"/>
            
        </form>
    </body>
</html>
