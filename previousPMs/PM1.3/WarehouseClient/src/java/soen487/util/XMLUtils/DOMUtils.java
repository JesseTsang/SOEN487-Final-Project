/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util.XMLUtils;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author ethanshen
 * @author Moh
 */
public class DOMUtils {
    
    private static final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private static DocumentBuilder docBuilder;
    private static Transformer transformer;
    
//    private final static Logger logger = Logger.getLogger(DOMUtils.class.getName());
    
    public static void appendNode(File sourcefile, File destinationFile, 
            String rootElement, String appendNode, String appendValue){
        try {
            if(null == DOMUtils.docBuilder){
                DOMUtils.docBuilder = DOMUtils.docFactory.newDocumentBuilder();
            }
            Document doc = docBuilder.parse(sourcefile);
            doc.getDocumentElement().normalize();
            
            Node rootNode = doc.getElementsByTagName(rootElement).item(0);
            
            Element element = doc.createElement(appendNode);
            element.appendChild(doc.createTextNode(appendValue));
            rootNode.appendChild(element);
            
            if(null == DOMUtils.transformer){
                transformer = transformerFactory.newTransformer();
            }
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(destinationFile);
            transformer.transform(source, result);
                    
        } catch (ParserConfigurationException | IOException 
                | SAXException | TransformerException ex) {
//            logger.debug(ex.getMessage(), ex.getCause());
        }
    }
}