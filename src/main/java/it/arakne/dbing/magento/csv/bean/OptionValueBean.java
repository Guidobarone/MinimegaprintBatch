package it.arakne.dbing.magento.csv.bean;

import java.util.Map;

public class OptionValueBean {

	private String type;
	private String title;
	private String quotePath;
	private String orderPath;
	private String fullpath;
	private String size;
	
	public OptionValueBean(Map<String,String> optionValueMap) {
		super();
		this.setFullpath(optionValueMap.get("fullpath"));
		this.setOrderPath(optionValueMap.get("order_path"));
		this.setQuotePath(optionValueMap.get("quote_path"));
		this.setSize(optionValueMap.get("size"));
		this.setTitle(optionValueMap.get("title"));
		this.setType(optionValueMap.get("type"));
	}
	
	public OptionValueBean() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuotePath() {
		return quotePath;
	}

	public void setQuotePath(String quotePath) {
		this.quotePath = quotePath;
	}

	public String getOrderPath() {
		return orderPath;
	}

	public void setOrderPath(String orderPath) {
		this.orderPath = orderPath;
	}

	public String getFullpath() {
		return fullpath;
	}

	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}
