package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.app.dto.CustomerDTO;
import com.app.dto.CustomerReturnDTO;
import com.app.dto.LoginRequestDTO;
import com.app.entity.customer.Customer;
import com.app.entity.enums.Role;
import com.app.exceptions.BadRequestException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.service.CustomerService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.List;

@RestController
@RequestMapping("/bank/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;
 
    @PostMapping
    public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerDTO customerDto) {
        if (customerDto.getFirstName() != null) {
            customerDto.setFirstName(customerDto.getFirstName().toUpperCase());
        }
        if (customerDto.getMiddleName() != null) {
            customerDto.setMiddleName(customerDto.getMiddleName().toUpperCase());
        }
        if (customerDto.getLastName() != null) {
            customerDto.setLastName(customerDto.getLastName().toUpperCase());
        }
        String response = customerService.addCustomer(customerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomerEmailAndPhone(
            @PathVariable Long id,
            @RequestParam(required = false) @Email(message = "Invalid email format") String email,
            @RequestParam(required = false) @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String phoneNumber) {
        String response = customerService.updateCustomerEmailAndPhone(id, email, phoneNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<String> updateCustomerPassword(
            @PathVariable Long id,
            @RequestParam @NotNull(message = "Password must not be empty") String password){
           String response =  customerService.updateCustomerPassword(id , password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<CustomerReturnDTO>> getAllCustomers() {
        List<CustomerReturnDTO> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    
    @GetMapping("/admins")
    public ResponseEntity<List<CustomerReturnDTO>> getAllAdmins() {
        List<CustomerReturnDTO> admins = customerService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
        Customer customer = customerService.getCustomer(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    @GetMapping("/status/false")
    public ResponseEntity<List<CustomerReturnDTO>> getCustomersWithStatusFalse() {
        List<CustomerReturnDTO> customers = customerService.getCustomersWithStatusFalse();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> setCustomerStatusToTrue(@PathVariable Long id) {
        String response = customerService.setCustomerStatusToTrue(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        customerService.login(loginRequest.getEmailOrPhone(), loginRequest.getPassword());
        return new ResponseEntity<>("Login successful!", HttpStatus.OK);
    }
    
    @PutMapping("/updateAdmin/{id}")
    public ResponseEntity<String> createAdmin(@PathVariable("id") Long id) {
        Customer customer = customerService.getCustomer(id);
        if (Role.ROLE_SUPER_ADMIN.equals(customer.getRole())) {
            throw new BadRequestException("Cannot update role for a super admin");
        }
        String msg = customerService.createAdmin(id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    
    @PutMapping("/deleteAdmin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable("id") Long id) {
        Customer customer = customerService.getCustomer(id);
        if (Role.ROLE_ADMIN.equals(customer.getRole())) {
        	String msg = customerService.deleteAdmin(id);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        throw new BadRequestException("Not Found");
    }
    @PostMapping("/{customerId}/update-tpin")
    public ResponseEntity<String> updateTpin(@PathVariable Long customerId, @RequestParam @Min(100000) @Max(999999) String newTpin) {
        try {
            customerService.updateCustomerTpin(customerId, newTpin);
            return new ResponseEntity<>("TPIN updated successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}

