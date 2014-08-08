package it.arakne.dbing.magento.util;

import it.arakne.dbing.magento.csv.bean.AdditionalDataBean;
import it.arakne.dbing.magento.csv.bean.CustomOptionBean;
import it.arakne.dbing.magento.csv.bean.OptionValueBean;
import it.arakne.dbing.magento.xml.flat.MagentoFlatGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.lorecraft.phparser.SerializedPhpParser;

public class UnserializerUtil {

	private final static Logger LOGGER = Logger.getLogger(MagentoFlatGenerator.class.getName());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "a:2:{s:15:\"info_buyRequest\";a:5:{s:4:\"uenc\";s:80:\"aHR0cDovL21pbmltZWdhcHJpbnQuY29tL2RldmVsMy9wcm9kb3R0by1jb24tb3B6aW9uZS0xOC5odG1s\";s:7:\"product\";s:2:\"18\";s:15:\"related_product\";s:0:\"\";s:7:\"options\";a:2:{i:63;s:3:\"174\";i:64;s:3:\"CMM\";}s:3:\"qty\";s:1:\"0\";}s:7:\"options\";a:2:{i:0;a:7:{s:5:\"label\";s:13:\"CustomOption1\";s:5:\"value\";s:1:\"M\";s:11:\"print_value\";s:1:\"M\";s:9:\"option_id\";s:2:\"63\";s:11:\"option_type\";s:9:\"drop_down\";s:12:\"option_value\";s:3:\"174\";s:11:\"custom_view\";b:0;}i:1;a:7:{s:5:\"label\";s:13:\"CustomOption2\";s:5:\"value\";s:3:\"CMM\";s:11:\"print_value\";s:3:\"CMM\";s:9:\"option_id\";s:2:\"64\";s:11:\"option_type\";s:5:\"field\";s:12:\"option_value\";s:3:\"CMM\";s:11:\"custom_view\";b:0;}}}";
		List<CustomOptionBean> list = unserializeCustomOptions(input);
		for (CustomOptionBean customOptionBean : list) {
			System.out.println(customOptionBean);
		}
		String additionalData = "a:9:{s:4:\"type\";s:9:\"image/gif\";s:5:\"title\";s:13:\"cellulare.gif\";s:10:\"quote_path\";s:68:\"/media/custom_options/quote/c/e/b17601772d92c53bdb7230ea35a00d6e.gif\";s:10:\"order_path\";s:68:\"/media/custom_options/order/c/e/b17601772d92c53bdb7230ea35a00d6e.gif\";s:8:\"fullpath\";s:102:\"/var/www/html/minimegaprint_devel4/media/custom_options/quote/c/e/b17601772d92c53bdb7230ea35a00d6e.gif\";s:4:\"size\";s:4:\"3689\";s:5:\"width\";i:147;s:6:\"height\";i:197;s:10:\"secret_key\";s:20:\"b17601772d92c53bdb72\";}";
		AdditionalDataBean adb = unserializeFileAttachmentMap(additionalData);
		System.out.println(adb);
	}

	public static List<CustomOptionBean> unserializeCustomOptions(String customOptions) {
		List<CustomOptionBean> optionsList = new ArrayList<CustomOptionBean>();
		if (customOptions!=null && !customOptions.isEmpty()) {
			SerializedPhpParser serializedPhpParser = new SerializedPhpParser(customOptions);
			Object result = serializedPhpParser.parse();
			if (result instanceof HashMap<?,?>){
				Map mappa = (HashMap) result;
				Object opzioni = mappa.get("options");
				if (opzioni instanceof Map){
					Map mappaOpzioni = (Map) opzioni;
					Iterator<?> iter = mappaOpzioni.keySet().iterator();
					while (iter.hasNext()){
						Integer key = (Integer) iter.next();
						Object customOption = mappaOpzioni.get(key);
						if (customOption instanceof Map){
							Map optMap = (Map) customOption;
							CustomOptionBean cob = new CustomOptionBean();
							cob.setCustomView((Boolean) optMap.get("custom_view") + "");
							cob.setLabel((String) optMap.get("label"));
							cob.setOptionId((String) optMap.get("option_id"));
							cob.setOptionType((String) optMap.get("option_type"));
							cob.setOptionValue((String) optMap.get("option_value"));
							cob.setPrintValue((String) optMap.get("print_value"));
							cob.setValue((String) optMap.get("value"));
							optionsList.add(cob);
						}
					}
				}
			}
		}
		return optionsList;
	}

	public static Map<String,CustomOptionBean> unserializeCustomOptionsMap(String customOptions) {
		Map<String,CustomOptionBean> optionsMap = new HashMap<String,CustomOptionBean>();
		if (customOptions!=null && !customOptions.isEmpty()) {
			SerializedPhpParser serializedPhpParser = new SerializedPhpParser(customOptions);
			Object result = serializedPhpParser.parse();
			if (result instanceof HashMap<?,?>){
				Map mappa = (HashMap) result;
				Object opzioni = mappa.get("options");
				if (opzioni instanceof Map){
					Map mappaOpzioni = (Map) opzioni;
					Iterator<?> iter = mappaOpzioni.keySet().iterator();
					while (iter.hasNext()){
						Integer key = (Integer) iter.next();
						Object customOption = mappaOpzioni.get(key);
						if (customOption instanceof Map){
							Map optMap = (Map) customOption;
							CustomOptionBean cob = new CustomOptionBean();
							cob.setCustomView((Boolean) optMap.get("custom_view") + "");
							cob.setLabel((String) optMap.get("label"));
							cob.setOptionId((String) optMap.get("option_id"));
							cob.setOptionType((String) optMap.get("option_type"));
							cob.setOptionValue((String) optMap.get("option_value"));
							cob.setPrintValue((String) optMap.get("print_value"));
							cob.setValue((String) optMap.get("value"));
							optionsMap.put((String) optMap.get("label"),cob);
						}
					}
				}
			}
		}
		return optionsMap;
	}

	public static AdditionalDataBean unserializeFileAttachmentMap(String additionalData) {
		AdditionalDataBean adb = null;
		if (additionalData!=null && !additionalData.isEmpty()) {
			SerializedPhpParser serializedPhpParser = new SerializedPhpParser(additionalData);

			Object result = serializedPhpParser.parse();
			if (result instanceof HashMap<?,?>){
				Map<String,String> mappa = (HashMap<String,String>) result;
				adb = new AdditionalDataBean(mappa);
			}
		}
		return adb;
	}

	public static OptionValueBean unserializeFileUploadOptionValue(String fileUploadOptionValue) {
		OptionValueBean ovb = null;
		if (fileUploadOptionValue!=null && !fileUploadOptionValue.isEmpty()) {
			SerializedPhpParser serializedPhpParser = new SerializedPhpParser(fileUploadOptionValue);
			Object optionValue = serializedPhpParser.parse();

			if (optionValue instanceof HashMap<?,?>){
				Map<String,String> optionValueMap = (HashMap<String,String>) optionValue;

				//get first element
				Object optionValueFirstElement = null;
				Iterator it = optionValueMap.entrySet().iterator();
			    if (it.hasNext()) {
			        Map.Entry pairs = (Map.Entry)it.next();
			        optionValueFirstElement = pairs.getValue();
			    }
			    LOGGER.info("optionValueFirstElement: "+optionValueFirstElement+"");

			    if (optionValueFirstElement instanceof HashMap<?,?>){
			    	Map<String,String> optionValueFirstElementMap = (HashMap<String,String>) optionValueFirstElement;
			    	LOGGER.info("optionValueFirstElementMap: "+optionValueFirstElementMap+"");
			    	ovb = new OptionValueBean(optionValueFirstElementMap);
			    }
			}
		}
		return ovb;
	}
}
