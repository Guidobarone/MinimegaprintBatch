/**
 * @author ascanio borga
 *
 */
package com.google.code.magja.service.fileuploader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;

import com.google.code.magja.magento.ResourcePath;
import com.google.code.magja.model.fileuploader.Attachment;
import com.google.code.magja.service.GeneralServiceImpl;
import com.google.code.magja.service.ServiceException;

public class FileuploaderRemoteServiceImpl extends GeneralServiceImpl<Attachment> implements FileuploaderRemoteService {

	private static final long serialVersionUID = 659108187779112628L;

	private Attachment buildAttachment(Map<String, Object> attributes) throws ServiceException {
        Attachment attachment = new Attachment();
        for (Map.Entry<String, Object> attr : attributes.entrySet()) attachment.set(attr.getKey(), attr.getValue());
        return attachment;
    }

    @Override
    public List<Attachment> list(String filter) throws ServiceException {

        List<Attachment> attachments = new ArrayList<Attachment>();

        List<Map<String, Object>> results = null;
        try {
            results = (List<Map<String, Object>>) soapClient.call(ResourcePath.FileuploaderGetProductAttachments, filter);
        } catch (AxisFault e) {
            if(debug) e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }

        for (Map<String, Object> result : results)
            attachments.add(buildAttachment(result));

        return attachments;
    }

	@Override
	public String getMediaPath() throws ServiceException {

		String basePath = "";
		
        try {
        	basePath = (String) soapClient.call(ResourcePath.FileuploaderGetMediaPath, "");
        } catch (AxisFault e) {
            if(debug) e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
		
		return basePath;
	}

}