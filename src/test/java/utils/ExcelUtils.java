package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	/*
	 * ================================================= READ INPUT
	 * =================================================
	 */
	public static Map<String, String> readInput(String filePath, String sheetName) throws Exception {

		Map<String, String> dataMap = new HashMap<>();

		try (FileInputStream fis = new FileInputStream(filePath); Workbook wb = new XSSFWorkbook(fis)) {

			Sheet sheet = wb.getSheet(sheetName);

			Row headerRow = sheet.getRow(0);
			Row dataRow = sheet.getRow(1);

			for (int i = 0; i < headerRow.getLastCellNum(); i++) {

				String key = headerRow.getCell(i).getStringCellValue().trim();
				String value = dataRow.getCell(i).getStringCellValue().trim();

				dataMap.put(key, value);
			}
		}

		return dataMap;
	}

	/*
	 * ================================================= WRITE RESULT (PERFECT
	 * ALIGNMENT + AUTO ID) =================================================
	 */
	public static void writeResultWithAutoId(String filePath, String sheetName, List<String[]> results)
			throws Exception {

		Workbook wb;
		Sheet sheet;

		File file = new File(filePath);

		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			fis.close();
		} else {
			wb = new XSSFWorkbook();
		}

		sheet = wb.getSheet(sheetName);

		if (sheet == null) {
			sheet = wb.createSheet(sheetName);
		}

		String[] headers = { "Id","", "Vacancy", "Candidate", "Hiring Manager", "Date of Application", "Status" };

		/*
		 * ================================================= FORCE HEADER FIX
		 * (IMPORTANT) Always create header at row 0, col 0
		 * =================================================
		 */
		Row headerRow = sheet.getRow(0);

		if (headerRow == null) {
			headerRow = sheet.createRow(0);
		}

		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.getCell(i);
			if (cell == null)
				cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}

		/*
		 * ================================================= FIND NEXT ROW CORRECTLY
		 * =================================================
		 */
		int nextRow = sheet.getLastRowNum() + 1;

		if (nextRow == 0)
			nextRow = 1;

		/*
		 * ================================================= AUTO ID BASED ON EXISTING
		 * ROWS =================================================
		 */
		int id = nextRow;

		/*
		 * ================================================= WRITE DATA
		 * =================================================
		 */
		for (String[] rowData : results) {

			Row row = sheet.createRow(nextRow++);

			row.createCell(0).setCellValue(id++);

			for (int i = 0; i < rowData.length; i++) {
				row.createCell(i + 1).setCellValue(rowData[i]);
			}
		}

		/*
		 * ================================================= AUTO SIZE
		 * =================================================
		 */
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		fos.close();
		wb.close();
	}
}

//
//package utils;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import java.io.*;
//import java.util.*;
//
//public class ExcelUtils {
//
//    public static Map<String, String> readInput(String filePath, String sheetName) throws Exception {
//        FileInputStream fis = new FileInputStream(filePath);
//        Workbook wb = new XSSFWorkbook(fis);
//        Sheet sheet = wb.getSheet(sheetName);
//
//        Map<String, String> dataMap = new HashMap();
//                Row headerRow = sheet.getRow(0);
//        Row dataRow = sheet.getRow(1);
//
//        if (headerRow != null && dataRow != null) {
//            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
//                String key = headerRow.getCell(i).getStringCellValue().trim();
//                String value = dataRow.getCell(i).getStringCellValue().trim();
//                dataMap.put(key, value);
//            }
//        }
//
//        wb.close();
//        fis.close();
//        return dataMap;
//    }
//
//
//    
//    public static void writeResult(String filePath, String sheetName, List<String[]> results) throws Exception {
//
//        Workbook wb;
//        File file = new File(filePath);
//
//        if (file.exists()) {
//            FileInputStream fis = new FileInputStream(file);
//            wb = new XSSFWorkbook(fis);
//            fis.close();
//        } else {
//            wb = new XSSFWorkbook();
//        }
//
//        Sheet sheet = wb.getSheet(sheetName);
//        if (sheet == null) sheet = wb.createSheet(sheetName);
//
//        String[] headers = { "Id","Vacancy", "Candidate", "Hiring Manager", "Date of Application", "Status"};
//        Row headerRow = sheet.createRow(0);
//
//        for (int i = 0; i < headers.length; i++) {
//            headerRow.createCell(i).setCellValue(headers[i]);
//        }
//
//        int rowNum = 1;
//        for (String[] rowData : results) {
//            Row row = sheet.createRow(rowNum++);
//            for (int i = 0; i < rowData.length; i++) {
//                row.createCell(i).setCellValue(rowData[i]);
//            }
//        }
//
//        FileOutputStream fos = new FileOutputStream(filePath);
//        wb.write(fos);
//        fos.close();
//        wb.close();
//    }
//
//}
//
