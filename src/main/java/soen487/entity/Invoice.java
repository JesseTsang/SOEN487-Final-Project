/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.entity;

import java.sql.Timestamp;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ethanshen
 */
@Entity
@Table(name = "Invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerRef;
    @Column(name = "amount_due")
    private double amount_due;
    @Column(name = "payment_status")
    private boolean payment_status;
    @Column(name = "last_update")
    private Timestamp last_update;

    public Invoice() {
    }
    
    public Invoice(Integer customerRef, double amount_due, boolean payment_status) {
        this.customerRef = customerRef;
        this.amount_due = amount_due;
        this.payment_status = payment_status;
    }
   
    public double getAmount_due() {
        return amount_due;
    }

    public void setAmount_due(double amount_due) {
        this.amount_due = amount_due;
    }

    public boolean isPayment_status() {
        return payment_status;
    }

    public void setPayment_status(boolean payment_status) {
        this.payment_status = payment_status;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }
    
    public Integer getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(Integer CustomerRef) {
        this.customerRef = CustomerRef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerRef != null ? customerRef.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the CustomerRef fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.customerRef == null && other.customerRef != null) || (this.customerRef != null && !this.customerRef.equals(other.customerRef))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soen487.client.Invoice[ id=" + customerRef + " ]";
    }
    
}
