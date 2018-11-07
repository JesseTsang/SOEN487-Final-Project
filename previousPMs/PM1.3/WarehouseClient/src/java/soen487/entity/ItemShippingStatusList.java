package soen487.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Moh
 */
public class ItemShippingStatusList {
    
    // declaring ItemList variables
    @XmlElement (name = "shippedItemList")
    ArrayList<Item> shippedItemList = new ArrayList();
    
    @XmlElement (name = "notshippedItemList")
    ArrayList<Item> notShippedItemList = new ArrayList();
    
    @XmlElement (name = "shippingStatusList")
    Map<ItemList, Customer> shippingStatusList = new HashMap();
    
    public ItemShippingStatusList(Map<ItemList, Customer> shippingStatusList) {
       this.shippingStatusList = shippingStatusList;
    }
            
    //getters and setters   
    public ArrayList getShippedItemList() {
        return shippedItemList;
    }
    
    public void setShippedItemList(Item item) {
        this.shippedItemList.add(item);
    }
    
    public ArrayList getNotShippedItemList() {
        return notShippedItemList;
    }
    
    public void setNotShippedItemList(Item item) {
        this.notShippedItemList.add(item);
    }   
    
    public Map getItemShippingStatusList() {
        return shippingStatusList;
    }   
    
    public void setItemShippingStatusList(ItemList shippingStatusList, Customer customer) {
        this.shippingStatusList.put(shippingStatusList, customer);
    }
}