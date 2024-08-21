package com.app.loan.dto;

import com.app.loan.entities.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoanDetailResponse {
	private String loanNo;
    private double loanAmount;
    private double remainingAmount;
    private double emi;
    private String loanName;
    private double interestRate;
    private int tenure;
	private String asset;
	private String value;
	private String startDate;
	private String endDate;
	private String accountNo;
	private String status ;
	private String requestId;
	
}
