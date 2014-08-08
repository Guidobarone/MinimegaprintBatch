package it.arakne.dbing.magento.csv.bean.arca;

import it.arakne.dbing.magento.csv.generator.ArcaConverter;
import it.arakne.dbing.magento.runner.wrapper.MagjaWrapper;
import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.NumberUtil;
import it.arakne.dbing.magento.util.StringUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.code.magja.model.order.Invoice;
import com.google.code.magja.model.order.Order;

public class ArcaInvoiceBean {

	private String tipodoc;
	private String codicecf;	
	private String datadoc;
	private String numerodoc;
	private String pag;
	private String totdoc;
	private String totesen;
	private String totimp;
	private String totiva;
	private String discountamount;
	private String shippingamount;
	
	private List<ArcaInvoiceItemBean> itemList;

	public ArcaInvoiceBean() {
		super();
	}

	public ArcaInvoiceBean(Invoice inv, Order ord) throws ParseException {
		super();
		//		System.out.println("invoice "+inv.getId()+" : "+inv);
		this.tipodoc = StringUtil.cutStringRight(Config.TIPO_DOCUMENTO_FATTURA, 2);
		
		/**
		 * Il customer ID all'interno dell'invoice non viene valorizzato quindi prelevare
		 * questo valore da getCustomerId restituisce SEMPRE un valore null
		 */
		//this.codicecf = StringUtil.cutStringRight(NumberUtil.idPaddingWithPrefix(inv.getCustomerId()), 6);
		this.datadoc = StringUtil.cutStringRight(ArcaConverter.getDatadoc(inv), 8);
		
		//tronca invoice id in base al property arca.report.fatture.tagliaidfattura
		if (Config.ARCA_REPORT_TAGLIAIDFATTURA.equals("true"))
			this.numerodoc = StringUtil.cutStringLeft(NumberUtil.idPaddingWithSuffix(inv.getId()), 8);
		else
			this.numerodoc = NumberUtil.idPaddingWithSuffix(inv.getId());
		
		this.pag = StringUtil.cutStringRight(ArcaConverter.getPag(ord), 4);
		this.totdoc = StringUtil.cutStringRight(ArcaConverter.getTotdoc(inv), 8);
		this.totesen = StringUtil.cutStringRight(Config.TOTALE_ESENTE, 8);
		this.totimp = StringUtil.cutStringRight(ArcaConverter.getTotimp(inv), 8);
		this.totiva = StringUtil.cutStringRight(ArcaConverter.getTotiva(inv), 8);
		
		/**
		 * Prelevamento dell'ordine per l'accesso al customer ID
		 */
		try {
			
			Order order = null;
			
			if (inv.getOrderNumber().split("\\-").length == 1) {
				// INT
				order = MagjaWrapper.getOrderById(Config.conf, Integer.parseInt(inv.getOrderNumber()) );
			} else {
				// STRING
				order = MagjaWrapper.getOrderById(Config.conf, inv.getOrderNumber() );
			}
			
			this.codicecf = StringUtil.cutStringRight(NumberUtil.idPaddingWithPrefix( order.getCustomer().getId() ), 6);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		this.discountamount = ord.getDiscountAmount()+"";
		
		this.shippingamount = StringUtil.cutStringRight( ArcaConverter.getShippingAmount(inv), 8 );
		
		//preleva la lista degli item nella fattura
		List<ArcaInvoiceItemBean> itemList = new ArrayList<ArcaInvoiceItemBean>();
		List<Map> invItemList = (List<Map>) inv.get("items");
		for (Map<String,String> item : invItemList) {
			//System.out.println("chiave mappa: "+item);
			//Map<String,String> itemMap = invItemList.get(item);
			ArcaInvoiceItemBean aiib = new ArcaInvoiceItemBean(item);
			itemList.add(aiib);
		}
		this.itemList = itemList;
	}

	public String getTipodoc() {
		return tipodoc;
	}

	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}

	public String getCodicecf() {
		return codicecf;
	}

	public void setCodicecf(String codicecf) {
		this.codicecf = codicecf;
	}

	public String getDatadoc() {
		return datadoc;
	}

	public void setDatadoc(String datadoc) {
		this.datadoc = datadoc;
	}

	public String getNumerodoc() {
		return numerodoc;
	}

	public void setNumerodoc(String numerodoc) {
		this.numerodoc = numerodoc;
	}

	public String getPag() {
		return pag;
	}

	public void setPag(String pag) {
		this.pag = pag;
	}

	public String getTotdoc() {
		return totdoc;
	}

	public void setTotdoc(String totdoc) {
		this.totdoc = totdoc;
	}

	public String getTotesen() {
		return totesen;
	}

	public void setTotesen(String totesen) {
		this.totesen = totesen;
	}

	public String getTotimp() {
		return totimp;
	}

	public void setTotimp(String totimp) {
		this.totimp = totimp;
	}

	public String getTotiva() {
		return totiva;
	}

	public void setTotiva(String totiva) {
		this.totiva = totiva;
	}
	
	public String getDiscountamount() {
		return discountamount;
	}

	public void setDiscountamount(String discountamount) {
		this.discountamount = discountamount;
	}
	
	public String getShippingAmount() {
		return shippingamount;
	}
	
	public void setShippingAmount(String shippingamount) {
		this.shippingamount = shippingamount;
	}

	public List<ArcaInvoiceItemBean> getItemList() {
		return itemList;
	}

	public void setItemList(List<ArcaInvoiceItemBean> itemList) {
		this.itemList = itemList;
	}

}
