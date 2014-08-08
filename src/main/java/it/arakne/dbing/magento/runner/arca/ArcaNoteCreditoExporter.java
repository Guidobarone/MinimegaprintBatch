package it.arakne.dbing.magento.runner.arca;

import it.arakne.dbing.magento.csv.bean.arca.ArcaCreditMemoBean;
import it.arakne.dbing.magento.csv.generator.arca.ArcaCreditMemoReport;
import it.arakne.dbing.magento.logger.MagentoLogger;
import it.arakne.dbing.magento.runner.wrapper.MagjaWrapper;
import it.arakne.dbing.magento.util.Config;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.code.magja.model.order.CreditMemo;
import com.google.code.magja.model.order.Order;
import com.google.code.magja.service.ServiceException;

public class ArcaNoteCreditoExporter {

	private final static Logger LOGGER = Logger.getLogger(ArcaNoteCreditoExporter.class.getName());

	public static void main(String[] args) {
		
		if (args.length!=1){
			System.err.println("ARGOMENTO FILE DI PROPERTIES NON RILEVATO");
			System.exit(1);
		}
		try {
			Config.setup(args[0]);
			
			//REPORT DELLE NOTE DI CREDITO
			MagentoLogger.setup(Config.ARCA_NOTECREDITO_LOG_FILE);
			LOGGER.info("REPORT DELLE NOTE DI CREDITO");
			LOGGER.info("Recupero lista delle note di credito..");
			LOGGER.finest("API USER: "+Config.conf.getApiUser());
			LOGGER.finest("API KEY: "+Config.conf.getApiKey());
			LOGGER.finest("API URL: "+Config.conf.getRemoteHost());
			List<CreditMemo> creditMemoList = MagjaWrapper.getCreditMemosList(Config.conf, "");
			LOGGER.info("Recuperate "+creditMemoList.size()+" note di credito");
			LOGGER.finest("note di credito: "+creditMemoList);
			List<ArcaCreditMemoBean> arcaCreditMemoList = new ArrayList<ArcaCreditMemoBean>();
//			System.out.println("credit memo list size: "+creditMemoList.size());
			
			for (CreditMemo creditMemo : creditMemoList) {
				CreditMemo cm = MagjaWrapper.getCreditMemo(Config.conf, creditMemo.getId());
				//System.out.println("OrderNumber: -"+cm.getOrderNumber()+"-");
				//System.out.println("OrderNumber integer: "+Integer.parseInt(cm.getOrderNumber()));
				
				String[] orderNumber = cm.getOrderNumber().split("\\-");
				//Order ord = MagjaWrapper.getOrderById(Config.conf, Integer.parseInt( orderNumber[0] ) );
				
				Order ord = null;
				
				if (orderNumber.length == 1) {
					// INT
					ord = MagjaWrapper.getOrderById(Config.conf, Integer.parseInt(cm.getOrderNumber()) );
				} else {
					// STRING
					ord = MagjaWrapper.getOrderById(Config.conf, cm.getOrderNumber() );
				}
								
				ArcaCreditMemoBean aib = new ArcaCreditMemoBean(cm, ord);
				arcaCreditMemoList.add(aib);
			}

			LOGGER.info("Generazione report..");
			ArcaCreditMemoReport.generateReport(arcaCreditMemoList, Config.ARCA_REPORT_DIR, Config.ARCA_REPORT_CREDITMEMO_CSV);
			LOGGER.info("EXPORT NOTE DI CREDITO COMPLETATO");
			
		} catch (ServiceException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

}
