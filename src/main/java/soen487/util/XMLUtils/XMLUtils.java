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
import soen487.logging.LogLevel;
import soen487.logging.Logger;

/**
 *
 * @author ethanshen
 */
public class XMLUtils {
    
    private static JAXBContext jc = null;
    private static Unmarshaller umarshaller = null;
    private static Marshaller marshaller = null;
    
    private final static Logger logger = Logger.getLogger(XMLUtils.class.getName());
    
    public static Object unmarshalXML(File sourceFile, Class clazz) throws JAXBException{
        try {
            jc = JAXBContext.newInstance(clazz);
            umarshaller = jc.createUnmarshaller();
            
            return umarshaller.unmarshal(sourceFile);
        } catch (JAXBException ex) {
            logger.log(LogLevel.ERROR, "XML unmarshal failed.");
            throw ex;
        }
    }
    
    public static boolean marshalObject(File outputFile, Object obj) throws JAXBException{
        try{
            jc = JAXBContext.newInstance(obj.getClass());
            
            marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(obj, outputFile);
            marshaller.marshal(obj, System.out);
            
            return true;
        }catch(JAXBException ex){
            logger.log(LogLevel.ERROR, "XML marshal failed.");
            outputFile.delete();
            throw ex;
        }
    }
    
}
