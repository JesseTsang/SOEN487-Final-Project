/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import soen487.logging.Log;
import soen487.logging.LogLevel;
import soen487.logging.Logger;
import soen487.util.DateTimeFormatter;
import soen487.util.PropertyManager;

/**
 *
 * @author ethanshen
 */
public class LoggingDAO implements DaoInterface<Log>{

    private final static Logger logger = Logger.getLogger(DbInitiator.class.getName());
    private static LoggingDAO instance;
    private static List<Log> cachedLogs;
    private static boolean cached;
    
    private final static String DB_URL = PropertyManager.getProperty("db.properties", "logging_url");
    private final static String DB_USER = PropertyManager.getProperty("db.properties", "user");
    private final static String DB_PASSWORD = PropertyManager.getProperty("db.properties", "password");
    
    private LoggingDAO(){
    }
    
    public static LoggingDAO getInstance(){
        if(null == instance){
            cachedLogs = new ArrayList();
            return LoggingDAO.instance = new LoggingDAO();
        }
        return LoggingDAO.instance;
    }
    
    @Override
    public List<Log> fetchAll() {
        if(cached){
            return cachedLogs;
        }
        String sql = "SELECT * FROM Log;";
        Log log;
        List<Log> newLogs = new ArrayList();
        
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = con.createStatement()){
            
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()){
                log = new Log();
                log.setLogClass(rs.getString("log_class"));
                log.setLogLevel(rs.getString("log_level"));
                log.setLogMsg(rs.getString("log_message"));
                log.setLog_timestamp(rs.getTimestamp("log_timestamp"));
                
                newLogs.add(log);
            }
                    
            cachedLogs = newLogs;
            cached = true;
        }
       catch (SQLException ex) {
           logger.log(LogLevel.ERROR, ex.getMessage());
        }
        
        return cachedLogs;
    }
    
    @Override
    public List<Log> fetchAll(long lower, long upper) {        
        String sql = "SELECT * FROM Log WHERE log_timestamp >= "
                + "'" + DateTimeFormatter.convert(lower)+ "'"
                + " AND log_timestamp < "
                + "'" + DateTimeFormatter.convert(upper) + "';";
        Log log;
        List<Log> interval_log = new ArrayList();
        
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = con.createStatement()){
            
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()){
                log = new Log();
                log.setLogClass(rs.getString("log_class"));
                log.setLogLevel(rs.getString("log_level"));
                log.setLogMsg(rs.getString("log_message"));
                log.setLog_timestamp(rs.getTimestamp("log_timestamp"));
                
                interval_log.add(log);
            }
        }
       catch (SQLException ex) {
           logger.log(LogLevel.ERROR, ex.getMessage());
        }
        
        return interval_log;
    }
    
    @Override
    public Log fetch(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Log log) {
        String sql = "INSERT INTO Log (log_class, log_level, log_message) VALUE (?,?,?)";
        
        try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pst = con.prepareStatement(sql)){
            pst.setString(1, log.getLogClass());
            pst.setString(2, log.getLogLevel());
            pst.setString(3, log.getLogMsg());
            pst.executeUpdate();
            
            cached = false;
        }
       catch (SQLException ex) {
           System.err.print(ex);
        }
    }

    @Override
    public void update(Log t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Log t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}