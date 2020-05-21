package com.infy.fd.translator.translatortool.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.infy.fd.translator.translatortool.model.Field;
import com.infy.fd.translator.translatortool.model.Layout;
import com.infy.fd.translator.translatortool.service.LayoutService;
import com.infy.fd.translator.translatortool.validator.Validator;

@RestController
public class LayoutController {
	
	@Autowired
	private LayoutService layoutService;

	@Autowired
	Validator validator;

	private static final Logger LOGGER = LoggerFactory.getLogger(LayoutController.class);

	@PostMapping(value = "/layout")
	public ResponseEntity<Layout> saveLayout(@RequestParam("uploadfile") MultipartFile file,
			@RequestParam("clientName") String clientName, @RequestParam("layoutName") String layoutName)
			throws Exception {
		LOGGER.debug("REST request to save layout : {}");
		Field field = new Field();
		final String UPLOAD_FOLDER = "c:/uploadfile/";
		XSSFWorkbook workbook = null;
		Layout layout = new Layout();
		layout.setName(layoutName);
		layout.setClient(clientName);
		String fileName = file.getOriginalFilename();
		if (!validator.validateLayoutObject(layout) || file.isEmpty())
			return ResponseEntity.badRequest().header("Failure", "Invalid Layout object").body(null);

		List<Field> fields = new ArrayList<Field>();

		try {
			
			File theDir = new File(UPLOAD_FOLDER);
			if (!theDir.exists()) {
				theDir.mkdir();
			}
			String fileExtension = getFileExtension(fileName);
			field.setFileExtension(fileExtension);
			if (fileExtension.equals("xsd")) {
				fields = translateXsd(file, fields);
			} else {
				workbook = new XSSFWorkbook(file.getInputStream());
				fields = mapExcelLayout(workbook, fields, fileName);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();

		} catch (IOException ed) {
			System.out.println("hello");
			ed.printStackTrace();
		}

		layout.setFields(fields);
		layout = layoutService.saveLayout(layout);
		return ResponseEntity.created(new URI("/layout/" + layout.getLayoutId())).body(layout);
	}

	private List<Field> translateXsd(MultipartFile file, List<Field> fields)
			throws ParserConfigurationException, SAXException, IOException {

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setIgnoringComments(true);
	
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse (file.getInputStream()); 
		try {
			
			NodeList list = doc.getElementsByTagName("xs:element");
			
			// loop to print data
			for (int i = 1; i < list.getLength(); i++) {
				Field field = new Field();
				Element first = (Element) list.item(i);
				if (first.hasAttributes()) {
					String nm = first.getAttribute("name");
					field.setName(nm);

					// System.out.println(nm);
					String nm1 = first.getAttribute("type");
					field.setTagName(nm1);
					// System.out.println(nm1);
					String nm2 = first.getAttribute("minOccurs");
					field.setMinOccurs(nm2);
					field.setIsOutputfile(getIOFileName(file.getOriginalFilename()));
					field.setFileExtension("xsd");
					
				}
				fields.add(field);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return fields;

	}

	private boolean getIOFileName(String fileName) {
		
		String fileContain = "out";
		boolean  isOutputfile = false;
		if(fileName.contains(fileContain))
		{
			isOutputfile = true;
		}
		System.out.println("FileName"+fileName);
		return isOutputfile; 
	}

	@GetMapping(value = "/layout/{id}")
	public ResponseEntity<Layout> getLayout(@PathVariable("id") String id) throws Exception {
		LOGGER.debug("REST request to get layout : {}", id);
		Optional<Layout> layout = layoutService.findLayoutById(id);
		if (layout.isPresent())
			return new ResponseEntity<Layout>(layout.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Layout>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/client/all")
	public ResponseEntity<List<String>> getAllClient() throws Exception {
		LOGGER.debug("REST request to get list of client");
		List<String> clientList = layoutService.getAllClient();
		if (clientList.size() > 0)
			return new ResponseEntity<List<String>>(clientList, HttpStatus.OK);
		else
			return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/client/{client}/layouts")
	public ResponseEntity<List<Layout>> getLayoutByClient(@PathVariable("client") String client) throws Exception {
		LOGGER.debug("REST request to get layout by client name : {}", client);
		List<Layout> layoutList = layoutService.findLayoutByClient(client.toUpperCase());
		if (layoutList.size() > 0) {

			return new ResponseEntity<List<Layout>>(layoutList, HttpStatus.OK);
		} else
			return new ResponseEntity<List<Layout>>(HttpStatus.NOT_FOUND);
	}

	private List<Field> mapExcelLayout(XSSFWorkbook workbook, List<Field> fields, String fileName) {
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			String fieldLengthStr = "";
			Field field = new Field();
			XSSFRow row = worksheet.getRow(i);
			if (row != null) {

				fieldLengthStr = validator.getCellValue(row.getCell(3)).equals("") ? "0"
						: validator.getCellValue(row.getCell(3));
				field.setName(validator.getCellValue(row.getCell(0)));
				field.setTagName(validator.getCellValue(row.getCell(1)));
				field.setIdentification(validator.getCellValue(row.getCell(2)));
				field.setLength(Integer.valueOf(fieldLengthStr));
				field.setIsOutputfile(getIOFileName(fileName));
				field.setFileExtension("xlsx");
			}

			if (!validator.validateFieldObject(field))
				continue;

			fields.add(field);
			// writeFromExcel(fields,worksheet,fileName);

		}
		return fields;
	}

	public String getFileExtension(String filename) {
		String extension = "";

		int i = filename.lastIndexOf('.');
		if (i >= 0) {
			extension = filename.substring(i + 1);
		}
		return extension;
	}

	
}
