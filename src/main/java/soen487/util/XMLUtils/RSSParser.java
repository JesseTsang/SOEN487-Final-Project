package soen487.util.XMLUtils;

import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

/**
 * @author Tsang Chi Kit (ID: 25692636)
 * This class reads an RSS file from an URL and returns all the contents of <title> nodes.
 */
public class RSSParser
{
    public static List<String> RSSHandler(String RSSURL)
    {        
        try
        {
            URL url = new URL(RSSURL);
            URLConnection connection = url.openConnection();
        	
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            List<String> content = new ArrayList();
            
            Document document = builder.parse(connection.getInputStream());

            document.getDocumentElement().normalize(); //Normalize XML tree structure

            String rootEle = document.getDocumentElement().getNodeName();

            System.out.println("XML type is: " + rootEle);
            content.add("XML type is: " + rootEle);

            //Loop through the XML tree to get to <title>
            NodeList treeList = document.getElementsByTagName("item"); //Each node starts with <item>

            for (int i = 0; i < treeList.getLength(); i++)
            {
                Node node = treeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    //Extract the <title> element from each node
                    NodeList titleList = element.getElementsByTagName("title");
                    Element titleElement = (Element) titleList.item(0);

                    //Extract the text inside the <title> element
                    Node titleNode = titleElement.getChildNodes().item(0);
                    String titleText = titleNode.getNodeValue();

                    System.out.println("Title " + i + ": " + titleText);
                    content.add("Title " + i + ": " + titleText);
                }
            }
            return content;
        }
        catch (IOException | ParserConfigurationException | DOMException | SAXException e){
            e.printStackTrace();
        }
        return null;   
    }
}