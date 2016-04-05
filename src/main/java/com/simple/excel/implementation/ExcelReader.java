package com.simple.excel.implementation;

import com.simple.pozo.ExcelField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: SACHIN
 * Date: 3/19/2016.
 */
public class ExcelReader extends AbstractExcelOperator{

    JSONArray[] lists;
    JSONObject object;

    @Override
    public void processFile(ExcelField excelField){
        lists = new JSONArray[excelField.getTotalColumn()];
        object = new JSONObject();

        for(File eachFile:excelField.getFile()){
            addToJsonList(eachFile,excelField);
        }
        processedData.put("data",object);
        System.out.println("Processed Data is "+processedData);
        return;
    }

    public void addToJsonList(File file,ExcelField excelField){
        Map<String,Integer> requiredCells = new LinkedHashMap<>();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            int rowNUmber=0;

            for(Row nextRow:firstSheet){
                if(rowNUmber==0){
                    Iterator<Cell> cellIterator = nextRow.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if(cell.getCellType()== Cell.CELL_TYPE_STRING){
                            if(excelField.getColumnMaps().keySet().contains(cell.getStringCellValue())){
                                requiredCells.put(cell.getStringCellValue(),cell.getColumnIndex());
                            }
                        }
                    }
                }else if (rowNUmber > 1) {
                    int index = 0;
                    for (String cellName : excelField.getColumnMaps().keySet()) {
                        if (requiredCells.keySet().contains(cellName)) {
                            try{
                                int columnIndex = requiredCells.get(cellName);
                                Cell cell = nextRow.getCell(columnIndex);
                                if (lists[index] == null) {
                                    lists[index] = new JSONArray();
                                }
                                if (cell != null) {
                                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC &&
                                            excelField.getColumnMaps().get(cellName).equalsIgnoreCase("INTEGER")) {
                                        lists[index].add(new Integer((int) cell.getNumericCellValue()));
                                    } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                        lists[index].add(cell.getStringCellValue());
                                    } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                                        lists[index].add("");
                                    } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                                        lists[index].add(cell.getBooleanCellValue());
                                    } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC &&
                                            excelField.getColumnMaps().get(cellName).equalsIgnoreCase("DOUBLE")) {
                                        lists[index].add(new Double(cell.getNumericCellValue()));
                                    }
                                }
                                index++;
                            }catch (Exception er){
                                System.out.println("Error reading excel value.");
                            }
                        }

                    }
                }
                rowNUmber++;
            }
            requiredCells.clear();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        int index=0;
        JSONObject innerObject = new JSONObject();
        for(String cellName:excelField.getColumnMaps().keySet()){
            innerObject.put(cellName,lists[index]);
            lists[index]=null;
            index++;
        }
        object.put(file.getName(),innerObject);
    }
}
