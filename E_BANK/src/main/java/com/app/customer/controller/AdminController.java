package com.app.customer.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.app.dao.AccountTypeDao;
import com.app.dto.AccountTypeDTO;
import com.app.dto.CustomerReturnDTO;
import com.app.dto.LoginRequestDTO;
import com.app.dto.LoginResponseDTO;
import com.app.entity.account.Account;
import com.app.loan.exceptions.ResourceNotFoundException;
import com.app.service.AccountSevice;
import com.app.service.AccountTypeService;
import com.app.service.CustomerService;
@CrossOrigin(origins ="http://localhost:3000/")
@RestController
@RequestMapping("/bank")
@Validated
public class AdminController {

	@Autowired
	private AccountTypeService accTypeService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private AccountSevice accService;

	@PreAuthorize("hasRole( 'SUPER_ADMIN')")
	@PostMapping("/superadmin/add")
	public ResponseEntity<?> addAccountType(@RequestBody @Valid AccountTypeDTO accDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(accTypeService.addAccType(accDTO));
	}

	@PreAuthorize("hasRole( 'SUPER_ADMIN')")
	@PatchMapping("/superadmin/update/{accType}")
	public ResponseEntity<?> updateAccountType(@PathVariable("accType") String accType,
			@RequestBody @Valid float interestRate) {
		return ResponseEntity.ok(accTypeService.updateAccountType(accType, interestRate));
	}

	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/actiavted")
	public ResponseEntity<?> getAllActivatedAccounts() {
		
		try {
			List<Account> activatedAccounts = accService.getAllActivatedAccount();
			return new ResponseEntity<>(activatedAccounts, HttpStatus.CREATED);
		}catch(NullPointerException e) {
			List<Account> emptyList = null;
			return new ResponseEntity<>(emptyList, HttpStatus.ACCEPTED);
		}
		
		
	}

	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/deactivated")
	public ResponseEntity<?> getAllDeactivatedAccounts() {
		
		try {
			List<Account> deactivatedAccounts = accService.getAllDeactivatedAccount();
			return new ResponseEntity<>(deactivatedAccounts, HttpStatus.CREATED);
		}catch(NullPointerException e) {
			List<Account> emptyList = null;
			return new ResponseEntity<>(emptyList, HttpStatus.ACCEPTED);
		}catch(ResourceNotFoundException | InternalServerError e) {
			return new ResponseEntity<>("Given ID Doesn`t match with record", HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/suspended")
	public ResponseEntity<?> getAllSuspendedAccounts() {
		
		try {
			List<Account> suspendedAccounts = accService.getAllSuspendedAccount();
			return new ResponseEntity<>(suspendedAccounts, HttpStatus.CREATED);
		}catch(NullPointerException e) {
			List<Account> emptyList = null;
			return new ResponseEntity<>(emptyList, HttpStatus.ACCEPTED);
		}catch(ResourceNotFoundException | InternalServerError e) {
			return new ResponseEntity<>("Given ID Doesn`t match with record", HttpStatus.NOT_FOUND);
		}
		
	}

	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@PatchMapping("/admin/updateStatusActivated/{accId}")
	public ResponseEntity<?> changeToActivate(@PathVariable("accId") String accId) {
		return ResponseEntity.ok(accService.changeStatusOfActivatedAccount(accId));
	}

	@PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
	@PatchMapping("/admin/updateStatusDeactivated/{accId}")
	public ResponseEntity<?> changeToDeactivate(@PathVariable("accId") String accId) {
		return ResponseEntity.ok(accService.changeStatusOfDeactivatedAccount(accId));
	}

	@PreAuthorize("hasRole( 'SUPER_ADMIN')")
    @GetMapping("/superadmin/admins")
    public ResponseEntity<List<CustomerReturnDTO>> getAllAdmins() {
        List<CustomerReturnDTO> admins = customerService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/admin/getallwithstatusfalse")
    public ResponseEntity<List<CustomerReturnDTO>> getCustomersWithStatusFalse() {
        List<CustomerReturnDTO> customers = customerService.getCustomersWithStatusFalse();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole( 'ADMIN', 'SUPER_ADMIN')")
    @PatchMapping("/admin/changestatustotrue/{id}")
    public ResponseEntity<String> setCustomerStatusToTrue(@PathVariable Long id) {
        String response = customerService.setCustomerStatusToTrue(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
//      LoginResponseDTO loginResponse = customerService.login(loginRequest.getEmailOrPhone(), loginRequest.getPassword());
//        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
//    }

}