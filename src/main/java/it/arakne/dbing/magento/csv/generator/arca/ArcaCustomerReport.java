package it.arakne.dbing.magento.csv.generator.arca;

import it.arakne.dbing.magento.csv.bean.arca.ArcaCustomerBean;
import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArcaCustomerReport {

	static Character CSV_SEPARATOR = Config.CSV_SEPARATOR;

	public static void generateReport(List<ArcaCustomerBean> custList, String reportDir, String fileReport) 
			throws IOException{
		File csvFile = new File(reportDir + File.separatorChar + fileReport);
		FileWriter writer = new FileWriter(csvFile);
		generateCSVHeader(writer);
		for (ArcaCustomerBean custab : custList) {
			//customer values
			writer.append(StringUtil.insertQuote( custab.getAllegati() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getCap() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getCodfiscale() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getCodice() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getCodiceiso() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getDescrizione() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getElenchi() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getEmail() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getEstero() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getIndirizzo() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getLocalita() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getPartiva() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getProv() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getStatoCF() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getSupragsoc() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( custab.getTelefono() ));
			//end line
			writer.append("\n");
		}
		writer.flush();
		writer.close();
	}

	private static void generateCSVHeader(FileWriter writer) 
			throws IOException{
		//customer values
		writer.append(StringUtil.insertQuote("Allegati"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Cap"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Codfiscale"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Codice"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Codiceiso"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Descrizione"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Elenchi"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Email"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Estero"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Indirizzo"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Localita"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Partiva"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Prov"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("statoCF"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Supragsoc"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Telefono"));
		//end line
		writer.append("\n");
	}
}
