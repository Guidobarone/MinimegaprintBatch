package it.arakne.dbing.magento.csv.bean;

public class CustomOptionBean {

	private String label;
	private String value;
	private String printValue;
	private String optionId;
	private String optionType;
	private String optionValue;
	private String customView;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPrintValue() {
		return printValue;
	}
	public void setPrintValue(String printValue) {
		this.printValue = printValue;
	}
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public String getOptionType() {
		return optionType;
	}
	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public String getCustomView() {
		return customView;
	}
	public void setCustomView(String customView) {
		this.customView = customView;
	}
	
    @Override
    public String toString() {
        return "CustomOptionBean [label=" + label
                + ", value=" + value
                + ", printValue=" + printValue
                + ", optionId=" + optionId
                + ", optionType=" + optionType
                + ", optionValue=" + optionValue
                + ", customView=" + customView + "]";
    }
}
