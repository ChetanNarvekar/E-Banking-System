package com.app.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.TransactionHistoryDao;
import com.app.dto.TransactionHistoryDTO;
import com.app.entity.account.Account;
import com.app.entity.payment.Payment;
import com.app.entity.payment.TransactionHistory;
import com.app.loan.entities.LoanPayment;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionHistoryDao transactionHistoryDao;

    @Autowired
    private ModelMapper mapper;

    // Method to get all transactions as DTOs
    public List<TransactionHistoryDTO> getTransactionsByStatus(String status) {
        List<TransactionHistory> histories = transactionHistoryDao.findByStatusWithAccountsAndPayments(status);
        return histories.stream()
                        .map(history -> mapper.map(history, TransactionHistoryDTO.class))
                        .collect(Collectors.toList());
    }
    

    @Override
    public List<TransactionHistoryDTO> getAllTransactionHistories() {
        List<TransactionHistory> histories = transactionHistoryDao.findAll();
        
        return histories.stream()
                        .map(history -> {
                            // Map TransactionHistory to TransactionHistoryDTO
                            TransactionHistoryDTO dto = mapper.map(history, TransactionHistoryDTO.class);
                           
                            // Fetch and set additional details
                            Account account = history.getAccount(); 
                            if (account != null) {
                                dto.setAccountId(account.getAccountNo()); 
                                System.out.println(account.getAccountNo());
                                
                            }

                            Payment payment = history.getPayment(); 
                            if (payment != null) {
                                dto.setPaymentId(payment.getRefId()); 
                                System.out.println(payment.getRefId());
                            }

                            LoanPayment loanPayment = history.getLoanPayment(); 
                            if (loanPayment != null) {
                               dto.setPaymentId(loanPayment.getPayment_id()); 
                          
                            }
                           
                            return dto;
                        })
                        .collect(Collectors.toList());
    }
    
    @Override
    public List<TransactionHistoryDTO> getTransactionsByAccountNoAndMonth(String accountNo, int month) {
    	 List<TransactionHistory> histories = transactionHistoryDao.findByAccountNoAndMonth(accountNo, month);
         return histories.stream()
                         .map(history -> mapper.map(history, TransactionHistoryDTO.class))
                         .collect(Collectors.toList());
    }
    @Override
    public List<TransactionHistoryDTO> getTransactionsByAccountNo(String accountNo) {
        // Fetch transaction histories by account number
        List<TransactionHistory> histories = transactionHistoryDao.findByAccountNo(accountNo);

        if (histories == null || histories.isEmpty()) {
            // Handle empty result or log as necessary
            return Collections.emptyList();
        }

        // Map each TransactionHistory to TransactionHistoryDTO and set additional details
        return histories.stream()
                        .map(history -> {
                            // Map TransactionHistory to TransactionHistoryDTO
                            TransactionHistoryDTO dto = mapper.map(history, TransactionHistoryDTO.class);

                            // Fetch and set additional details
                            if (history.getAccount() != null) {
                                dto.setAccountId(history.getAccount().getAccountNo()); // Adjust method as needed
                            }

                            if (history.getPayment() != null) {
                                dto.setPaymentId(history.getPayment().getRefId()); // Adjust method as needed
                            }

                            if (history.getLoanPayment() != null) {
                                dto.setPaymentId(history.getLoanPayment().getPayment_id()); // Ensure naming consistency
                            }

                            return dto;
                        })
                        .collect(Collectors.toList());
    }


   

    
}
