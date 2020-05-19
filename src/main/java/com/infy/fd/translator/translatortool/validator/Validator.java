package com.infy.fd.translator.translatortool.validator;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.stereotype.Service;

import com.infy.fd.translator.translatortool.model.Field;
import com.infy.fd.translator.translatortool.model.Layout;
@Service
public class Validator {
	
	public boolean validateLayoutObject(Layout layout) {
		if (layout.getClient().equals("") || layout.getClient().equals(null) || layout.getName().equals("")
				|| layout.getName().equals(""))
			return false;

		return true;
	}

	public boolean validateFieldObject(Field field) {
		if (field.getTagName().equals("") || field.getTagName().equals(null) )
			return false;

		return true;
	}

	public String getCellValue(XSSFCell cell) {
		if (cell == null) {
			return "";
		} else {
			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC)
				return String.valueOf((int) cell.getNumericCellValue());
			else
				return "";
		}
	}

}
