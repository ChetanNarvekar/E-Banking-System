package com.app.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.loan.dto.CollateralDto;
import com.app.loan.service.CollateralService;
@CrossOrigin(origins = "http://localhost:3001/")
@RestController
@RequestMapping("/bank")
public class LoanCollateralController {
	@Autowired
	private CollateralService collService;
	
	
    /**
     * End-point to add a new collateral request.
     * 
     * This method handles POST requests to the "/loan/collateral/add" end-point. 
     * It accepts a `CollateralDto` object in the request body, invokes the `addCollateral` method of the `CollateralService`
     * to process the collateral details, and returns a `ResponseEntity` with a status of 201 (Created) and the response from the service.
     * 
     * @param collateral The `CollateralDto` object containing details of the collateral to be added.
     * @return A `ResponseEntity` containing the response from the service and HTTP status 201 (Created).
     */
	@PostMapping("/user/collateral/add")
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SUPER_ADMIN', 'DISABLED')")
	public ResponseEntity<?> addCollateralRequest(@RequestBody CollateralDto collateral){
        // Call the service layer to add the collateral and return the result
		return ResponseEntity.status(HttpStatus.CREATED).body(collService.addCollateral(collateral));
	}
	
	
	// Future Enhancement:
    // Once a customer adds collateral, an administrator will review the collateral details.
    // The administrator will then trigger a method to create a new entry in the Loan table
    // using information from the Request and Collateral tables.
}
