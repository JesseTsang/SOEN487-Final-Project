/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Moh
 */
@Entity
@Table(name = "retailer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retailer.findAll", query = "SELECT r FROM Retailer r"),
    @NamedQuery(name = "Retailer.findById", query = "SELECT r FROM Retailer r WHERE r.id = :id"),
    @NamedQuery(name = "Retailer.findByRetailerid", query = "SELECT r FROM Retailer r WHERE r.retailerid = :retailerid"),
    @NamedQuery(name = "Retailer.findByProductId", query = "SELECT r FROM Retailer r WHERE r.productId = :productId"),
    @NamedQuery(name = "Retailer.findByQuantity", query = "SELECT r FROM Retailer r WHERE r.quantity = :quantity"),
    @NamedQuery(name = "Retailer.findByWarehouseid", query = "SELECT r FROM Retailer r WHERE r.warehouseid = :warehouseid"),
    @NamedQuery(name = "Retailer.removeRetailerWarehouseRelationship", query = "UPDATE Retailer r SET r.warehouseid=null WHERE r.retailerid = :retailerid")
})
public class Retailer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Retailer_id")
    private int retailerid;
    @Column(name = "Product_Id")
    private Integer productId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Warehouse_id")
    private Integer warehouseid;

    public Retailer() {
    }

    public Retailer(Integer id) {
        this.id = id;
    }

    public Retailer(Integer id, int retailerid, int quantity) {
        this.id = id;
        this.retailerid = retailerid;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRetailerid() {
        return retailerid;
    }

    public void setRetailerid(int retailerid) {
        this.retailerid = retailerid;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(Integer warehouseid) {
        this.warehouseid = warehouseid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retailer)) {
            return false;
        }
        Retailer other = (Retailer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soen487.entity.Retailer[ id=" + id + " ]";
    }
    
}
