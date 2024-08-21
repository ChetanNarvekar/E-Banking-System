package com.app.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.app.dto.PaymentDTO;
import com.app.exceptions.ResourceNotFoundException;
import com.app.service.PaymentService;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/bank")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/user/payment/within-bank")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SUPER_ADMIN', 'DISABLED')")
    public ResponseEntity<String> paymentWithinBank(@RequestBody PaymentDTO paymentDTO) {
        try {
            boolean result = paymentService.paymentWithinBank(paymentDTO);
            if (result) {
                return new ResponseEntity<>("Payment within bank was successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Payment within bank failed", HttpStatus.BAD_REQUEST);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the payment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/user/payment/outside-bank")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SUPER_ADMIN', 'DISABLED')")
    public ResponseEntity<String> paymentOutsideBank(@RequestBody PaymentDTO paymentDTO) {
        try {
            boolean result = paymentService.paymentOutsideBank(paymentDTO);
            if (result) {
                return ResponseEntity.ok("Payment processed successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment processing failed.");
            }
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the payment.");
        }
    }
    @PostMapping("/admin/deposit")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> depositAmountCustomer(@RequestBody PaymentDTO paymentDTO) {
        try {
            boolean isDeposited = paymentService.depositAmountCustomer(paymentDTO);
            if (isDeposited) {
                return ResponseEntity.ok("Payment deposited successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deposit payment");
            }
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the payment");
        }
    }
}

