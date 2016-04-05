package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;
import org.json.simple.JSONObject;

import javax.swing.*;

/**
 * Author: SACHIN
 * Date: 3/23/2016.
 */
public abstract class AbstractExcelOperator implements ExcelOperator{

    public AbstractExcelOperator(JFrame frame){
       new MenuBar().setMenuBar(frame);
    }

    public AbstractExcelOperator(){}

    protected JSONObject processedData = new JSONObject();

    @Override
    public void processFile(ExcelField excelField) {}

    @Override
    public JSONObject getFinalData() {
        return processedData;
    }

    @Override
    public void generateExcel(ExcelField excelField, ExcelOperator excelOperator,boolean merged){}

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
