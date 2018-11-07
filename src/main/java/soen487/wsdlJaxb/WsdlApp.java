/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.wsdlJaxb;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


/**
 *
 * @author Moh
 */
public class WsdlApp {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(Definitions.class);
        Unmarshaller u = jc.createUnmarshaller();   
        Definitions def = (Definitions) u.unmarshal(new File("C:\\Users\\mohammad\\soen487-w18-team07\\src\\main\\resources\\faultSample.wsdl"));
        System.out.println(def.getStr());
    }    
}