package it.arakne.dbing.magento.runner.wrapper;

import it.arakne.dbing.magento.csv.bean.CustomerAddressBean;
import it.arakne.dbing.magento.csv.bean.esko.EskoImportOrderBean;
import it.arakne.dbing.magento.util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.code.magja.model.cart.Cart;
import com.google.code.magja.model.category.Category;
import com.google.code.magja.model.customer.Customer;
import com.google.code.magja.model.customer.CustomerAddress;
import com.google.code.magja.model.customer.CustomerFilter;
import com.google.code.magja.model.customer.CustomerFilterItem;
import com.google.code.magja.model.fileuploader.Attachment;
import com.google.code.magja.model.order.CreditMemo;
import com.google.code.magja.model.order.Invoice;
import com.google.code.magja.model.order.Order;
import com.google.code.magja.model.order.OrderFilter;
import com.google.code.magja.model.order.OrderFilterItem;
import com.google.code.magja.model.order.OrderItem;
import com.google.code.magja.model.order.Shipment;
import com.google.code.magja.model.order.ShipmentItem;
import com.google.code.magja.model.order.ShipmentTrack;
import com.google.code.magja.model.product.Product;
import com.google.code.magja.model.product.ProductAttribute;
import com.google.code.magja.service.RemoteServiceFactory;
import com.google.code.magja.service.ServiceException;
import com.google.code.magja.service.cart.CartRemoteService;
import com.google.code.magja.service.category.CategoryRemoteService;
import com.google.code.magja.service.customer.CustomerAddressRemoteService;
import com.google.code.magja.service.customer.CustomerRemoteService;
import com.google.code.magja.service.fileuploader.FileuploaderRemoteService;
import com.google.code.magja.service.order.CreditMemoRemoteService;
import com.google.code.magja.service.order.InvoiceRemoteService;
import com.google.code.magja.service.order.OrderRemoteService;
import com.google.code.magja.service.order.ShipmentRemoteService;
import com.google.code.magja.service.product.ProductAttributeRemoteService;
import com.google.code.magja.service.product.ProductRemoteService;
import com.google.code.magja.soap.SoapConfig;

public class MagjaWrapper {

	private final static Logger LOGGER = Logger.getLogger(MagjaWrapper.class.getName());

	public static void parzializedShippings(SoapConfig conf, Order order, EskoImportOrderBean eiob) throws ServiceException {
		LOGGER.fine("setting shipments");
		ShipmentRemoteService srs = RemoteServiceFactory.getShipmentRemoteService(conf);
		List<OrderItem> itemList = order.getItems();
		LOGGER.fine("articoli trovati: "+itemList.size());
		for (OrderItem orderItem : itemList) {
			/*gestione shipment*/
			Shipment shipment = new Shipment();
			//			shipment.set(name, value); //a che serve???
			shipment.setCustomerId(order.getCustomer().getId());
			//			shipment.setId(id); //posso farlo autogenerato??
			List<ShipmentItem> items = new ArrayList<ShipmentItem>();
			ShipmentItem si = new ShipmentItem();
			//			si.set(name, value); //a che serve???
			//			si.setId(id);//posso farlo autogenerato??
			si.setName(orderItem.getName());
			si.setOrderItemId(orderItem.getId());
			si.setPrice(orderItem.getPrice());
			si.setProductId(orderItem.getProductId());
			si.setQty(orderItem.getQtyOrdered());
			si.setSku(orderItem.getSku());
			si.setWeight(orderItem.getWeight());
			items.add(si);
			shipment.setItems(items);
			shipment.setOrderNumber(order.getOrderNumber());
			//			shipment.setShipmentId(shipmentId);//posso farlo autogenerato??
			//provo a non farlo 
			//			shipment.setTotalQty(orderItem.getQtyOrdered());

			Boolean sendEmail = Config.ESKO_SHIPPING_SEND_EMAIL;
			Boolean includeComment = Config.ESKO_SHIPPING_NOTIFY_CUSTOMER;
			LOGGER.info("articolo: "+orderItem.getName()+" ");
			srs.save(shipment, Config.ESKO_SHIPPING_COMMENT, sendEmail, includeComment);
		}
		LOGGER.info("creato shipment relativo all'ordine: "+order.getOrderNumber());

	}

