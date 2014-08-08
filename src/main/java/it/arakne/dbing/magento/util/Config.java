package it.arakne.dbing.magento.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

import com.google.code.magja.soap.SoapConfig;

public class Config {

	public static  Character CSV_SEPARATOR ;//= ',';
	public static  String ALTERNATIVE_CSV_SEPARATOR ;//= "##";

//	public static  String TIPO_CLIENTE_SOCIETA_MAGENTO ;//= "127"; // "4";
//	public static  String TIPO_CLIENTE_ASSOCIAZIONE_MAGENTO ;//= "128";//"5";
//	public static  String TIPO_CLIENTE_PRIVATO_MAGENTO ;//= "125";//"6";
//	public static  String TIPO_CLIENTE_DITTA_MAGENTO ;//= "126"; //"3";
//	public static  String TIPO_CLIENTE_SOCIETA_ARCA ;//= "G";
//	public static  String TIPO_CLIENTE_ASSOCIAZIONE_ARCA ;//= "G";
//	public static  String TIPO_CLIENTE_PRIVATO_ARCA ;//= "N";
//	public static  String TIPO_CLIENTE_DITTA_ARCA ;//= "F";
	
//	public static  String TIPO_CLIENTE_SPA_MAGENTO;
//	public static  String TIPO_CLIENTE_SRL_MAGENTO;
//	public static  String TIPO_CLIENTE_SAPA_MAGENTO;
//	public static  String TIPO_CLIENTE_SS_MAGENTO;
//	public static  String TIPO_CLIENTE_SNC_MAGENTO;
//	public static  String TIPO_CLIENTE_SAS_MAGENTO;
//	public static  String TIPO_CLIENTE_SCOOP_MAGENTO;
//	public static  String TIPO_CLIENTE_ENTEPUBBLICO_MAGENTO;
//	public static  String TIPO_CLIENTE_CONSORZIO_MAGENTO;
//	public static  String TIPO_CLIENTE_CONDOMINIO_MAGENTO;
//	public static  String TIPO_CLIENTE_IMPRESAINDIVIDUALE_MAGENTO;
//	public static  String TIPO_CLIENTE_PRIVATO_MAGENTO;
//	public static  String TIPO_CLIENTE_ASSOCIAZIONE_MAGENTO;
//	public static  String TIPO_CLIENTE_SPA_ARCA;
//	public static  String TIPO_CLIENTE_SRL_ARCA;
//	public static  String TIPO_CLIENTE_SAPA_ARCA;
//	public static  String TIPO_CLIENTE_SS_ARCA;
//	public static  String TIPO_CLIENTE_SNC_ARCA;
//	public static  String TIPO_CLIENTE_SAS_ARCA;
//	public static  String TIPO_CLIENTE_SCOOP_ARCA;
//	public static  String TIPO_CLIENTE_ENTEPUBBLICO_ARCA;
//	public static  String TIPO_CLIENTE_CONSORZIO_ARCA;
//	public static  String TIPO_CLIENTE_CONDOMINIO_ARCA;
//	public static  String TIPO_CLIENTE_IMPRESAINDIVIDUALE_ARCA;
//	public static  String TIPO_CLIENTE_PRIVATO_ARCA;
//	public static  String TIPO_CLIENTE_ASSOCIAZIONE_ARCA;

	/*
	 * Mappa chiave-valore con
	 * CHIAVE	>	magento.tipo.cliente
	 * VALORE	>	arca.tipo.cliente
	 */
	public static  HashMap<String, String> TIPO_CLIENTE;
	
	public static  String TIPO_CLIENTE_NULLO_ARCA;//= "";//"NULL";
	public static  String TIPO_CLIENTE_INVALIDO_ARCA;//= "";//"INVALID";
	
	/*
	 * Mappa chiave-valore con
	 * CHIAVE	>	magento.professione
	 * VALORE	>	arca.professione
	 */
	public static  HashMap<String, String> PROFESSIONE;
	
