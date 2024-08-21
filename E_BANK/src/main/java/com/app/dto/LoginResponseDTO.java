package com.app.dto;

import com.app.entity.account.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

	private long customerId;
	
    private String email;

    private String role;

    private String phoneNumber;
    
    private String accountNo;
     
    private String jwtToken;
}
