package com.infy.fd.translator.translatortool.controller;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.*;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import jxl.Workbook.*;

class ExcelTooXml {

	private static Log logger = LogFactory.getLog(ExcelTooXml.class);

	InputStream spreadsheet;
	private static HSSFWorkbook workbook;
	private static HSSFSheet sheet;
	private static HSSFRow row;
	private static HSSFCell cell;

	final String strFNFE = "FileNotFoundException";
	final String strIOE = "IOException";
	final String strWk = "Excel Workbook cannot be opened";

	public ExcelTooXml() {

	}

	boolean IsExcelWorkBookOpen()
        {
        boolean bIsExcelWorkBookOpen = true;
        try
        {
        spreadsheet = new FileInputStream("D:\Documents and Settings\murali\Desktop\exceltoxml\BEAM_Location_data.xls");
        workbook = new HSSFWorkbook(spreadsheet);
        }
        catch (FileNotFoundException fnfe)
        {
        errorMessage(strFNFE);
        bIsExcelWorkBookOpen = false;
        }
        catch (IOException ioe)
        {
        errorMessage(strIOE);
        bIsExcelWorkBookOpen = false;
        }
 
        return bIsExcelWorkBookOpen;
        }

	void errorMessage(String strMessage) {
		logger.error("ERROR MESSAGE" + strMessage);
	}

	String ProcessRow(int nNoOfRows) {
		for (int nLoop = 0; nLoop < nNoOfRows; nLoop++) {
			row = sheet.getRow(nLoop);
			if (row != null) {
				ProcessCell(row.getLastCellNum());
				System.out.println();
			}
		}
		return null;
	}

	String ProcessCell(int nNoOfCells) {
		for (int nLoop = 0; nLoop < nNoOfCells; nLoop++) {
			cell = row.getCell((int) nLoop);

			if (cell != null) {
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_BLANK: {
					break;
				}
				case HSSFCell.CELL_TYPE_BOOLEAN: {
					System.out.print(cell.getBooleanCellValue() + " ");
					break;
				}
				case HSSFCell.CELL_TYPE_ERROR: {
					System.out.print(cell.getErrorCellValue() + " ");
					break;
				}
				case HSSFCell.CELL_TYPE_FORMULA: {
					// System.out.print(cell. + " ");
					break;
				}
				case HSSFCell.CELL_TYPE_NUMERIC: {
					System.out.print(cell.getNumericCellValue() + " ");
					break;
				}
				case HSSFCell.CELL_TYPE_STRING: {
					System.out.print(cell.getRichStringCellValue() + " ");
					break;
				}
				default: {

				}
				}

			}
		}
		return null;

	}
}

public class ExcelToXml {
	public static void main(String[] args) {
		ExcelTooXml ext = new ExcelTooXml();
		ext.IsExcelWorkBookOpen();
		ext.ProcessRow(20);
		ext.ProcessCell(40);
		System.out.println("Xml format has been generated");
	}
}