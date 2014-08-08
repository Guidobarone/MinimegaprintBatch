/**
 * @author ascanio borga
 *
 */
package com.google.code.magja.service.order;

import java.util.List;
import com.google.code.magja.model.order.CreditMemo;
import com.google.code.magja.service.GeneralService;
import com.google.code.magja.service.ServiceException;

public interface CreditMemoRemoteService extends GeneralService<CreditMemo> {

    void save(CreditMemo shipment, String comment, Boolean email,
              Boolean includeComment) throws ServiceException;

    void addComment(CreditMemo creditMemo, String comment, Boolean email,
                    Boolean includeComment) throws ServiceException;

    CreditMemo getById(Integer id) throws ServiceException;

    List<CreditMemo> list(String filter) throws ServiceException;
    
    void capture(CreditMemo creditMemo)
            throws ServiceException;
}