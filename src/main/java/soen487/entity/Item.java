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
    @XmlElement (name = "warehouseId")
    private String warehouseId;
    
    @XmlElement (name = "manufacturerName")
    private String manufacturerName;
    
    @XmlElement (name = "productId")
    private String productId;
    
    @XmlElement (name = "productType")
    private String productType;
    
    @XmlElement (name = "unitPrice")
    private float unitPrice;
    
    @XmlElement (name = "quantity")
    private int quantity; 
    
    @XmlElement (name = "status")
    private String status; 
    
    // getters and setters 
    
    public String getStatus() {
        return status;
    }
    
    public void setItemStatus(String status) {
        this.status = status;
    }
    
    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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