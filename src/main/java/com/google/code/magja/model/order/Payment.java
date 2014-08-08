/**
 * @author andre
 *
 */
package com.google.code.magja.model.order;

import java.util.LinkedList;
import java.util.List;

import com.google.code.magja.model.BaseMagentoModel;

public class Payment extends BaseMagentoModel {

	private static final long serialVersionUID = -8149178869302717686L;

	private Integer paymentId;

	private String method;

	/* (non-Javadoc)
	 * @see com.google.code.magja.model.BaseMagentoModel#serializeToApi()
	 */
	@Override
	public Object serializeToApi() {

		List<Object> params = new LinkedList<Object>();

		// only create for now
		if (id == null) {

			params.add(method);

		}

		return params;
	}

	/**
	 * @return the customerId
	 */
	public Integer getPaymentId() {
		return paymentId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the orderNumber
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((paymentId == null) ? 0 : paymentId.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
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
		Payment other = (Payment) obj;
		if (paymentId == null) {
			if (other.paymentId != null)
				return false;
		} else if (!paymentId.equals(other.paymentId))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Invoice [paymentId=" + paymentId 
				+ ", method=" + method + ", id=" + id + ", properties="
				+ properties + "]";
	}

}
