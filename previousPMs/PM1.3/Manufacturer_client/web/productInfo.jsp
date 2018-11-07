<%-- 
    Document   : productInfo
    Created on : Feb 23, 2018, 12:20:45 AM
    Author     : Ethan Shen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Information</title>
    </head>
    <body>
        <h2>Lookup product information</h2>
        <form action="ManufacturerServlet" method="GET">
            <input type="hidden" name="requestType" Value="productInfo"/>
            <label>Product Type</label>
            <select name="productType" required>
                <option value="TV">TV</option>
                <option value="DVD_Player">DVD Player</option>
                <option value="Video_Camera">Video Camera</option>
            </select>
            </br></br>
            <input type="submit" name="submit" Value="Submit"/>
        </form>
        </br>
        <form action="index.jsp">
            <input type="submit" action="index.jsp" Value="Home"/>
        </form>
    </body>
</html>
