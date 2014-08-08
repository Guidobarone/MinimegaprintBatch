package it.arakne.dbing.magento.xml.flat;

import it.arakne.dbing.magento.csv.bean.AdditionalDataBean;
import it.arakne.dbing.magento.csv.bean.CustomOptionBean;
import it.arakne.dbing.magento.csv.bean.OptionValueBean;
import it.arakne.dbing.magento.runner.wrapper.MagjaWrapper;
import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.DateUtil;
import it.arakne.dbing.magento.util.FileUtil;
import it.arakne.dbing.magento.util.NumberUtil;
import it.arakne.dbing.magento.util.OrderUtil;
import it.arakne.dbing.magento.util.UnserializerUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.activation.MimetypesFileTypeMap;
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
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.google.code.magja.model.customer.Customer;
import com.google.code.magja.model.fileuploader.Attachment;
import com.google.code.magja.model.order.Order;
import com.google.code.magja.model.order.OrderAddress;
import com.google.code.magja.model.order.OrderItem;
import com.google.code.magja.model.product.Product;
import com.google.code.magja.service.ServiceException;

public class MagentoFlatGenerator {
	
	private final static Logger LOGGER = Logger.getLogger(MagentoFlatGenerator.class.getName());

	public static Boolean createJob(String outputDir, String filename, Order order, OrderItem item, Map<String,CustomOptionBean> comap) 
			throws ServiceException, ParserConfigurationException, TransformerException {

		LOGGER.finest("outputDir: "+outputDir);
		LOGGER.finest("filename: "+filename);
		LOGGER.finest("order: "+order);
		LOGGER.finest("item: "+item);
		LOGGER.finest("customoptions: "+comap);
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Boolean moved =	createRootElements(doc, order, item, comap);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformerFactory.setAttribute("indent-number", 4);
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(outputDir+File.separatorChar+filename));
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		LOGGER.finest("OutputProperty: {http://xml.apache.org/xslt} indent-amount, 4 ");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		LOGGER.finest("OutputKeys.METHOD, xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		LOGGER.finest("OutputKeys.INDENT, yes");

		transformer.transform(source, result);

		return moved;
	}	

