package soen487.service;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import soen487.util.XMLUtils.Marfcatin;
import soen487.util.XMLUtils.Marfcatout;
import soen487.util.XMLUtils.Neural;
import soen487.util.XMLUtils.RSSParser;
import soen487.util.XMLUtils.WSDLParser;

/**
 *
 * @author Marco
 * @author Moh
 */
@WebService(serviceName = "ReaderWs")
public class ReaderWs {

    /**
     * Web service operation
     * @param uri
     * @param type
     * @return 
     * @throws java.lang.Exception
     */
    @WebMethod(operationName = "parser")
    public List<String> parser(@WebParam(name = "uri") String uri, @WebParam(name = "type") String type) throws Exception {
        
            switch(type){
                case "rss":
                   return RSSParser.RSSHandler(uri);
                case "nn":
                   return Neural.neuralHandler(uri);
                case "marfcatin":
                   return Marfcatin.marfcatInHandler(uri);
                case "marfcatout":
                   return Marfcatout.marfcatOutHandler(uri);
                case "wsdl":
                   return WSDLParser.WSDLHandler(uri);
            }       
        
        return  null;
    }
}