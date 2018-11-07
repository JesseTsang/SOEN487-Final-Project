/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.wsdlJaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Moh
 */
@XmlRootElement(name = "definitions", namespace = "http://schemas.xmlsoap.org/wsdl/")
public class Definitions {
    
    @XmlElement(name = "op1ResponseMsg")
    private String str;
    
    public String getStr() {
        return str;
    }
}
