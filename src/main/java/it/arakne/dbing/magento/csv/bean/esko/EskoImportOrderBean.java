package it.arakne.dbing.magento.csv.bean.esko;

import java.util.List;

public class EskoImportOrderBean {

	private String orderId;
	private List<String> trackingNumbers;
	
	public EskoImportOrderBean(String orderId, List<String> trackingNumbers) {
		super();
		this.orderId = orderId;
		this.trackingNumbers = trackingNumbers;
	}
	public EskoImportOrderBean() {
		super();
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public List<String> getTrackingNumbers() {
		return trackingNumbers;
	}
	public void setTrackingNumbers(List<String> trackingNumbers) {
		this.trackingNumbers = trackingNumbers;
	}
	
}
