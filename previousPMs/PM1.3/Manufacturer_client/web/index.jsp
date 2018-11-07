<%-- 
    Document   : Manufacturer client app
    Author     : Ethan SHEN
    
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manufacturer Client</title>
    </head>
    <body>
        <h1>Welcome, how can I assist?</h1>
        <form action="purchaseOrder.jsp" method="GET">
            <input type="submit" value="Purchase Order"/>
        </form>
        </br></br>
        <form action="productInfo.jsp" method="GET">
            <input type="submit" value="Product Information"/>
        </form>
        </br></br>
        <form action="paymentReceived.jsp" method="GET">
            <input type="submit" value="Pay order"/>
        </form>
    </body>
</html>
