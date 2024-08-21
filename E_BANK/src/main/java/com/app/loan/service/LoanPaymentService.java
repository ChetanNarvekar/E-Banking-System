package com.app.loan.service;

import java.util.List;

import com.app.loan.dto.ApiResponse;
import com.app.loan.dto.LoanResponseDto;

public interface LoanPaymentService {

	ApiResponse addPayment(String loan);
	List<LoanResponseDto> getLoanDetailsByAccountNo(String accountNo);
	
}
