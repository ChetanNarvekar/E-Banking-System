package com.app.dto;

import java.time.LocalDate;

import com.app.entity.account.Account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponseDTO {
	
	private String loanNo;
	
	private String accountId;
	
	private float loanAmount;
	
	private float remainingAmount;
	
	private float emi;
	
	private LocalDate startDate;

}
