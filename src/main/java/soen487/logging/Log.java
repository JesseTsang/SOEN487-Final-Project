/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.logging;

import java.sql.Timestamp;

/**
 *
 * @author ethanshen
 */
public class Log {
    
    private String logClass;
    private String logLevel;
    private String logMsg;
    private Timestamp log_timestamp;
    
    public Log() {
    }
    
    public Log(String logClass, String logLevel, String logMsg) {
        this.logClass = logClass;
        this.logLevel = logLevel;
        this.logMsg = logMsg;
    }
    
    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public Timestamp getLog_timestamp() {
        return log_timestamp;
    }

    public void setLog_timestamp(Timestamp log_timestamp) {
        this.log_timestamp = log_timestamp;
    }
}
