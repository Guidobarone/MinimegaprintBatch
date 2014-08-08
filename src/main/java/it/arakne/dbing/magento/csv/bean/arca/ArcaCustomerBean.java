package it.arakne.dbing.magento.csv.bean.arca;

import it.arakne.dbing.magento.csv.bean.CustomerAddressBean;
import it.arakne.dbing.magento.csv.generator.ArcaConverter;
import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.StringUtil;

import java.util.List;

import com.google.code.magja.model.customer.Customer;
import com.google.code.magja.model.customer.CustomerAddress;

public class ArcaCustomerBean {

	private String allegati;
	private String cap;	
	private String codfiscale;
	private String codice;
	private String codiceiso;
	private String descrizione;
	private String elenchi;
	private String email;
	private String estero;
	private String indirizzo;
	private String localita;
	private String partiva;
	private String prov;
	private String statoCF;
	private String supragsoc;
	private String telefono;


	public ArcaCustomerBean() {
		super();
	}

	public ArcaCustomerBean(CustomerAddressBean custab) {
		super();
		Customer cust = custab.getCustomer();
		List<CustomerAddress> custList = custab.getAddressesList();
		CustomerAddress billingAddress = new CustomerAddress();
		for (CustomerAddress ca : custList) {
			if (ca.getDefaultBilling()){
				billingAddress = ca;
			}
		}
		this.allegati = StringUtil.cutStringRight(ArcaConverter.getAllegati(cust), 1);
		this.cap = StringUtil.cutStringRight(billingAddress.getPostCode(), 5) ;
		this.codfiscale = StringUtil.cutStringRight(ArcaConverter.getCodiceFiscale(cust), 16);
		this.codice = StringUtil.cutStringRight(ArcaConverter.getCodice(cust), 6);
		this.codiceiso = StringUtil.cutStringRight(billingAddress.getCountryCode(), 2);
		this.descrizione = StringUtil.cutStringRight(ArcaConverter.getDescrizione(cust), 40);
		this.elenchi = StringUtil.cutStringRight(ArcaConverter.getElenchi(cust), 1);
		this.email = StringUtil.cutStringRight( cust.getEmail(), 50) ;
		this.estero = (billingAddress.getCountryCode()!=null && "IT".equalsIgnoreCase(billingAddress.getCountryCode() ) ? "0" : "1");
		this.indirizzo = StringUtil.cutStringRight( billingAddress.getStreet(), 60).replace('\n', ' ');
		this.localita = StringUtil.cutStringRight( billingAddress.getCity() , 30);
		this.partiva = StringUtil.cutStringRight(ArcaConverter.getPartitaIva(cust), 17);
		this.prov = StringUtil.cutStringRight( billingAddress.getRegion(), 2);
		this.statoCF = StringUtil.cutStringRight(Config.STATOCF, 1);
		this.supragsoc = StringUtil.cutStringRight(ArcaConverter.getSupragsoc(cust), 40);
		this.telefono = StringUtil.cutStringRight(ArcaConverter.getTelefono(cust), 20);
	}

	public String getAllegati() {
		return allegati;
	}
	public void setAllegati(String allegati) {
		this.allegati = allegati;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCodfiscale() {
		return codfiscale;
	}
	public void setCodfiscale(String codfiscale) {
		this.codfiscale = codfiscale;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getCodiceiso() {
		return codiceiso;
	}
	public void setCodiceiso(String codiceiso) {
		this.codiceiso = codiceiso;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getElenchi() {
		return elenchi;
	}
	public void setElenchi(String elenchi) {
		this.elenchi = elenchi;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEstero() {
		return estero;
	}
	public void setEstero(String estero) {
		this.estero = estero;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}
	public String getPartiva() {
		return partiva;
	}
	public void setPartiva(String partiva) {
		this.partiva = partiva;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getStatoCF() {
		return statoCF;
	}
	public void setStatoCF(String statoCF) {
		this.statoCF = statoCF;
	}
	public String getSupragsoc() {
		return supragsoc;
	}
	public void setSupragsoc(String supragsoc) {
		this.supragsoc = supragsoc;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
