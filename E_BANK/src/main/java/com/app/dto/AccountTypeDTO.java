package com.app.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountTypeDTO {
	
	 @NotBlank(message = "Account type name must not be blank")
	 private String accTypeName;

	 @Positive(message = "Interest rate must be positive")
	 private float interestRate;
}