	public static Shipment createShipping(SoapConfig conf, Order order) throws ServiceException {
		LOGGER.fine("setting shipments");
		ShipmentRemoteService srs = RemoteServiceFactory.getShipmentRemoteService(conf);
		List<OrderItem> itemList = order.getItems();
		LOGGER.fine("articoli trovati: "+itemList.size());
		/*gestione shipment*/
		Shipment shipment = new Shipment();
		//			shipment.set(name, value); //a che serve???
		shipment.setCustomerId(order.getCustomer().getId());
		//			shipment.setId(id); //posso farlo autogenerato??
		shipment.setOrderNumber(order.getOrderNumber());
		//			shipment.setShipmentId(shipmentId);//posso farlo autogenerato??
		List<ShipmentItem> items = new ArrayList<ShipmentItem>();
		Double totalQuantity = new Double(0);
		for (OrderItem orderItem : itemList) {
			ShipmentItem si = new ShipmentItem();
			//			si.set(name, value); //a che serve???
			//			si.setId(id);//posso farlo autogenerato??
			si.setName(orderItem.getName());
			si.setOrderItemId(orderItem.getId());
			si.setPrice(orderItem.getPrice());
			si.setProductId(orderItem.getProductId());
			si.setQty(orderItem.getQtyOrdered());
			si.setSku(orderItem.getSku());
			si.setWeight(orderItem.getWeight());
			items.add(si);
			totalQuantity += orderItem.getQtyOrdered();
			LOGGER.info("articolo: "+orderItem.getName()+" ");
		}
		shipment.setTotalQty(totalQuantity);
		shipment.setItems(items);
		Boolean sendEmail = Config.ESKO_SHIPPING_SEND_EMAIL;
		Boolean includeComment = Config.ESKO_SHIPPING_NOTIFY_CUSTOMER;
		srs.save(shipment, Config.ESKO_SHIPPING_COMMENT, sendEmail, includeComment);
		LOGGER.info("creato shipment relativo all'ordine: "+order.getOrderNumber());
		return shipment;
	}

	public static void addShippingTracks(SoapConfig conf, String carrier, Integer shipmentId, String trackN, Integer colloN) throws ServiceException {
		LOGGER.fine("adding shipment tracking number");
		ShipmentRemoteService srs = RemoteServiceFactory.getShipmentRemoteService(conf);
		Shipment sh = srs.getById(shipmentId);
		ShipmentTrack track = new ShipmentTrack();
		//		track.setCarrier(Config.ESKO_SHIPPING_CARRIER_NAME);
		track.setCarrier(carrier);
		track.setNumber(trackN);
		track.setTitle(Config.ESKO_SHIPPING_TITLE_PREFIX+" "+ colloN);
		srs.addTrack(sh, track);
	}

	public static Map<String, String> getCarriers(SoapConfig conf, Integer orderId) throws ServiceException {
		LOGGER.fine("get allowed carriers");
		ShipmentRemoteService srs = RemoteServiceFactory.getShipmentRemoteService(conf);
		return srs.getCarriers(orderId);
	}

	public static List<Order> ordersFiltering(SoapConfig conf, List<OrderFilterItem> filterList) throws ServiceException {
		OrderRemoteService ors = RemoteServiceFactory.getOrderRemoteService(conf);
		OrderFilter orderFilter = new OrderFilter();
		orderFilter.setItems(filterList);
		List<Order> orderList = ors.list(orderFilter);
		return orderList;
	}

	public static ProductAttribute getProductAttributeBySKU(SoapConfig conf, String productSKU) throws ServiceException {
		ProductAttributeRemoteService pars = RemoteServiceFactory.getProductAttributeRemoteService(conf);
		ProductAttribute prodAttr = pars.getByCode(productSKU);
		//		System.out.println(order);
		return prodAttr;
	}

	public static Product getProductBySKU(SoapConfig conf, String productSKU) throws ServiceException {
		ProductRemoteService prs = RemoteServiceFactory.getProductRemoteService(conf);
		Product prod = prs.getBySku(productSKU);
		//		System.out.println(order);
		return prod;
	}

