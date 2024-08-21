package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.payment.TransactionHistory;


@Repository
public interface TransactionHistoryDao extends JpaRepository<TransactionHistory, String> {
	 @Query("SELECT th FROM TransactionHistory th "
	         + "JOIN FETCH th.account "
	         + "JOIN FETCH th.payment "
	         + "WHERE th.status = :status")
	    List<TransactionHistory> findByStatusWithAccountsAndPayments(@Param("status") String status);
	
	
	 @Query("SELECT th FROM TransactionHistory th WHERE th.account.accountNo = :accountNo AND FUNCTION('MONTH', th.createdOn) = :month")
	    List<TransactionHistory> findByAccountNoAndMonth(@Param("accountNo") String accountNo, @Param("month") int month);
	 
	 @Query("SELECT th FROM TransactionHistory th WHERE th.account.accountNo = :accountNo ORDER BY th.createdOn DESC")
	    List<TransactionHistory> findByAccountNo(@Param("accountNo") String accountNo);
}
