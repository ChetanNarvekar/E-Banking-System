package com.app.loan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.loan.entities.LoanPayment;

public interface LoanPaymentDao extends JpaRepository<LoanPayment, String>{
	
}
