/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util.XMLUtils;

/**
 * This class is a simple data structure for storing attributes and attribute values from WSDL elements.
 * @author Jesse Tsang
 */
public class WSDLValuePair
{
	private String attribute;
	private String attributeValue;
	
	public WSDLValuePair(String attribute, String attributeValue)
	{
		this.attribute = attribute;
		this.attributeValue = attributeValue;	
	}

	public String getAttribute()
	{
		return attribute;
	}
        
	public String getAttributeValue()
	{
		return attributeValue;
	}
}