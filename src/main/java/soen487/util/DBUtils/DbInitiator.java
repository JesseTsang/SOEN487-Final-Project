package soen487.util.DBUtils;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import soen487.logging.LogLevel;
import soen487.logging.Logger;
import soen487.util.FileLocator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ethanshen
 */
public class DbInitiator {
    
    private final static Logger logger = Logger.getLogger(DbInitiator.class.getName());
    private static DbInitiator instance;
    private static final RandomGenerator rgt = RandomGenerator.getInstance();
    
    private final String URL = "jdbc:mysql://localhost:3306/soen487_w18_team07?useSSL=false";
    private final String USER = "root";
    private final String PASSWORD = "";
    
    private static List<String> cities;
    private static List<String> productTypes;
    private static List<String> manufactures;
    private static List<String> productModels; 
    
    private DbInitiator(){
    }
    
    public static DbInitiator getInstance(){
        if(null == instance){
            return DbInitiator.instance = new DbInitiator();
        }
        return DbInitiator.instance;
    }
    
    public void initDbEntries(){
        loadList();
        initManufacturer();
        initProduct();
        initWarehouse();
        initRetailer();
        initInventory();
        flush();
    }

    private void initManufacturer() {
        String sql = "INSERT INTO Manufacturer (Id, Manufacturer_name) VALUE (?,?)";
        TreeSet<String> check_name_conflict = new TreeSet<>();
        
        String manufacture_name;
        
        try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pst = con.prepareStatement(sql)){
            for(int i=1; i <= 30; i++){
                
                manufacture_name = DbInitiator.manufactures.get(rgt.randomInt(0, 99));
                while(check_name_conflict.contains(manufacture_name)){
                    manufacture_name = DbInitiator.manufactures.get(rgt.randomInt(0, 99));
                }
                check_name_conflict.add(manufacture_name);
                
                pst.setInt(1, i);
                pst.setString(2, manufacture_name);
                pst.executeUpdate();
            }
        }
       catch (SQLException ex) {
            logger.log(LogLevel.ERROR, ex.getMessage());
            System.out.print(ex);
        }
    }
    
    private void initProduct() {
        String sql = "INSERT INTO Product (Id, Product_Type, Manufacturer_id, Model, Unit_Price) VALUE (?,?,?,?,?)";
        Map<Integer, TreeSet<Pair<String, String>>> check_uniqueness = new HashMap<>();
        
        String product_type;
        int manufacturer_id;
        String product_model;
        double unit_price;
        
        try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pst = con.prepareStatement(sql)){
            for(int i=1; i <= 100; i++){
  
                manufacturer_id = rgt.randomInt(1, 30);
                product_type = DbInitiator.productTypes.get(rgt.randomInt(0, 13));
                product_model = DbInitiator.productModels.get(rgt.randomInt(0, 99));
                unit_price = rgt.randomDouble(0.99, 9999.99);
                
                //Check for unique constraint for manufacturer and product_type and product_model.
                if(check_uniqueness.containsKey(manufacturer_id)){
                    TreeSet<Pair<String, String>> tset = check_uniqueness.get(manufacturer_id);
                    Pair<String, String> keyPair = new MutablePair(product_type, product_model);
                    while(tset.contains(keyPair)){
                        product_type = DbInitiator.productTypes.get(rgt.randomInt(0, 13));
                        product_model = DbInitiator.productModels.get(rgt.randomInt(0, 99));
                        keyPair = new MutablePair(product_type, product_model);
                    }
                    tset.add(keyPair);
                    check_uniqueness.put(manufacturer_id, tset);
   
                }else{
                    TreeSet<Pair<String, String>> tset = new TreeSet<>();
                    tset.add(new MutablePair(product_type, product_model));
                    
                    check_uniqueness.put(manufacturer_id, tset);
                }
                
                pst.setInt(1, i);
                pst.setString(2, product_type);
                pst.setInt(3, manufacturer_id);
                pst.setString(4, product_model);
                pst.setDouble(5, unit_price);
                pst.executeUpdate();
            }
        }
       catch (SQLException ex) {
            logger.log(LogLevel.ERROR, ex.getMessage());
        }
    }

    private void initWarehouse() {
        String sql = "INSERT INTO Warehouse (Id, Location) VALUE (?,?)";

        try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pst = con.prepareStatement(sql)){
            for(int i=1; i <= 5; i++){
                pst.setInt(1, i);
                pst.setString(2, DbInitiator.cities.get(rgt.randomInt(0, 49)));
                pst.executeUpdate();
            }
        }
       catch (SQLException ex) {
            logger.log(LogLevel.ERROR, ex.getMessage());
        }
    }

    private void initRetailer() {
        String sql = "INSERT INTO Retailer (Id, Retailer_id, Product_Id, Quantity, Warehouse_id) VALUE (?,?,?,?,?)";
        TreeSet<Pair<Integer, Integer>> check_id_productId_uniqueness = new TreeSet<>();
        
        int id;
        int product_id;
        int quantity;
        int warehouse_id;
        
        try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pst = con.prepareStatement(sql)){
            for(int i=1; i <= 100; i++){
                
                id = rgt.randomInt(1,3);
                product_id = rgt.randomInt(1, 100);
                quantity = rgt.randomInt(0, 500);
                warehouse_id = rgt.randomInt(1, 5);
                
                Pair<Integer, Integer> keyPair = new MutablePair(id, product_id);
                //Check for unique constraint for retailer_id and product_id.
                while(check_id_productId_uniqueness.contains(keyPair)){
                    product_id = rgt.randomInt(1, 100);
                    keyPair.setValue(product_id);
                }
                
                check_id_productId_uniqueness.add(keyPair);
                
                pst.setInt(1, i);
                pst.setInt(2, id);
                pst.setInt(3, product_id);
                pst.setInt(4, quantity);
                pst.setInt(5, warehouse_id);
                pst.executeUpdate();
            }
        }
       catch (SQLException ex) {
            logger.log(LogLevel.ERROR, ex.getMessage());
        }
    }

    private void initInventory() {
        String sql = "INSERT INTO Inventory (Inventory_id, Product_Id, Warehouse_id, Quantity) VALUE (?,?,?,?)";
        TreeSet<Pair<Integer, Integer>> check_productId_warehouse_id_uniqueness = new TreeSet<>();
        
        int product_id;
        int warehouse_id;
        int quantity;
        
        try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pst = con.prepareStatement(sql)){
            for(int i=1; i <= 100; i++){
                
                product_id = rgt.randomInt(1, 100);
                quantity = rgt.randomInt(0, 500);
                warehouse_id = rgt.randomInt(1, 5);
                
                Pair<Integer, Integer> keyPair = new MutablePair(warehouse_id, product_id);
                //Check for unique constraint for retailer_id and product_id.
                while(check_productId_warehouse_id_uniqueness.contains(keyPair)){
                    product_id = rgt.randomInt(1, 100);
                    keyPair.setValue(product_id);
                }
                check_productId_warehouse_id_uniqueness.add(keyPair);
                
                pst.setInt(1, i);
                pst.setInt(2, product_id);
                pst.setInt(3, warehouse_id);
                pst.setInt(4, quantity);
                pst.executeUpdate();
            }
        }
       catch (SQLException ex) {
            logger.log(LogLevel.ERROR, ex.getMessage());
        }
    }
    
    private void loadList(){
        DbInitiator.cities = new ArrayList<>();
        DbInitiator.productTypes = new ArrayList<>();
        DbInitiator.manufactures = new ArrayList<>();
        DbInitiator.productModels = new ArrayList<>();
        
        try {
            Scanner scn = new Scanner(FileLocator.getFile("sql/cities.txt"));
            while(scn.hasNext()){
                cities.add(scn.nextLine());
            }
            
            scn = new Scanner(FileLocator.getFile("sql/manufactures.txt"));
            while(scn.hasNext()){
                manufactures.add(scn.nextLine());
            }
            
            scn = new Scanner(FileLocator.getFile("sql/models.txt"));
            while(scn.hasNext()){
                productModels.add(scn.nextLine());
            }
            
            scn = new Scanner(FileLocator.getFile("sql/productType.txt"));
            while(scn.hasNext()){
                productTypes.add(scn.nextLine());
            }
        } catch (FileNotFoundException ex) {
            logger.log(LogLevel.ERROR, ex.getMessage());
        }
    }
    
    private void flush(){
        DbInitiator.cities = null;
        DbInitiator.productTypes = null;
        DbInitiator.manufactures = null;
        DbInitiator.productModels = null;
    }
    
    public static void main(String[] args){
        DbInitiator dbi = DbInitiator.getInstance();
        
        dbi.initDbEntries();
        
        System.out.println("Done.");
    }
    
}
