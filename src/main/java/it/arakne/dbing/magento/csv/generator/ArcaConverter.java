package it.arakne.dbing.magento.csv.generator;

import it.arakne.dbing.magento.util.Config;
import it.arakne.dbing.magento.util.DateUtil;
import it.arakne.dbing.magento.util.NumberUtil;
import it.arakne.dbing.magento.util.StringUtil;

import java.text.ParseException;

import com.google.code.magja.model.customer.Customer;
import com.google.code.magja.model.order.CreditMemo;
import com.google.code.magja.model.order.Invoice;
import com.google.code.magja.model.order.Order;

public class ArcaConverter {

	public static String getAllegati (Customer cust) {
		String tipoCliente = (String) cust.get("tipologia");
		
		if (tipoCliente!=null) {
			
			/*
			 * Controllo sull'esistenza della chiave "tipoCliente" indicata
			 * e assegnazione del valore relativo in caso di successo
			 */
			if ( Config.TIPO_CLIENTE.containsKey(tipoCliente) ) {
				return Config.TIPO_CLIENTE.get( tipoCliente );
			}
			else { return Config.TIPO_CLIENTE_INVALIDO_ARCA; }
		}
		else {
			return Config.TIPO_CLIENTE_NULLO_ARCA;
		}
	}

	public static String getCodiceFiscale(Customer cust) {
		return StringUtil.returnVoidForEmpty( StringUtil.cutStringRight( (String) cust.get("codicefiscale") , 16) );
	}

	public static String getCodice(Customer cust) {
		return NumberUtil.idPaddingWithPrefix(cust.getId());
	}

	public static String getDescrizione(Customer cust) {
		String descrizione = StringUtil.returnVoidForEmpty( (String) cust.get("ragionesociale") ).trim();
		if (!descrizione.equals(""))
			return StringUtil.cutStringRight(descrizione, 40);
		descrizione = StringUtil.returnVoidForEmpty( cust.getLastName() );	
		descrizione += " ";
		descrizione += StringUtil.returnVoidForEmpty(  cust.getFirstName() );
		descrizione += " ";
		descrizione += StringUtil.returnVoidForEmpty( cust.getMiddleName() );
		return StringUtil.cutStringRight(descrizione, 40);
	}

	public static String getElenchi (Customer cust) {
		String partitaIva = StringUtil.returnVoidForEmpty( (String) cust.get("partitaiva") );
		if (partitaIva==null || partitaIva.isEmpty()) {
			return "0"; 
		}
		return "1";
	}

	public static String getPartitaIva (Customer cust) {
		return StringUtil.returnVoidForEmpty( StringUtil.cutStringRight( (String) cust.get("partitaiva"), 17) );
	}

	public static String getSupragsoc(Customer cust) {
		String ragsoc = StringUtil.returnVoidForEmpty( (String) cust.get("ragionesociale") ).trim();
		if ( ragsoc.length() > 40 ) {
			return StringUtil.cutStringRight( ragsoc.substring(40), 30);
		}
		return "";
	}

	public static String getTelefono (Customer cust) {
		return StringUtil.returnVoidForEmpty( StringUtil.cutStringRight( (String) cust.get("telefono"), 20) );
	}

	public static String getPag(Order ord) {
		System.out.println("payment: "+ord.getPayment());
		String paymentMethod = ord.getPayment().getMethod();
		if (paymentMethod!=null) {
			if (Config.TIPO_PAGAMENTO_BONIFICO_MAGENTO.equalsIgnoreCase(paymentMethod)) {
				return Config.TIPO_PAGAMENTO_BONIFICO_ARCA;
			}
			else if (Config.TIPO_PAGAMENTO_CONTRASSEGNO_MAGENTO.equalsIgnoreCase(paymentMethod)) {
				return Config.TIPO_PAGAMENTO_CONTRASSEGNO_ARCA;
			}
			else if (Config.TIPO_PAGAMENTO_PAYPAL_MAGENTO.equalsIgnoreCase(paymentMethod)) {
				return Config.TIPO_PAGAMENTO_PAYPAL_ARCA;
			}
			else {
				return Config.TIPO_PAGAMENTO_INVALIDO_ARCA;
			}
		}
		else {
			return Config.TIPO_PAGAMENTO_NULLO_ARCA;
		}
	}

	public static String getTotdoc(Invoice inv) {
		String priceFormatted = NumberUtil.convert2Price((String) inv.get("grand_total") );
		return StringUtil.returnVoidForEmpty( priceFormatted );
	}

	public static String getTotimp(Invoice inv) {
		Double total = Double.valueOf((String) inv.get("grand_total") );
		Double tax = Double.valueOf((String) inv.get("tax_amount") );
		String priceFormatted = NumberUtil.convert2Price(total - tax);
		return StringUtil.returnVoidForEmpty( priceFormatted );
	}

	public static String getTotiva(Invoice inv) {
		String priceFormatted = NumberUtil.convert2Price((String) inv.get("tax_amount") );
		return StringUtil.returnVoidForEmpty( priceFormatted );
	}

	public static String getDatadoc(Invoice inv) throws ParseException {
		String createdData = (String) inv.get("created_at");
		String transformedData = DateUtil.decodeData(createdData);
		return StringUtil.returnVoidForEmpty( transformedData );
	}
	
