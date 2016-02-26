/**
 * Created by sachin on 2/26/2016.
 */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

        readFromExcel();
        writeToExcel();
    }
    public static void readFromExcel(){
        String excelFilePath = "Read.xlsx";
        FileInputStream inputStream = null;
        try {
            System.out.println("Reading Excel");
            inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);

            for (Row nextRow : firstSheet) {
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue());
                            break;
                    }
                    System.out.print(" - ");
                }
                System.out.println();
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeToExcel(){
        System.out.println("Writing to Excel");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Friends");

        Object[][] bookData = {
                {"Sachin", "Aryal", 211},
                {"Anil", "Parajuli", 201}};

        int rowCount = 0;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }
        try (FileOutputStream outputStream = new FileOutputStream("Write.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
