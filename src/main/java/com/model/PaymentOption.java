package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENT_OPTION")
public class PaymentOption implements Serializable{

    @Id
    @Column(name = "CODE")
    private Integer paymentOptionCode;

    @Column(name = "NAME", nullable = false)
    private String paymentOptionName;
    
    public PaymentOption(){}

	public Integer getPaymentOptionCode() {
		return paymentOptionCode;
	}

	public void setPaymentOptionCode(Integer paymentOptionCode) {
		this.paymentOptionCode = paymentOptionCode;
	}

	public String getPaymentOptionName() {
		return paymentOptionName;
	}

	public void setPaymentOptionName(String paymentOptionName) {
		this.paymentOptionName = paymentOptionName;
	}

	public PaymentOption(Integer paymentOptionCode, String paymentOptionName) {
		super();
		this.paymentOptionCode = paymentOptionCode;
		this.paymentOptionName = paymentOptionName;
	}
    
    
}
