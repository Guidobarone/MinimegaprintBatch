package it.arakne.dbing.magento.util;

public class StringUtil {

	public static String convertCsvSeparator(String input){
		if (input==null || input.isEmpty()) {
			return "";
		}
		if (input.contains(Config.CSV_SEPARATOR+"")) {
			return input.replaceAll(Config.CSV_SEPARATOR+"", Config.ALTERNATIVE_CSV_SEPARATOR);
		}
		return input;
	}

	public static String escapeLineFeed(String input){
		if (input==null || input.isEmpty()) {
			return "";
		}
		input = input.replaceAll("\r\n", " ").replaceAll("\n", " ");
		return input;
	}

	public static String cutStringRight(String input, int dim){
		if (input==null || input.isEmpty()) {
			return "";
		}
		if (input.length()>dim) {
			input = input.substring(0, dim);
		}
		return input;
	}

	public static String cutStringLeft(String input, int dim){
		if (input==null || input.isEmpty()) {
			return "";
		}
		if (input.length()>dim) {
			input = input.substring(input.length()-dim, input.length());
		}
		return input;
	}

	public static String returnVoidForEmpty(String input){
		if (input==null || input.isEmpty()) {
			return "";
		}
		return input;
	}

	public static String insertQuote(String input){
		if (input==null || input.isEmpty()) {
			return "\"\"";
		}
		return "\""+input+"\"";
	}
}
