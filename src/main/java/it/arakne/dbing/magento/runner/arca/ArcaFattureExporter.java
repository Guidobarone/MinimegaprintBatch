package it.arakne.dbing.magento.runner.arca;

import it.arakne.dbing.magento.csv.bean.arca.ArcaInvoiceBean;
import it.arakne.dbing.magento.csv.generator.arca.ArcaInvoicesReport;
import it.arakne.dbing.magento.logger.MagentoLogger;
import it.arakne.dbing.magento.runner.wrapper.MagjaWrapper;
import it.arakne.dbing.magento.util.Config;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.code.magja.model.order.Invoice;
import com.google.code.magja.model.order.Order;
import com.google.code.magja.service.ServiceException;

public class ArcaFattureExporter {

	private final static Logger LOGGER = Logger.getLogger(ArcaFattureExporter.class.getName());

	public static void main(String[] args) {
		
		if (args.length!=1){
			System.err.println("ARGOMENTO FILE DI PROPERTIES NON RILEVATO");
			System.exit(1);
		}
		try {
			Config.setup(args[0]);
			
			//REPORT DELLE FATTURE
			MagentoLogger.setup(Config.ARCA_FATTURE_LOG_FILE);
			LOGGER.info("REPORT DELLE FATTURE");
			LOGGER.info("Recupero lista delle fatture..");
			LOGGER.finest("API USER: "+Config.conf.getApiUser());
			LOGGER.finest("API KEY: "+Config.conf.getApiKey());
			LOGGER.finest("API URL: "+Config.conf.getRemoteHost());
			List<Invoice> invoiceList = MagjaWrapper.getInvoicesList(Config.conf, "");
			LOGGER.info("Recuperate "+invoiceList.size()+" fatture");
			LOGGER.finest("fatture: "+invoiceList);
			List<ArcaInvoiceBean> arcaInvoiceList = new ArrayList<ArcaInvoiceBean>();
//			System.out.println("invoice list size: "+invoiceList.size());
			
			for (Invoice inv : invoiceList) {
				Invoice invv = MagjaWrapper.getInvoice(Config.conf, inv.getId());
				//System.out.println("OrderNumber: -"+invv.getOrderNumber()+"-");
				//System.out.println("OrderNumber integer: "+Integer.parseInt(invv.getOrderNumber()));
				
				String[] orderNumber = invv.getOrderNumber().split("\\-");
				//Order ord = MagjaWrapper.getOrderById(Config.conf, Integer.parseInt( orderNumber[0] ) );
				
				Order ord = null;
				
				if (orderNumber.length == 1) {
					// INT
					ord = MagjaWrapper.getOrderById(Config.conf, Integer.parseInt(invv.getOrderNumber()) );
				} else {
					// STRING
					ord = MagjaWrapper.getOrderById(Config.conf, invv.getOrderNumber() );
				}
								
				ArcaInvoiceBean aib = new ArcaInvoiceBean(invv, ord);
				arcaInvoiceList.add(aib);
			}

			LOGGER.info("Generazione report..");
			ArcaInvoicesReport.generateReport(arcaInvoiceList, Config.ARCA_REPORT_DIR, Config.ARCA_REPORT_INVOICE_CSV);
			LOGGER.info("EXPORT FATTURE COMPLETATO");
			
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
