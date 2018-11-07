/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author ethanshen
 */
public class DateTimeFormatter {
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static String convert(long timestamp){
        return sdf.format(new Timestamp(timestamp));
    }
}
