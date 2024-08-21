
package com.app.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.account.Account;
import com.app.service.AccountSevice;
@CrossOrigin(origins = "http://localhost:3001/")
@RestController
@RequestMapping("/loan/account")
public class LoanAccountController {
	@Autowired
	private AccountSevice accService;
	
	/**
     * End-point to add a new account.
     * 
     * This method handles POST requests to the "/loan/account/add" end-point. 
     * It accepts an `Account` object in the request body, invokes the `addAccount` method of the `AccountService`
     * to save the account, and returns a `ResponseEntity` with a status of 201 (Created) and the saved account.
     * 
     * @param account The `Account` object to be added.
     * @return A `ResponseEntity` containing the created account and HTTP status 201 (Created).
     */
	@PostMapping("/add")
	public ResponseEntity<?> addAccounts(@RequestBody Account account)
	{
        // Call the service layer to add the account and return the result
		return ResponseEntity.status(HttpStatus.CREATED).body(accService.addAccount(account));
	}
	
	@GetMapping("/acc/{id}")
	public ResponseEntity<?> customerDetail(@PathVariable("id") String id){
		return ResponseEntity.ok(accService.getAccountDetails(id));
	}
}
