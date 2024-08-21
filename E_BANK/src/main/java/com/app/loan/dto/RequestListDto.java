package com.app.loan.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.app.loan.entities.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Data Transfer Object (DTO) for representing a list of loan requests.
 * This class is used to transfer summary information about loan requests, typically used in responses that include
 * multiple requests or for displaying request details in a list format.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RequestListDto {
	
	
	/**
     * The unique identifier for the loan request.
     * This field represents the ID assigned to each loan request, which is used for identification and retrieval.
     */
    @NotBlank(message = "Request ID cannot be blank")
	private String requestId;
	
	
	/**
     * The amount of money requested for the loan.
     * This field indicates the total amount that the applicant is seeking to borrow.
     */
    @NotNull(message = "Loan amount cannot be null")
    @Min(value = 1, message = "Loan amount must be at least 1")
	private int loanAmount;
	
	
	/**
     * The duration of the loan in months.
     * This field specifies how long the applicant intends to take to repay the loan.
     */
    @NotNull(message = "Loan duration cannot be null")
    @Min(value = 1, message = "Loan duration must be at least 1 month")
	private int loanDuration;
	
	
	/**
     * The type of loan requested.
     * This field represents the category or type of the loan that the applicant has applied for.
     */
    @NotBlank(message = "Loan type cannot be blank")
	private String loanType;
	
	
	 /**
     * The current status of the loan request.
     * This field represents the current state or progress of the loan request, such as pending, approved, or rejected.
     */
    @NotNull(message = "Status cannot be null")
	private Status status ;

}
