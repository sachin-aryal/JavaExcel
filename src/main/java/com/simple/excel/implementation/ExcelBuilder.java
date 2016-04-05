package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * Author: SACHIN
 * Date: 3/19/2016.
 */
public class ExcelBuilder extends AbstractExcelOperator{


    @Override
    public void generateExcel(ExcelField excelField,ExcelOperator excelOperator,boolean merged) {
        String outputFileName = "Output-"+new Date().toString();
        JSONObject finalData = excelOperator.getFinalData();
        if(merged){
            writeToExcel(outputFileName,excelField, (JSONObject) finalData.get("data"),merged);
        }else{
            writeToExcel(outputFileName,excelField, (JSONObject) finalData.get("data"));
        }
    }



    public void writeToExcel(String outputFileName,ExcelField excelField,JSONObject finalData){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        int columnCount = 0,rowCount = 0;

        int count =0;
        Row headerRow = sheet.createRow(rowCount);
        for(File file:excelField.getFile()){
            for(String hCells : excelField.getColumnMaps().keySet()){
                Cell cell = headerRow.createCell(columnCount);
                if(count==0){
                    cell.setCellValue(hCells+" File Name: "+file.getName());
                }else{
                    cell.setCellValue(hCells);
                }
                columnCount++;
                count++;
            }
            count=0;
        }
        columnCount=0;
        Row row=null;
        for (File file : excelField.getFile()) {
            for (String cellName : excelField.getColumnMaps().keySet()) {
                try {
                    rowCount = 1;
                    JSONObject fileJson = (JSONObject) finalData.get(file.getName());
                    JSONArray allCellData = (JSONArray) fileJson.get(cellName);
                    Iterator<String> iterator = allCellData.iterator();
                    while (iterator.hasNext()) {
                        row = sheet.getRow(rowCount);
                        if(row==null){
                            row = sheet.createRow(rowCount);
                        }
                        Cell cell = row.createCell(columnCount);
                        Object field = iterator.next();
                        try {
                            if (field instanceof String) {
                                cell.setCellValue((String) field);

                            } else if (field instanceof Integer) {

                                if(excelField.getColumnMaps().get(cellName).equals("INTEGER")){
                                    cell.setCellValue((Integer) field);
                                }else if(excelField.getColumnMaps().get(cellName).equals("STRING")){
                                    cell.setCellValue(String.valueOf(field));
                                } else{
                                    cell.setCellValue((Double) field);
                                }

                            } else if (field instanceof Double) {

                                if(excelField.getColumnMaps().get(cellName).equals("INTEGER")){
                                    cell.setCellValue((Integer) field);
                                }else if(excelField.getColumnMaps().get(cellName).equals("STRING")){
                                    cell.setCellValue(String.valueOf(field));
                                } else{
                                    cell.setCellValue((Double) field);
                                }

                            } else {
                                cell.setCellValue(String.valueOf(field));
                            }
                        }catch (Exception er){
                            cell.setCellValue(String.valueOf(field));
                        }
                        rowCount++;
                    }
                }catch(Exception er){
//                    er.printStackTrace();
                    System.out.println("Exception while getting Json Data");
                }
                columnCount++;
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("output/finalResult.xlsx")) {
            workbook.write(outputStream);
            System.out.println("Successfully Completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToExcel(String outputFileName,ExcelField excelField,JSONObject finalData,boolean merged){

    }
}
