package it.arakne.dbing.magento.csv.generator;

import it.arakne.dbing.magento.csv.bean.CustomOptionBean;
import it.arakne.dbing.magento.util.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.code.magja.model.order.OrderItem;

public class ItemReport {

	static Character CSV_SEPARATOR = Config.CSV_SEPARATOR;

	public static void generateReport(OrderItem item, List<CustomOptionBean> list, String reportDir, String fileReport) 
			throws IOException{
		File csvFile = new File(reportDir + File.separatorChar + fileReport);
//		System.out.println("csvfile : "+csvFile);
		FileWriter writer = new FileWriter(csvFile);
		generateCSVHeader(writer, list);
		writer.append(item.getDescription());
		writer.append(CSV_SEPARATOR);
		writer.append(item.getName());
		writer.append(CSV_SEPARATOR);
		writer.append(item.getSku());
		writer.append(CSV_SEPARATOR);
		writer.append(item.getBaseCost()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getBaseOriginalPrice()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getBasePrice()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getBaseRowInvoiced()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getBaseRowTotal()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getId()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getOriginalPrice()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getParentItemId()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getPrice()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getProductId()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getQtyOrdered()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getRowInvoiced()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getRowTotal()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getRowWeight()+"");
		writer.append(CSV_SEPARATOR);
		writer.append(item.getWeight()+"");
//		writer.append(CSV_SEPARATOR);
//		writer.append(StringUtil.convertCsvSeparator(item.getProductOptions()));
		//custom options
		if (list!=null && !list.isEmpty()) {
			for (CustomOptionBean customOptionBean : list) {
				writer.append(CSV_SEPARATOR);
				writer.append(customOptionBean.getValue());
			}
		}
		//end line
		writer.append("\n");

		writer.flush();
		writer.close();
	}

	private static void generateCSVHeader(FileWriter writer, List<CustomOptionBean> list) 
			throws IOException{
		//item values
		writer.append("Description");
		writer.append(CSV_SEPARATOR);
		writer.append("Name");
		writer.append(CSV_SEPARATOR);
		writer.append("Sku");
		writer.append(CSV_SEPARATOR);
		writer.append("BaseCost"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("BaseOriginalPrice"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("BasePrice"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("BaseRowInvoiced"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("BaseRowTotal"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("Id"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("OriginalPrice"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("ParentItemId"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("Price"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("ProductId"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("QtyOrdered"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("RowInvoiced"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("RowTotal"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("RowWeight"+"");
		writer.append(CSV_SEPARATOR);
		writer.append("Weight"+"");
//		writer.append(CSV_SEPARATOR);
//		writer.append("ProductOptions");
		//custom options
		if (list!=null && !list.isEmpty()) {
			for (CustomOptionBean customOptionBean : list) {
				writer.append(CSV_SEPARATOR);
				writer.append(customOptionBean.getLabel());
			}
		}
		//end line
		writer.append("\n");
	}
}
