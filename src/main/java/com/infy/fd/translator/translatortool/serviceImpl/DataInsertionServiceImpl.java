package com.infy.fd.translator.translatortool.serviceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.fd.translator.translatortool.model.MappingRules;
import com.infy.fd.translator.translatortool.repositoy.MappingRulesRepository;
import com.infy.fd.translator.translatortool.service.DataInsertionService;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
import java.io.File;

@Service
public class DataInsertionServiceImpl implements DataInsertionService {
	
	@Autowired
	private MappingRulesRepository repo;
	
	@Override
	public void insertData(String[] values) {
		Map<String,String> valuesmap=parseValue();
		
		Map<String,String> insertionValues=new HashMap<>();
		for(String a:values) {
			String[] b=a.split("-->");
			for(String c:b) {
				MappingRules rules=(MappingRules) repo.findByFieldTag(c);
				if(rules!=null) {
					insertionValues.put(c, valuesmap.get(rules.getIdentification()));
					
				}
			}
				
		}
		insertValues(insertionValues);
		
		
	}
	
	public Map<String, String> parseValue() {
		Map<String, String> map = new HashMap<String, String>();
		Pattern patt = Pattern.compile("[0-9]+[/A-Za-z\\s]*:[/0-9a-zA-Z-]+");
		try {
			BufferedReader r = new BufferedReader(
					new FileReader("C:\\Users\\Malli\\git\\translator-tool\\Documents\\01. MT202 input.txt"));
			String line;
			String[] a = null;
			while ((line = r.readLine()) != null) {
				Matcher m = patt.matcher(line);
				while (m.find()) {
					int start = m.start(0);
					int end = m.end(0);
					a = line.substring(start, end).split(":");
					map.put(a[0], a[1]);
				}

			}
			r.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public void insertValues(Map<String,String> values) {
		
		try {
            //TODO - need to pass the string dynamically
            String xmlFile = "C:\\Users\\Malli\\Desktop\\translator-tool-master\\Documents\\new.xml";
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            //get the root element
            Node root = document.getFirstChild();
            
            for(String val:values.keySet()) {
            	String a="ns:"+val;
            	System.out.println(val+"===================="+values.get(val));
            	Node nodeElement = document.getElementsByTagName(a).item(0);
                nodeElement.setTextContent(values.get(val));
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult xmlResult = new StreamResult(new File(xmlFile));
            transformer.transform(domSource, xmlResult);
            System.out.println("XML data population completed...");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
	}

}
