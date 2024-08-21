package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {
	
	@NotBlank(message = "First name is required")
    @Size(max = 50, message = "Bank name can have at most 50 characters")
	private String bankName;
	
	@NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
	private String contactEmail;

}
