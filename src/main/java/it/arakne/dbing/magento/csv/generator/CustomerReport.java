package it.arakne.dbing.magento.csv.generator;

import it.arakne.dbing.magento.csv.bean.CustomerAddressBean;
import it.arakne.dbing.magento.util.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.code.magja.model.customer.Customer;
import com.google.code.magja.model.customer.CustomerAddress;

public class CustomerReport {

	static Character CSV_SEPARATOR = Config.CSV_SEPARATOR;

	public static void generateReport(List<CustomerAddressBean> custList, Boolean withAddress, String reportDir, String fileReport) 
			throws IOException{
		File csvFile = new File(reportDir + File.pathSeparator + fileReport);
		FileWriter writer = new FileWriter(csvFile);
		generateCSVHeader(writer, withAddress);
		for (CustomerAddressBean custab : custList) {
			//customer values
			Customer cust = custab.getCustomer();
			writer.append(cust.getCreatedAt());
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getEmail());
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getFirstName());
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getLastName());
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getMiddleName());
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getPassword());
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getPrefix());
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getSuffix());
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getGender()+"");
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getGroupId()+"");
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getId()+"");
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getStoreId()+"");
			writer.append(CSV_SEPARATOR);
			writer.append(cust.getWebsiteId()+"");
			if (withAddress){
				//address values
				Integer addrNum = 0;
				for (CustomerAddress ca : custab.getAddressesList()) {
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getCity());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getCompany());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getCountryCode());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getFax());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getFirstName());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getLastName());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getMiddleName());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getPostCode());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getPrefix());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getRegion());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getStreet());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getSuffix());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getTelephone());
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getDefaultBilling()+"");
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getDefaultShipping()+"");
					writer.append(CSV_SEPARATOR);
					writer.append(ca.getId()+"");
					addrNum++;
					if ( addrNum != custab.getAddressesList().size()){
						writer.append("\n");
						for (int i=0 ; i<12 ; i++){
							writer.append(CSV_SEPARATOR);
						}
					}
				}
			}
			//end line
			writer.append("\n");


		}

		writer.flush();
		writer.close();
	}

	private static void generateCSVHeader(FileWriter writer, Boolean withAddress) 
			throws IOException{
		//customer values
		writer.append("CreatedAt");
		writer.append(CSV_SEPARATOR);
		writer.append("Email");
		writer.append(CSV_SEPARATOR);
		writer.append("FirstName");
		writer.append(CSV_SEPARATOR);
		writer.append("LastName");
		writer.append(CSV_SEPARATOR);
		writer.append("MiddleName");
		writer.append(CSV_SEPARATOR);
		writer.append("Password");
		writer.append(CSV_SEPARATOR);
		writer.append("Prefix");
		writer.append(CSV_SEPARATOR);
		writer.append("Suffix");
		writer.append(CSV_SEPARATOR);
		writer.append("Gender");
		writer.append(CSV_SEPARATOR);
		writer.append("GroupId");
		writer.append(CSV_SEPARATOR);
		writer.append("Id");
		writer.append(CSV_SEPARATOR);
		writer.append("StoreId");
		writer.append(CSV_SEPARATOR);
		writer.append("WebsiteId");
		if (withAddress){
			//address values
			writer.append(CSV_SEPARATOR);
			writer.append("City");
			writer.append(CSV_SEPARATOR);
			writer.append("Company");
			writer.append(CSV_SEPARATOR);
			writer.append("CountryCode");
			writer.append(CSV_SEPARATOR);
			writer.append("Fax");
			writer.append(CSV_SEPARATOR);
			writer.append("FirstName");
			writer.append(CSV_SEPARATOR);
			writer.append("LastName");
			writer.append(CSV_SEPARATOR);
			writer.append("MiddleName");
			writer.append(CSV_SEPARATOR);
			writer.append("PostCode");
			writer.append(CSV_SEPARATOR);
			writer.append("Prefix");
			writer.append(CSV_SEPARATOR);
			writer.append("Region");
			writer.append(CSV_SEPARATOR);
			writer.append("Street");
			writer.append(CSV_SEPARATOR);
			writer.append("Suffix");
			writer.append(CSV_SEPARATOR);
			writer.append("Telephone");
			writer.append(CSV_SEPARATOR);
			writer.append("DefaultBilling");
			writer.append(CSV_SEPARATOR);
			writer.append("DefaultShipping");
			writer.append(CSV_SEPARATOR);
			writer.append("Id");
		}
		//end line
		writer.append("\n");
	}
}
