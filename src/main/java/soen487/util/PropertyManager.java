/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ethanshen
 */
public class PropertyManager {
    
    public static String getProperty(String file, String key){
        FileReader reader = null;
        try {
            reader = new FileReader(FileLocator.getFile(file));
            Properties p = new Properties();
            p.load(reader);
            return p.getProperty(key);
        } catch (IOException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
}
