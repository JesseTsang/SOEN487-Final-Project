package soen487.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import soen487.service.ReaderWs;
/**
 *
 * @author Marco
 * @author Moh
 */
@WebServlet(name = "ReaderClientServlet", urlPatterns = {"/ReaderClientServlet"})
public class ReaderClientServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ReaderClientServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h2>Servlet ReaderClientServlet at " + request.getContextPath () + "</h2>");

        // Getting variables from client
        String type = request.getParameter("type");
        String uri  = request.getParameter("uri");
        out.println("TYPE: " + type);
        out.println("<br/>");
        out.println("URI: " + uri);
        out.println("<br><br/>");
        ReaderWs service = new ReaderWs();
        List content = service.parser(uri, type);            
       
        // print parsed output
        for(int i = 0; i < content.size(); i++) {
            out.println(content.get(i));
            out.println("<br/>");
        }
    }
}