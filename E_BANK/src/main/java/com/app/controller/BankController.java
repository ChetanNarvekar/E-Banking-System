package com.app.controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.BankDTO;
import com.app.loan.exceptions.ResourceNotFoundException;
import com.app.service.BankService;

@RestController
@RequestMapping("/bank")
@Validated
@CrossOrigin(origins = "http://localhost:3001/")
public class BankController {
	
	@Autowired
	private BankService bankService;
	
	private static final Logger logger = LogManager.getLogger(BankController.class);
	
	@PreAuthorize("hasRole( 'SUPER_ADMIN')")
	@PostMapping("/superadmin/addBankDetails")
	public ResponseEntity<?> addBanks(@RequestBody @Valid BankDTO bank)
	{
//		logger.info("Entered into Bank Add");
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(bankService.addBank(bank));
		}catch(ResourceNotFoundException e) {
//			logger.warn("Resource Not Found");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(IllegalArgumentException  | HttpMessageNotReadableException  e) {
//			logger.warn("Illegal Argument Exception");
			return new ResponseEntity<>("Error reading arguments", HttpStatus.BAD_REQUEST);
		}catch(RuntimeException e) {
//			logger.error("Bank Exists Already"+ bank.getBankName());
			return new ResponseEntity<>("Bank Already Exists !!!!", HttpStatus.BAD_REQUEST);
		}finally {
//			logger.info("Existed from bank add");
		}
		
	}
}
