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

public class AccountTypeAllDetailsDto {
	
	@NotBlank
	private String accTypeId;

	@NotBlank
	private String accTypeName;
	
	@NotNull
	private float interestRate;
	
}