	public static  String PROFESSIONE_NULLO_ARCA;//= "";//"NULL";
	public static  String PROFESSIONE_INVALIDO_ARCA;//= "";//"INVALID";
	
	public static  String TIPO_PAGAMENTO_PAYPAL_MAGENTO ;//= "paypal_standard";
	public static  String TIPO_PAGAMENTO_BONIFICO_MAGENTO ;//= "checkmo";
	public static  String TIPO_PAGAMENTO_CONTRASSEGNO_MAGENTO ;//= "cashondelivery";
	public static  String TIPO_PAGAMENTO_PAYPAL_ARCA ;//= "BPP";
	public static  String TIPO_PAGAMENTO_BONIFICO_ARCA ;//= "BBA";
	public static  String TIPO_PAGAMENTO_CONTRASSEGNO_ARCA ;//= "C2";
	public static  String TIPO_PAGAMENTO_NULLO_ARCA ;//= "";//"NULL";
	public static  String TIPO_PAGAMENTO_INVALIDO_ARCA ;//= "";//"INVALID";

	public static  String CODICE_CLIENTE_PREFIX ;//= "CA";

	public static  String STATOCF ;//= "C";

	public static  String TIPO_DOCUMENTO_FATTURA;//= "FT";
	public static  String TIPO_DOCUMENTO_NOTACREDITO;//= "C9";

	public static  String FATTURA_SUFFIX ;//= "/W";

	public static  String UNMISURA ;//= "NR";
	public static  String CDS ;//= "DIN";

	public static  String INVOICE_ROW ;//= "1";
	public static  String INVOICEITEM_ROW ;//= "2";
	
	public static  String CREDITMEMO_ROW ;//= "1";
	public static  String CREDITMEMOITEM_ROW ;//= "2";

	public static  String TOTALE_ESENTE ;//= "0.00";

	public static  String ESKO_ORDER_NUMBER_PREFIX ;//= "01";

	public static  String MAGENTO_API_USER ;//= "apimento";
	public static  String MAGENTO_API_PWD ;//= "4nn4d3ll4m4gl14n4";
	public static  String MAGENTO_API_URL ;//= "http://minimegaprint.com/devel1/api/";

	public static SoapConfig conf;

	public static  String ARCA_REPORT_DIR ;//= "G:\\Magento\\report\\arca\\";
	public static  String ARCA_REPORT_PRODUCT_CSV ;//= "Articoli.csv";
	public static  String ARCA_REPORT_CUSTOMER_CSV ;//= "Clienti.csv";
	public static  String ARCA_REPORT_INVOICE_CSV ;//= "Fatture.csv";
	public static  String ARCA_REPORT_CREDITMEMO_CSV ;//= "NoteCredito.csv";
	
	public static  String ARCA_REPORT_STAMPAIMBALLAGGIO;//=true;
	public static  String ARCA_REPORT_TAGLIAIDFATTURA;//=true;

	public static  String ESKO_FLAT_XML_REPORT_DIR ;//= "G:\\Magento\\report\\esko\\";
	public static  String ESKO_REPORT_FLAT_XML_SUFFIX ;//= "item.xml";
	public static  String ESKO_ZIP_DIR ;//= "G:\\Magento\\report\\esko\\";
	public static  String ESKO_TRANSFORMED_XML_REPORT_DIR ;//= "G:\\Magento\\report\\esko\\";
	public static  String ESKO_TRANSFORMED_XML_REPORT_SUFFIX ;//= "G:\\Magento\\report\\esko\\";
	public static  String ESKO_XML_XSLT_FILENAME ;//= "magentoTransform.xsl";

	public static  String ESKO_ORDER_STATUS_FILTERING ;//= "processing_uploaded";
	public static  String ESKO_ORDER_STATUS_UPDATE ;//= "exported";
	public static  String ESKO_ORDER_STATUS_UPDATE_COMMENT ;//= "Ordine inviato alla stampa";
	public static  Boolean ESKO_ORDER_STATUS_UPDATE_NOTIFY_CUSTOMER ;//= true;
	public static  String ESKO_ORDER_STATUS_ERROR ;//= "export_error";
	public static  String ESKO_ORDER_STATUS_ERROR_COMMENT ;//= "Ordine inviato alla stampa";
	public static  Boolean ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER ;//= true;

