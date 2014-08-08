package it.arakne.dbing.magento.xml.transform;

import it.arakne.dbing.magento.runner.esko.EskoExporter;

import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLTransformer {

	private final static Logger LOGGER = Logger.getLogger(EskoExporter.class.getName());

	public static void main(String[] args) {
		String reportDir = "G:\\Magento\\report\\esko\\";
		String inXML = reportDir+"01100000045_punto-metallico-sku_82-flat.xml";
		String inXSL = reportDir+"magentoTransform.xsl";
		String outTXT = reportDir+"01100000045_82_punto-metallico-sku_transformed_test.xml";

		transform(inXML,inXSL,outTXT);

	}

	public static Boolean transform(String inXML,String inXSL,String outXml) {
		LOGGER.info("INIZIO TRASFORMAZIONE XML");
		LOGGER.fine("xml di input: "+inXML);
		LOGGER.fine("xsl di input: "+inXSL);
		LOGGER.fine("xml di output: "+outXml);
		try {
			TransformerFactory factory = TransformerFactory.newInstance();

			StreamSource xslStream = new StreamSource(inXSL);
			Transformer transformer = factory.newTransformer(xslStream);
			//		transformer.setErrorListener(new MyErrorListener());

			factory.setAttribute("indent-number", 4);
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			
			StreamSource in = new StreamSource(inXML);
			StreamResult out = new StreamResult(outXml);
			transformer.transform(in,out);
			LOGGER.info("TRASFORMAZIONE XML TERMINATA");
		} catch(TransformerConfigurationException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
			return false;
		} catch(TransformerException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;

	}
}
