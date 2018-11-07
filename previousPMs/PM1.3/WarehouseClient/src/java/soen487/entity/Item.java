package soen487.entity;

import javax.xml.bind.annotation.*;
/**
 *
 * @author Moh
 */
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    // declaring Item variables
    @XmlElement (name = "manufacturerName")
    private String manufacturerName;
    
    @XmlElement (name = "productType")
    private String productType;
    
    @XmlElement (name = "unitPrice")
    private float unitPrice;
    
    @XmlElement (name = "quantity")
    private int quantity; 
    
    // getters and setters    
    public String getManufacturerName() {
        return manufacturerName;
    }
    
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
    
    public String getProductType() {
        return productType;
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}