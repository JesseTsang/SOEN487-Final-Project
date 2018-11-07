/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util.XMLUtils;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
/**
 *
 * @author edwinyachoui
 */

public class Neural {
    
    public static List<String> neuralHandler(String neuralURI) throws Exception {
        
        URL url = new URL(neuralURI);
        URLConnection connection = url.openConnection();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(connection.getInputStream()); 
        List<String> content = new ArrayList();

        doc.getDocumentElement().normalize();

        NodeList rootNodes= doc.getElementsByTagName("net");
        Node rootNode= rootNodes.item(0);
        Element rootElement= (Element) rootNode;

        System.out.println("Root: " + rootNode.getNodeName());
        content.add("Root: " + rootNode.getNodeName());
        System.out.println("---------------------------------");
        content.add("---------------------------------");


        NodeList netList= rootElement.getElementsByTagName("layer");
        for(int i=0; i< netList.getLength(); i++){
            Node layerNode= netList.item(i);

            Element layerElement= (Element) layerNode;
            System.out.println("Layer Index: " + layerElement.getAttribute("index"));
            content.add("Layer Index: " + layerElement.getAttribute("index"));
            System.out.println("Layer Type: " + layerElement.getAttribute("type"));
            content.add("Layer Type: " + layerElement.getAttribute("type"));
            System.out.println("------------------------");
            content.add("------------------------");


            NodeList layerList= layerElement.getElementsByTagName("neuron");
            for(int j=0; j< layerList.getLength(); j++){
               Node neuronNode= layerList.item(j);

               Element neuronElement= (Element) neuronNode;
               System.out.println("\t"+ "Neuron Index: " + neuronElement.getAttribute("index"));
               content.add("\t"+ "Neuron Index: " + neuronElement.getAttribute("index"));
               System.out.println("\t"+"Neuron Thresh: " + neuronElement.getAttribute("thresh"));
               content.add("\t"+"Neuron Thresh: " + neuronElement.getAttribute("thresh"));

               NodeList neuronOutList= neuronElement.getElementsByTagName("output");
               NodeList neuronInList= neuronElement.getElementsByTagName("input");
               for(int k=0; k< neuronOutList.getLength(); k++){
                   Node outputNode= neuronOutList.item(k);
                   Element outputElement= (Element) outputNode;
                   if(outputElement.getAttribute("ref")!= null){
                       System.out.println("\t\t"+ "Output Ref: " + outputElement.getAttribute("ref"));
                       content.add("\t\t"+ "Output Ref: " + outputElement.getAttribute("ref"));
                   }
               }
               for(int x=0; x< neuronInList.getLength(); x++){
                   Node inputNode= neuronInList.item(x);
                   Element inputElement= (Element) inputNode;
                   if(inputElement.getAttribute("ref") != null){
                       System.out.println("\t\t"+ "Input Ref: " + inputElement.getAttribute("ref"));
                       content.add("\t\t"+ "Input Ref: " + inputElement.getAttribute("ref"));
                   }
                   if(inputElement.getAttribute("weight") != null){
                       System.out.println("\t\t"+ "Input Weight: " + inputElement.getAttribute("weight"));
                       content.add("\t\t"+ "Input Weight: " + inputElement.getAttribute("weight"));
                   }
               }
               System.out.println("\t"+"------------------------");                 
            }
            System.out.println("==================================");
            content.add("==================================");
        }  
        return content;
    }
}