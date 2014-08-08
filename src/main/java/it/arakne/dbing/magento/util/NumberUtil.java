package it.arakne.dbing.magento.util;


public class NumberUtil {

	public static String idPaddingWithPrefix(Integer id){
		String codice = Config.CODICE_CLIENTE_PREFIX;
		if (id==null || id<0) {
			return codice + "0000";
		}
		else if (id<10) {
			return codice + "000" + id;
		}
		else if (id<100) {
			return codice + "00" + id;
		}
		else if (id<1000) {
			return codice + "0" + id;
		}
		else {
			return codice + id;
		}
	}

	public static String idPaddingWithSuffix(Integer id){
		String codice = Config.FATTURA_SUFFIX;
		if (id==null || id<0) {
			return "000000" + codice;
		}
		else if (id<10) {
			return "00000" + id + codice;
		}
		else if (id<100) {
			return "0000" + id + codice;
		}
		else if (id<1000) {
			return "000" + id + codice;
		}
		else if (id<10000) {
			return "00" + id + codice;
		}
		else if (id<100000) {
			return "0" + id + codice;
		}
		else {
			return id + codice;
		}
	}

	public static String convert2Price(String price){
		Config.formatter.applyPattern(Config.PRICE_FORMAT_PATTERN);
		return Config.formatter.format(Double.parseDouble(price));
	}

	public static String convert2Price(Double price){
		Config.formatter.applyPattern(Config.PRICE_FORMAT_PATTERN);
		return Config.formatter.format(1.0*( Math.round(price*100))/100);
	}

	public static String convert2Integer(String qty){
		Double d = Double.parseDouble(qty);
		return d.intValue()+"";
	}

	public static String convert2Integer(Double qty){
		return Math.round(qty)+"";
	}

//	public static void main(String[] args) {
//		String price = "3.00000000000000000000000000000000";
//		Double dprice = new Double("3.4400000000000004");
//		System.out.println(dprice);
//		System.out.println(convert2Price(dprice));
//		Config.formatter.applyPattern(Config.PRICE_FORMAT_PATTERN);
//		System.out.println(dprice);
//		System.out.println(dprice*100);
//		System.out.println(Math.round(dprice*100));
//		System.out.println((1.0*( Math.round(dprice*100))/100));
//		System.out.println(Config.formatter.format(1.0*( Math.round(dprice*100))/100));
//	}

}
