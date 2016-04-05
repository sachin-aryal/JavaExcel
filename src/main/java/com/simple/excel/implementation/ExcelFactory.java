package com.simple.excel.implementation;

/**
 * Author: SACHIN
 * Date: 3/23/2016.
 */
public class ExcelFactory {
    public static ExcelOperator getObjectInstance(String className){
        try {
            Class cls = Class.forName("com.simple.excel.implementation."+className);
            return (ExcelOperator) cls.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
