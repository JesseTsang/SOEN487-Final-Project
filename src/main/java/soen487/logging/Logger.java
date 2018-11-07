/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.logging;

import soen487.util.DBUtils.LoggingDAO;

/**
 *
 * @author ethanshen
 */
public class Logger {

    private String className;
    
    public Logger(String className) {
        this.className = className;
    }

    public static Logger getLogger(String name) {
        return new Logger(name);
    }
    
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    
    public void log(LogLevel level, String msg){
        Log log = new Log(getClassName(), level.name(), msg);
        LoggingDAO.getInstance().insert(log);
    }
    
}
