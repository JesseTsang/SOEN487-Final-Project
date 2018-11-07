package soen487.entity;

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
public class Inventory {
    
    // declaring Inventory Items  
    @XmlElement (name = "warehouseId")
    private String warehouseId;
    
    @XmlElement (name = "productId")
    private String productId;
    
    @XmlElement (name = "quantity")
    private int quantity; 

    // getters and setters
    public String getWarehoused() {
        return warehouseId;
    }
    
    public void setWarehouseId(String warehoueId) {
        this.warehouseId = warehoueId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}