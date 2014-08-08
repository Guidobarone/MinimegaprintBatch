package it.arakne.dbing.magento.runner.arca;

import it.arakne.dbing.magento.csv.bean.CustomerAddressBean;
import it.arakne.dbing.magento.csv.bean.arca.ArcaCustomerBean;
import it.arakne.dbing.magento.csv.generator.arca.ArcaCustomerReport;
import it.arakne.dbing.magento.logger.MagentoLogger;
import it.arakne.dbing.magento.runner.wrapper.MagjaWrapper;
import it.arakne.dbing.magento.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.code.magja.service.ServiceException;

public class ArcaClientiExporter {

	private final static Logger LOGGER = Logger.getLogger(ArcaClientiExporter.class.getName());

	public static void main(String[] args) {

		if (args.length!=1){
			System.err.println("ARGOMENTO FILE DI PROPERTIES NON RILEVATO");
			System.exit(1);
		}
		try {
			Config.setup(args[0]);

			//REPORT DEI CLIENTI
			MagentoLogger.setup(Config.ARCA_CLIENTI_LOG_FILE);
			LOGGER.info("REPORT DEI CLIENTI");
			LOGGER.info("Recupero lista dei clienti..");
			LOGGER.finest("API USER: "+Config.conf.getApiUser());
			LOGGER.finest("API KEY: "+Config.conf.getApiKey());
			LOGGER.finest("API URL: "+Config.conf.getRemoteHost());
			List<CustomerAddressBean> custList = MagjaWrapper.getCustomerList(Config.conf, true);
			LOGGER.info("Recuperati "+custList.size()+" clienti");
			LOGGER.finest("list: "+custList);
			List<ArcaCustomerBean> arcaList = new ArrayList<ArcaCustomerBean>();
			for (CustomerAddressBean customer : custList) {
				System.out.println("Customer: "+customer.getCustomer().getFirstName()+" "+customer.getCustomer().getLastName());
				
				ArcaCustomerBean acb = new ArcaCustomerBean(customer);
				arcaList.add(acb);
			}

			LOGGER.info("Generazione report..");
			ArcaCustomerReport.generateReport(arcaList, Config.ARCA_REPORT_DIR, Config.ARCA_REPORT_CUSTOMER_CSV);
			LOGGER.info("GENERAZIONE EXPORT CLIENTI COMPLETATA");

		} catch (ServiceException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

}
