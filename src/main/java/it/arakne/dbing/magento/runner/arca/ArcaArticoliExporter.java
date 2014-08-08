package it.arakne.dbing.magento.runner.arca;

import it.arakne.dbing.magento.csv.bean.arca.ArcaProductBean;
import it.arakne.dbing.magento.csv.generator.arca.ArcaProductReport;
import it.arakne.dbing.magento.logger.MagentoLogger;
import it.arakne.dbing.magento.runner.wrapper.MagjaWrapper;
import it.arakne.dbing.magento.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.code.magja.model.product.Product;
import com.google.code.magja.service.ServiceException;

public class ArcaArticoliExporter {

	private final static Logger LOGGER = Logger.getLogger(ArcaArticoliExporter.class.getName());

	public static void main(String[] args) {

		if (args.length!=1){
			System.err.println("ARGOMENTO FILE DI PROPERTIES NON RILEVATO");
			System.exit(1);
		}
		try {
			Config.setup(args[0]);
			
			//REPORT DEI PRODOTTI
			MagentoLogger.setup(Config.ARCA_ARTICOLI_LOG_FILE);
			LOGGER.info("REPORT DEI PRODOTTI");
			List<Product> prodList = MagjaWrapper.getProductList(Config.conf);
			LOGGER.finest("prodotti : "+prodList);
			LOGGER.info("prodotti trovati: "+prodList.size());
			LOGGER.finest("lista prodotti: "+prodList);
			List<ArcaProductBean> arcaProdList = new ArrayList<ArcaProductBean>() ;
			for (Product prod : prodList) {
				Product prodotto = MagjaWrapper.getProductInfo(Config.conf, prod.getId());
				System.out.println("Product: " + prodotto.getName());
				ArcaProductBean cab = new ArcaProductBean(prodotto);
				arcaProdList.add(cab);
			}

			LOGGER.info("Generazione report..");
			ArcaProductReport.generateReport(arcaProdList, Config.ARCA_REPORT_DIR, Config.ARCA_REPORT_PRODUCT_CSV);
			LOGGER.info("EXPORT ARTICOLI COMPLETATO");

		} catch (ServiceException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}

}