	public static String getShippingAmount(Invoice inv) {
		String shippingamount = NumberUtil.convert2Price( (String)inv.get("shipping_amount") );
		return StringUtil.returnVoidForEmpty( shippingamount );
	}
	
	public static String getTotdoc(CreditMemo cm) {
		String priceFormatted = NumberUtil.convert2Price((String) cm.get("grand_total") );
		return StringUtil.returnVoidForEmpty( priceFormatted );
	}
	
	public static String getTotimp(CreditMemo cm) {
		Double total = Double.valueOf((String) cm.get("grand_total") );
		Double tax = Double.valueOf((String) cm.get("tax_amount") );
		String priceFormatted = NumberUtil.convert2Price(total - tax);
		return StringUtil.returnVoidForEmpty( priceFormatted );
	}
	
	public static String getTotiva(CreditMemo cm) {
		String priceFormatted = NumberUtil.convert2Price((String) cm.get("tax_amount") );
		return StringUtil.returnVoidForEmpty( priceFormatted );
	}
	
	public static String getDatadoc(CreditMemo cm) throws ParseException {
		String createdData = (String) cm.get("created_at");
		String transformedData = DateUtil.decodeData(createdData);
		return StringUtil.returnVoidForEmpty( transformedData );
	}
	
	public static String getShippingAmount(CreditMemo cm) {
		String shippingamount = NumberUtil.convert2Price( (String)cm.get("shipping_amount") );
		return StringUtil.returnVoidForEmpty( shippingamount );
	}

}


/////////////////////////////////////////////////////////

/*
 * Vecchi controlli sul tipo cliente magento per conversione con tipo cliente arca
 */

/*
if (Config.TIPO_CLIENTE_ASSOCIAZIONE_MAGENTO.equalsIgnoreCase(tipoCliente)) {
	return Config.TIPO_CLIENTE_ASSOCIAZIONE_ARCA;
}
else if (Config.TIPO_CLIENTE_DITTA_MAGENTO.equalsIgnoreCase(tipoCliente)) {
	return Config.TIPO_CLIENTE_DITTA_ARCA;
}
else if (Config.TIPO_CLIENTE_PRIVATO_MAGENTO.equalsIgnoreCase(tipoCliente)) {
	return Config.TIPO_CLIENTE_PRIVATO_ARCA;
}
else if (Config.TIPO_CLIENTE_SOCIETA_MAGENTO.equalsIgnoreCase(tipoCliente)) {
	return Config.TIPO_CLIENTE_SOCIETA_ARCA;
}
else {
	return Config.TIPO_CLIENTE_INVALIDO_ARCA;
}

if (Config.TIPO_CLIENTE_SPA_MAGENTO.equals(tipoCliente))						{ return Config.TIPO_CLIENTE_SPA_ARCA; }
else if (Config.TIPO_CLIENTE_SRL_MAGENTO.equals(tipoCliente))					{ return Config.TIPO_CLIENTE_SRL_ARCA; }
else if (Config.TIPO_CLIENTE_SAPA_MAGENTO.equals(tipoCliente)) 					{ return Config.TIPO_CLIENTE_SAPA_ARCA; }
else if (Config.TIPO_CLIENTE_SS_MAGENTO.equals(tipoCliente))					{ return Config.TIPO_CLIENTE_SS_ARCA; }
else if (Config.TIPO_CLIENTE_SNC_MAGENTO.equals(tipoCliente)) 					{ return Config.TIPO_CLIENTE_SNC_ARCA; }
else if (Config.TIPO_CLIENTE_SAS_MAGENTO.equals(tipoCliente)) 					{ return Config.TIPO_CLIENTE_SAS_ARCA; }
else if (Config.TIPO_CLIENTE_SCOOP_MAGENTO.equals(tipoCliente)) 				{ return Config.TIPO_CLIENTE_SCOOP_ARCA; }
else if (Config.TIPO_CLIENTE_ENTEPUBBLICO_MAGENTO.equals(tipoCliente)) 			{ return Config.TIPO_CLIENTE_ENTEPUBBLICO_ARCA; }
else if (Config.TIPO_CLIENTE_CONSORZIO_MAGENTO.equals(tipoCliente)) 			{ return Config.TIPO_CLIENTE_CONSORZIO_ARCA; }
else if (Config.TIPO_CLIENTE_CONDOMINIO_MAGENTO.equals(tipoCliente)) 			{ return Config.TIPO_CLIENTE_CONDOMINIO_ARCA; }
else if (Config.TIPO_CLIENTE_IMPRESAINDIVIDUALE_MAGENTO.equals(tipoCliente))	{ return Config.TIPO_CLIENTE_IMPRESAINDIVIDUALE_ARCA; }
else if (Config.TIPO_CLIENTE_PRIVATO_MAGENTO.equals(tipoCliente)) 				{ return Config.TIPO_CLIENTE_PRIVATO_ARCA; }
else if (Config.TIPO_CLIENTE_ASSOCIAZIONE_MAGENTO.equals(tipoCliente)) 			{ return Config.TIPO_CLIENTE_ASSOCIAZIONE_ARCA; }
*/
