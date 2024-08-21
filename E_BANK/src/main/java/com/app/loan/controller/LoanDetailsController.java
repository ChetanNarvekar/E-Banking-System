package com.app.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.loan.dto.LoanDetailsDto;
import com.app.loan.service.LoanDetailsService;
@CrossOrigin(origins = "http://localhost:3001/")
@RestController
@RequestMapping("/bank")
public class LoanDetailsController {
	
	@Autowired
	private LoanDetailsService loDeService;
	
	
	/**
	 * End-point to add a new loan type.
	 * 
	 * This method handles POST requests to the "/loantypes/add" end-point.
	 * It accepts a `LoanDetailsDto` object in the request body and invokes the 
	 * `addNewLoanDetails` method of the `LoanDetailsService` to process the loan details.
	 * 
	 * @param loDeDto The `LoanDetailsDto` object containing details of the loan type to be added.
	 * @return A `ResponseEntity` containing the response from the service and HTTP status 201 (Created).
	 */
	@PostMapping("/superadmin/loantype/add")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<?> addLoanTypeDetails(@RequestBody LoanDetailsDto loDeDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(loDeService.addNewLoanDetails(loDeDto));
	}
	
	
	/**
	 * End-point to remove an existing loan type.
	 * 
	 * This method handles DELETE requests to the "/loantypes/delete/{id}" end-point.
	 * It takes a loan type ID as a path variable and invokes the `removeLoanDretails` method 
	 * of the `LoanDetailsService` to remove the loan type details.
	 * 
	 * @param id The ID of the loan type to be removed.
	 * @return A `ResponseEntity` containing the response from the service and HTTP status 201 (Created).
	 */
	@DeleteMapping("/superadmin/deleteloantype/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<?> removeLoanType(@PathVariable("id") String id){
		return ResponseEntity.status(HttpStatus.CREATED).body(loDeService.removeLoanDretails(id));
	}
	
	
	@GetMapping("/user/getloantypes/{loanName}")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SUPER_ADMIN', 'DISABLED')")
	public ResponseEntity<?> getLoansByLoanName(@PathVariable("loanName")String loanName){
		return ResponseEntity.status(HttpStatus.CREATED).body(loDeService.getAllLoansByLoanName(loanName));	
	}
	
}
