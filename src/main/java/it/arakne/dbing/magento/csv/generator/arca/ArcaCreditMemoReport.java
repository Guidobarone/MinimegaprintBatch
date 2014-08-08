package it.arakne.dbing.magento.csv.generator.arca;

import it.arakne.dbing.magento.csv.bean.arca.ArcaCreditMemoBean;
import it.arakne.dbing.magento.csv.bean.arca.ArcaCreditMemoItemBean;
import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.NumberUtil;
import it.arakne.dbing.magento.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArcaCreditMemoReport {

	static Character CSV_SEPARATOR = Config.CSV_SEPARATOR;

	public static void generateReport(List<ArcaCreditMemoBean> creditMemoList, String reportDir, String fileReport) 
			throws IOException{
		
		File csvFile = new File(reportDir + File.separatorChar + fileReport);
		
		FileWriter writer = new FileWriter(csvFile);
		generateCSVHeader(writer);
		for (ArcaCreditMemoBean creditMemo : creditMemoList) {
			//credit memo values
			writer.append(StringUtil.insertQuote( Config.CREDITMEMO_ROW ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( creditMemo.getTipodoc() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( creditMemo.getCodicecf() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( creditMemo.getDatadoc() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( creditMemo.getNumerodoc() ));			
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( creditMemo.getPag() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( creditMemo.getTotdoc() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( creditMemo.getTotesen() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( creditMemo.getTotimp() ));
			writer.append(CSV_SEPARATOR);
			writer.append(StringUtil.insertQuote( creditMemo.getTotiva() ));
			insertCreditMemoRowSuffix(writer);
			//end line
			writer.append("\n");
			
			Double shipam = new Double( creditMemo.getShippingAmount() );
			Double totimb = new Double(0);
			
			for (ArcaCreditMemoItemBean acmib : creditMemo.getItemList()){
				writer.append(StringUtil.insertQuote( Config.CREDITMEMOITEM_ROW ));
				writer.append(CSV_SEPARATOR);
				//clonazione intestazione nota di credito
				writer.append(StringUtil.insertQuote( creditMemo.getTipodoc() ));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( creditMemo.getCodicecf() ));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( creditMemo.getDatadoc() ));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( creditMemo.getNumerodoc() ));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( creditMemo.getPag() ));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( creditMemo.getTotdoc() ));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( creditMemo.getTotesen() ));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( creditMemo.getTotimp() ));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( creditMemo.getTotiva() ));
				writer.append(CSV_SEPARATOR);
				//fine clonazione

				//insertCreditMemoRowPrefix(writer);
				writer.append(StringUtil.insertQuote( acmib.getQuantita()+" "+acmib.getCodicearti() ));
				writer.append(CSV_SEPARATOR);
//				writer.append(StringUtil.insertQuote( aiib.getPrezzoun() ));
				writer.append(StringUtil.insertQuote( acmib.getPrezzoTot() ));
				writer.append(CSV_SEPARATOR);
//				writer.append(StringUtil.insertQuote( aiib.getQuantita() ));
				writer.append(StringUtil.insertQuote( "1"));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( acmib.getUnmisura() ));
				writer.append(CSV_SEPARATOR);
				writer.append(StringUtil.insertQuote( acmib.getCds() ));
				//end line
				writer.append("\n");
				
				//TEST CALC IMB				
				Double prezzotot = Double.parseDouble(acmib.getPrezzoTot());
				Double perc_imb = Double.parseDouble(acmib.getPercImballaggio());		//percentuale imballaggio prodotto
				Double imbitm = prezzotot * perc_imb / 100;								//costo imballaggio totale prodotto
				
				totimb += imbitm;
			}
			
			String num_totimb = NumberUtil.convert2Price(totimb);
			String num_totsped = NumberUtil.convert2Price(shipam - totimb);
			
			if (shipam == 0) {
				num_totimb = "0";
				num_totsped = "0";
			}

			Double discam = new Double( creditMemo.getDiscountamount() );
			if (discam != 0)
				addCustomItemRow("SCN", discam+"", "1", Config.UNMISURA, Config.CDS, writer, creditMemo);

			//imballaggio | Stampa imballaggio in base al properties arca.report.fatture.stampaimballaggio
			if (Config.ARCA_REPORT_STAMPAIMBALLAGGIO.equals("true")) {
				addCustomItemRow("IMB", num_totimb, "1", Config.UNMISURA, Config.CDS, writer, creditMemo );
			}
			else if (!num_totimb.equals( "0" )){
				addCustomItemRow("IMB", num_totimb, "1", Config.UNMISURA, Config.CDS, writer, creditMemo );
			}
			
			addCustomItemRow("SPED", num_totsped, "1", Config.UNMISURA, Config.CDS, writer, creditMemo);
			
		}
		writer.flush();
		writer.close();
	}

	private static void generateCSVHeader(FileWriter writer) 
			throws IOException{
		//customer values
		writer.append(StringUtil.insertQuote("Tiporiga"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Tipodoc"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Codicecf"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Datadoc"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Numerodoc"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Pag"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Totdoc"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Totesen"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Totimp"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Totiva"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Codicearti"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Prezzo"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Quantita"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Unmisura"));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote("Cds"));
		//end line
		writer.append("\n");
	}

	private static void insertCreditMemoRowPrefix(FileWriter writer) 
			throws IOException{
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
	}

	private static void insertCreditMemoRowSuffix(FileWriter writer) 
			throws IOException{
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
		writer.append(CSV_SEPARATOR);
	}
	
	private static void addCustomItemRow(String name, String val, String qty, String unmisura, String cds, FileWriter writer, ArcaCreditMemoBean creditMemo) throws IOException {
		writer.append(StringUtil.insertQuote( Config.CREDITMEMOITEM_ROW ));
		writer.append(CSV_SEPARATOR);
		/*clonazione intestazione fattura*/
		writer.append(StringUtil.insertQuote( creditMemo.getTipodoc() ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( creditMemo.getCodicecf() ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( creditMemo.getDatadoc() ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( creditMemo.getNumerodoc() ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( creditMemo.getPag() ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( creditMemo.getTotdoc() ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( creditMemo.getTotesen() ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( creditMemo.getTotimp() ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( creditMemo.getTotiva() ));
		writer.append(CSV_SEPARATOR);
		/*fine clonazione*/
		
		/* output shipment */
		writer.append(StringUtil.insertQuote( name ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( val ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( qty ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( unmisura ));
		writer.append(CSV_SEPARATOR);
		writer.append(StringUtil.insertQuote( cds ));
		
		writer.append("\n");
	}
}
