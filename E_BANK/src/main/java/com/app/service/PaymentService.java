package com.app.service;

import org.apache.coyote.BadRequestException;

import com.app.dto.PaymentDTO;

public interface PaymentService {
	

	boolean paymentWithinBank(PaymentDTO payments) throws BadRequestException;
	boolean paymentOutsideBank(PaymentDTO payments) throws BadRequestException;
	String paymentEMI(PaymentDTO payments);
	String paymentLoan(PaymentDTO payments);
	boolean depositAmountCustomer(PaymentDTO payments) throws BadRequestException;
}