	public static  String ESKO_ORDER_DUE_DATE_CUSTOM_OPTION_LABEL;
	public static  String ESKO_ORDER_DUE_DATE_CUSTOM_OPTION_SUFFIX;
	public static  String ESKO_ORDER_DUE_DATE_DELAY;

	public static  String ESKO_ORDER_REPORT_DIR;
	public static  String ESKO_ORDER_REPORT_EXTENSION ;

	public static  String ESKO_SHIPPING_INPUT_DIR ;
	public static  String ESKO_SHIPPING_OUTPUT_DIR;
	public static  String ESKO_SHIPPING_OUTPUT_ERROR_DIR;
	public static  Boolean ESKO_SHIPPING_REMOVE_FILE;
	public static  String ESKO_SHIPPING_UPDATE_STATUS;
	public static  String ESKO_SHIPPING_CARRIER_NAME;
	public static  String ESKO_SHIPPING_TITLE_PREFIX;
	public static  Boolean ESKO_SHIPPING_SEND_EMAIL;
	public static  Boolean ESKO_SHIPPING_NOTIFY_CUSTOMER;
	public static  String ESKO_SHIPPING_COMMENT;

//	public static  Level LOG_LEVEL = Level.INFO;
	public static  String LOG_LEVEL_STRING ;
	public static  String ARCA_ARTICOLI_LOG_FILE;
	public static  String ARCA_CLIENTI_LOG_FILE;
	public static  String ARCA_FATTURE_LOG_FILE;
	public static  String ARCA_NOTECREDITO_LOG_FILE;
	public static  String ESKO_EXPORT_LOG_FILE;
	public static  String ESKO_IMPORT_LOG_FILE ;

	public static  String INPUT_DATE_FORMAT ;//= "yyyy-MM-dd HH:mm:ss";
	public static  String LOCAL_INPUT_DATE_FORMAT = "yyyy-MM-dd";
	public static  String OUTPUT_DATE_FORMAT ;//= "ddMMyyyy";

	public static  DecimalFormat formatter = (DecimalFormat)DecimalFormat.getInstance(new Locale("en"));
	public static  String PRICE_FORMAT_PATTERN = ".00";
	
	public static  String ESKO_CARTELLA_TEMPORANEA;
	public static  String MESSAGGIO_ERRORE_ASSENZA_FILE;
	public static  String MESSAGGIO_ERRORE_COPIA_FILE;
	
