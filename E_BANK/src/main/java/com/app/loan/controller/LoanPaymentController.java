package com.app.loan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exceptions.ResourceNotFoundException;
import com.app.loan.dto.LoanResponseDto;
import com.app.loan.entities.Loan;
import com.app.loan.service.LoanPaymentService;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/bank")
public class LoanPaymentController {
	
	@Autowired
	private LoanPaymentService loanPaymentService;
	
	
	@PostMapping("/user/addloanpayment/{loanId}")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SUPER_ADMIN', 'DISABLED')")
	public ResponseEntity<?> addLoanPayment(@PathVariable("loanId") String loanId){
		return ResponseEntity.ok(loanPaymentService.addPayment(loanId));
	}
	
	@GetMapping("/user/getloandetailsbyacno/{accountNo}")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SUPER_ADMIN', 'DISABLED')")
	public ResponseEntity<?> getLoanDetailsByAccountNo(@PathVariable String accountNo) {
	    try {
	        // Call service method to get a list of LoanResponseDto
	        List<LoanResponseDto> loans = loanPaymentService.getLoanDetailsByAccountNo(accountNo);
	        
	        // Return OK response with the list of loans
	        return ResponseEntity.ok(loans);
	    } catch (ResourceNotFoundException e) {
	        // Handle case where the account or loans are not found
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        // Handle other unexpected errors
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }
	}

}
