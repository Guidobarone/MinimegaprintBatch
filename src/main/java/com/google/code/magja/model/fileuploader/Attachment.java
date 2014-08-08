/**
 * @author ascanio borga
 *
 */
package com.google.code.magja.model.fileuploader;

import java.util.LinkedList;
import java.util.List;

import com.google.code.magja.model.BaseMagentoModel;

public class Attachment extends BaseMagentoModel {

	private static final long serialVersionUID = -6408624348951323608L;
	private String fileuploaderId;
    private String title;
    private String uploadedFile;
    private String fileContent;
    private String productIds;
    private String fileStatus;
    private String contentDisp;
    private String sortOrder;
    private String updateTime;

    /* (non-Javadoc)
     * @see com.google.code.magja.model.BaseMagentoModel#serializeToApi()
     */
    @Override
    public Object serializeToApi() {

        List<Object> params = new LinkedList<Object>();

        // only create for now
        if (id == null) {

            params.add(title);

//            ArrayItemMap map = new ArrayItemMap();
//            for (FileuploaderItem item : items) {
//                map.add(item.getOrderItemId(), item.getQty().intValue());
//            }
//            params.add(map);
        }

        return params;
    }

    /**
     * @return the fileuploaderId
     */
	public String getFileuploaderId() {
		return fileuploaderId;
	}

    /**
     * @param fileuploaderId the fileuploaderId to set
     */
	public void setFileuploaderId(String fileuploaderId) {
		this.fileuploaderId = fileuploaderId;
	}

    /**
     * @return the title
     */
	public String getTitle() {
		return title;
	}

    /**
     * @param title the title to set
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * @return the uploadedFile
     */
	public String getUploadedFile() {
		return uploadedFile;
	}

    /**
     * @param uploadedFile the uploadedFile to set
     */
	public void setUploadedFile(String uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

    /**
     * @return the fileContent
     */
	public String getFileContent() {
		return fileContent;
	}

    /**
     * @param fileContent the fileContent to set
     */
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

    /**
     * @return the productIds
     */
	public String getProductIds() {
		return productIds;
	}

    /**
     * @param productIds the productIds to set
     */
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

    /**
     * @return the fileStatus
     */
	public String getFileStatus() {
		return fileStatus;
	}

    /**
     * @param fileStatus the fileStatus to set
     */
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

    /**
     * @return the contentDisp
     */
	public String getContentDisp() {
		return contentDisp;
	}

    /**
     * @param contentDisp the contentDisp to set
     */
	public void setContentDisp(String contentDisp) {
		this.contentDisp = contentDisp;
	}

    /**
     * @return the sortOrder
     */
	public String getSortOrder() {
		return sortOrder;
	}

    /**
     * @param sortOrder the sortOrder to set
     */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

    /**
     * @return the updateTime
     */
	public String getUpdateTime() {
		return updateTime;
	}

    /**
     * @param updateTime the updateTime to set
     */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((fileuploaderId == null) ? 0 : fileuploaderId.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((uploadedFile == null) ? 0 : uploadedFile.hashCode());
        result = prime * result + ((fileContent == null) ? 0 : fileContent.hashCode());
        result = prime * result + ((productIds == null) ? 0 : productIds.hashCode());
        result = prime * result + ((fileStatus == null) ? 0 : fileStatus.hashCode());
        result = prime * result + ((contentDisp == null) ? 0 : contentDisp.hashCode());
        result = prime * result + ((sortOrder == null) ? 0 : sortOrder.hashCode());
        result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
        return result;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Attachment other = (Attachment) obj;
        if (fileuploaderId == null) {
            if (other.fileuploaderId != null)
                return false;
        } else if (!fileuploaderId.equals(other.fileuploaderId))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (uploadedFile == null) {
            if (other.uploadedFile != null)
                return false;
        } else if (!uploadedFile.equals(other.uploadedFile))
            return false;
        if (fileContent == null) {
            if (other.fileContent != null)
                return false;
        } else if (!fileContent.equals(other.fileContent))
            return false;
        if (productIds == null) {
            if (other.productIds != null)
                return false;
        } else if (!productIds.equals(other.productIds))
            return false;
        if (fileStatus == null) {
            if (other.fileStatus != null)
                return false;
        } else if (!fileStatus.equals(other.fileStatus))
            return false;
        if (contentDisp == null) {
            if (other.contentDisp != null)
                return false;
        } else if (!contentDisp.equals(other.contentDisp))
            return false;
        if (sortOrder == null) {
            if (other.sortOrder != null)
                return false;
        } else if (!sortOrder.equals(other.sortOrder))
            return false;
        if (updateTime == null) {
            if (other.updateTime != null)
                return false;
        } else if (!updateTime.equals(other.updateTime))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Attachment [fileuploaderId=" + fileuploaderId
                + ", title=" + title
                + ", uploadedFile=" + uploadedFile
                + ", fileContent=" + fileContent
                + ", productIds=" + productIds
                + ", fileStatus=" + fileStatus
                + ", contentDisp=" + contentDisp
                + ", sortOrder=" + sortOrder
                + ", updateTime=" + updateTime
                + ", properties=" + properties + "]";
    }

}