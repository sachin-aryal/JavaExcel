package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;
import org.json.simple.JSONObject;

/**
 * Author: SACHIN
 * Date: 3/23/2016.
 */
public abstract class AbstractExcelOperator implements ExcelOperator{

    protected JSONObject processedData = new JSONObject();

    protected static ExcelOperator excelOperator;
    protected static ExcelField excelField;


    public static ExcelOperator getExcelOperator() {
        return excelOperator;
    }

    public static void setExcelOperator(ExcelOperator excelOperator){
        AbstractExcelOperator.excelOperator = excelOperator;
    }

    public static void setExcelField(ExcelField excelField){
        AbstractExcelOperator.excelField = excelField;
    }

    public static ExcelField getExcelField(){return excelField;}

    public AbstractExcelOperator(){}

    @Override
    public void processFile() {}

    @Override
    public JSONObject getFinalData() {
        return processedData;
    }

    @Override
    public void generateExcel(boolean merged){}

    @Override
    public void showProcessedData(){}

    @Override
    public void setMapping(){}

    @Override
    public void mergeColumns(){}

    @Override
    public void setFinalData(JSONObject processedData){
        this.processedData=processedData;
    }
}
