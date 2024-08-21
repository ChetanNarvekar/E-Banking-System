package com.app.response.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class AccountTypeDto {
	@NotBlank
	private String accountType;
	
	@NotNull(message = "Value can't be null")
	private float interestRate;
}
