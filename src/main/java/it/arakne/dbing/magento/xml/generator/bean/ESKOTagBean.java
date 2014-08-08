package it.arakne.dbing.magento.xml.generator.bean;

public class ESKOTagBean {
	
	private String tagName;
	private String tagValue;
	private Boolean present;
	private String associatedType;
	private Boolean mandatory;
	
	public ESKOTagBean(String tagName, Boolean mandatory) {
		super();
		this.tagName = tagName;
		this.mandatory = mandatory;
	}
	
	public Boolean isValid() {
		return this.mandatory || this.present;
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagValue() {
		return tagValue;
	}
	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}
	public Boolean getPresent() {
		return present;
	}
	public void setPresent(Boolean present) {
		this.present = present;
	}
	public String getAssociatedType() {
		return associatedType;
	}
	public void setAssociatedType(String associatedType) {
		this.associatedType = associatedType;
	}
	public Boolean getMandatory() {
		return mandatory;
	}
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	

}
