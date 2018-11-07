package soen487.entity;

import javax.xml.bind.annotation.*;
/**
 *
 * @author Moh
 */
@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    
    // declaring Customer variables
    @XmlElement (name = "customerReferenceNumber")
    private int customerReferenceNumber;
    
    @XmlElement (name = "name")
    private String name;
    
    @XmlElement (name = "street1")
    private String street1;
    
    @XmlElement (name = "street2")
    private String street2;
    
    @XmlElement (name = "city")
    private String city;
    
    @XmlElement (name = "state")
    private String state; 
    
    @XmlElement (name = "zip")
    private String zip;
    
    @XmlElement (name = "country")
    private String country;
    
    // getters and setters
    
    public int getCustomerReferenceNumber() {
        return customerReferenceNumber;
    }
    
    public void setCustomerReferenceNumber(int customerReferenceNumber) {
        this.customerReferenceNumber = customerReferenceNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
   
    public String getStreet1() {
        return street1;
    }
    
    public void setStreet1(String street1) {
        this.street1 = street1;
    }
    
    public String getStreet2() {
        return street2;
    }
    
    public void setStreet2(String street2) {
        this.street2 = street2;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZip() {
        return zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }    
}