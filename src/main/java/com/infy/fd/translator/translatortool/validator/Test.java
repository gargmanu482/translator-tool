package com.infy.fd.translator.translatortool.validator;

import java.io.File;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jlibs.xml.sax.XMLDocument;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

public class Test {

	public static void main(String[] pArgs) {
		try {
			//xsd file in your local
			String filename ="C:\\Users\\Malli\\Desktop\\translator-tool-master\\Documents\\ISO_pacs.009.001.09.xsd";
			// instance.
			
			final Document doc = loadXsdDocument(filename);

			// Find the docs root element and use it to find the targetNamespace
			final Element rootElem = doc.getDocumentElement();
			Node node=(Node)rootElem;
			String targetNamespace = null;
			if (rootElem != null && rootElem.getNodeName().equals("xs:schema")) {
				targetNamespace = rootElem.getAttribute("targetNamespace");
			}

			// Parse the file into an XSModel object
			XSModel xsModel = new XSParser().parse(filename);

			// Define defaults for the XML generation
			XSInstance instance = new XSInstance();
			instance.minimumElementsGenerated = 1;
			instance.maximumElementsGenerated = 1;
			instance.generateDefaultAttributes = true;
			instance.generateOptionalAttributes = true;
			instance.maximumRecursionDepth = 0;
			instance.generateAllChoices = true;
			instance.showContentModel = true;
			instance.generateOptionalElements = true;
			
			Element name=(Element)doc.getElementsByTagName("xs:element").item(0);
			QName rootElement = new QName(targetNamespace,name.getAttribute("name"));
			
			//xml file creation file
			XMLDocument sampleXml = new XMLDocument(new StreamResult("C:\\Users\\Malli\\Desktop\\translator-tool-master\\Documents\\new.xml"), true, 4, null);
			//System.out.println("Check point 3");
			instance.generate(xsModel, rootElement, sampleXml);
			System.out.println("file created");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Document loadXsdDocument(String inputName) {
		final String filename = inputName;

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setIgnoringComments(true);
		Document doc = null;

		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final File inputFile = new File(filename);
			doc = builder.parse(inputFile);
		} catch (final Exception e) {
			e.printStackTrace();
			// throw new ContentLoadException(msg);
		}
		//LOGGER.debug("Document created");
		System.out.println("Document created");
		return doc;
	}
}


