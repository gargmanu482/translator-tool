package com.infy.fd.translator.translatortool.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xerces.xs.XSModel;
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

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

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
			String uploadedFileLocation = UPLOAD_FOLDER + file.getOriginalFilename();
			String fileExtension = getFileExtension(fileName);
			if (fileExtension.equals("xsd")) {
				translateXsd(file, fields, uploadedFileLocation);
			} else {
				workbook = new XSSFWorkbook(file.getInputStream());
				mapExcelLayout(workbook, fields, fileName);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();

		} catch (IOException ed) {
			ed.printStackTrace();
		}

		layout.setFields(fields);
		layout = layoutService.saveLayout(layout);
		return ResponseEntity.created(new URI("/layout/" + layout.getLayoutId())).body(layout);
	}

	private Document translateXsd(MultipartFile file, List<Field> fields, String uploadedFileLocation)
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
				}
				fields.add(field);
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		writeFromXsd(file,uploadedFileLocation,doc);
		return doc;

	}

	private void writeFromXsd(MultipartFile file, String fileLocation, Document doc) {
		try {
			Path path = Paths.get(fileLocation);
			Path fileName = path.getFileName();
			final Element rootElem = doc.getDocumentElement();
			String targetNamespace = null;
			if (doc != null && doc.getNodeName().equals("xs:schema")) {
				targetNamespace = rootElem.getAttribute("targetNamespace");
			}
			
			saveToFile(file.getInputStream(), fileLocation);
			// Parse the file into an XSModel object
			// Define defaults for the XML generation
			XSInstance instance = new XSInstance();
			instance.minimumElementsGenerated = 1;
			instance.maximumElementsGenerated = 1;
			instance.generateDefaultAttributes = true;
			instance.generateOptionalAttributes = true;
			instance.maximumRecursionDepth = 0;
			//instance.generateDefaultElementValues = Boolean.TRUE;
			instance.generateAllChoices = true;
			instance.showContentModel = true;
			instance.generateOptionalElements = true;
			QName rootElement = new QName(targetNamespace, " ");
			// QName rootElement = new QName("root");
			 XSModel xsModel = new XSParser().parse(fileLocation);
			 XMLDocument sampleXml = new XMLDocument(new StreamResult(System.out), true, 4, null);
			instance.generate(xsModel, rootElement, sampleXml);
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}public static void main(String[] args) {
		
	}
	private void saveToFile(InputStream inStream, String target)
			throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
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

	private void mapExcelLayout(XSSFWorkbook workbook, List<Field> fields, String fileName) {
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
			}

			if (!validator.validateFieldObject(field))
				continue;

			fields.add(field);
			// writeFromExcel(fields,worksheet,fileName);

		}
	}

	private void writeFromExcel(List<Field> fields, Sheet sheet, String fileName) {
		// TODO Auto-generated method stub
		try {
			FileWriter fostream;
			String strOutputPath = "D:\\InfyWorkspace\\Current_\\";
			PrintWriter out = null;
			boolean firstRow = true;

			for (Row row : sheet) {

				if (firstRow == true) {

					firstRow = false;

					continue;

				}
				fostream = new FileWriter(new File(strOutputPath + File.separator + row.getCell(4) + ".xml"));

				System.out.println("try1" + strOutputPath + File.separator + row.getCell(4) + ".xml");

				out = new PrintWriter(new BufferedWriter(fostream));

				out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

				out.println("<FICdtTrf>");

				out.println("\t<FICdtTrf>");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getFileExtension(String filename) {
		String extension = "";

		int i = filename.lastIndexOf('.');
		if (i >= 0) {
			extension = filename.substring(i + 1);
		}
		return extension;
	}

	private static String formatElement(String prefix, String tag, String value) {

		StringBuilder sb = new StringBuilder(prefix);

		sb.append("<");

		sb.append(tag);

		if (value != null && value.length() > 0) {

			sb.append(">");

			sb.append(value);

			sb.append("</");

			sb.append(tag);
			sb.append(">");

		} else {

			sb.append(">");

			sb.append("<");

			sb.append(tag);

			sb.append("/>");

		}

		return sb.toString();

	}
}