	public static Product getProductById(SoapConfig conf, Integer productId) throws ServiceException {
		ProductRemoteService prs = RemoteServiceFactory.getProductRemoteService(conf);
		Product prod = prs.getById(productId);
		//		System.out.println(order);
		return prod;
	}

	public static Order getOrderById(SoapConfig conf, Integer orderNumber) throws ServiceException {
		OrderRemoteService ors = RemoteServiceFactory.getOrderRemoteService(conf);
		Order order = ors.getById(orderNumber);
		//		System.out.println(order);
		return order;
	}
	
	/**
	 * Implementazione metodo con utilizzo di un orderNumber tipo String
	 * 
	 * @date: 2012 - 06 - 21
	 * 
	 * @param conf
	 * @param orderNumber
	 * @return
	 * @throws ServiceException
	 */
	public static Order getOrderById(SoapConfig conf, String orderNumber) throws ServiceException {
		OrderRemoteService ors = RemoteServiceFactory.getOrderRemoteService(conf);
		Order order = ors.getById(orderNumber);
		//		System.out.println(order);
		return order;
	}

	public static void updateOrderStatusById(SoapConfig conf, Integer orderNumber, String newOrderStatus, String comment, Boolean notify) throws ServiceException {
		OrderRemoteService ors = RemoteServiceFactory.getOrderRemoteService(conf);
		Order order = ors.getById(orderNumber);
		ors.addComment(order, newOrderStatus, comment, notify);
	}
	
	/**
	 * Metodo utilizzato per l'aggiornamento dello stato dell'ordine ridefinito per accettare un orderNumber di tipo String
	 * @date: 2012 - 06 - 21
	 * 
	 * @param conf
	 * @param orderNumber
	 * @param newOrderStatus
	 * @param comment
	 * @param notify
	 * @throws ServiceException
	 */
	public static void updateOrderStatusById(SoapConfig conf, String orderNumber, String newOrderStatus, String comment, Boolean notify) throws ServiceException {
		OrderRemoteService ors = RemoteServiceFactory.getOrderRemoteService(conf);
		Order order = ors.getById(orderNumber);
		ors.addComment(order, newOrderStatus, comment, notify);
	}

	public static void getCategoryInfo(SoapConfig conf, Integer categoryId) throws ServiceException {
		CategoryRemoteService crs = RemoteServiceFactory.getCategoryRemoteService(conf);
		Category c = crs.getByIdClean(categoryId);
		//		System.out.println("getByIdClean:\n");
		//		System.out.println(c);
		//		System.out.println("\n\n");
		c = crs.getByIdWithParentAndChildren(categoryId);
		//		System.out.println("getByIdWithParentAndChildren:\n");
		//		System.out.println(c);
		//		System.out.println("\n\n");
	}

	public static List<CustomerAddressBean> getFilteredCustomerList(SoapConfig conf, List<CustomerFilterItem> filtList, Boolean withAddress) 
			throws ServiceException {
		CustomerRemoteService crs = RemoteServiceFactory.getCustomerRemoteService(conf);
		//		Customer cusFil = new Customer();
		//		List<Customer> custList = crs.list(cusFil);
		CustomerFilter filter = new CustomerFilter();
		filter.setItems(filtList);
		List<Customer> custList = crs.list2(filter);
		List<CustomerAddressBean> custBeanList = new ArrayList<CustomerAddressBean>() ;
		for (Customer cust : custList){
			CustomerAddressBean cab = new CustomerAddressBean(cust);
			if (withAddress) {
				List<CustomerAddress> addrList = getCustomerAddressesList(conf, cust.getId());
				cab.setAddressesList(addrList);
			}
			custBeanList.add(cab);
		}
		return custBeanList;
	}

