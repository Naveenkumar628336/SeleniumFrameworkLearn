package com.automationlearn.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private Workbook workbook;
    private Sheet sheet;

    // Constructor — open Excel file and select sheet
    public ExcelReader(String filePath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(filePath);

            // XSSFWorkbook → for .xlsx files
            // HSSFWorkbook → for .xls files
            workbook = new XSSFWorkbook(fis);

            // Select the sheet by name
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException(
                    "Sheet not found: " + sheetName);
            }

            System.out.println("✅ Excel loaded: "
                + filePath + " | Sheet: " + sheetName);

        } catch (IOException e) {
            throw new RuntimeException(
                "Cannot open Excel: " + filePath, e);
        }
    }

    // Get total number of rows
    // -1 because first row is header
    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    // Get total number of columns in first row
    public int getColumnCount() {
        return sheet.getRow(0).getLastCellNum();
    }

    // Get cell value as String
    // rowNum → 0 = first row (header)
    //          1 = second row (first data row)
    // colNum → 0 = first column, 1 = second column
    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return "";

        Cell cell = row.getCell(colNum);
        if (cell == null) return "";

        // Handle different cell types
        if (cell.getCellType() == CellType.NUMERIC) {
            // Return number without decimal point
            return String.valueOf(
                (long) cell.getNumericCellValue());
        } else {
            return cell.getStringCellValue().trim();
        }
    }

    // Get all data as 2D array
    // Ready to use with @DataProvider
    public Object[][] getAllData() {

        // Row count excludes header row
        int rows = getRowCount();
        int cols = getColumnCount();

        Object[][] data = new Object[rows][cols];

        // Start from row 1 — skip header row 0
        for (int i = 1; i <= rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i - 1][j] = getCellData(i, j);
            }
        }
        return data;
    }

    // Close workbook after use
    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            System.out.println("Error closing Excel: "
                + e.getMessage());
        }
    }
}