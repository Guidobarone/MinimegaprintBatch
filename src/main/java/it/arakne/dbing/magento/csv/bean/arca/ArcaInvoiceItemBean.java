package it.arakne.dbing.magento.csv.bean.arca;

import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.NumberUtil;
import it.arakne.dbing.magento.util.StringUtil;

import java.util.Map;
import java.util.logging.Logger;

import org.lorecraft.phparser.SerializedPhpParser;

import com.google.code.magja.model.order.InvoiceItem;

public class ArcaInvoiceItemBean {

	private String codicearti;
	private String prezzoun;
	
	private String prezzotot;
	
	private String quantita;
	private String unmisura;
	private String cds;
	
	private String percimballaggio;

	private final static Logger LOGGER = Logger.getLogger(ArcaInvoiceItemBean.class.getName());

	public ArcaInvoiceItemBean() {
		super();
	}

	public ArcaInvoiceItemBean(Map<String,String> item) {
		super();

//		Product prodotto = null;
//		try {
//			prodotto = MagjaWrapper.getProductInfo(Config.conf, item.get("product_id"));
//		} catch (ServiceException e) {
//			LOGGER.severe("product_id: "+item.get("product_id"));
//			LOGGER.severe("sku: "+item.get("sku"));
//			LOGGER.severe("name: "+item.get("name"));
//			e.printStackTrace();
//		}
//		this.codicearti = StringUtil.cutStringRight( prodotto!=null ? (String) prodotto.get("codiceinterno") : "", 20); /*inserire codice interno*/
		
		String additionaldata = item.get("additional_data");
		String tmp_codicearti = "";
		String tmp_percimballaggio = "0";
		
		if (additionaldata != null) {
			SerializedPhpParser serializedPhpParser = new SerializedPhpParser(additionaldata);
			Object result = null;
			
			try {
				result = serializedPhpParser.parse();
			}
			catch (Exception e) {
				//e.printStackTrace();
			}
			
			//Map<String, String> result = (Map<String, String>)
			
			if (result != null && result instanceof Map<?,?>) {
				Map<String, String> r = (Map<String, String>)result;
				
				tmp_codicearti = StringUtil.cutStringRight( r.get("codiceinterno")!=null ? r.get("codiceinterno") : "", 20);
				tmp_percimballaggio = StringUtil.cutStringRight( r.get("perc_imballaggio") , 8);
			}
		}
		
		this.codicearti = tmp_codicearti;
		this.percimballaggio = tmp_percimballaggio;
		
//		this.prezzoun = StringUtil.cutStringRight(NumberUtil.convert2Price(Double.parseDouble(item.get("price"))), 8);
		this.prezzoun = item.get("price");
		
		//Elaborazione prezzo totale valutando tutte le unita decimali arrivate in input
//		Double price_tmp = Double.parseDouble(item.get("price"));
//		Double qty_tmp = Double.parseDouble(item.get("qty"));
//		this.prezzotot = StringUtil.cutStringRight( NumberUtil.convert2Price(new Double(price_tmp * qty_tmp).toString()) , 8);
		this.prezzotot = NumberUtil.convert2Price(item.get("row_total"));
		
		this.quantita = StringUtil.cutStringRight(NumberUtil.convert2Integer(item.get("qty")), 8);
		this.unmisura = StringUtil.cutStringRight(Config.UNMISURA, 2);
		this.cds = StringUtil.cutStringRight(Config.CDS, 3);
		
		//this.percimballaggio = StringUtil.cutStringRight( (prodotto.get("perc_imballaggio") instanceof String && prodotto != null) ? (String)prodotto.get("perc_imballaggio") : "0", 8);

		//this.percimballaggio = StringUtil.cutStringRight( prodotto !=null && prodotto.get("perc_imballaggio") instanceof String ? (String)prodotto.get("perc_imballaggio") : "0", 8);

	}

	public ArcaInvoiceItemBean(InvoiceItem item) {
		super();
		this.codicearti = item.getSku();
		this.prezzoun = item.getPrice()+"";
		this.quantita = item.getQty()+"";
		this.unmisura = Config.UNMISURA;
		this.cds = Config.CDS;
	}

	public String getCodicearti() {
		return codicearti;
	}

	public void setCodicearti(String codicearti) {
		this.codicearti = codicearti;
	}

	public String getPrezzoun() {
		return prezzoun;
	}

	public void setPrezzoun(String prezzoun) {
		this.prezzoun = prezzoun;
	}

	public String getQuantita() {
		return quantita;
	}

	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}

	public String getUnmisura() {
		return unmisura;
	}

	public void setUnmisura(String unmisura) {
		this.unmisura = unmisura;
	}

	public String getCds() {
		return cds;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}
	
	public String getPercImballaggio() {
		return percimballaggio;
	}
	
	public void setPercImballaggio(String percimballaggio) {
		this.percimballaggio = percimballaggio;
	}
	
	public String getPrezzoTot() {
		return this.prezzotot;
	}
	
	public void setPrezzoTot(String prezzotot) {
		this.prezzotot = prezzotot;
	}

}
