package com.app.loan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.loan.dto.LoanDetailResponse;
import com.app.loan.dto.RequestDto;
import com.app.loan.dto.RequestResponseDto;
import com.app.loan.entities.Request;
import com.app.loan.service.RequestService;
@CrossOrigin(origins = "http://localhost:3001/")
@RestController
@RequestMapping("/bank")
public class LoanRequestController {
	@Autowired 
	private RequestService reqService;
	
	
	//Administrator Side API
	
	// Administrator will have the access to see status of loan request
	
	// By default when customer is going to apply for loan , status of his request is going to be "requested"
	/**
	 * End-point for administrators to retrieve all loan requests with status "requested".
	 * 
	 * This method handles GET requests to "/loan/requested". It fetches all loan requests
	 * that have the status "requested". If no requests are found, it returns HTTP status 204 (No Content).
	 * 
	 * @return A `ResponseEntity` containing the list of requested loan requests or HTTP status 204 if no content.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/getallloanrequested")
	public ResponseEntity<?> getAllRequestedAccount()
	{
		System.out.println("Hii");
		List<RequestResponseDto> accList =reqService.viewRequested();
		if(accList.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(accList);
	}
	
	
	/**
	 * End-point for administrators to retrieve all loan requests with status "pending".
	 * 
	 * This method handles GET requests to "/loan/pending". It fetches all loan requests
	 * that have the status "pending". If no requests are found, it returns HTTP status 204 (No Content).
	 * 
	 * @return A `ResponseEntity` containing the list of pending loan requests or HTTP status 204 if no content.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/getallloanpending")
	public ResponseEntity<?> getAllPendingAccount()
	{
		List<LoanDetailResponse> accList =reqService.viewPending();
		if(accList.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(accList);
	}
	
	
	/**
	 * End-point for administrators to retrieve all loan requests with status "approved".
	 * 
	 * This method handles GET requests to "/loan/approved". It fetches all loan requests
	 * that have the status "approved". If no requests are found, it returns HTTP status 204 (No Content).
	 * 
	 * @return A `ResponseEntity` containing the list of approved loan requests or HTTP status 204 if no content.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/getallloanapproved")
	public ResponseEntity<?> getAllApprovedAccount()
	{
		List<Request> accList =reqService.viewApproved();
		if(accList.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(accList);
	}
	
	
	/**
	 * End-point for administrators to retrieve all loan requests with status "declined".
	 * 
	 * This method handles GET requests to "/loan/declined". It fetches all loan requests
	 * that have the status "declined". If no requests are found, it returns HTTP status 204 (No Content).
	 * 
	 * @return A `ResponseEntity` containing the list of declined loan requests or HTTP status 204 if no content.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/getallloandeclined")
	public ResponseEntity<?> getAllDeclinedAccount()
	{
		List<LoanDetailResponse> accList =reqService.viewDeclined();
		if(accList.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(accList);
	}
	
	
	/**
	 * End-point for administrators to update a loan request status to "pending".
	 * 
	 * This method handles PATCH requests to "/loan/pending/{id}". It updates the status of a specific
	 * loan request identified by the provided ID to "pending". The method returns HTTP status 200 (OK).
	 * 
	 * @param id The ID of the loan request to update.
	 * @return A `ResponseEntity` with the result of the update operation.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
	@PatchMapping("/admin/pending/{id}")
	public ResponseEntity<?> updateToPending(@PathVariable("id") String id){
		return ResponseEntity.ok(reqService.updateToPending(id));
	}
	
	
	/**
	 * End-point for administrators to update a loan request status to "approved".
	 * 
	 * This method handles PATCH requests to "/loan/approved/{id}". It updates the status of a specific
	 * loan request identified by the provided ID to "approved". The method returns HTTP status 200 (OK).
	 * 
	 * @param id The ID of the loan request to update.
	 * @return A `ResponseEntity` with the result of the update operation.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
	@PatchMapping("/admin/approved/{id}")
	public ResponseEntity<?> updateToApproved(@PathVariable("id") String id){
		return ResponseEntity.ok(reqService.updateToApproved(id));
	}
	
	
	/**
	 * End-point for administrators to update a loan request status to "declined".
	 * 
	 * This method handles PATCH requests to "/loan/declined/{id}". It updates the status of a specific
	 * loan request identified by the provided ID to "declined". The method returns HTTP status 200 (OK).
	 * 
	 * @param id The ID of the loan request to update.
	 * @return A `ResponseEntity` with the result of the update operation.
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
	@PatchMapping("/admin/declined/{id}")
	public ResponseEntity<?> updateToDeclined(@PathVariable("id") String id){
		return ResponseEntity.ok(reqService.updateToDeclined(id));
	}
	
	/**
	 Customer Side API -> Customer will have only Add Request
	 Once he submits the request administrator is going to check for conditions for loan approval
	 If all conditions meets then he will send an email to Customer for their loan eligibility and asks for Collateral Details and Status will be set to pending in Loan Request table  
	
	 * End-point for customers to add a new loan request.
	 * 
	 * This method handles POST requests to "/loan/add". It accepts a `RequestDto` object in the request body
	 * and invokes the `addRequest` method of the `RequestService` to create a new loan request. The method 
	 * returns HTTP status 201 (Created).
	 * 
	 * @param requestDto The `RequestDto` object containing details of the loan request to be added.
	 * @return A `ResponseEntity` containing the response from the service and HTTP status 201 (Created).
	 */
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SUPER_ADMIN', 'DISABLED')")
	@PostMapping("/user/addloanrequest")
	public ResponseEntity<?> addLoanRequest(@RequestBody RequestDto requestDto){
		System.out.println("Hii in controller");
		System.out.println(requestDto.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(reqService.addRequest(requestDto));
		
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SUPER_ADMIN', 'DISABLED')")
	@GetMapping("/user/listOfRequest/{id}")
	public ResponseEntity<?> getListOfLoansByAccountId(@PathVariable("id") String id){
		
		return ResponseEntity.ok(reqService.getListOfLoansByAccount(id));
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'SUPER_ADMIN', 'DISABLED')")
	@GetMapping("/user/getloanrequestsbyacno/{accountNo}")
    public ResponseEntity<List<RequestResponseDto>> getRequestsByAccountNo(@PathVariable String accountNo) {
        List<RequestResponseDto> requests = reqService.getAllRequestsByAccountNo(accountNo);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
	@GetMapping("/admin/getallloandetails")
	    public List<LoanDetailResponse> getAllLoanDetails() {
	        return reqService.getAllRequests();
	    }
	
	
}
