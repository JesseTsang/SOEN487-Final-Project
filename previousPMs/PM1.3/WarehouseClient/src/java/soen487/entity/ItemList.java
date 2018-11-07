package soen487.entity;

import javax.xml.bind.annotation.XmlElement;
import java.util.*;
/**
 *
 * @author Moh
 */
public class ItemList {
    
    // declaring ItemList variables
    @XmlElement (name = "itemList")
    ArrayList<Item> itemList = new ArrayList();
    
    //getters and setters   
    public ArrayList getItemList() {
        return itemList;
    }
    
    public void setItemList(Item item) {
        this.itemList.add(item);
    }
}
