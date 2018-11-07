package soen487.util.XMLUtils;

/**
 *
 * @author edwinyachoui
 */

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import soen487.util.Terms;

public class Marfcatin {
    
    public static List<String> marfcatInHandler(String marcartinURI) throws Exception{
              
        URI url = new URI(marcartinURI);
        
        // Prepares the request
        HttpClient lHttpClient = new DefaultHttpClient();
        
        int timeout = 10; // seconds
        HttpParams httpParams = lHttpClient.getParams();
        httpParams.setParameter(
        CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        httpParams.setParameter(
        CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
        
        HttpGet lHttpGet = new HttpGet();
        lHttpGet.setURI(url);
        lHttpGet.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(Terms.USERNAME, Terms.PASSWORD), "UTF-8", false));

        // Sends the request and read the response
        HttpResponse lHttpResponse = lHttpClient.execute(lHttpGet);
        InputStream lInputStream = lHttpResponse.getEntity().getContent();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(lInputStream);
        
        List<String> content = new ArrayList();
         
        doc.getDocumentElement().normalize();

        System.out.println("----------------------------");
        content.add("----------------------------");
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        content.add("Root element :" + doc.getDocumentElement().getNodeName());
        System.out.println("----------------------------");
        content.add("----------------------------");

        //Parse File
        NodeList nList = doc.getElementsByTagName("file");
        for (int temp = 0; temp < nList.getLength(); temp++) {

           Node nNode = nList.item(temp);
           System.out.println("\nCurrent Element :" + nNode.getNodeName());
           content.add("\nCurrent Element :" + nNode.getNodeName());

           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
              Element eElement = (Element) nNode;

              System.out.println("File ID: " 
                 + eElement.getAttribute("id"));

              content.add("File ID: " 
                 + eElement.getAttribute("id"));

              System.out.println("Type : " 
                 + eElement
                 .getElementsByTagName("type")
                 .item(0)
                 .getTextContent());

              content.add("Type : " 
                 + eElement
                 .getElementsByTagName("type")
                 .item(0)
                 .getTextContent());

              //Parse length attributes
              NodeList lengthList = doc.getElementsByTagName("length");
              Node lengthNode = lengthList.item(temp);

              System.out.println("Length Node :" );
              content.add("Length Node :");

              if (lengthNode.getNodeType() == Node.ELEMENT_NODE) {
              Element lengthElement = (Element) lengthNode;

              System.out.println("Lines : " 
                 + lengthElement.getAttribute("lines"));
              content.add("Lines : " 
                 + lengthElement.getAttribute("lines"));

              System.out.println("Words : " 
                 + lengthElement.getAttribute("words"));
              content.add("Words : " 
                 + lengthElement.getAttribute("words"));

              System.out.println("Bytes : " 
                 + lengthElement.getAttribute("bytes"));

              content.add("Bytes : " 
                 + lengthElement.getAttribute("bytes"));
              }

              //Parse Location
              System.out.println( "Location: " 
                 + eElement.getAttribute("location"));
              content.add("Location: " 
                 + eElement.getAttribute("location"));

              System.out.println("-------------------------------------");

              content.add("-------------------------------------");
           }
        }
      return content;   
    }  
}