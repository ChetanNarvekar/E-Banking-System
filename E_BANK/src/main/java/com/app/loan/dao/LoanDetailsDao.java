package com.app.loan.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.app.loan.entities.Collateral;
import com.app.loan.entities.LoanDetails;
import com.app.loan.entities.Request;

public interface LoanDetailsDao extends JpaRepository<LoanDetails, String>{
	@Query("SELECT u FROM LoanDetails u WHERE u.loanName = :requestId")
    LoanDetails findLoanTypeByRequestId(@Param("requestId") String username);
	
	@Query("SELECT u FROM LoanDetails u WHERE u.loanName = :loanName")
	List<Request> findLoanDetailsByLoanName(@Param("loanName")String loanName);
}
