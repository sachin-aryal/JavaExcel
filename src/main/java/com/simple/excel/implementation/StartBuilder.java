package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;

import javax.swing.*;

/**
 * Author: SACHIN
 * Date: 3/30/2016.
 */
public class StartBuilder {

    public void startProcess(ExcelField excelField,JFrame frame){

        new DialogueBuilder("progressBar",frame).showWaitDialogue();

        ExcelOperator excelOperator = ExcelFactory.getObjectInstance("ExcelReader");
        if (excelOperator != null) {
            excelOperator.processFile(excelField);
        }

        ExcelOperator excelOperatorB = ExcelFactory.getObjectInstance("ExcelBuilder");
        if(excelOperatorB!=null){
            excelOperatorB.generateExcel(excelField,excelOperator,false);
        }
        new DataViewer(excelField,excelOperator);
        frame.dispose();
    }
}
