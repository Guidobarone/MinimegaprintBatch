package it.arakne.dbing.magento.util;

import com.google.code.magja.model.order.Order;

public class OrderUtil {

	public static String getEskoOrderNumber(Order o){
		if (o==null) {
			return "";
		}
		return Config.ESKO_ORDER_NUMBER_PREFIX+o.getOrderNumber();
	}

	public static Double getEskoOrderNumber(String eskoOrderId){
		if (eskoOrderId==null || eskoOrderId.isEmpty()) {
			return new Double(-1);
		}
		else if (eskoOrderId.startsWith(Config.ESKO_ORDER_NUMBER_PREFIX)) {
			eskoOrderId = eskoOrderId.replaceFirst(Config.ESKO_ORDER_NUMBER_PREFIX, "");
			return Double.valueOf(eskoOrderId);
		}
		return Double.valueOf(eskoOrderId);
	}
}
