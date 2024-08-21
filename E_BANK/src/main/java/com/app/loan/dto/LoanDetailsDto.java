package com.app.loan.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Data Transfer Object (DTO) for representing loan details.
 * This class is used to transfer loan information between different layers of the application.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoanDetailsDto {
	
	/**
     * The name or type of the loan.
     * This field describes the specific category or name given to the loan product.
     */
	@NotBlank(message = "Loan name cannot be blank")
	private String loanName;
	
	
	/**
     * The interest rate applicable to the loan.
     * This field represents the percentage rate at which interest is charged on the loan.
     */
    @NotNull(message = "Interest rate cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Interest rate must be greater than 0")
	private float interestRate;
	
	
	/**
     * The tenure of the loan in months.
     * This field indicates the total duration over which the loan is to be repaid.
     */
    @NotNull(message = "Tenure cannot be null")
    @Min(value = 1, message = "Tenure must be at least 1 month")
	private int tenure;
	
	
	/**
     * The maximum amount that can be borrowed under this loan type.
     * This field specifies the upper limit of the loan amount that can be approved.
     */
    @NotNull(message = "Maximum amount cannot be null")
    @Positive(message = "Maximum amount must be greater than 0")
	private int maxAmount;
	
	
	 /**
     * The minimum amount that can be borrowed under this loan type.
     * This field specifies the lower limit of the loan amount that can be approved.
     */
    @NotNull(message = "Minimum amount cannot be null")
	@Positive(message = "Minimum amount must be greater than 0")
	private int minAmount;
}
