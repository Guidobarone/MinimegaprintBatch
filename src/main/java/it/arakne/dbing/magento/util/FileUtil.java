package it.arakne.dbing.magento.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.google.code.magja.model.order.Order;
import com.google.code.magja.model.order.OrderItem;

public class FileUtil {

	public static Boolean deleteFile (String file){
		File fromFile = new File(file);
		return fromFile.delete();
	}

	public static Boolean moveFile (String from, String toDir, String toName){
		File fromFile = new File(from);
		File toFile = new File(toDir);
		return fromFile.renameTo(new File(toFile, toName));
	}

	public static Boolean moveFile (String from, String toDir){
		File fromFile = new File(from);
		File toFile = new File(toDir);
		return fromFile.renameTo(new File(toFile, fromFile.getName()));
	}
	
	@SuppressWarnings("finally")
	public static Boolean copyFile(String from, String to, String fileName) throws IOException{		
		
		String toTmpName = Config.ESKO_CARTELLA_TEMPORANEA + fileName;
		
		File fromFile = new File(from);
		File toFile = new File(toTmpName);
		
		if (!toFile.exists()) {
			toFile.createNewFile();
		}
		
		FileChannel fin = null;
		FileChannel fout = null;
		try{
			fin = new FileInputStream(fromFile).getChannel();
			fout = new FileOutputStream(toFile).getChannel();
			
			long count = 0;
			long size = fin.size();
	        while((count += fout.transferFrom(fin, count, size-count))<size);
		}
		catch (FileNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
		finally {
			if (fin != null && fout != null) {
				if (fin.size() == fout.size()) {
					fin.close();
					fout.close();
					
					return moveFile(toTmpName, to, fileName);
				}
				
				fin.close();
				fout.close();
				
				return false;
			}
			else {
				return false;
			}
		}
	}

	public static Boolean checkFile (String fileString){
		if (fileString!=null ) {
			File file = new File(fileString);
			if (file.exists() && file.isFile() ){
				return true;
			}
			return false;
		}
		return false;
	}

	public static String getAttachName(Order o, OrderItem oi, String origFilename){
		return OrderUtil.getEskoOrderNumber(o)+"_"+oi.getId()+"_"+origFilename;
	}

	public static Boolean createOrderSummary(Order o, String orderReportDir) throws IOException{
		File orderFileReport=null; 
		FileWriter fstream=null; 
		BufferedWriter out = null ;
		try {
			String fileName = OrderUtil.getEskoOrderNumber(o)+Config.ESKO_ORDER_REPORT_EXTENSION;
			orderFileReport = new File(orderReportDir+File.separatorChar+fileName);
			fstream = new FileWriter(orderFileReport);
			out = new BufferedWriter(fstream);
			out.write("Ordine: "+OrderUtil.getEskoOrderNumber(o)+"\n");
			out.write("Numero articoli: "+o.getItems().size()+"\n");
			int i=0;
			for (OrderItem oi : o.getItems()){
				out.write("Articolo "+(++i)+" : "+oi.getSku()+"\n");
			}
			out.write("\n\n");
		}
		finally {
			out.flush();
			out.close();
		}
		return orderFileReport.exists();
	}

	public static void appendOrderStatus(String orderReportDir, String orderNumber, String status) throws IOException{
		File orderFileReport=null; 
		FileWriter fstream=null; 
		BufferedWriter out = null ;
		try {
			String fileName = orderNumber+Config.ESKO_ORDER_REPORT_EXTENSION;
			orderFileReport = new File(orderReportDir+File.separatorChar+fileName);
			fstream = new FileWriter(orderFileReport, true);
			out = new BufferedWriter(fstream);
			out.write("Ordine: "+orderNumber+"\n");
			out.write("Stato di arrivo : "+status+"\n");
		}
		finally {
			out.flush();
			out.close();
		}
	}

	public static void appendItemSummary(OrderItem oi, String orderReportDir, String orderNumber, Boolean done) throws IOException{
		File orderFileReport=null; 
		FileWriter fstream=null; 
		BufferedWriter out = null ;
		try {
			String fileName = orderNumber+Config.ESKO_ORDER_REPORT_EXTENSION;
			orderFileReport = new File(orderReportDir+File.separatorChar+fileName);
			fstream = new FileWriter(orderFileReport, true);
			out = new BufferedWriter(fstream);
			out.write("Articolo: --"+oi.getName()+"--   --"+oi.getSku()+"--");
			out.write((done ? " TERMINATO\n " : " IN COSTRUZIONE ")+"\n");
		}
		finally {
			out.flush();
			out.close();
		}
	}

	public static void appendItemAttachSummary(OrderItem oi, String orderReportDir, String orderNumber, Boolean attachOk) throws IOException{
		File orderFileReport=null; 
		FileWriter fstream=null; 
		BufferedWriter out = null ;
		try {
			String fileName = orderNumber+Config.ESKO_ORDER_REPORT_EXTENSION;
			orderFileReport = new File(orderReportDir+File.separatorChar+fileName);
			fstream = new FileWriter(orderFileReport, true);
			out = new BufferedWriter(fstream);
			out.write("Articolo: --"+oi.getName()+"--   --"+oi.getSku()+"--");
			out.write((attachOk ? " ATTACHMENT CREATO " : " ERRORE ATTACHMENT ")+"\n");
		}
		finally {
			out.flush();
			out.close();
		}
	}

	public static void appendItemTransformSummary(OrderItem oi, String orderReportDir, String orderNumber, Boolean transformOk) throws IOException{
		File orderFileReport=null; 
		FileWriter fstream=null; 
		BufferedWriter out = null ;
		try {
			String fileName = orderNumber+Config.ESKO_ORDER_REPORT_EXTENSION;
			orderFileReport = new File(orderReportDir+File.separatorChar+fileName);
			fstream = new FileWriter(orderFileReport, true);
			out = new BufferedWriter(fstream);
			out.write("Articolo: --"+oi.getName()+"--   --"+oi.getSku()+"--");
			out.write((transformOk ? " XML PER ESKO CREATO " : " ERRORE TRASFORMAZIONE XML PER ESKO ")+"\n");
		}
		finally {
			out.flush();
			out.close();
		}
	}

	//	public static void main(String[] args) {
	//		String file = "G:\\Magento\\report\\esko\\pippo.txt";
	//		System.out.println(moveFile(file, "G:\\Magento\\report\\", "Clienti.csv.old"));
	//	}
}
