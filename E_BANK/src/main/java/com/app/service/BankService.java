package com.app.service;

import java.util.List;

import com.app.dto.BankDTO;

import com.app.entity.bank.Bank;


public interface BankService {
	
	String addBank(BankDTO bank);
	
	Bank getBankDetails();

	List<Bank>getAllBankDetails();
}