	private static Boolean createRootElements(Document doc, Order order, OrderItem item, Map<String,CustomOptionBean> comap)
			throws ServiceException {
		
		OrderAddress address = order.getShippingAddress();
		Customer customer = order.getCustomer();

		Element root = doc.createElement("Job");
		doc.appendChild(root);

		//Customer
		Element customerTag = doc.createElement("Customer");
		root.appendChild(customerTag);

		//Customer tags
		Element emailTag = doc.createElement("Email");
		customerTag.appendChild(emailTag);
		Text emailValue = doc.createTextNode(customer.getEmail()+"");
		emailTag.appendChild(emailValue);

		Element firstNameTag = doc.createElement("FirstName");
		customerTag.appendChild(firstNameTag);
		Text firstNameTagValue = doc.createTextNode(customer.getFirstName()+"");
		firstNameTag.appendChild(firstNameTagValue);

		Element lastNameTag = doc.createElement("LastName");
		customerTag.appendChild(lastNameTag);
		Text lastNameTagValue = doc.createTextNode(customer.getLastName()+"");
		lastNameTag.appendChild(lastNameTagValue);

		//Telephone
		Element telephoneTag = doc.createElement("Telephone");
		customerTag.appendChild(telephoneTag);
		Text telephoneTagValue = doc.createTextNode(address.getTelephone()+"");
		telephoneTag.appendChild(telephoneTagValue);

		//Cellulare
		Element cellphoneTag = doc.createElement("Cellulare");
		customerTag.appendChild(cellphoneTag);
		Text cellphoneTagValue = doc.createTextNode(address.getFax()+"");
		cellphoneTag.appendChild(cellphoneTagValue);
		
		//Company
		Element ragioneSocialeTAG = doc.createElement("Company");
		customerTag.appendChild(ragioneSocialeTAG);

		LOGGER.finest("ORDINE: " + order.toString());
		LOGGER.finest("BILLING: " + order.getBillingAddress());
		LOGGER.finest("COMPANY: " + order.getBillingAddress().getCompany());
		String[] splitCompanyString = order.getBillingAddress().getCompany().split("[|]");
		String ragioneSociale = "";
		if (splitCompanyString.length>4 && !splitCompanyString[4].equalsIgnoreCase("")) {
			ragioneSociale = splitCompanyString[4];
		}
		else {
			ragioneSociale = address.getFirstName() + " " + address.getLastName();
		}
		Text ragioneSocialeTAGValue = doc.createTextNode(ragioneSociale + "");
		ragioneSocialeTAG.appendChild(ragioneSocialeTAGValue);

		//Professione
		Element professioneTag = doc.createElement("Professione");
		customerTag.appendChild(professioneTag);

		LOGGER.finest("customer="+customer);
		customer = MagjaWrapper.getCustomerInfo(Config.conf, customer.getId());
		LOGGER.finest("customer="+customer);
		String professioneKey = null;
		if (customer != null)
			professioneKey = (String) customer.get("professione");
		String professioneValue = "";
		if (professioneKey != null) {
			/*
			 * Controllo sull'esistenza della chiave "professione" indicata
			 * e assegnazione del valore relativo in caso di successo
			 */
			if ( Config.PROFESSIONE.containsKey(professioneKey) ) {
				professioneValue = Config.PROFESSIONE.get( professioneKey );
			}
			else {
				professioneValue = Config.PROFESSIONE_INVALIDO_ARCA;
			}
		}
		else {
			professioneValue = Config.PROFESSIONE_NULLO_ARCA;
		}
		LOGGER.finest("professioneKey="+professioneKey);
		LOGGER.finest("professioneValue="+professioneValue);

		Text professioneTagValue = doc.createTextNode(professioneValue);
		professioneTag.appendChild(professioneTagValue);
		
		//ShippingAddress
		Element shippAddrTag = doc.createElement("ShippingAddress");
		root.appendChild(shippAddrTag);

		//ShippingAddress tags
		Element firstNameShippingTag = doc.createElement("FirstName");
		shippAddrTag.appendChild(firstNameShippingTag);
		Text firstNameShippingTagValue = doc.createTextNode(address.getFirstName()+"");
		firstNameShippingTag.appendChild(firstNameShippingTagValue);
		
		Element lastNameShippingTag = doc.createElement("LastName");
		shippAddrTag.appendChild(lastNameShippingTag);
		Text lastNameShippingTagValue = doc.createTextNode(address.getLastName()+"");
		lastNameShippingTag.appendChild(lastNameShippingTagValue);
		
		Element cityTag = doc.createElement("City");
		shippAddrTag.appendChild(cityTag);
		Text cityTagValue = doc.createTextNode(address.getCity()+"");
		cityTag.appendChild(cityTagValue);

		Element countryCodeTag = doc.createElement("CountryCode");
		shippAddrTag.appendChild(countryCodeTag);
		Text countryCodeTagValue = doc.createTextNode(address.getCountryCode()+"");
		countryCodeTag.appendChild(countryCodeTagValue);

		Element postCodeTag = doc.createElement("PostCode");
		shippAddrTag.appendChild(postCodeTag);
		Text postCodeTagValue = doc.createTextNode(address.getPostCode()+"");
		postCodeTag.appendChild(postCodeTagValue);

		Element streetTag = doc.createElement("Street");
		shippAddrTag.appendChild(streetTag);
		Text streetTagValue = doc.createTextNode(address.getStreet()+"");
		streetTag.appendChild(streetTagValue);
		
		//Riferimento Spedizione
		Element riferimentoSpedizioneTAG = doc.createElement("RiferimentoSpedizione");
		shippAddrTag.appendChild(riferimentoSpedizioneTAG);
		Text riferimentoSpedizioneTAGValue = doc.createTextNode(address.getCompany() + "");
		riferimentoSpedizioneTAG.appendChild(riferimentoSpedizioneTAGValue);

		//Order
		Element orderTag = doc.createElement("Order");
		root.appendChild(orderTag);

		//Order tags
		Element orderNumberTag = doc.createElement("OrderNumber");
		orderTag.appendChild(orderNumberTag);
		Text orderNumberTagValue = doc.createTextNode(OrderUtil.getEskoOrderNumber(order)+"");
		orderNumberTag.appendChild(orderNumberTagValue);

		Element statusTag = doc.createElement("Status");
		orderTag.appendChild(statusTag);
		Text statusTagValue = doc.createTextNode(order.getStatus()+"");
		statusTag.appendChild(statusTagValue);

		Element grandTotalTag = doc.createElement("GrandTotal");
		orderTag.appendChild(grandTotalTag);
		Text grandTotalTagValue = doc.createTextNode(NumberUtil.convert2Price(order.getGrandTotal())+"");
		grandTotalTag.appendChild(grandTotalTagValue);

		Element discountAmountTag = doc.createElement("DiscountAmount");
		orderTag.appendChild(discountAmountTag);
		Text discountAmountTagValue = doc.createTextNode(NumberUtil.convert2Price(order.getDiscountAmount())+"");
		discountAmountTag.appendChild(discountAmountTagValue);

		Element shippingAmountTag = doc.createElement("ShippingAmount");
		orderTag.appendChild(shippingAmountTag);
		Text shippingAmountTagValue = doc.createTextNode(NumberUtil.convert2Price(order.getShippingAmount())+"");
		shippingAmountTag.appendChild(shippingAmountTagValue);

		Element shippingTaxAmountTag = doc.createElement("ShippingTaxAmount");
		orderTag.appendChild(shippingTaxAmountTag);
		Text shippingTaxAmountTagValue = doc.createTextNode(NumberUtil.convert2Price(order.getShippingTaxAmount())+"");
		shippingTaxAmountTag.appendChild(shippingTaxAmountTagValue);

		Element subtotalTag = doc.createElement("Subtotal");
		orderTag.appendChild(subtotalTag);
		Text subtotalTagValue = doc.createTextNode(NumberUtil.convert2Price(order.getSubtotal())+"");
		subtotalTag.appendChild(subtotalTagValue);

		Element taxAmountTag = doc.createElement("TaxAmount");
		orderTag.appendChild(taxAmountTag);
		Text taxAmountTagValue = doc.createTextNode(NumberUtil.convert2Price(order.getTaxAmount())+"");
		taxAmountTag.appendChild(taxAmountTagValue);

		Element weightTag = doc.createElement("Weight");
		orderTag.appendChild(weightTag);
		Text weightTagValue = doc.createTextNode(String.valueOf(order.getWeight())+"");
		weightTag.appendChild(weightTagValue);

		Element totalQtyOrderedTag = doc.createElement("TotalQtyOrdered");
		orderTag.appendChild(totalQtyOrderedTag);
		Text totalQtyOrderedTagValue = doc.createTextNode(NumberUtil.convert2Integer(order.getTotalQtyOrdered())+"");
		totalQtyOrderedTag.appendChild(totalQtyOrderedTagValue);

		Element paymentTag = doc.createElement("Payment");
		orderTag.appendChild(paymentTag);
		Text paymentTagValue = doc.createTextNode(order.getPayment()!=null ? order.getPayment().getMethod() : "");
		paymentTag.appendChild(paymentTagValue);
		
        Element storeTag = doc.createElement("Store");
        orderTag.appendChild(storeTag);
        Text storeTagValue = doc.createTextNode(order.getStoreName()+"");
        storeTag.appendChild(storeTagValue);

		//Item
		Element itemTag = doc.createElement("Item");
		root.appendChild(itemTag);

		//Item tags
		Element descriptionTag = doc.createElement("Description");
		itemTag.appendChild(descriptionTag);
		Text descriptionTagValue = doc.createTextNode(item.getDescription()+"");
		descriptionTag.appendChild(descriptionTagValue);

		Element nameTag = doc.createElement("Name");
		itemTag.appendChild(nameTag);
		Text nameTagValue = doc.createTextNode(item.getName()+"");
		nameTag.appendChild(nameTagValue);

		Element skuTag = doc.createElement("Sku");
		itemTag.appendChild(skuTag);
		Text skuTagValue = doc.createTextNode(item.getSku()+"");
		skuTag.appendChild(skuTagValue);

		//gestione codice interno da chiamata api
		//System.out.println("SKU: "+item.getSku());
		Product prodAttr = MagjaWrapper.getProductById(Config.conf, item.getProductId());
		//System.out.println(prodAttr.get("codiceinterno"));

		Element codiceInternoTag = doc.createElement("CodiceInterno");
		itemTag.appendChild(codiceInternoTag);
		Text codiceInternoTagValue = doc.createTextNode( (prodAttr!=null ? prodAttr.get("codiceinterno")+"" :"" ) );
		codiceInternoTag.appendChild(codiceInternoTagValue);

		Element priceTag = doc.createElement("Price");
		itemTag.appendChild(priceTag);
		Text priceTagValue = doc.createTextNode(NumberUtil.convert2Price(item.getPrice())+"");
		priceTag.appendChild(priceTagValue);

		Element baseCostTag = doc.createElement("BaseCost");
		itemTag.appendChild(baseCostTag);
		Text baseCostTagValue = doc.createTextNode(NumberUtil.convert2Price(item.getPrice())+"");
		baseCostTag.appendChild(baseCostTagValue);

		Element basePriceTag = doc.createElement("BasePrice");
		itemTag.appendChild(basePriceTag);
		Text basePriceTagValue = doc.createTextNode(NumberUtil.convert2Price(item.getBasePrice())+"");
		basePriceTag.appendChild(basePriceTagValue);

		Element baseOriginalPriceTag = doc.createElement("BaseOriginalPrice");
		itemTag.appendChild(baseOriginalPriceTag);
		Text baseOriginalPriceTagValue = doc.createTextNode(NumberUtil.convert2Price(item.getBaseOriginalPrice())+"");
		baseOriginalPriceTag.appendChild(baseOriginalPriceTagValue);

		Element productIdTag = doc.createElement("ProductId");
		itemTag.appendChild(productIdTag);
		Text productIdTagValue = doc.createTextNode(item.getProductId()+"");
		productIdTag.appendChild(productIdTagValue);

		Element itemIdTag = doc.createElement("ItemId");
		itemTag.appendChild(itemIdTag);
		Text itemIdTagValue = doc.createTextNode(OrderUtil.getEskoOrderNumber(order)+"_"+item.getId());
		itemIdTag.appendChild(itemIdTagValue);

		Element qtyOrderedTag = doc.createElement("QtyOrdered");
		itemTag.appendChild(qtyOrderedTag);
		Text qtyOrderedTagValue = doc.createTextNode(NumberUtil.convert2Integer(item.getQtyOrdered())+"");
		qtyOrderedTag.appendChild(qtyOrderedTagValue);

		Element wweightTag = doc.createElement("Weight");
		itemTag.appendChild(wweightTag);
		Text wweightTagValue = doc.createTextNode(String.valueOf(item.getWeight())+"");
		wweightTag.appendChild(wweightTagValue);

		// fileUpload
		boolean fileUploadOptionExists = false;
		boolean fileUploadExists = false;
		boolean fileUploadCopied = false;

		CustomOptionBean fileUpload = comap.get("fileUpload");
		LOGGER.info("fileUpload: "+fileUpload);

		if (fileUpload != null) {

			fileUploadOptionExists = true;

			OptionValueBean ovb = UnserializerUtil.unserializeFileUploadOptionValue((String)fileUpload.getOptionValue());
			LOGGER.info("ovb.getType(): "+ovb.getType());
			LOGGER.info("ovb.getTitle(): "+ovb.getTitle());
			LOGGER.info("ovb.getQuotePath(): "+ovb.getQuotePath());
			LOGGER.info("ovb.getOrderPath(): "+ovb.getOrderPath());
			LOGGER.info("ovb.getFullpath(): "+ovb.getFullpath());
			LOGGER.info("ovb.getSize(): "+ovb.getSize());

			if (ovb.getFullpath() != null)
				fileUploadExists = FileUtil.checkFile(ovb.getFullpath());

			//esecuzione delle operazioni di copia del file upload nel caso questo esista
			if (fileUploadExists) {
			    LOGGER.info("copying uploaded file..");
			    LOGGER.fine("from file: "+ovb.getFullpath());
			    LOGGER.fine("to dir: "+Config.ESKO_ZIP_DIR);
			    LOGGER.fine("to name: "+FileUtil.getAttachName(order, item, ovb.getTitle()));
			    try {
			    	fileUploadCopied = FileUtil.copyFile(ovb.getFullpath(), Config.ESKO_ZIP_DIR, FileUtil.getAttachName(order, item, ovb.getTitle()));
			        LOGGER.info("file upload copied: "+fileUploadCopied);
			    }
			    catch (IOException e) {
			        e.printStackTrace();
			    }
			    
				//se la copia è andata a buon fine scrivo i tag sull'XML
				if (fileUploadCopied) {
					Element fileUploadTag = doc.createElement("FileUploadPath");
					itemTag.appendChild(fileUploadTag);
					Text fileUploadTagValue = doc.createTextNode(Config.ESKO_ZIP_DIR);
					fileUploadTag.appendChild(fileUploadTagValue);

					Element fileUploadSizeTag = doc.createElement("FileUploadSize");
					itemTag.appendChild(fileUploadSizeTag);
					Text fileUploadSizeTagValue = doc.createTextNode(String.valueOf(ovb.getSize())+"");
					fileUploadSizeTag.appendChild(fileUploadSizeTagValue);

					Element fileUploadTitleTag = doc.createElement("FileUploadTitle");
					itemTag.appendChild(fileUploadTitleTag);
					Text fileUploadTitleTagValue = doc.createTextNode(FileUtil.getAttachName(order, item, ovb.getTitle())+"");
					fileUploadTitleTag.appendChild(fileUploadTitleTagValue);

					Element fileUploadTypeTag = doc.createElement("FileUploadType");
					itemTag.appendChild(fileUploadTypeTag);
					Text fileUploadTypeTagValue = doc.createTextNode(String.valueOf(ovb.getType())+"");
					fileUploadTypeTag.appendChild(fileUploadTypeTagValue);
				}
				//altrimenti scrivo i tag di errore copia file sull'XML
				else {
					Element fileUploadTag = doc.createElement("FileUploadPath");
					itemTag.appendChild(fileUploadTag);
					Text fileUploadTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileUploadTag.appendChild(fileUploadTagValue);

					Element fileUploadSizeTag = doc.createElement("FileUploadSize");
					itemTag.appendChild(fileUploadSizeTag);
					Text fileUploadSizeTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileUploadSizeTag.appendChild(fileUploadSizeTagValue);

					Element fileUploadTitleTag = doc.createElement("FileUploadTitle");
					itemTag.appendChild(fileUploadTitleTag);
					Text fileUploadTitleTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileUploadTitleTag.appendChild(fileUploadTitleTagValue);
					
					Element fileUploadTypeTag = doc.createElement("FileUploadType");
					itemTag.appendChild(fileUploadTypeTag);
					Text fileUploadTypeTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileUploadTypeTag.appendChild(fileUploadTypeTagValue);
				}
			}
		}

		if (!fileUploadOptionExists || !fileUploadExists)
		{
			Element fileUploadTag = doc.createElement("FileUploadPath");
			itemTag.appendChild(fileUploadTag);
			Text fileUploadTagValue = doc.createTextNode( Config.MESSAGGIO_ERRORE_ASSENZA_FILE );
			fileUploadTag.appendChild(fileUploadTagValue);

			Element fileUploadSizeTag = doc.createElement("FileUploadSize");
			itemTag.appendChild(fileUploadSizeTag);
			Text fileUploadSizeTagValue = doc.createTextNode( Config.MESSAGGIO_ERRORE_ASSENZA_FILE );
			fileUploadSizeTag.appendChild(fileUploadSizeTagValue);

			Element fileUploadTitleTag = doc.createElement("FileUploadTitle");
			itemTag.appendChild(fileUploadTitleTag);
			Text fileUploadTitleTagValue = doc.createTextNode( Config.MESSAGGIO_ERRORE_ASSENZA_FILE );
			fileUploadTitleTag.appendChild(fileUploadTitleTagValue);
			
			Element fileUploadTypeTag = doc.createElement("FileUploadType");
			itemTag.appendChild(fileUploadTypeTag);
			Text fileUploadTypeTagValue = doc.createTextNode( Config.MESSAGGIO_ERRORE_ASSENZA_FILE );
			fileUploadTypeTag.appendChild(fileUploadTypeTagValue);
		}

		//File attachment
		boolean additionalDataExists = false;
		boolean attachmentExists = false;
		boolean attachmentCopied = false;

		//cerco il campo 'additional_data' nell'item
		if (item.get("additional_data") != null) {

			additionalDataExists = true;

			//parsing dei parametri 'additional_data' dell'item
			AdditionalDataBean adb = UnserializerUtil.unserializeFileAttachmentMap((String)item.get("additional_data"));
			
			//controllo l'esistenza del file attachment su disco
			if (adb.getFullpath() != null) {
				attachmentExists = FileUtil.checkFile(adb.getFullpath());
			}

			//esecuzione delle operazioni di copia del file attachment nel caso questo esista
			if (attachmentExists) {

				LOGGER.info("copying uploaded attachment file..");
				LOGGER.fine("from file: "+adb.getFullpath());
				LOGGER.fine("to dir: "+Config.ESKO_ZIP_DIR);
				LOGGER.fine("to name: "+FileUtil.getAttachName(order, item, adb.getTitle()));
				try {
					attachmentCopied = FileUtil.copyFile(adb.getFullpath(), Config.ESKO_ZIP_DIR, FileUtil.getAttachName(order, item, adb.getTitle()));
					LOGGER.info("file copied: "+attachmentCopied);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
				//se la copia è andata a buon fine scrivo i tag sull'XML
				if (attachmentCopied) {
					Element fileAttachmentTag = doc.createElement("FileAttachmentPath");
					itemTag.appendChild(fileAttachmentTag);
					Text fileAttachmentTagValue = doc.createTextNode(Config.ESKO_ZIP_DIR);
					fileAttachmentTag.appendChild(fileAttachmentTagValue);

					Element fileAttachmentSizeTag = doc.createElement("FileAttachmentSize");
					itemTag.appendChild(fileAttachmentSizeTag);
					Text fileAttachmentSizeTagValue = doc.createTextNode(String.valueOf(adb.getSize())+"");
					fileAttachmentSizeTag.appendChild(fileAttachmentSizeTagValue);

					Element fileAttachmentTitleTag = doc.createElement("FileAttachmentTitle");
					itemTag.appendChild(fileAttachmentTitleTag);
					Text fileAttachmentTitleTagValue = doc.createTextNode(FileUtil.getAttachName(order, item, adb.getTitle())+"");
					fileAttachmentTitleTag.appendChild(fileAttachmentTitleTagValue);

					Element fileAttachmentTypeTag = doc.createElement("FileAttachmentType");
					itemTag.appendChild(fileAttachmentTypeTag);
					Text fileAttachmentTypeTagValue = doc.createTextNode(String.valueOf(adb.getType())+"");
					fileAttachmentTypeTag.appendChild(fileAttachmentTypeTagValue);
				}
				//altrimenti scrivo i tag di errore copia file sull'XML
				else {
					Element fileAttachmentTag = doc.createElement("FileAttachmentPath");
					itemTag.appendChild(fileAttachmentTag);
					Text fileAttachmentTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileAttachmentTag.appendChild(fileAttachmentTagValue);

					Element fileAttachmentSizeTag = doc.createElement("FileAttachmentSize");
					itemTag.appendChild(fileAttachmentSizeTag);
					Text fileAttachmentSizeTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileAttachmentSizeTag.appendChild(fileAttachmentSizeTagValue);

					Element fileAttachmentTitleTag = doc.createElement("FileAttachmentTitle");
					itemTag.appendChild(fileAttachmentTitleTag);
					Text fileAttachmentTitleTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileAttachmentTitleTag.appendChild(fileAttachmentTitleTagValue);
					
					Element fileAttachmentTypeTag = doc.createElement("FileAttachmentType");
					itemTag.appendChild(fileAttachmentTypeTag);
					Text fileAttachmentTypeTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileAttachmentTypeTag.appendChild(fileAttachmentTypeTagValue);
				}
			}
		}
		
		//se il campo 'additional_data' dell'item non esiste, oppure se non c'è un attachment caricato
		//cerco tra gli attachment di prodotto precaricati
		if (!additionalDataExists || !attachmentExists) {

			//recupero la lista di attachments del prodotto
			List<Attachment> attachmentList = MagjaWrapper.getProductAttachments(Config.conf, item.getProductId()+"");
			LOGGER.info("attachment list: "+attachmentList);

			//costruisco una mappa di attachments
			Map<String,Attachment> attMap = new HashMap<String,Attachment>();
			Iterator<Attachment> itr = attachmentList.iterator();
			while(itr.hasNext()) {
			
				Attachment element = itr.next();
				attMap.put(element.getTitle(), element);
			}

			//recupero la custom option modello
			CustomOptionBean modello = comap.get("Modello");
			LOGGER.info("modello: "+modello);
			
			//cerco l'attachment corrispondente al valore della custom option 'Modello'
			
			Attachment attachment = null;
			if (modello!=null)
				attachment = attMap.get(modello.getValue());
			
			String attachmentFullPath = "";
			//se l'attachment esiste controllo l'esistenza del file su disco
			if (attachment != null) {
				attachmentFullPath = MagjaWrapper.getMagentoMediaPath(Config.conf)+"/"+attachment.getUploadedFile();
				attachmentExists = FileUtil.checkFile(attachmentFullPath);
			}
			
			//esecuzione delle operazioni di copia del file nel caso questo esista
			if (attachmentExists) {

				String attachmentTitle = attachment.getTitle().replace(" ", "_");
				String attachmentExtension = attachmentFullPath.substring(attachmentFullPath.lastIndexOf(".")+1);
				File file = new File(attachmentFullPath);
				String attachmentSize = file.length()+"";
				String attachmentType = new MimetypesFileTypeMap().getContentType(file);
				String attachmentFilename = FileUtil.getAttachName(order, item, attachmentTitle+"."+attachmentExtension);
				
				LOGGER.info("copying template attachment file..");
				LOGGER.info("from file: "+attachmentFullPath);
				LOGGER.info("to dir: "+Config.ESKO_ZIP_DIR);
				LOGGER.info("to name: "+attachmentFilename);
				try {
					attachmentCopied = FileUtil.copyFile(attachmentFullPath, Config.ESKO_ZIP_DIR, attachmentFilename);
					LOGGER.info("file copied: "+attachmentCopied);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
				//se la copia è andata a buon fine scrivo i tag sull'XML
				if (attachmentCopied) {
					Element fileAttachmentTag = doc.createElement("FileAttachmentPath");
					itemTag.appendChild(fileAttachmentTag);
					Text fileAttachmentTagValue = doc.createTextNode(Config.ESKO_ZIP_DIR);
					fileAttachmentTag.appendChild(fileAttachmentTagValue);

					Element fileAttachmentSizeTag = doc.createElement("FileAttachmentSize");
					itemTag.appendChild(fileAttachmentSizeTag);
					Text fileAttachmentSizeTagValue = doc.createTextNode(String.valueOf(attachmentSize));
					fileAttachmentSizeTag.appendChild(fileAttachmentSizeTagValue);

					Element fileAttachmentTitleTag = doc.createElement("FileAttachmentTitle");
					itemTag.appendChild(fileAttachmentTitleTag);
					Text fileAttachmentTitleTagValue = doc.createTextNode(attachmentFilename);
					fileAttachmentTitleTag.appendChild(fileAttachmentTitleTagValue);

					Element fileAttachmentTypeTag = doc.createElement("FileAttachmentType");
					itemTag.appendChild(fileAttachmentTypeTag);
					Text fileAttachmentTypeTagValue = doc.createTextNode(String.valueOf(attachmentType));
					fileAttachmentTypeTag.appendChild(fileAttachmentTypeTagValue);
				}
				//altrimenti scrivo i tag di errore copia file sull'XML
				else {
					Element fileAttachmentTag = doc.createElement("FileAttachmentPath");
					itemTag.appendChild(fileAttachmentTag);
					Text fileAttachmentTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileAttachmentTag.appendChild(fileAttachmentTagValue);

					Element fileAttachmentSizeTag = doc.createElement("FileAttachmentSize");
					itemTag.appendChild(fileAttachmentSizeTag);
					Text fileAttachmentSizeTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileAttachmentSizeTag.appendChild(fileAttachmentSizeTagValue);

					Element fileAttachmentTitleTag = doc.createElement("FileAttachmentTitle");
					itemTag.appendChild(fileAttachmentTitleTag);
					Text fileAttachmentTitleTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileAttachmentTitleTag.appendChild(fileAttachmentTitleTagValue);
					
					Element fileAttachmentTypeTag = doc.createElement("FileAttachmentType");
					itemTag.appendChild(fileAttachmentTypeTag);
					Text fileAttachmentTypeTagValue = doc.createTextNode(Config.MESSAGGIO_ERRORE_COPIA_FILE);
					fileAttachmentTypeTag.appendChild(fileAttachmentTypeTagValue);
				}
			}
		}
		
		//se non esiste nessun attachment né caricato dall'utente, né precaricato, scrivo i tag di assenza file sull'XML
		if (!attachmentExists) {

			Element fileAttachmentTag = doc.createElement("FileAttachmentPath");
			itemTag.appendChild(fileAttachmentTag);
			Text fileAttachmentTagValue = doc.createTextNode( Config.MESSAGGIO_ERRORE_ASSENZA_FILE );
			fileAttachmentTag.appendChild(fileAttachmentTagValue);

			Element fileAttachmentSizeTag = doc.createElement("FileAttachmentSize");
			itemTag.appendChild(fileAttachmentSizeTag);
			Text fileAttachmentSizeTagValue = doc.createTextNode( Config.MESSAGGIO_ERRORE_ASSENZA_FILE );
			fileAttachmentSizeTag.appendChild(fileAttachmentSizeTagValue);

			Element fileAttachmentTitleTag = doc.createElement("FileAttachmentTitle");
			itemTag.appendChild(fileAttachmentTitleTag);
			Text fileAttachmentTitleTagValue = doc.createTextNode( Config.MESSAGGIO_ERRORE_ASSENZA_FILE );
			fileAttachmentTitleTag.appendChild(fileAttachmentTitleTagValue);
			
			Element fileAttachmentTypeTag = doc.createElement("FileAttachmentType");
			itemTag.appendChild(fileAttachmentTypeTag);
			Text fileAttachmentTypeTagValue = doc.createTextNode( Config.MESSAGGIO_ERRORE_ASSENZA_FILE );
			fileAttachmentTypeTag.appendChild(fileAttachmentTypeTagValue);			
		}
		
		//Custom options
		Element customOptionsTag = doc.createElement("customOptionsTag");
		itemTag.appendChild(customOptionsTag);

		//Custom options tags
		Set<String> keys = comap.keySet();
		for (String key : keys) {

			//gestione duedate
			if (Config.ESKO_ORDER_DUE_DATE_CUSTOM_OPTION_LABEL.equalsIgnoreCase(key)) {
				
				//Modifiche 2012 - 05 - 30 / Rastelli
				
				//Conversione del tag in un intero
				Integer dueDate = DateUtil.convertBusinessDays(comap.get(key).getValue());
				//Inizializzazione dell'iteratore sulle date a partire dallo span definito dall'intero
				//definendo però lo span come GIORNO-2 (per poi iterare sul giorno successivo senza problemi)
				DateUtil.initBusinessDayBySpan(dueDate-2);

				//2c: aggiunto incremento di un numero "delay", fissato in configurazione, di business days
				int delay = Integer.parseInt(Config.ESKO_ORDER_DUE_DATE_DELAY);
				for (int i=0; i<delay; i++)
					DateUtil.nextBusinessDay();
				
				//Inserimento della DATA OTTIMIZZAZIONE
				Element customOptionTag = doc.createElement("dataOttimizzazione");
				customOptionsTag.appendChild(customOptionTag);
				Text dataOttimizzazioneTagValue = doc.createTextNode( DateUtil.getBusinessDay() + "" );
				customOptionTag.appendChild(dataOttimizzazioneTagValue);
				
				//Prelevamento del giorno lavorativo successivo
				DateUtil.nextBusinessDay();
				
				//Inserimento della DUE DATE
				customOptionTag = doc.createElement("dueDate");
				customOptionsTag.appendChild(customOptionTag);
				Text dataDueDateTagValue = doc.createTextNode( DateUtil.getBusinessDay() + "" );
				customOptionTag.appendChild(dataDueDateTagValue);
				
				//Prelevamento del giorno lavorativo successivo
				DateUtil.nextBusinessDay();
				
				//Inserimento della DATA CONSEGNA
				customOptionTag = doc.createElement("dataConsegna");
				customOptionsTag.appendChild(customOptionTag);
				Text dataConsegnaTagValue = doc.createTextNode( DateUtil.getBusinessDay() + "" );
				customOptionTag.appendChild(dataConsegnaTagValue);
				
//				// DATA CONSEGNA
//				LocalDate localDateConsegna = DateUtil.getWorkingDaysDate(date);
//				Text customOptionTagValue = doc.createTextNode(localDateConsegna.toString()+"");
//				customOptionTag.appendChild(customOptionTagValue);
//
//				System.out.println("Data di consegna: " + date);
//				System.out.println("Local Data consegna: " + localDateConsegna);
//				
//				// DUE DATE
//				LocalDate localDateDueDate = DateUtil.getWorkingDaysDate(date-1);
//				customOptionTagValue = doc.createTextNode(localDateDueDate.toString()+"");
//				customOptionTag = doc.createElement("dueDate");
//				customOptionTag.appendChild(customOptionTagValue);
//				customOptionsTag.appendChild(customOptionTag);
//				
//				System.out.println("Local Due Date: " + localDateDueDate);
//				
//				// DATA OTTIMIZZAZIONE
//				LocalDate localDataOttimizzazione = DateUtil.getWorkingDaysDate(date-2);
//				customOptionTagValue = doc.createTextNode(localDataOttimizzazione.toString()+"");
//				customOptionTag = doc.createElement("dataOttimizzazione");
//				customOptionTag.appendChild(customOptionTagValue);
//				customOptionsTag.appendChild(customOptionTag);
//
//				System.out.println("Local data ottimizzazione: " + localDataOttimizzazione);
			}
			else {
				Element customOptionTag = doc.createElement(key);
				customOptionsTag.appendChild(customOptionTag);
				Text customOptionTagValue = doc.createTextNode(comap.get(key).getValue()+"");
				customOptionTag.appendChild(customOptionTagValue);
			}
		}

//		//DueDate
//		Element duedateTag = doc.createElement("Address");
//		root.appendChild(duedateTag);
//		if (comap.containsKey("duedate")) {
//			CustomOptionBean cob = comap.get("duedate");
//			Text duedateValue = doc.createTextNode(cob.getValue());
//			duedateTag.appendChild(duedateValue);
//		}

		Node pi = doc.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\""+Config.ESKO_XML_XSLT_FILENAME+"\"");
		LOGGER.finest("ProcessingInstruction(xml-stylesheet, type= text/xsl,  href= "+Config.ESKO_XML_XSLT_FILENAME+" ");
		doc.insertBefore(pi, root);

		return attachmentCopied;
	}
}
