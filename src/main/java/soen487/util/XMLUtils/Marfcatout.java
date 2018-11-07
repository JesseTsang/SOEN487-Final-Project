/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util.XMLUtils;

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
/**
 *
 * @author edwinyachoui
 */
public class Marfcatout {
    
    public static List<String> marfcatOutHandler(String marcartoutURI) throws Exception{    
        
        URI url = new URI(marcartoutURI);
        
        // Prepares the request
        HttpClient lHttpClient = new DefaultHttpClient();
        HttpGet lHttpGet = new HttpGet();
        int timeout = 10; // seconds
        HttpParams httpParams = lHttpClient.getParams();
        httpParams.setParameter(
        CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        httpParams.setParameter(
        CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
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
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        content.add("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList weaknessList = doc.getElementsByTagName("weakness");
        System.out.println("---------------------------------");
        content.add("---------------------------------");

        for (int temp = 0; temp < weaknessList.getLength(); temp++) {
           Node weaknessNode = weaknessList.item(temp);
           System.out.println("\nCurrent Element :" + weaknessNode.getNodeName());
           content.add("\nCurrent Element :" + weaknessNode.getNodeName());

           if (weaknessNode.getNodeType() == Node.ELEMENT_NODE) {
              Element eElement = (Element) weaknessNode;
              System.out.println("Weakness ID : " + eElement.getAttribute("id"));
              content.add("Weakness ID : " + eElement.getAttribute("id"));

              System.out.println("Tool Specific ID : " + eElement.getAttribute("tool_specific_id"));
              content.add("Tool Specific ID : " 
                 + eElement.getAttribute("tool_specific_id"));

              System.out.println("Fragment : " 
                 + eElement
                 .getElementsByTagName("fragment")
                 .item(0)
                 .getTextContent());

              content.add("Fragment : " 
                 + eElement
                 .getElementsByTagName("fragment")
                 .item(0)
                 .getTextContent());

              System.out.println("Explanation : " 
                 + eElement
                 .getElementsByTagName("explanation")
                 .item(0)
                 .getTextContent());
              content.add("Explanation : " 
                 + eElement
                 .getElementsByTagName("explanation")
                 .item(0)
                 .getTextContent());

              System.out.print("Textoutput : " 
                 + eElement
                 .getElementsByTagName("textoutput")
                 .item(0)
                 .getTextContent());
              content.add("Textoutput : " 
                 + eElement
                 .getElementsByTagName("textoutput")
                 .item(0)
                 .getTextContent());

               System.out.println("------------------------------------------------------------------");
               content.add("------------------------------------------------------------------");
          }
       }
        return content;
    }
}