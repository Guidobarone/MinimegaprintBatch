package it.arakne.dbing.magento.csv.bean.arca;

import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.StringUtil;

import com.google.code.magja.model.product.Product;

public class ArcaProductBean {

	private String codice;
//	private String descrizion;
	private String nome;
	private String unmisura;


	public ArcaProductBean() {
		super();
	}

	public ArcaProductBean(Product prod) {
		super();

		/*
		Product prodAttr = MinimegaprintClientV2.getProductBySKU(MinimegaprintClientV2.conf, item.getSku());
		System.out.println(prodAttr.get("codiceinterno"));

		Element codiceInternoTag = doc.createElement("CodiceInterno");
		itemTag.appendChild(codiceInternoTag);
		Text codiceInternoTagValue = doc.createTextNode(prodAttr.get("codiceinterno")+"");
		*/

		this.codice = StringUtil.cutStringRight((String) prod.get("codiceinterno"), 20);
//		this.descrizion = StringUtil.cutStringRight(StringUtil.escapeLineFeed(prod.getDescription()), 40) ;
		this.nome = prod.getName();
		this.unmisura = StringUtil.cutStringRight(Config.UNMISURA, 2);
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

//	public String getDescrizion() {
//		return descrizion;
//	}

//	public void setDescrizion(String descrizion) {
//		this.descrizion = descrizion;
//	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUnmisura() {
		return unmisura;
	}

	public void setUnmisura(String unmisura) {
		this.unmisura = unmisura;
	}

}
