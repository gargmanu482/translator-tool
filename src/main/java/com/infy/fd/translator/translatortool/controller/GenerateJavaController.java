package com.infy.fd.translator.translatortool.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
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
import org.xml.sax.SAXException;

import com.infy.fd.translator.translatortool.service.DataInsertionService;

import javassist.CannotCompileException;
import javassist.NotFoundException;


@RestController
public class GenerateJavaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LayoutController.class);

	@PostMapping(value = "/java")
	public ResponseEntity<String> generateJava(@RequestBody() String[] mappingArray)
			throws NotFoundException, CannotCompileException, IOException {
		File file = new File("C:\\Users\\Malli\\Desktop\\translator-tool-master\\ref\\XmlGenerator.java");
		// initialize File // object and // passing path as // argument
		final String xmlFilePath = "C:\\\\Users\\\\Malli\\\\Desktop\\\\translator-tool-master\\\\Documents\\\\newXml.xml";
		try {
			file.createNewFile(); // creates a new file
			FileWriter fw = new FileWriter(file);
			String classname = file.getName().split("\\.")[0];
			String allValues = "";
			for (String layout : mappingArray) {
				allValues += layout + " ";
			}
			String sourceCode = "import java.io.File;\r\n"
					+ "import javax.xml.transform.stream.StreamResult;\r\n" 
					+ "import org.apache.xerces.xs.*;\r\n"
					+ "import org.w3c.dom.Document;\r\n" 
					+ "import org.w3c.dom.Element;\r\n"
					+ "import org.w3c.dom.Node;\r\n" 
					+ "import jlibs.xml.sax.XMLDocument;\r\n"
					+ "import javax.xml.namespace.QName;\r\n" 
					+ "import javax.xml.parsers.DocumentBuilder;\r\n"
					+ "import javax.xml.parsers.DocumentBuilderFactory;\r\n" 
					+ "import jlibs.xml.xsd.XSInstance;\r\n"
					+"import java.util.HashMap;\r\n" 
					+"import java.util.regex.Pattern;\r\n" 
					+"import java.io.BufferedReader;\r\n" 
					+"import java.io.FileReader;"
					+"import java.util.Map;"
					+"import java.io.IOException;\r\n"  
					+"import javax.xml.transform.Transformer;\r\n"  
					+"import javax.xml.transform.TransformerConfigurationException;\r\n"  
					+"import javax.xml.transform.TransformerException;\r\n"  
					+"import javax.xml.transform.TransformerFactory;"
					+"import javax.xml.transform.dom.DOMSource;\r\n" 
					+"import javax.xml.parsers.ParserConfigurationException;\r\n"
					+"import org.xml.sax.SAXException;\r\n" 
					+"import jlibs.xml.xsd.XSParser;" 
					+"import java.util.regex.Matcher;\r\n"
					+ "public class " + classname + "{\r\n"
					+"public static void main(String[] pArgs) {\r\n" 
					+ "		try {\r\n"
					+ "			//xsd file in your local\r\n"
					+ "			String filename =\"C:\\\\Users\\\\Malli\\\\Desktop\\\\translator-tool-master\\\\Documents\\\\ISO_pacs.009.001.09.xsd\";\r\n"
					+ "			// instance.\r\n" 
					+ "			final Document doc = loadXsdDocument(filename);\r\n" 
					+ "			// Find the docs root element and use it to find the targetNamespace\r\n"
					+ "			final Element rootElem = doc.getDocumentElement();\r\n"
					+ "			Node node=(Node)rootElem;\r\n" 
					+ "			String targetNamespace = null;\r\n"
					+ "			if (rootElem != null && rootElem.getNodeName().equals(\"xs:schema\")) {\r\n"
					+ "				targetNamespace = rootElem.getAttribute(\"targetNamespace\");\r\n"
					+ "			}\r\n"
					+ "			// Parse the file into an XSModel object\r\n"
					+ "			XSModel xsModel = new XSParser().parse(filename);\r\n" 
					+ "			// Define defaults for the XML generation\r\n"
					+ "			XSInstance instance = new XSInstance();\r\n"
					+ "			instance.minimumElementsGenerated = 1;\r\n"
					+ "			instance.maximumElementsGenerated = 1;\r\n"
					+ "			instance.generateDefaultAttributes = true;\r\n"
					+ "			instance.generateOptionalAttributes = true;\r\n"
					+ "			instance.maximumRecursionDepth = 0;\r\n"
					+ "			instance.generateAllChoices = true;\r\n"
					+ "			instance.showContentModel = true;\r\n"
					+ "			instance.generateOptionalElements = true;\r\n"
					+ "			Element name=(Element)doc.getElementsByTagName(\"xs:element\").item(0);\r\n"
					+ "			QName rootElement = new QName(targetNamespace,name.getAttribute(\"name\"));\r\n"
					+ "			//xml file creation file\r\n"
					+ "			XMLDocument sampleXml = new XMLDocument(new StreamResult(\"C:\\\\Users\\\\Malli\\\\Desktop\\\\translator-tool-master\\\\Documents\\\\new.xml\"), true, 4, null);\r\n"
					+"			sampleXml.getNamespaceSupport().setSuggestPrefix(\"\");\r\n"
					+ "			instance.generate(xsModel, rootElement, sampleXml);\r\n"
					+ "			System.out.println(\"file created\");\r\n"
					+"			String mappings=\""+allValues+"\";"
					+"			String[] a=mappings.split(\" \");"
								+classname+" b=new "+classname+"();\r\n"
					+"			b.insertData(a);"
					+ "		} catch (TransformerConfigurationException e) {\r\n"
					+ "			// TODO Auto-generated catch block\r\n"
					+ "			e.printStackTrace();\r\n"
					+ "		}\r\n" 
					+ "	}" 
					+ "public static Document loadXsdDocument(String inputName) {\r\n"
					+ "		final String filename = inputName;\r\n" 
					+ "		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();\r\n"
					+ "		factory.setValidating(false);\r\n"
					+ "		factory.setIgnoringElementContentWhitespace(true);\r\n"
					+ "		factory.setIgnoringComments(true);\r\n" 
					+ "		Document doc = null;\r\n" 
					+ "		try {\r\n" 
					+ "			final DocumentBuilder builder = factory.newDocumentBuilder();\r\n"
					+ "			final File inputFile = new File(filename);\r\n"
					+ "			doc = builder.parse(inputFile);\r\n" 
					+ "		} catch (final Exception e) {\r\n"
					+ "			e.printStackTrace();\r\n" 
					+ "			// throw new ContentLoadException(msg);\r\n"
					+ "		}\r\n" 
					+ "		//LOGGER.debug(\"Document created\");\r\n"
					+ "		System.out.println(\"Document created\");\r\n" 
					+ "		return doc;\r\n" 
					+ "	}" 
					+"public Map<String, String> parseValue() {\r\n" + 
					"Map<String, String> map = new HashMap<String, String>();\r\n" + 
					"		Pattern patt = Pattern.compile(\"[:]?[0-9]+[/A-Za-z\\\\s]*:[/0-9a-zA-Z-]+\");\r\n" + 
					"		try {\r\n" + 
					"			BufferedReader r = new BufferedReader(\r\n" + 
					"					new FileReader(\"C:\\\\Users\\\\Malli\\\\git\\\\translator-tool\\\\Documents\\\\01. MT202 input.txt\"));\r\n" + 
					"			String line;\r\n" + 
					"			String[] a = null;\r\n" + 
					"			while ((line = r.readLine()) != null) {\r\n" + 
					"				Matcher m = patt.matcher(line);\r\n" + 
					"				while (m.find()) {\r\n" + 
					"					int start = m.start(0);\r\n" + 
					"					int end = m.end(0);\r\n" + 
					"					String b = line.substring(start, end);\r\n" + 
					"					if(b.startsWith(\":\")) {\r\n" + 
					"						a=line.substring(start+1, end).split(\":\");\r\n" + 
					"						map.put(\":\"+a[0]+\":\", a[1]);\r\n" + 
					"					}else {\r\n" + 
					"						a=line.substring(start, end).split(\":\");\r\n" + 
					"						map.put(a[0]+\":\", a[1]);\r\n" + 
					"					}\r\n" + 
					"				}\r\n" + 
					"\r\n" + 
					"			}\r\n" + 
					"			r.close();\r\n" + 
					"		} catch (IOException ie) {\r\n" + 
					"			ie.printStackTrace();\r\n" + 
					"		} catch (Exception e) {\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		}\r\n" + 
					"		for(String a:map.keySet()) {\r\n" + 
					"			System.out.println(a+\"..............................\"+map.get(a));\r\n" + 
					"		}\r\n" + 				"		return map;\r\n" + 
					"	}"+
					"public void insertData(String[] mappingValues) {\r\n" + 
					"\r\n" + 
					"		Map<String, String> valuesmap = parseValue();\r\n" + 
					"\r\n" + 
					"		Map<String, String> insertionValues = new HashMap<>();\r\n" + 
					"		for (String mapping : mappingValues) {\r\n" + 
					"			String[] separatedTags = mapping.split(\"->\");\r\n" + 
					"			for (int i=0;i<separatedTags.length-1;i+=2) {\r\n" + 
					"				insertionValues.put(separatedTags[i], valuesmap.get(separatedTags[i+1]));\r\n" + 
					"				System.out.println(separatedTags[i]+\"------------ -----------------\"+separatedTags[i+1]);\r\n" + 
					"			}\r\n" + 
					"\r\n" + 
					"		}\r\n" + 
					"		insertValues(insertionValues);\r\n" + 
					"\r\n" + 
					"	}"+
					"public void insertValues(Map<String, String> values) {\r\n" + 
					"\r\n" + 
					"		try {\r\n" + 
					"			// TODO - need to pass the string dynamically\r\n" + 
					"			String xmlFile = \"C:\\\\Users\\\\Malli\\\\Desktop\\\\translator-tool-master\\\\Documents\\\\new.xml\";\r\n" + 
					"			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();\r\n" + 
					"			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();\r\n" + 
					"			Document document = documentBuilder.parse(xmlFile);\r\n" + 
					"			\r\n" + 
					"\r\n" + 
					"			for (String val : values.keySet()) {\r\n" + 
					"				\r\n" + 
					"				System.out.println(val+\"=====================\"+values.get(val));\r\n" + 
					"				Node nodeElement = document.getElementsByTagName(val).item(0);\r\n" + 
					"				System.out.println(\"****************************\"+nodeElement);\r\n" + 
					"				nodeElement.setTextContent(values.get(val));\r\n" + 
					"			}\r\n" + 
					"\r\n" + 
					"			TransformerFactory transformerFactory = TransformerFactory.newInstance();\r\n" + 
					"			Transformer transformer = transformerFactory.newTransformer();\r\n" + 
					"			DOMSource domSource = new DOMSource(document);\r\n" + 
					"			StreamResult xmlResult = new StreamResult(new File(xmlFile));\r\n" + 
					"			transformer.transform(domSource, xmlResult);\r\n" + 
					"			System.out.println(\"XML data population completed...\");\r\n" + 
					"		} catch (ParserConfigurationException e) {\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		} catch (SAXException e) {\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		} catch (IOException e) {\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		} catch (TransformerConfigurationException e) {\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		} catch (TransformerException e) {\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		}\r\n" + 
					"	}"+
					"}";
					

			fw.write(sourceCode);
			fw.close();
			LOGGER.debug("file generated successfully");
		} catch (IOException e) {
			e.printStackTrace(); // prints exception if any
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);

	}
	

}
