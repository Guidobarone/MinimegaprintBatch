package it.arakne.dbing.magento.runner.esko;

import it.arakne.dbing.magento.csv.bean.esko.EskoImportOrderBean;
import it.arakne.dbing.magento.logger.MagentoLogger;
import it.arakne.dbing.magento.runner.wrapper.MagjaWrapper;
import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.FileUtil;
import it.arakne.dbing.magento.util.OrderUtil;
import it.arakne.dbing.magento.util.XmlUtil;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.code.magja.model.order.Order;
import com.google.code.magja.model.order.Shipment;
import com.google.code.magja.service.ServiceException;

public class EskoImporter {

	private final static Logger LOGGER = Logger.getLogger(EskoImporter.class.getName());

	public static void main(String[] args) {
		//"G:\\Eclipse Workspaces\\DB Ing Magento Workspace\\minimegaprintBatch\\src\\main\\resources\\magento.properties"
		if (args.length!=1){
			System.err.println("ARGOMENTO FILE DI PROPERTIES NON RILEVATO");
			System.exit(1);
		}
		try {
			Config.setup(args[0]);
			MagentoLogger.setup(Config.ESKO_IMPORT_LOG_FILE);
			LOGGER.info("RECUPERO FILES ORDINI");

			File inputDir = new File(Config.ESKO_SHIPPING_INPUT_DIR);
			for (String orderFile : inputDir.list() ){
				orderFile = Config.ESKO_SHIPPING_INPUT_DIR + orderFile;
				LOGGER.info("analisi file: "+orderFile);
				if (FileUtil.checkFile(orderFile)){
					LOGGER.fine("file valido.");
					LOGGER.fine("inizio lettura.");
					EskoImportOrderBean eiob = XmlUtil.readOrderFile(orderFile);
					LOGGER.fine("lettura effettuata.");
					LOGGER.info("orderId in input: "+eiob.getOrderId());
					LOGGER.fine("TrackingNumbers: "+eiob.getTrackingNumbers());
					Double orderId = OrderUtil.getEskoOrderNumber(eiob.getOrderId());
					if (orderId>0) {
						LOGGER.fine("recupero ordine: "+orderId);
						Order o = MagjaWrapper.getOrderById(Config.conf, orderId.intValue());
						if (o!=null) {
							LOGGER.fine("ordine recuperato. ");
							LOGGER.info("creazione spedizione. ");
							Shipment sh = MagjaWrapper.createShipping(Config.conf, o);
							LOGGER.fine("spedizione creata. "+sh.getShipmentId());
							
							LOGGER.info("creazione tracking numbers. ");
							int i=0;
							for (String trackN : eiob.getTrackingNumbers()){
								LOGGER.info("tracking number: "+trackN);
								MagjaWrapper.addShippingTracks(Config.conf, Config.ESKO_SHIPPING_CARRIER_NAME, sh.getId(), trackN, ++i);
								LOGGER.fine("tracking number aggiunto. ");
							}

							
							
							/*fine gestione file*/
							if (Config.ESKO_SHIPPING_REMOVE_FILE) {
								Boolean removed = FileUtil.deleteFile(orderFile);
								LOGGER.info("Cancellato il file "+ orderFile+" : "+removed);
							}
							else {
								Boolean moved = FileUtil.moveFile(orderFile, Config.ESKO_SHIPPING_OUTPUT_DIR);
								LOGGER.info("Spostato il file "+ orderFile+" in "+Config.ESKO_SHIPPING_OUTPUT_DIR+" : "+moved);
							}
						}
						else {
							LOGGER.severe("ordine inesistente: "+orderId);
						}
					}
					else {
						LOGGER.severe("order id non valido: "+orderId);
					}
				}
				else {
					LOGGER.warning("FILE "+orderFile+" NON CORRETTO.");
					Boolean moved = FileUtil.moveFile(orderFile, Config.ESKO_SHIPPING_OUTPUT_ERROR_DIR);
					LOGGER.warning("FILE "+orderFile+" SPOSTATO IN: "+Config.ESKO_SHIPPING_OUTPUT_ERROR_DIR+" : "+moved);
				}
			}

			LOGGER.info("FILTRAGGIO ORDINI COMPLETATO");
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		} catch (SAXException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

}
