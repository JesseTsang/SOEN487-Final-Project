/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ethanshen
 */
@XmlRootElement(name = "retailer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Retailer {
    
    @XmlElement(name="id")  
    private int id;
    private HashMap<Integer, Integer> stock;
    private ArrayList<Integer> warehouseIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElementWrapper
    @XmlElement(name="warehouseId")
    public List<Integer> getWarehouseIds() {
        return warehouseIds;
    }

    public void setWarehouseIds(List<Integer> warehouseIds) {
        this.warehouseIds = (ArrayList<Integer>) warehouseIds;
    }

    public HashMap<Integer, Integer> getStock() {
        return stock;
    }

    public void setStock(HashMap<Integer, Integer> stock) {
        this.stock = stock;
    }
 
}
