<%-- 
    Document   : purchaseOrder
    Created on : Feb 22, 2018, 10:57:05 PM
    Author     : Ethan Shen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Order</title>
    </head>
    <body>
        <h2>Create Order</h2>
        <form action="ManufacturerServlet" method="POST">
            <input type="hidden" name="requestType" Value="purchaseOrder"/>
            <label>Order number: </label>
            <input type="number" size="40" name="orderNum"/>
            <br/>
            <label>Customer Reference: </label>
            <input type="text" size="34" name="customerRef"/>
            </br>
            <label>Product:</label>
            </br>
            <label>Manufacturer:</label>
            <select name="manufacturerName" required>
                <option value="Samsung">Samsung</option>
                <option value="Sony">Sony</option>
                <option value="Panasonic">Panasonic</option>
            </select>
            <label>&emsp;Product Type: </label>
            <select name="productType" required>
                <option value="TV">TV</option>
                <option value="DVD_Player">DVD Player</option>
                <option value="Video_Camera">Video Camera</option>
            </select>
            <label>&emsp;Quantity: </label>
            <input type="number" name="quantity" min="1"/>
            <label>&emsp;Unit Price: </label>
            <input type="number" name="unitPrice" min="0" step=".01"/>
            </br>
            <input type="submit" name="submit" Value="Submit Order"/>
            <label>&emsp;</label>
            <input type="reset" name="reset" Value="Reset Form"/>
        </form>
        </br>
        <form action="index.jsp">
            <input type="submit" action="index.jsp" Value="Home"/>
        </form>
    </body>
</html>
