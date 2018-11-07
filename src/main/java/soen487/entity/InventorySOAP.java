package soen487.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Moh
 */
@XmlRootElement(name = "inventory")
@XmlAccessorType(XmlAccessType.FIELD)
public class InventorySOAP {
    
    // declaring Inventory Items
    @XmlElement(name = "item")
    public List<Item> items;
    
    @XmlElement (name = "manufacturerName")
    private String manufacturerName;
    
    @XmlElement (name = "productType")
    private String productType;
    
    @XmlElement (name = "unitPrice")
    private float unitPrice;
    
    @XmlElement (name = "quantity")
    private int quantity; 

    // getters and setters
    
    public List<Item> getItems() {
        return items;
    }
    
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