package com.app.service;

import java.util.List;

import com.app.dto.TransactionHistoryDTO;
import com.app.entity.payment.TransactionHistory;

public interface TransactionService {
	List<TransactionHistoryDTO> getTransactionsByStatus(String status);

	List<TransactionHistoryDTO> getAllTransactionHistories();
	
	List<TransactionHistoryDTO> getTransactionsByAccountNoAndMonth(String accountNo, int month);

	List<TransactionHistoryDTO> getTransactionsByAccountNo(String accountNo);
}
