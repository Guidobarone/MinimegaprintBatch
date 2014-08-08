package it.arakne.dbing.magento.xml.generator.bean;

public class RootBean {
	private String workflow;
	private String  product;
	private String  orderId;
	private String  itemId;
	private String  quantity;
	private String  title;
	private String  priority;
	private ComponentBean component1;
	private ComponentBean component2;
	private ComponentBean component3;
	
	public String getWorkflow() {
		return workflow;
	}
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public ComponentBean getComponent1() {
		return component1;
	}
	public void setComponent1(ComponentBean component1) {
		this.component1 = component1;
	}
	public ComponentBean getComponent2() {
		return component2;
	}
	public void setComponent2(ComponentBean component2) {
		this.component2 = component2;
	}
	public ComponentBean getComponent3() {
		return component3;
	}
	public void setComponent3(ComponentBean component3) {
		this.component3 = component3;
	}

}