	public static void setup(String propertiesFile) {
		try {
			Properties p  = new Properties();
			File f = new File(propertiesFile);
			p.load(new FileInputStream(f));

			CSV_SEPARATOR = ((String) p.get("csv.separator")).charAt(0);
			ALTERNATIVE_CSV_SEPARATOR = p.getProperty("csv.separator.alternative");

//			TIPO_CLIENTE_SOCIETA_MAGENTO = p.getProperty("magento.tipo.cliente.societa");//"127"; // "4";
//			TIPO_CLIENTE_ASSOCIAZIONE_MAGENTO = p.getProperty("magento.tipo.cliente.associazione");//"128";//"5";
//			TIPO_CLIENTE_PRIVATO_MAGENTO = p.getProperty("magento.tipo.cliente.privato");//"125";//"6";
//			TIPO_CLIENTE_DITTA_MAGENTO = p.getProperty("magento.tipo.cliente.ditta");//"126"; //"3";
//			TIPO_CLIENTE_SOCIETA_ARCA = p.getProperty("arca.tipo.cliente.societa");//"G";
//			TIPO_CLIENTE_ASSOCIAZIONE_ARCA = p.getProperty("arca.tipo.cliente.associazione");//"G";
//			TIPO_CLIENTE_PRIVATO_ARCA = p.getProperty("arca.tipo.cliente.privato");//"N";
//			TIPO_CLIENTE_DITTA_ARCA = p.getProperty("arca.tipo.cliente.ditta");//"F";
			
//			TIPO_CLIENTE_SPA_MAGENTO					= p.getProperty("magento.tipo.cliente.spa");
//			TIPO_CLIENTE_SRL_MAGENTO					= p.getProperty("magento.tipo.cliente.srl");
//			TIPO_CLIENTE_SAPA_MAGENTO					= p.getProperty("magento.tipo.cliente.sapa");
//			TIPO_CLIENTE_SS_MAGENTO						= p.getProperty("magento.tipo.cliente.ss");
//			TIPO_CLIENTE_SNC_MAGENTO					= p.getProperty("magento.tipo.cliente.snc");
//			TIPO_CLIENTE_SAS_MAGENTO					= p.getProperty("magento.tipo.cliente.sas");
//			TIPO_CLIENTE_SCOOP_MAGENTO					= p.getProperty("magento.tipo.cliente.scoop");
//			TIPO_CLIENTE_ENTEPUBBLICO_MAGENTO			= p.getProperty("magento.tipo.cliente.entepubblico");
//			TIPO_CLIENTE_CONSORZIO_MAGENTO				= p.getProperty("magento.tipo.cliente.consorzio");
//			TIPO_CLIENTE_CONDOMINIO_MAGENTO				= p.getProperty("magento.tipo.cliente.condominio");
//			TIPO_CLIENTE_IMPRESAINDIVIDUALE_MAGENTO		= p.getProperty("magento.tipo.cliente.impresaindividuale");
//			TIPO_CLIENTE_PRIVATO_MAGENTO				= p.getProperty("magento.tipo.cliente.privato");
//			TIPO_CLIENTE_ASSOCIAZIONE_MAGENTO			= p.getProperty("magento.tipo.cliente.associazione");
//			TIPO_CLIENTE_SPA_ARCA					= p.getProperty("arca.tipo.cliente.spa");
//			TIPO_CLIENTE_SRL_ARCA					= p.getProperty("arca.tipo.cliente.srl");
//			TIPO_CLIENTE_SAPA_ARCA					= p.getProperty("arca.tipo.cliente.sapa");
//			TIPO_CLIENTE_SS_ARCA					= p.getProperty("arca.tipo.cliente.ss");
//			TIPO_CLIENTE_SNC_ARCA					= p.getProperty("arca.tipo.cliente.snc");
//			TIPO_CLIENTE_SAS_ARCA					= p.getProperty("arca.tipo.cliente.sas");
//			TIPO_CLIENTE_SCOOP_ARCA					= p.getProperty("arca.tipo.cliente.scoop");
//			TIPO_CLIENTE_ENTEPUBBLICO_ARCA			= p.getProperty("arca.tipo.cliente.entepubblico");
//			TIPO_CLIENTE_CONSORZIO_ARCA				= p.getProperty("arca.tipo.cliente.consorzio");
//			TIPO_CLIENTE_CONDOMINIO_ARCA			= p.getProperty("arca.tipo.cliente.condominio");
//			TIPO_CLIENTE_IMPRESAINDIVIDUALE_ARCA	= p.getProperty("arca.tipo.cliente.impresaindividuale");
//			TIPO_CLIENTE_PRIVATO_ARCA				= p.getProperty("arca.tipo.cliente.privato");
//			TIPO_CLIENTE_ASSOCIAZIONE_ARCA			= p.getProperty("arca.tipo.cliente.associazione");

			/*
			 * Accesso ai dati "tipo cliente" con una hashmap
			 */
			TIPO_CLIENTE = new HashMap<String, String>();
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.spa") , 				p.getProperty("arca.tipo.cliente.spa") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.srl"), 				p.getProperty("arca.tipo.cliente.srl") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.sapa"), 				p.getProperty("arca.tipo.cliente.sapa") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.ss"), 				p.getProperty("arca.tipo.cliente.ss") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.snc"), 				p.getProperty("arca.tipo.cliente.snc") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.sass"), 				p.getProperty("arca.tipo.cliente.sass") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.scoop"), 				p.getProperty("arca.tipo.cliente.scoop") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.entepubblico"), 		p.getProperty("arca.tipo.cliente.entepubblico") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.consorzio"), 			p.getProperty("arca.tipo.cliente.consorzio") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.condominio"), 		p.getProperty("arca.tipo.cliente.consominio") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.impresaindividuale"),	p.getProperty("arca.tipo.cliente.impresaindividuale") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.privato"), 			p.getProperty("arca.tipo.cliente.privato") );
			TIPO_CLIENTE.put( p.getProperty("magento.tipo.cliente.associazione"), 		p.getProperty("arca.tipo.cliente.associazione") );

