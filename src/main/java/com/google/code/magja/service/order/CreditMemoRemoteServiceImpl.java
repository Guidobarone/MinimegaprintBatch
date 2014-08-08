/**
 * @author ascanio borga
 *
 */
package com.google.code.magja.service.order;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;

import com.google.code.magja.magento.ResourcePath;
import com.google.code.magja.model.order.CreditMemo;
import com.google.code.magja.service.GeneralServiceImpl;
import com.google.code.magja.service.ServiceException;

public class CreditMemoRemoteServiceImpl extends GeneralServiceImpl<CreditMemo> implements CreditMemoRemoteService {

	private static final long serialVersionUID = 154745712196853061L;

	private CreditMemo buildCreditMemo(Map<String, Object> attributes) throws ServiceException {
        CreditMemo creditMemo = new CreditMemo();
        for (Map.Entry<String, Object> attr : attributes.entrySet()) creditMemo.set(attr.getKey(), attr.getValue());
        return creditMemo;
    }

    @Override
    public void addComment(CreditMemo creditMemo, String comment, Boolean email,
                           Boolean includeComment) throws ServiceException {

        List<Object> params = new LinkedList<Object>();
        params.add(creditMemo.getId());
        params.add((comment != null ? comment : ""));
        params.add((email ? "1" : "0"));
        params.add((includeComment ? "1" : "0"));

        try {
            soapClient.call(ResourcePath.SalesOrderCreditMemoAddComment, params);
        } catch (AxisFault e) {
            if(debug) e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }


    @Override
    public void save(CreditMemo creditMemo, String comment, Boolean email,
                     Boolean includeComment) throws ServiceException {

        List<Object> params = (LinkedList<Object>) creditMemo.serializeToApi();
        params.add((comment != null ? comment : ""));
        params.add((email ? "1" : "0"));
        params.add((includeComment ? "1" : "0"));

        Integer id = null;
        try {
            id = Integer.parseInt((String) soapClient.call(ResourcePath.SalesOrderCreditMemoCreate, params));
        } catch (NumberFormatException e) {
            if(debug) e.printStackTrace();
            throw new ServiceException(e.getMessage());
        } catch (AxisFault e) {
            if(debug) e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }

        if(id == null || id <= 0) throw new ServiceException("Error saving credit memo");

        creditMemo.setId(id);
    }


    @Override
    public CreditMemo getById(Integer id) throws ServiceException {

        Map<String, Object> result = null;
        try {
            result = (Map<String, Object>) soapClient.call(ResourcePath.SalesOrderCreditMemoInfo, id);
        } catch (AxisFault e) {
            if(debug) e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }

        return (result != null ? buildCreditMemo(result) : null);
    }


    @Override
    public List<CreditMemo> list(String filter) throws ServiceException {

        List<CreditMemo> shipments = new ArrayList<CreditMemo>();

        List<Map<String, Object>> results = null;
        try {
            results = (List<Map<String, Object>>) soapClient.call(ResourcePath.SalesOrderCreditMemoList, filter);
        } catch (AxisFault e) {
            if(debug) e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }

        for (Map<String, Object> result : results)
            shipments.add(buildCreditMemo(result));

        return shipments;
    }

    @Override
    public void capture(CreditMemo creditMemo)
            throws ServiceException {

        List<Object> params = new LinkedList<Object>();
        params.add(creditMemo.getId());

        try {
            soapClient.call(ResourcePath.SalesOrderCreditMemoCapture, params);
        } catch (AxisFault e) {
            if(debug) e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }

    }

}