package it.arakne.dbing.magento.xml.generator;

import it.arakne.dbing.magento.xml.generator.bean.ESKORoot;
import it.arakne.dbing.magento.xml.generator.bean.ESKOTagBean;
import it.arakne.dbing.magento.xml.generator.bean.ESKOTagList;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ESKOFileGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ESKOXmlExample();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void ESKOXmlExample2() throws ParserConfigurationException, TransformerException{
		ESKORoot.init();

		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element root = doc.createElement("Job");
		doc.appendChild(root);

		for (String rootTag : ESKORoot.getTagRootList().keySet()) {
			ESKOTagBean tagBean = ESKORoot.getTagRootList().get(rootTag);
			if (tagBean.isValid()) {

			}
			Element child = doc.createElement(rootTag);
			root.appendChild(child);
		}
		Element component1 = doc.createElement("Component1");
		for (String innerTag : ESKOTagList.getComponentTagList()) {
			Element innerChild = doc.createElement(innerTag);;
			component1.appendChild(innerChild);
		}
		root.appendChild(component1);
		Element component2 = doc.createElement("Component2");
		for (String innerTag : ESKOTagList.getComponentTagList()) {
			Element innerChild = doc.createElement(innerTag);;
			component2.appendChild(innerChild);
		}
		root.appendChild(component2);
		Element component3 = doc.createElement("Component3");
		for (String innerTag : ESKOTagList.getComponentTagList()) {
			Element innerChild = doc.createElement(innerTag);;
			component3.appendChild(innerChild);
		}
		root.appendChild(component3);

		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		//create string from xml tree
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(doc);
		trans.transform(source, result);
		String xmlString = sw.toString();

		//print xml
		System.out.println("Here's the xml:\n\n" + xmlString);
	}

	public static void ESKOXmlExample() 
			throws ParserConfigurationException, TransformerException{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element root = doc.createElement("Job");
		doc.appendChild(root);

		for (String rootTag : ESKOTagList.getRootTagList()) {
			Element child = doc.createElement(rootTag);
			root.appendChild(child);
		}
		Element component1 = doc.createElement("Component1");
		for (String innerTag : ESKOTagList.getComponentTagList()) {
			Element innerChild = doc.createElement(innerTag);;
			component1.appendChild(innerChild);
		}
		root.appendChild(component1);
		Element component2 = doc.createElement("Component2");
		for (String innerTag : ESKOTagList.getComponentTagList()) {
			Element innerChild = doc.createElement(innerTag);;
			component2.appendChild(innerChild);
		}
		root.appendChild(component2);
		Element component3 = doc.createElement("Component3");
		for (String innerTag : ESKOTagList.getComponentTagList()) {
			Element innerChild = doc.createElement(innerTag);;
			component3.appendChild(innerChild);
		}
		root.appendChild(component3);

		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		//create string from xml tree
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(doc);
		trans.transform(source, result);
		String xmlString = sw.toString();

		//print xml
		System.out.println("Here's the xml:\n\n" + xmlString);

	}


}
