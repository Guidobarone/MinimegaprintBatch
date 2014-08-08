package it.arakne.dbing.magento.util;

import it.arakne.dbing.magento.csv.bean.esko.EskoImportOrderBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtil {

	private final static Logger LOGGER = Logger.getLogger(XmlUtil.class.getName());

	public static EskoImportOrderBean readOrderFile (String orderFile) 
			throws SAXException, IOException, ParserConfigurationException{

		EskoImportOrderBean eiob = new EskoImportOrderBean();
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse (new File(orderFile));

		doc.getDocumentElement().normalize();
		LOGGER.finest("Root element of the doc is " + doc.getDocumentElement().getNodeName());

		/*RETRIEVING ORDER ID*/
		NodeList orderList = doc.getElementsByTagName("id");
		Node orderNode = orderList.item(0);
		if(orderNode.getNodeType() == Node.ELEMENT_NODE){
			Element orderElement = (Element)orderNode;
			NodeList textOrder = orderElement.getChildNodes();
			String orderId = ((Node)textOrder.item(0)).getNodeValue().trim();
			LOGGER.fine("Order id : " + orderId);
			eiob.setOrderId(orderId);
		}

		/*RETRIEVING TRACKING NUMBERS*/
		NodeList trackingList = doc.getElementsByTagName("trackingNumber");
		int totalTracks = trackingList.getLength();
		LOGGER.finest("Total no of tracks : " + totalTracks);
		List<String> trackNs = new ArrayList<String>();
		for(int s=0; s<trackingList.getLength() ; s++){
			Node trackNode = trackingList.item(s);
			if(trackNode.getNodeType() == Node.ELEMENT_NODE){
				Element trackElement = (Element)trackNode;
				NodeList textFNList = trackElement.getChildNodes();
				String trackNmb = ((Node)textFNList.item(0)).getNodeValue().trim(); 
				LOGGER.fine("Tracking Number : " + trackNmb);
				trackNs.add(trackNmb);
			}
		}
		
		eiob.setTrackingNumbers(trackNs);
		return eiob;
	}

//	public static void main(String[] args) {
//		String file = "G:\\Magento\\report\\esko\\input\\input.xml";
//		try {
//			System.out.println(readOrderFile(file));
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
