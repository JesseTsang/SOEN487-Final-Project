package soen487.entity;

import java.sql.Date;
import javax.xml.bind.annotation.*;
/**
 *
 * @author ethanshen
 */
@XmlRootElement(name = "purchaseOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseOrder {
    
    @XmlElement(name = "orderNum")
    private long orderNum;
    @XmlElement(name = "customerRef")
    private String customerRef;
    @XmlElement(name = "warehouseId")
    private int warehouseId;
    @XmlElement(name = "manufacturerId")
    private int manufacturerId;
    @XmlElement(name = "product")
    private Product product;
    @XmlElement(name = "quantity")
    private int quantity;
    @XmlElement(name = "unitPrice")
    private float unitPrice;
    @XmlElement(name = "orderDate")
    private Date orderDate;

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    } 
    
}
