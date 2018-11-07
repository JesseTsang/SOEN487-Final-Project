/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.util.DBUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 *
 * @author ethanshen
 */
public class RandomGenerator {
    
    private static RandomGenerator instance;
    
    private RandomGenerator(){}
    
    public static RandomGenerator getInstance(){
        if(null == RandomGenerator.instance){
            return RandomGenerator.instance = new RandomGenerator();
        }
        return RandomGenerator.instance;
    }
    
    public double randomDouble(double minRange, double maxRange){
        Random r = new Random();
        double value = 0;
        if((minRange + (maxRange - minRange)) > Double.MAX_VALUE){
            value = Double.MAX_VALUE * r.nextDouble();
        }else if((minRange + (maxRange - minRange)) < 0){
            return 0.01;
        }else {
            value = (minRange + (maxRange - minRange)) * r.nextDouble();
        }
        
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
    
    public int randomInt(int minRange, int maxRange){
        Random r = new Random();
        return r.nextInt((maxRange - minRange) + 1) + minRange;
    }

}