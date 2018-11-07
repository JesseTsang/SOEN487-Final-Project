package soen487.util.XMLUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Tsang Chi Kit (ID: 25692636)
 * This class reads a WSDL file from an URI and parse all the element names and its associated attribute.
 */
public class WSDLParser 
{
    /**
    * WSDLHandler(String str)
     * @return 
    * @Desc This method take a WSDL file from an URI and will parse all 
    * the element names and its associated attributes.
    * @param wsdlURI - an absolute URL (as a string) that contains a WSDL file.
    */
    public static List<String> WSDLHandler(String wsdlURI)
    {     
        try 
        {
            URL url = new URL(wsdlURI);
            URLConnection connection = url.openConnection();
      	
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Document document = builder.parse(wsdlURI);
            Document document = builder.parse(connection.getInputStream());
            List<String> content = new ArrayList();
                   
            //document.getDocumentElement().normalize();
            NodeList treeList = document.getElementsByTagName("*");
                               
            for (int i = 0; i < treeList.getLength(); i++)
            {
            	String element = treeList.item(i).getNodeName(); //get tag name
            	String attribute = "";
            	String attributeValue = "";
            	
            	int attributeListSize = treeList.item(i).getAttributes().getLength();
            	
            	System.out.println("Attribute size for : " + element + " is " + attributeListSize + "\n");
                content.add("Attribute size for : " + element + " is " + attributeListSize + "\n");
            	
            	ArrayList<WSDLValuePair> attributeList = new ArrayList<>();
            	
            	if (attributeListSize > 0)
            	{
                	for (int j = 0; j < attributeListSize; j++)
                	{             		
                		attribute = treeList.item(i).getAttributes().item(j).getNodeName();
                		attributeValue = treeList.item(i).getAttributes().item(j).getNodeValue();
                		          		
                		attributeList.add(new WSDLValuePair(attribute, attributeValue));
                	}
                	
                	for(WSDLValuePair n: attributeList)
                	{
                		System.out.println("Element: " + element + " :: Attribute = " + n.getAttribute() + " :: Value = " + n.getAttributeValue());                		
                                content.add("Element: " + element + " :: Attribute = " + n.getAttribute() + " :: Value = " + n.getAttributeValue());
                	}                       	
            	}
            	else
            	{
            		System.out.println("Element: " + element + " :: Attribute = " + attribute + " :: Value = " + attributeValue);     
                        content.add("Element: " + element + " :: Attribute = " + attribute + " :: Value = " + attributeValue);
            	}
            	
            	System.out.println("");
                content.add("");
            }
            return content;
        } 
        catch (ParserConfigurationException | SAXException | IOException ex) 
        {
            Logger.getLogger(WSDLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}