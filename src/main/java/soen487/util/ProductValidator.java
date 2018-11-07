/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util;

import soen487.logging.LogLevel;
import soen487.logging.Logger;


/**
 *
 * @author ethanshen
 */
public class ProductValidator {    
    private final static Logger logger = Logger.getLogger(ProductValidator.class.getName());
    
    public static boolean validateProductName(String pName){
         if(!(pName.equalsIgnoreCase(Terms.DVD_PLAYER) || pName.equalsIgnoreCase(Terms.VIDEO_CAMERA) 
                || pName.equalsIgnoreCase(Terms.TV))){
             logger.log(LogLevel.DEBUG, "Invalid product name: "+ pName +", refuse to produce");
             return false;
         }
         return true;
    }
}
