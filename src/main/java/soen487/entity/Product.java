package soen487.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ethanshen
 * @author Moh
 */
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {
    
    @XmlElement(name = "productId")
    private int id;
    @XmlElement(name = "warehouseId")
    private String warehouseId;
    @XmlElement(name = "manufacturerName")
    private String manufacturerId;
    @XmlElement(name = "productType")
    private String productType;
    @XmlElement(name = "model")
    private String model;
    @XmlElement(name = "unitPrice")
    private float unitPrice;
    @XmlElement (name = "quantity")
    private int quantity; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
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
    @Override
    public String toString() {
        return "Product{" + "warehouseId=" + warehouseId + ", manufacturerName=" + manufacturerId + ", productType=" + productType + ", unitPrice=" + unitPrice + '}';
    }
}
