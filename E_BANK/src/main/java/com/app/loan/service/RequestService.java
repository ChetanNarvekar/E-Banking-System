package com.app.loan.service;

import java.util.List;

import com.app.dto.AccountResponseDTO;
import com.app.entity.account.*;
import com.app.loan.dto.ApiResponse;
import com.app.loan.dto.LoanDetailResponse;
import com.app.loan.dto.RequestDto;
import com.app.loan.dto.RequestResponseDto;
import com.app.loan.entities.Loan;
import com.app.loan.entities.Request;


/**
 * Service interface for handling loan request operations.
 * Provides methods to manage loan requests, including adding new requests and updating their statuses.
 */
public interface RequestService {
	
	/**
     * Adds a new loan request.
     * 
     * This method takes a `RequestDto` object containing the details of the loan request and processes it to
     * create a new `Request` entity in the system.
     * 
     * @param reqDto The data transfer object (DTO) containing the loan request details.
     * @return An `ApiResponse` object indicating the result of the operation.
     */
	ApiResponse addRequest(RequestDto reqDto);
	
	/**
     * Retrieves a list of pending loan requests.
     * 
     * This method returns all loan requests that are currently in a pending state.
     * 
     * @return A list of `Request` entities with a pending status.
     */
	List<LoanDetailResponse> viewPending();
	
	 /**
     * Retrieves a list of all requested loan requests.
     * 
     * This method returns all loan requests regardless of their current status.
     * 
     * @return A list of all `Request` entities.
     */
	List<RequestResponseDto> viewRequested();
	
	/**
     * Retrieves a list of approved loan requests.
     * 
     * This method returns all loan requests that have been approved.
     * 
     * @return A list of `Request` entities with an approved status.
     */
	List<Request> viewApproved();
	
	/**
     * Retrieves a list of declined loan requests.
     * 
     * This method returns all loan requests that have been declined.
     * 
     * @return A list of `Request` entities with a declined status.
     */
	List<LoanDetailResponse> viewDeclined();
	
	/**
     * Updates the status of a loan request to pending.
     * 
     * This method changes the status of a specific loan request identified by its `requestId` to "pending".
     * 
     * @param requestId The unique identifier of the loan request to be updated.
     * @return A string message indicating the result of the operation.
     */
	ApiResponse updateToPending(String requestId);
	
	/**
     * Updates the status of a loan request to approved.
     * 
     * This method changes the status of a specific loan request identified by its `requestId` to "approved".
     * 
     * @param requestId The unique identifier of the loan request to be updated.
     * @return A string message indicating the result of the operation.
     */
	ApiResponse updateToApproved(String requestId);
	
	/**
     * Updates the status of a loan request to declined.
     * 
     * This method changes the status of a specific loan request identified by its `requestId` to "declined".
     * 
     * @param requestId The unique identifier of the loan request to be updated.
     * @return A string message indicating the result of the operation.
     */
	ApiResponse updateToDeclined(String requestId);
	
	

	List<LoanDetailResponse> getListOfLoansByAccount(String accountNo);

	List<RequestResponseDto> getAllRequestsByAccountNo(String accountNo);

	List<LoanDetailResponse> getAllRequests();
	
}