	public static List<CustomerAddressBean> getCustomerList(SoapConfig conf, Boolean withAddress) 
			throws ServiceException {
		CustomerRemoteService crs = RemoteServiceFactory.getCustomerRemoteService(conf);
		List<Customer> custList = crs.list();
		List<CustomerAddressBean> custBeanList = new ArrayList<CustomerAddressBean>() ;
		for (Customer cust : custList){
			CustomerAddressBean cab = new CustomerAddressBean(cust);
			if (withAddress) {
				List<CustomerAddress> addrList = getCustomerAddressesList(conf, cust.getId());
				cab.setAddressesList(addrList);
			}			
			custBeanList.add(cab);
		}
		return custBeanList;
	}

	public static Customer getCustomerInfo(SoapConfig conf, Integer id) 
			throws ServiceException {
		CustomerRemoteService crs = RemoteServiceFactory.getCustomerRemoteService(conf);
		return crs.getById(id);
	}

	public static List<CustomerAddress> getCustomerAddressesList(SoapConfig conf, Integer customerId) throws ServiceException {
		CustomerAddressRemoteService crs = RemoteServiceFactory.getCustomerAddressRemoteService(conf);
		List<CustomerAddress> addressesList = crs.list(customerId);
		return addressesList;
	}

	public static List<Invoice> getInvoicesList(SoapConfig conf, String filter) 
			throws ServiceException {
		InvoiceRemoteService irs = RemoteServiceFactory.getInvoiceRemoteService(conf);
		List<Invoice> invoicesList = irs.list(filter);
		return invoicesList;
	}

	public static Invoice getInvoice(SoapConfig conf, Integer id) 
			throws ServiceException {
		InvoiceRemoteService irs = RemoteServiceFactory.getInvoiceRemoteService(conf);
		Invoice invoice = irs.getById(id);
		return invoice;
	}

	public static List<CreditMemo> getCreditMemosList(SoapConfig conf, String filter) 
			throws ServiceException {
		CreditMemoRemoteService cmrs = RemoteServiceFactory.getCreditMemoRemoteService(conf);
		List<CreditMemo> creditMemosList = cmrs.list(filter);
		return creditMemosList;
	}

	public static CreditMemo getCreditMemo(SoapConfig conf, Integer id) 
			throws ServiceException {
		CreditMemoRemoteService cmrs = RemoteServiceFactory.getCreditMemoRemoteService(conf);
		CreditMemo creditMemo = cmrs.getById(id);
		return creditMemo;
	}
	
	public static Cart getCart(SoapConfig conf, Integer id, Integer storeId) 
			throws ServiceException {
		CartRemoteService crs = RemoteServiceFactory.getCartRemoteService(conf);
		Cart cart = crs.getById(id,storeId);
		return cart;
	}

	public static List<Product> getProductList(SoapConfig conf) 
			throws ServiceException {
		ProductRemoteService prs = RemoteServiceFactory.getProductRemoteService(conf);
		List<Product> prodList = prs.listAllNoDep();
		LOGGER.finest("lista prodotti: "+prodList);
		return prodList;
	}

	public static Product getProductInfo(SoapConfig conf, String id) 
			throws ServiceException {
		Product prod = null; 
		try {
			Integer idProd = Integer.valueOf(id);
			return getProductInfo(conf, idProd) ;
		}
		catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
		return prod;
	}

	public static Product getProductInfo(SoapConfig conf, Integer id) 
			throws ServiceException {
		ProductRemoteService prs = RemoteServiceFactory.getProductRemoteService(conf);
		Product prod = prs.getById(id);
		LOGGER.finest("prodotto: "+prod);
		return prod;
	}
	
	public static List<Attachment> getProductAttachments(SoapConfig conf, String filter)
			throws ServiceException {
		FileuploaderRemoteService furs = RemoteServiceFactory.getFileuploaderRemoteService(conf);
		List<Attachment> prodAttachments = furs.list(filter);
		LOGGER.finest("lista attachments: "+prodAttachments);
		return prodAttachments;
	}
	
	public static String getMagentoMediaPath(SoapConfig conf)
			throws ServiceException {
		FileuploaderRemoteService furs = RemoteServiceFactory.getFileuploaderRemoteService(conf);
		String magentoMediaPath = furs.getMediaPath();
		LOGGER.finest("magento media path: "+magentoMediaPath);
		return magentoMediaPath;
	}
}