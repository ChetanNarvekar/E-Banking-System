package com.app.loan.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoanResponseDto {
	
	private String loanNo;
	
	private float loanAmount;
	
	private float emi;
	
	private String startDate;
	
	private String endDate;
	
	private float remainingAmount;

}