//			TIPO_CLIENTE_NULLO_ARCA = p.getProperty("arca.tipo.cliente.nullo");//"";//"NULL";
//			TIPO_CLIENTE_INVALIDO_ARCA = p.getProperty("arca.tipo.cliente.invalido");//"";//"INVALID";
			TIPO_CLIENTE_NULLO_ARCA	= p.getProperty("arca.tipo.cliente.nullo");
			TIPO_CLIENTE_INVALIDO_ARCA = p.getProperty("arca.tipo.cliente.invalido");

			/*
			 * Accesso ai dati "professione" con una hashmap
			 */
			PROFESSIONE = new HashMap<String, String>();
			PROFESSIONE.put( p.getProperty("magento.professione.studente") , 		p.getProperty("arca.professione.studente") );
			PROFESSIONE.put( p.getProperty("magento.professione.impiegato") , 		p.getProperty("arca.professione.impiegato") );
			PROFESSIONE.put( p.getProperty("magento.professione.pensionato") , 		p.getProperty("arca.professione.pensionato") );

			PROFESSIONE_NULLO_ARCA	= p.getProperty("arca.professione.nullo");
			PROFESSIONE_INVALIDO_ARCA = p.getProperty("arca.professione.invalido");
			
			TIPO_PAGAMENTO_PAYPAL_MAGENTO = p.getProperty("magento.tipo.pagamento.paypal");//"paypal_standard";
			TIPO_PAGAMENTO_BONIFICO_MAGENTO = p.getProperty("magento.tipo.pagamento.bonifico");//"checkmo";
			TIPO_PAGAMENTO_CONTRASSEGNO_MAGENTO = p.getProperty("magento.tipo.pagamento.contrassegno");//"cashondelivery";
			TIPO_PAGAMENTO_PAYPAL_ARCA = p.getProperty("arca.tipo.pagamento.paypal");//"BPP";
			TIPO_PAGAMENTO_BONIFICO_ARCA = p.getProperty("arca.tipo.pagamento.bonifico");//"BBA";
			TIPO_PAGAMENTO_CONTRASSEGNO_ARCA = p.getProperty("arca.tipo.pagamento.contrassegno");//"C2";
			TIPO_PAGAMENTO_NULLO_ARCA = p.getProperty("arca.tipo.pagamento.nullo");//"";//"NULL";
			TIPO_PAGAMENTO_INVALIDO_ARCA = p.getProperty("arca.tipo.pagamento.invalido");//"";//"INVALID";

			CODICE_CLIENTE_PREFIX = p.getProperty("arca.codice.cliente.prefisso");//"CA";

			STATOCF = p.getProperty("arca.statocf");//"C";

			TIPO_DOCUMENTO_FATTURA= p.getProperty("arca.tipo.documento");//"FT";
			TIPO_DOCUMENTO_NOTACREDITO= p.getProperty("arca.tipo.documento.notacredito");//"NC";

			FATTURA_SUFFIX = p.getProperty("arca.fatture.suffisso");//"/W";

			UNMISURA = p.getProperty("arca.unmisura");//"NR";
			CDS = p.getProperty("arca.cds");//"DIN";

			INVOICE_ROW = p.getProperty("arca.fatture.indice.riga.fattura");//"1";
			INVOICEITEM_ROW = p.getProperty("arca.fatture.indice.riga.articolo");//"2";

			CREDITMEMO_ROW = p.getProperty("arca.notecredito.indice.riga.notacredito");//"1";
			CREDITMEMOITEM_ROW = p.getProperty("arca.notecredito.indice.riga.articolo");//"2";
			
			TOTALE_ESENTE = p.getProperty("arca.fatture.totale.esente");//"0.00";

			ESKO_ORDER_NUMBER_PREFIX = p.getProperty("esko.ordine.numero.prefisso");//"";

			MAGENTO_API_USER = p.getProperty("magento.api.utente");//"apimento";
			MAGENTO_API_PWD = p.getProperty("magento.api.key");//"4nn4d3ll4m4gl14n4";
			MAGENTO_API_URL = p.getProperty("magento.api.url");//"http://minimegaprint.com/api/";

			conf = new SoapConfig(MAGENTO_API_USER, MAGENTO_API_PWD, MAGENTO_API_URL);

			ARCA_REPORT_DIR = p.getProperty("arca.report.dir");//"G:\\Magento\\report\\arca\\";
			ARCA_REPORT_PRODUCT_CSV = p.getProperty("arca.report.articoli.nomefile");//"Articoli.csv";
			ARCA_REPORT_CUSTOMER_CSV = p.getProperty("arca.report.clienti.nomefile");//"Clienti.csv";
			ARCA_REPORT_INVOICE_CSV = p.getProperty("arca.report.fatture.nomefile");//"Fatture.csv";
			ARCA_REPORT_CREDITMEMO_CSV = p.getProperty("arca.report.notecredito.nomefile");//"NoteCredito.csv";
			
			/* ARCA SETUP EXPORTER */
			ARCA_REPORT_STAMPAIMBALLAGGIO = p.getProperty("arca.report.fatture.stampaimballaggio");
			ARCA_REPORT_TAGLIAIDFATTURA = p.getProperty("arca.report.fatture.tagliaidfattura");

			ESKO_FLAT_XML_REPORT_DIR = p.getProperty("esko.report.flat.xml.dir");//"G:\\Magento\\report\\esko\\";
			ESKO_REPORT_FLAT_XML_SUFFIX = p.getProperty("esko.report.flat.xml.filename.suffix");//"item.xml";
			ESKO_ZIP_DIR = p.getProperty("esko.report.zip.dir");//"G:\\Magento\\report\\esko\\";
			ESKO_TRANSFORMED_XML_REPORT_DIR = p.getProperty("esko.report.transform.xml.dir");//"G:\\Magento\\report\\esko\\";
			ESKO_TRANSFORMED_XML_REPORT_SUFFIX = p.getProperty("esko.report.transform.xml.filename.suffix");
			ESKO_XML_XSLT_FILENAME = p.getProperty("esko.report.xsl.filename");//"magentoTransform.xsl";

			ESKO_ORDER_STATUS_FILTERING = p.getProperty("esko.ordini.filtro.status");//"processing_uploaded";
			ESKO_ORDER_STATUS_UPDATE = p.getProperty("esko.ordini.update.status");//"exported";
			ESKO_ORDER_STATUS_UPDATE_COMMENT = p.getProperty("esko.ordini.update.commento");//"Ordine inviato alla stampa";
			ESKO_ORDER_STATUS_UPDATE_NOTIFY_CUSTOMER = Boolean.valueOf( p.getProperty("esko.ordini.update.notifica") );//true;
			ESKO_ORDER_STATUS_ERROR = p.getProperty("esko.ordini.error.status");//"export_error";
			ESKO_ORDER_STATUS_ERROR_COMMENT = p.getProperty("esko.ordini.error.commento");//"Ordine inviato alla stampa";
			ESKO_ORDER_STATUS_ERROR_NOTIFY_CUSTOMER = Boolean.valueOf( p.getProperty("esko.ordini.error.notifica") );//true;

			ESKO_ORDER_DUE_DATE_CUSTOM_OPTION_LABEL = p.getProperty("esko.ordini.duedate");//"dueDate";
			ESKO_ORDER_DUE_DATE_CUSTOM_OPTION_SUFFIX = p.getProperty("esko.ordini.duedate.suffix");//"bd";
			ESKO_ORDER_DUE_DATE_DELAY = p.getProperty("esko.ordini.duedate.delay");//"0";

			ESKO_ORDER_REPORT_EXTENSION = p.getProperty("esko.ordini.report.estensione") ;//true;
			ESKO_ORDER_REPORT_DIR = p.getProperty("esko.ordini.report.directory") ;//true;

			ESKO_SHIPPING_INPUT_DIR = p.getProperty("esko.spedizione.input.directory");//=G:\\Magento\\report\\esko\\input\\
			ESKO_SHIPPING_OUTPUT_DIR = p.getProperty("esko.spedizione.output.directory");//=G:\\Magento\\report\\esko\\output\\
			ESKO_SHIPPING_OUTPUT_ERROR_DIR = p.getProperty("esko.spedizione.output.error.directory");//=G:\\Magento\\report\\esko\\output\\error\\
			ESKO_SHIPPING_REMOVE_FILE = Boolean.valueOf( p.getProperty("esko.spedizione.rimuovi.file") );//=false
			ESKO_SHIPPING_UPDATE_STATUS = p.getProperty("esko.spedizione.update.status");//=shipped
			ESKO_SHIPPING_CARRIER_NAME = p.getProperty("esko.spedizione.corriere");//=GLS
			ESKO_SHIPPING_TITLE_PREFIX = p.getProperty("esko.spedizione.titolo");//=COLLO
			ESKO_SHIPPING_SEND_EMAIL = Boolean.valueOf( p.getProperty("esko.spedizione.spedisci.email") );//=true
			ESKO_SHIPPING_NOTIFY_CUSTOMER = Boolean.valueOf( p.getProperty("esko.spedizione.notifica.commento") );//=true
			ESKO_SHIPPING_COMMENT = p.getProperty("esko.spedizione.commento");//=Il tuo ordine è stato spedito

