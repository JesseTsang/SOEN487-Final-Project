/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util.XMLUtils;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
//import org.apache.log4j.Logger;

/**
 *
 * @author ethanshen
 */
public class XMLUtils {
    
    private static JAXBContext jc = null;
    private static Unmarshaller umarshaller = null;
    private static Marshaller marshaller = null;
    
//    private final static Logger logger = Logger.getLogger(XMLUtils.class.getName());
    
    public static Object unmarshalXML(File sourceFile, Class clazz){
        try {
            jc = JAXBContext.newInstance(clazz);
            umarshaller = jc.createUnmarshaller();
            
            return umarshaller.unmarshal(sourceFile);
        } catch (JAXBException ex) {
//            logger.debug(ex.getMessage(), ex.getCause());
        }
        return null;
    }
    
    public static boolean marshalObject(File outputFile, Class clazz){
        try{
            jc = JAXBContext.newInstance(clazz);
            
            marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(clazz, outputFile);
            marshaller.marshal(clazz, System.out);
            
            return true;
        }catch(JAXBException ex){
//            logger.debug(ex.getMessage(), ex.getCause());
        }
        
        return false;
    }
    
}
