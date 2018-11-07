/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util.DBUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import soen487.entity.Invoice;
import soen487.logging.LogLevel;
import soen487.logging.Logger;
import soen487.util.NumberFormatter;
import soen487.util.PropertyManager;

/**
 *
 * @author ethanshen
 */
public class InvoiceDAO implements DaoInterface<Invoice>{

    private final static Logger logger = Logger.getLogger(InvoiceDAO.class.getName());
    private static InvoiceDAO instance;
    private static List<Invoice> cachedInvoces;
    private static boolean cached;   
    
    private final static String DB_URL = PropertyManager.getProperty("db.properties", "logging_url");
    private final static String DB_USER = PropertyManager.getProperty("db.properties", "user");
    private final static String DB_PASSWORD = PropertyManager.getProperty("db.properties", "password");
    
    private InvoiceDAO() throws IOException{
    }
    
    public static InvoiceDAO getInstance(){
        if(null == instance){
            try {
                cachedInvoces = new ArrayList();
                cached = false;
                
                return InvoiceDAO.instance = new InvoiceDAO();
            } catch (IOException ex) {
                logger.log(LogLevel.ERROR, ex.getMessage());
            }
        }
        return InvoiceDAO.instance;
    }
    
    @Override
    public List<Invoice> fetchAll() {
        if(cached){
            return cachedInvoces;
        }
        String sql = "SELECT * FROM Invoice;";
        Invoice invoice;
        List<Invoice> newInvoices = new ArrayList();
        
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = con.createStatement()){
            
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()){
                invoice = new Invoice();
                invoice.setCustomerRef(rs.getInt("customerRef"));
                invoice.setAmount_due(rs.getDouble("amount_due"));
                invoice.setPayment_status(rs.getBoolean("payment_status"));
                invoice.setLast_update(rs.getTimestamp("last_update"));
                
                newInvoices.add(invoice);
            }
        }
       catch (SQLException ex) {
           logger.log(LogLevel.ERROR, ex.getMessage());
        }
        
        cachedInvoces = newInvoices;
        cached = true;
        
        return cachedInvoces;
    }

    @Override
    public List<Invoice> fetchAll(long upper, long lower) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Invoice invoice) throws SQLException {
        String sql = "INSERT INTO Invoice (customerRef, amount_due, payment_status) VALUE (?,?,?)";
        
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pst = con.prepareStatement(sql)){
            pst.setInt(1, invoice.getCustomerRef());
            pst.setDouble(2, invoice.getAmount_due());
            pst.setBoolean(3, invoice.isPayment_status());
            pst.executeUpdate();
        }
       catch (SQLException ex) {
           logger.log(LogLevel.ERROR, ex.getMessage());
           throw ex;
        }
        
        cached = false;
    }
    
    @Override
    public void update(Invoice invoice) throws SQLException {
        String sql = "UPDATE Invoice SET payment_status = " + invoice.isPayment_status()
                + ", amount_due = " + NumberFormatter.round(invoice.getAmount_due(), 2)
                + " WHERE customerRef = " + invoice.getCustomerRef();
        
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = con.createStatement()){
            
            statement.execute(sql);
        }
       catch (SQLException ex) {
           logger.log(LogLevel.ERROR, ex.getMessage());
           throw ex;
        }
        
        cached = false;
    }

    @Override
    public void delete(Invoice t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Invoice fetch(int customerRef) throws SQLException {
        String sql = "SELECT * FROM Invoice WHERE customerRef = "+ customerRef;
        Invoice invoice = null;
        
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = con.createStatement()){
            
            ResultSet rs = statement.executeQuery(sql);
            
            if(rs.next()){
                invoice = new Invoice();
                invoice.setCustomerRef(rs.getInt("customerRef"));
                invoice.setAmount_due(rs.getDouble("amount_due"));
                invoice.setPayment_status(rs.getBoolean("payment_status"));
                invoice.setLast_update(rs.getTimestamp("last_update"));
            }
        }
       catch (SQLException ex) {
           logger.log(LogLevel.ERROR, ex.getMessage());
           throw ex;
        }
        
        return invoice;
    }
    
}
