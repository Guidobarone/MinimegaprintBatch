package it.arakne.dbing.magento.runner.esko;

import it.arakne.dbing.magento.csv.bean.CustomOptionBean;
import it.arakne.dbing.magento.logger.MagentoLogger;
import it.arakne.dbing.magento.runner.wrapper.MagjaWrapper;
import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.FileUtil;
import it.arakne.dbing.magento.util.OrderUtil;
import it.arakne.dbing.magento.util.UnserializerUtil;
import it.arakne.dbing.magento.xml.flat.MagentoFlatGenerator;
import it.arakne.dbing.magento.xml.transform.XMLTransformer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.google.code.magja.model.order.Order;
import com.google.code.magja.model.order.OrderFilterItem;
import com.google.code.magja.model.order.OrderItem;
import com.google.code.magja.service.ServiceException;

public class EskoExporter {

	private final static Logger LOGGER = Logger.getLogger(EskoExporter.class.getName());

	public static void main(String[] args) {

		if (args.length!=1){
			System.err.println("ARGOMENTO FILE DI PROPERTIES NON RILEVATO");
			System.exit(1);
		}
		try {
			Config.setup(args[0]);
			MagentoLogger.setup(Config.ESKO_EXPORT_LOG_FILE);

			//ESPORTAZIONE ORDINI
			LOGGER.info("ESPORTAZIONE ORDINI");
			List<OrderFilterItem> filterList = new ArrayList<OrderFilterItem>();
			OrderFilterItem ofitem = new OrderFilterItem("status", "eq", Config.ESKO_ORDER_STATUS_FILTERING);
			LOGGER.info("filtraggio: "+ofitem.getProperty() + " " + ofitem.getOperator()+" "+ofitem.getValue() );
			filterList.add(ofitem);
			List<Order> orderList = MagjaWrapper.ordersFiltering(Config.conf, filterList);
			LOGGER.info("ordini trovati: "+orderList.size());

			//CICLO SUGLI ORDINI
			for (Order o : orderList) {
//				Boolean orderIsOk = false;
				Boolean itemIsOk = true;

				//LETTURA ORDINE
				LOGGER.info("LETTURA ORDINE. ");
				LOGGER.info("ordine: "+OrderUtil.getEskoOrderNumber(o));
				Order order;
				try {
					
					if (o.getOrderNumber().split("\\-").length == 1) {
						// INT
						//order = MagjaWrapper.getOrderById(Config.conf, Integer.parseInt(o.getOrderNumber()) );
						order = MagjaWrapper.getOrderById(Config.conf, o.getOrderNumber() );
					} else {
						// STRING
						order = MagjaWrapper.getOrderById(Config.conf, o.getOrderNumber() );
					}
					
					//CREAZIONE REPORT ORDINE
					LOGGER.info("CREAZIONE REPORT ORDINE. ");
					LOGGER.fine("dir: "+Config.ESKO_ORDER_REPORT_DIR+" "+OrderUtil.getEskoOrderNumber(order)+Config.ESKO_ORDER_REPORT_EXTENSION);
					Boolean summaryOk = FileUtil.createOrderSummary(order, Config.ESKO_ORDER_REPORT_DIR);

					if (order!=null && summaryOk) {
						//orderIsOk=true;
						LOGGER.info("CREAZIONE REPORT ORDINE OK. ");
						LOGGER.finest("ordine: "+order);

						//CICLO SUGLI ITEMS
						for (OrderItem item : order.getItems()) {
							
							//AGGIORNAMENTO REPORT ORDINE
							FileUtil.appendItemSummary(item, Config.ESKO_ORDER_REPORT_DIR, OrderUtil.getEskoOrderNumber(order), false);
							
							//LETTURA CUSTOM OPTIONS
							LOGGER.finest("item: "+item);
							LOGGER.finest("Item Product Options: "+item.getProductOptions());
							//List<CustomOptionBean> list = UnserializerUtil.unserializeCustomOptions(item.getProductOptions());
							Map<String,CustomOptionBean> comap = UnserializerUtil.unserializeCustomOptionsMap(item.getProductOptions());
							LOGGER.finest("custom options: "+comap);

							//GENERAZIONE XML ITEM
							LOGGER.info("GENERAZIONE XML ITEM.");
							LOGGER.fine("Report dir: "+Config.ESKO_FLAT_XML_REPORT_DIR);
							String xmlFlatName = OrderUtil.getEskoOrderNumber(order)+"_"+item.getId()+"_"+item.getSku()+"-"+Config.ESKO_REPORT_FLAT_XML_SUFFIX;
							String xml2EskoName = OrderUtil.getEskoOrderNumber(order)+"_"+item.getId()+"_"+item.getSku()+"-"+Config.ESKO_TRANSFORMED_XML_REPORT_SUFFIX;
							LOGGER.info("filename: "+xmlFlatName);
							//ItemReport.generateReport(item, list, reportDir, orderNumber+"_"+item.getName()+"_"+item.getId()+"-"+itemReport);
							Boolean attachIsOk = MagentoFlatGenerator.createJob(
									Config.ESKO_FLAT_XML_REPORT_DIR,
									xmlFlatName,
									order, 
									item, 
									comap);
							itemIsOk &= attachIsOk;
							LOGGER.info("GENERAZIONE XML ITEM TERMINATA.");
							
							//AGGIORNAMENTO REPORT ORDINE
							FileUtil.appendItemAttachSummary(item, Config.ESKO_ORDER_REPORT_DIR, OrderUtil.getEskoOrderNumber(order), attachIsOk);

							//TRASFORMAZIONE XML ITEM
							LOGGER.info("TRASFORMAZIONE XML ITEM.");
							Boolean tranformOk = XMLTransformer.transform(
									Config.ESKO_FLAT_XML_REPORT_DIR + File.separatorChar + xmlFlatName, 
									Config.ESKO_TRANSFORMED_XML_REPORT_DIR + File.separatorChar + Config.ESKO_XML_XSLT_FILENAME, 
									Config.ESKO_TRANSFORMED_XML_REPORT_DIR + File.separatorChar + xml2EskoName);
							LOGGER.info("TRASFORMAZIONE XML ITEM TERMINATA.");
							
							//AGGIORNAMENTO REPORT ORDINE
							FileUtil.appendItemTransformSummary(item, Config.ESKO_ORDER_REPORT_DIR, OrderUtil.getEskoOrderNumber(order), tranformOk);
							FileUtil.appendItemSummary(item, Config.ESKO_ORDER_REPORT_DIR, OrderUtil.getEskoOrderNumber(order), true);
						}
						LOGGER.info("CREAZIONE REPORT ORDER TERMINATA. ");

						//CAMBIO STATUS ORDER
						LOGGER.info("CAMBIO STATUS ORDER. ");
						//LOGGER.info("ordine ok: "+orderIsOk);
						LOGGER.info("articoli ok: "+itemIsOk); 
						//if (orderIsOk && itemIsOk) {
						if (itemIsOk) {
							if (o.getOrderNumber().split("\\-").length==1) {
								/*MagjaWrapper.updateOrderStatusById(Config.conf, 
										Integer.parseInt(o.getOrderNumber()), 
										Config.ESKO_ORDER_STATUS_UPDATE, 
										Config.ESKO_ORDER_STATUS_UPDATE_COMMENT, 
										Config.ESKO_ORDER_STATUS_UPDATE_NOTIFY_CUSTOMER);*/
								MagjaWrapper.updateOrderStatusById(Config.conf, 
										o.getOrderNumber(), 
										Config.ESKO_ORDER_STATUS_UPDATE, 
										Config.ESKO_ORDER_STATUS_UPDATE_COMMENT, 
										Config.ESKO_ORDER_STATUS_UPDATE_NOTIFY_CUSTOMER);
							} else {
								MagjaWrapper.updateOrderStatusById(Config.conf, 
										o.getOrderNumber(), 
										Config.ESKO_ORDER_STATUS_UPDATE, 
										Config.ESKO_ORDER_STATUS_UPDATE_COMMENT, 
										Config.ESKO_ORDER_STATUS_UPDATE_NOTIFY_CUSTOMER);
							}
							LOGGER.info("STATUS ORDER: "+Config.ESKO_ORDER_STATUS_UPDATE+" commento: "+ 
									Config.ESKO_ORDER_STATUS_UPDATE_COMMENT+" notifica cliente: "+ 
									Config.ESKO_ORDER_STATUS_UPDATE_NOTIFY_CUSTOMER);
							FileUtil.appendOrderStatus(Config.ESKO_ORDER_REPORT_DIR, OrderUtil.getEskoOrderNumber(order), Config.ESKO_ORDER_STATUS_UPDATE);
						}
						else {
							if (o.getOrderNumber().split("\\-").length==1) { 
								/*MagjaWrapper.updateOrderStatusById(Config.conf, 
										Integer.parseInt(o.getOrderNumber()), 
										Config.ESKO_ORDER_STATUS_ERROR, 
										Config.ESKO_ORDER_STATUS_ERROR_COMMENT, 
										Config.ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER); */
								MagjaWrapper.updateOrderStatusById(Config.conf, 
										o.getOrderNumber(), 
										Config.ESKO_ORDER_STATUS_ERROR, 
										Config.ESKO_ORDER_STATUS_ERROR_COMMENT, 
										Config.ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER);
							}
							else {
								MagjaWrapper.updateOrderStatusById(Config.conf, 
										o.getOrderNumber(), 
										Config.ESKO_ORDER_STATUS_ERROR, 
										Config.ESKO_ORDER_STATUS_ERROR_COMMENT, 
										Config.ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER);
							}
							LOGGER.severe("STATUS ORDER: "+Config.ESKO_ORDER_STATUS_ERROR+" commento: "+ 
									Config.ESKO_ORDER_STATUS_ERROR_COMMENT+" notifica cliente: "+ 
									Config.ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER);								
							FileUtil.appendOrderStatus(Config.ESKO_ORDER_REPORT_DIR, OrderUtil.getEskoOrderNumber(order), Config.ESKO_ORDER_STATUS_ERROR);
						}
						LOGGER.info("CAMBIO STATUS ORDER TERMINATO. ");
					}
					else {
						LOGGER.severe("ERRORE CREAZIONE ORDINE. ");
						if (o.getOrderNumber().split("\\-").length==1) {
							/*MagjaWrapper.updateOrderStatusById(Config.conf, 
									Integer.parseInt(o.getOrderNumber()), 
									Config.ESKO_ORDER_STATUS_ERROR, 
									Config.ESKO_ORDER_STATUS_ERROR_COMMENT, 
									Config.ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER);*/
							MagjaWrapper.updateOrderStatusById(Config.conf, 
									o.getOrderNumber(), 
									Config.ESKO_ORDER_STATUS_ERROR, 
									Config.ESKO_ORDER_STATUS_ERROR_COMMENT, 
									Config.ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER);
						} else {
							MagjaWrapper.updateOrderStatusById(Config.conf,
									o.getOrderNumber(), 
									Config.ESKO_ORDER_STATUS_ERROR, 
									Config.ESKO_ORDER_STATUS_ERROR_COMMENT, 
									Config.ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER);
						}
						LOGGER.severe("STATUS ORDER: "+Config.ESKO_ORDER_STATUS_ERROR+" commento: "+ 
								Config.ESKO_ORDER_STATUS_ERROR_COMMENT+" notifica cliente: "+ 
								Config.ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER);
						FileUtil.appendOrderStatus(Config.ESKO_ORDER_REPORT_DIR, OrderUtil.getEskoOrderNumber(order), Config.ESKO_ORDER_STATUS_ERROR);
					}
				} catch (NumberFormatException e) {
					LOGGER.severe(e.getMessage());
					e.printStackTrace();
				} catch (ServiceException e) {
					LOGGER.severe(e.getMessage());
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					LOGGER.severe(e.getMessage());
					e.printStackTrace();
				} catch (TransformerException e) {
					LOGGER.severe(e.getMessage());
					e.printStackTrace();
				}
			}
			LOGGER.info("ESPORTAZIONE ORDINI COMPLETATA");

		} catch (ServiceException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

}