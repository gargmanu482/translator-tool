package com.infy.fd.translator.translatortool.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.infy.fd.translator.translatortool.service.DataInsertionService;
import com.infy.fd.translator.translatortool.service.MappingValidationService;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

@RestController
public class MappingValidationController {

	@Autowired
	private MappingValidationService mappingService;
	
	@Autowired
	private DataInsertionService dataInsertionService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LayoutController.class);

	@PostMapping(value = "/validateMapping")
	public ResponseEntity<String> validateMappings(@RequestParam("inputName") String input,
			@RequestParam("outputName") String output) {

		LOGGER.debug("REST request to Mapping validation");
		try {
			if (mappingService.validateMappings(input, output)) {
				return new ResponseEntity<String>("correct mapping", HttpStatus.OK);
			} else {
				throw new Exception("Invalid Mapping");
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/java")
	public ResponseEntity<String> generateJava(@RequestBody() String[] layoutArray) {
		
		  File file = new File("C:\\Users\\Malli\\Desktop\\translator-tool-master\\ref\\XmlGenerator.java");
		  // initialize File // object and // passing path as // argument 
		  final String xmlFilePath ="C:\\\\Users\\\\Malli\\\\Desktop\\\\translator-tool-master\\\\Documents\\\\newXml.xml";
		  try { file.createNewFile(); // creates a new file 
		  FileWriter fw = new FileWriter(file); String classname = file.getName().split("\\.")[0]; 
		  String allValues = ""; for (String layout : layoutArray) { allValues += layout +
		  " "; } String sourceCode = "import java.io.File;\r\n" + 
		  		"import javax.xml.transform.TransformerConfigurationException;\r\n" + 
		  		"import javax.xml.transform.stream.StreamResult;\r\n" + 
		  		"import org.apache.xerces.xs.*;\r\n" + 
		  		"import org.w3c.dom.Document;\r\n" + 
		  		"import org.w3c.dom.Element;\r\n" + 
		  		"import org.w3c.dom.Node;\r\n" + 
		  		"import jlibs.xml.sax.XMLDocument;\r\n" + 
		  		"import javax.xml.namespace.QName;\r\n" + 
		  		"import javax.xml.parsers.DocumentBuilder;\r\n" + 
		  		"import javax.xml.parsers.DocumentBuilderFactory;\r\n" + 
		  		"import jlibs.xml.xsd.XSInstance;\r\n" + 
		  		"import jlibs.xml.xsd.XSParser;"+
		  "public class " + classname + "{\r\n" +
		  "public static void main(String[] pArgs) {\r\n" + 
		  "		try {\r\n" + 
		  "			//xsd file in your local\r\n" + 
		  "			String filename =\"C:\\\\Users\\\\Malli\\\\Desktop\\\\translator-tool-master\\\\Documents\\\\ISO_pacs.009.001.09.xsd\";\r\n" + 
		  "			// instance.\r\n" + 
		  "			\r\n" + 
		  "			final Document doc = loadXsdDocument(filename);\r\n" + 
		  "\r\n" + 
		  "			// Find the docs root element and use it to find the targetNamespace\r\n" + 
		  "			final Element rootElem = doc.getDocumentElement();\r\n" + 
		  "			Node node=(Node)rootElem;\r\n" + 
		  "			String targetNamespace = null;\r\n" + 
		  "			if (rootElem != null && rootElem.getNodeName().equals(\"xs:schema\")) {\r\n" + 
		  "				targetNamespace = rootElem.getAttribute(\"targetNamespace\");\r\n" + 
		  "			}\r\n" + 
		  "\r\n" + 
		  "			// Parse the file into an XSModel object\r\n" + 
		  "			XSModel xsModel = new XSParser().parse(filename);\r\n" + 
		  "\r\n" + 
		  "			// Define defaults for the XML generation\r\n" + 
		  "			XSInstance instance = new XSInstance();\r\n" + 
		  "			instance.minimumElementsGenerated = 1;\r\n" + 
		  "			instance.maximumElementsGenerated = 1;\r\n" + 
		  "			instance.generateDefaultAttributes = true;\r\n" + 
		  "			instance.generateOptionalAttributes = true;\r\n" + 
		  "			instance.maximumRecursionDepth = 0;\r\n" + 
		  "			instance.generateAllChoices = true;\r\n" + 
		  "			instance.showContentModel = true;\r\n" + 
		  "			instance.generateOptionalElements = true;\r\n" + 
		  "			\r\n" + 
		  "			Element name=(Element)doc.getElementsByTagName(\"xs:element\").item(0);\r\n" + 
		  "			QName rootElement = new QName(targetNamespace,name.getAttribute(\"name\"));\r\n" + 
		  "			\r\n" + 
		  "			//xml file creation file\r\n" + 
		  "			XMLDocument sampleXml = new XMLDocument(new StreamResult(\"C:\\\\Users\\\\Malli\\\\Desktop\\\\translator-tool-master\\\\Documents\\\\new.xml\"), true, 4, null);\r\n" + 
		  "			//System.out.println(\"Check point 3\");\r\n" + 
		  "			instance.generate(xsModel, rootElement, sampleXml);\r\n" + 
		  "			System.out.println(\"file created\");\r\n" + 
		  "		} catch (TransformerConfigurationException e) {\r\n" + 
		  "			// TODO Auto-generated catch block\r\n" + 
		  "			e.printStackTrace();\r\n" + 
		  "		}\r\n" + 
		  "	}"+
		  "public static Document loadXsdDocument(String inputName) {\r\n" + 
		  "		final String filename = inputName;\r\n" + 
		  "\r\n" + 
		  "		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();\r\n" + 
		  "		factory.setValidating(false);\r\n" + 
		  "		factory.setIgnoringElementContentWhitespace(true);\r\n" + 
		  "		factory.setIgnoringComments(true);\r\n" + 
		  "		Document doc = null;\r\n" + 
		  "\r\n" + 
		  "		try {\r\n" + 
		  "			final DocumentBuilder builder = factory.newDocumentBuilder();\r\n" + 
		  "			final File inputFile = new File(filename);\r\n" + 
		  "			doc = builder.parse(inputFile);\r\n" + 
		  "		} catch (final Exception e) {\r\n" + 
		  "			e.printStackTrace();\r\n" + 
		  "			// throw new ContentLoadException(msg);\r\n" + 
		  "		}\r\n" + 
		  "		//LOGGER.debug(\"Document created\");\r\n" + 
		  "		System.out.println(\"Document created\");\r\n" + 
		  "		return doc;\r\n" + 
		  "	}"+
		  "}";
		  
		  fw.write(sourceCode); 
		  dataInsertionService.insertData(layoutArray);
		  fw.close();
		  LOGGER.debug("file generated successfully");
		  } catch (IOException e) {
		  e.printStackTrace(); // prints exception if any 
		  return new ResponseEntity<String>(HttpStatus.BAD_REQUEST); 
		  } 
		  return new ResponseEntity<String>(HttpStatus.OK);
		  }
		 
		
		

	}
