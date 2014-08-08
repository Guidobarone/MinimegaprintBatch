package it.arakne.dbing.magento.csv.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.code.magja.model.customer.Customer;
import com.google.code.magja.model.customer.CustomerAddress;

public class CustomerAddressBean {

	private List<CustomerAddress> addressesList = new ArrayList<CustomerAddress>();
	private Customer customer;

	public CustomerAddressBean(Customer customer) {
		this.customer = customer;
	}

	public CustomerAddressBean(Customer customer,
			List<CustomerAddress> addressesList) {
		this.customer = customer;
		this.addressesList = addressesList;
	}

	public List<CustomerAddress> getAddressesList() {
		return addressesList;
	}
	public void setAddressesList(List<CustomerAddress> addressesList) {
		this.addressesList = addressesList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
