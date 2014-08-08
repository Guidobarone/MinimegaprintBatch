/**
 * @author ascanio borga
 *
 */
package com.google.code.magja.service.fileuploader;

import java.util.List;

import com.google.code.magja.model.fileuploader.Attachment;
import com.google.code.magja.service.GeneralService;
import com.google.code.magja.service.ServiceException;

public interface FileuploaderRemoteService extends GeneralService<Attachment> {

    List<Attachment> list(String filter) throws ServiceException;
    
    String getMediaPath() throws ServiceException;
    
}