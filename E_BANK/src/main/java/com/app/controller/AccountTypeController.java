package com.app.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AccountTypeDTO;
import com.app.dto.ApiResponse;
import com.app.entity.account.AccountType;
import com.app.service.AccountTypeService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/accType")
@Validated
public class AccountTypeController {
	
	@Autowired
	private AccountTypeService accService;
//	
//	@Autowired(required=true)
//	private AccountTypeService accService;
//	
	
	@GetMapping
	public ResponseEntity<?> getAllAccountType()
	{
		List<AccountType> accTypeList =accService.getAllAccountType();
		if (accTypeList.isEmpty()) {
            return ResponseEntity.status(204).body(new ApiResponse("No account types found"));
        }
        return ResponseEntity.ok(accTypeList);

	}
	@GetMapping("/{accType}")
	public ResponseEntity<?> getAccountType(@PathVariable("accType") String accType)
	{
		
        return ResponseEntity.ok(accService.getAccountType(accType));
	}
	@PostMapping("/add")
	public ResponseEntity<?> addAccountType(@RequestBody @Valid AccountTypeDTO accDTO)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(accService.addAccType(accDTO));
        
	}
	@PatchMapping("{accType}/update")
	public ResponseEntity<?> updateAccountType(@PathVariable("accType")String accType,@RequestBody @Valid float interestRate)
	{
		return ResponseEntity.ok(accService.updateAccountType(accType, interestRate));
        
	}
}
