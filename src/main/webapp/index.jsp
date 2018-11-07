<%-- 
    Document   : reader
    Author     : Marco
    Author     : Moh
    
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>URI Reader</h1>
        <form action="ReaderClientServlet">
            <label>Type of Feed</label>
            <select name="type">
                <option value="rss">RSS</option>
                <option value="nn">Neural Network</option>
                <option value="marfcatin">Marfcat-In</option>
                <option value="marfcatout">Marfcat-Out</option>
                <option value="wsdl">WSDL</option>
            </select>
            <br><br/>            
            <label>URI</label>
            <input type="text" size="100" name="uri"/>
            <br><br/>
            <input type="submit" name="submit" value="Submit"/>
        </form>
    </body>
</html>