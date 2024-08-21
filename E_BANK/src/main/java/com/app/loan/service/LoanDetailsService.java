package com.app.loan.service;

import java.util.List;

import com.app.loan.dto.ApiResponse;
import com.app.loan.dto.LoanDetailsDto;
import com.app.loan.entities.Request;


/**
 * Service interface for handling operations related to `LoanDetails` entities.
 * This interface defines the methods available for managing loan details data.
 */
public interface LoanDetailsService {

	
    /**
     * Adds new loan details to the system.
     * This method processes the provided `LoanDetailsDto` to create and persist a new `LoanDetails` entity.
     *
     * @param loanDetails The data transfer object (DTO) containing information about the loan details to be added.
     *                    It includes details such as loan name, interest rate, tenure, maximum and minimum amounts.
     * @return An `ApiResponse` object containing the result of the operation, including a success message or error details.
     */
	ApiResponse addNewLoanDetails(LoanDetailsDto loanDetails);
	
	
	/**
     * Removes existing loan details from the system.
     * This method deletes the `LoanDetails` entity identified by the given loan type name.
     *
     * @param loanTypeName The name of the loan type to be removed. This value identifies the `LoanDetails` entity to be deleted.
     * @return An `ApiResponse` object containing the result of the operation, including a success message or error details.
     */
	ApiResponse removeLoanDretails(String loanTypeName);
	List<Request> getAllLoansByLoanName(String loanName);
}