//			Level LOG_LEVEL = Level.INFO;
			ARCA_ARTICOLI_LOG_FILE = p.getProperty("magento.arca.articoli.log.file");//=G:\\Magento\\report\\arcaArticoli.log
			ARCA_CLIENTI_LOG_FILE = p.getProperty("magento.arca.clienti.log.file");//=G:\\Magento\\report\\arcaClienti.log
			ARCA_FATTURE_LOG_FILE = p.getProperty("magento.arca.fatture.log.file");//=G:\\Magento\\report\\arcaFatture.log
			ARCA_NOTECREDITO_LOG_FILE = p.getProperty("magento.arca.notecredito.log.file");//=G:\\Magento\\report\\arcaNoteCredito.log
			ESKO_EXPORT_LOG_FILE = p.getProperty("magento.esko.export.log.file");//=G:\\Magento\\report\\eskoExport.log
			ESKO_IMPORT_LOG_FILE = p.getProperty("magento.esko.import.log.file");//=G:\\Magento\\report\\eskoImport.log

			LOG_LEVEL_STRING = p.getProperty("magento.log.level");//"G:\\Magento\\report\\logging.txt";

			INPUT_DATE_FORMAT = p.getProperty("magento.data.formato.input");//"yyyy-MM-dd HH:mm:ss";
			OUTPUT_DATE_FORMAT = p.getProperty("magento.data.formato.output");//"ddMMyyyy";

//			DecimalFormat formatter = (DecimalFormat)DecimalFormat.getInstance(new Locale("en"));
			PRICE_FORMAT_PATTERN = ".00";
			
			ESKO_CARTELLA_TEMPORANEA = p.getProperty("magento.esko.file.cartella.tmp");
			MESSAGGIO_ERRORE_ASSENZA_FILE = p.getProperty( "magento.esko.file.messaggio.errore.assenzafile" );
			MESSAGGIO_ERRORE_COPIA_FILE = p.getProperty( "magento.esko.file.messaggio.errore.spostamentofile" );

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}