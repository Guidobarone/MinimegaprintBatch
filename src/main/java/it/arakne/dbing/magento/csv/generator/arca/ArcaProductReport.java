package it.arakne.dbing.magento.csv.generator.arca;

import it.arakne.dbing.magento.csv.bean.arca.ArcaProductBean;
import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArcaProductReport {

	static Character CSV_SEPARATOR = Config.CSV_SEPARATOR;

	public static void generateReport(List<ArcaProductBean> productList, String reportDir, String fileReport) 
			throws IOException{
		File csvFile = new File(reportDir + File.separatorChar + fileReport);
		FileWriter writer = new FileWriter(csvFile);
		generateCSVHeader(writer);
		for (ArcaProductBean prod : productList) {
			//invoice values
			writer.append(StringUtil.insertQuote( prod.getCodice() ));
			writer.append(CSV_SEPARATOR);
//			writer.append(StringUtil.insertQuote( prod.getDescrizion() ));
			writer.append(StringUtil.insertQuote( prod.getNome() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( prod.getUnmisura() ));
			writer.append("\n");
		}
		writer.flush();
		writer.close();
	}

	private static void generateCSVHeader(FileWriter writer) 
			throws IOException{
		//product values
		writer.append(StringUtil.insertQuote("Codice"));
		writer.append(CSV_SEPARATOR);
//		writer.append(StringUtil.insertQuote("Descrizion"));
		writer.append(StringUtil.insertQuote("NomeProd"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Unmisura"));
		//end line
		writer.append("\n");
	}

}
