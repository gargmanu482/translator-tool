package com.infy.fd.translator.translatortool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infy.fd.translator.translatortool.model.MappingRules;
import com.infy.fd.translator.translatortool.repositoy.MappingRulesRepository;

@Component
public class MappingController {
	
	@Autowired
	public MappingRulesRepository mapRepo;
		
	
	public void saveMap() {
		// TODO Auto-generated method stub
		  File file= new File("C:\\Users\\user\\git\\translator-tool\\Documents\\MT202topacs009mapping02.xlsx"); 
		  FileInputStream inputStream = null; 
		  mapRepo.deleteAll();
		  try{ 
			  inputStream = new FileInputStream(file); 
			  XSSFWorkbook workbook = new XSSFWorkbook(inputStream); 
			  XSSFSheet worksheet = workbook.getSheetAt(0);
			  Iterator<Row> rowIterator = worksheet.iterator();
			  rowIterator.next();
			  rowIterator.next();
		
			  
			  while (rowIterator.hasNext()) {
				  Row nextRow = rowIterator.next();
				  Iterator<Cell> cellIterator = nextRow.cellIterator();
				  String xmlTag="";
				  String identificationID="";
				  String field="";
				  String rule="";
				  
				  if(cellIterator.hasNext() && nextRow.getCell(0).getStringCellValue()=="")
					  break;
				  
				  while (cellIterator.hasNext()) {
					  Cell nextCell = cellIterator.next();
					  int columnIndex = nextCell.getColumnIndex();
					  switch (columnIndex) {
					  	case 1:
					  		xmlTag=nextCell.getStringCellValue();
					  		xmlTag=xmlTag.substring(1, xmlTag.length()-1);
					  		//System.out.println(xmlTag);
					  		break;
					  	case 4:
					  		if (nextCell.getCellType() == CellType.NUMERIC)
					  			identificationID= String.valueOf((int) nextCell.getNumericCellValue());
							else
								identificationID= nextCell.getStringCellValue();
								
					  		//System.out.println(identificationID);
					  		break;
					  	case 5:
					  		field=nextCell.getStringCellValue();
					  		//System.out.println(field);
					  		break;
					  	case 7:
					  		rule=nextCell.getStringCellValue();
					  		//System.out.println(rule);
					  		break;
					  
					  }
					 
				  }
				  if(xmlTag!="" && identificationID!="" && field!="") {
					  
					  MappingRules mapRule=new MappingRules();
					  mapRule.setFieldName(field);
					  mapRule.setFieldTag(xmlTag);
					  mapRule.setIdentification(identificationID);
					  mapRule.setDescription(rule);
					  //System.out.println(mapRule.getDescription()+" "+mapRule.getFieldName()+" "+mapRule.getFieldTag()+" "+mapRule.getIdentification());
					  mapRepo.save(mapRule);
					  List<MappingRules> mpRule=mapRepo.findByFieldNameOrFieldTag(field,xmlTag);
					  for (MappingRules mappingRules : mpRule) {
						  System.out.println(mappingRules);
					}
					  

				  }
				 
			  }
				  
			  workbook.close();
		  } 

			 catch (FileNotFoundException e) { // TODO Auto-generated catch block
	    	 e.printStackTrace(); 
	     } //XSSFWorkbook workbook =new XSSFWorkbook(fileName);
		 catch (IOException e) { // TODO Auto-generated catch block
			 e.printStackTrace(); 
		 } 
		
	    
	}

}
