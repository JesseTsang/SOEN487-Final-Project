/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Moh
 */
@Entity
@Table(name = "purchase_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PurchaseOrder.findAll", query = "SELECT p FROM PurchaseOrder p"),
    @NamedQuery(name = "PurchaseOrder.findByOrderid", query = "SELECT p FROM PurchaseOrder p WHERE p.orderid = :orderid"),
    @NamedQuery(name = "PurchaseOrder.findByWarehouseId", query = "SELECT p FROM PurchaseOrder p WHERE p.warehouseId = :warehouseId"),
    @NamedQuery(name = "PurchaseOrder.findByManufacturerId", query = "SELECT p FROM PurchaseOrder p WHERE p.manufacturerId = :manufacturerId"),
    @NamedQuery(name = "PurchaseOrder.findByProductId", query = "SELECT p FROM PurchaseOrder p WHERE p.productId = :productId"),
    @NamedQuery(name = "PurchaseOrder.findByCustomerRef", query = "SELECT p FROM PurchaseOrder p WHERE p.customerRef = :customerRef"),
    @NamedQuery(name = "PurchaseOrder.findByQuantity", query = "SELECT p FROM PurchaseOrder p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PurchaseOrder.findByUnitPrice", query = "SELECT p FROM PurchaseOrder p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PurchaseOrder.findByOrderdate", query = "SELECT p FROM PurchaseOrder p WHERE p.orderdate = :orderdate")})
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Order_id")
    private Integer orderid;
    @Column(name = "Warehouse_Id")
    private Integer warehouseId;
    @Column(name = "Manufacturer_Id")
    private Integer manufacturerId;
    @Column(name = "Product_Id")
    private Integer productId;
    @Column(name = "Customer_Ref")
    private Integer customerRef;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity")
    private int quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Unit_Price")
    private BigDecimal unitPrice;
    @Column(name = "Order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderdate;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Integer orderid) {
        this.orderid = orderid;
    }

    public PurchaseOrder(Integer orderid, int quantity, BigDecimal unitPrice) {
        this.orderid = orderid;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(Integer customerRef) {
        this.customerRef = customerRef;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseOrder)) {
            return false;
        }
        PurchaseOrder other = (PurchaseOrder) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soen487.entity.PurchaseOrder[ orderid=" + orderid + " ]";
    }
    
}
