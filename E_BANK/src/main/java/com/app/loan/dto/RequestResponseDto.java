package com.app.loan.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.app.loan.entities.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RequestResponseDto {
	@NotNull(message ="Date can't be null")
	private LocalDate createdOn;

	@NotNull(message ="Date can't be null")
	private LocalDateTime updatedOn;
	
    @NotBlank(message = "Request ID cannot be blank")
	private String requestId;

    @NotNull(message = "Loan amount cannot be null")
    @Min(value = 1, message = "Loan amount must be at least 1")
	private int loanAmount;
	
    @NotNull(message = "Loan duration cannot be null")
    @Min(value = 1, message = "Loan duration must be at least 1 month")
	private int loanDuration;
	
    @NotBlank(message = "Loan type cannot be blank")
	private String loanName;
	
    @NotNull(message = "Status cannot be null")
	private Status status ;
    
    private int tenure;
    
    private String accountNo;
    
    



}
