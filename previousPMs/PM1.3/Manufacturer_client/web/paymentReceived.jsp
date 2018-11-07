<%-- 
    Document   : paymentReceived
    Created on : Feb 23, 2018, 1:15:03 AM
    Author     : sy818
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Payment</title>
    </head>
    <body>
        <h2>Order Payment</h2>
        <form action="ManufacturerServlet" method="POST">
            <input type="hidden" name="requestType" Value="paymentReceived"/>
            <label>Order Number: </label>
            <input type="number" size="50" name="orderNum"/>
            <label> &emsp; Payment Amount: </label>
            <input type="number" size="50" name="totalPrice" min="0" step=".01"/>
            </br></br>
            <input type="submit" name="submit" Value="Submit"/>
        </form>
        </br>
        <form action="index.jsp">
            <input type="submit" action="index.jsp" Value="Home"/>
        </form>
    </body>
</html>
