package com.app.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.app.entity.enums.TransType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
public class PaymentDTO {
	
	@NotBlank(message = "Sender account number is mandatory")
    //@Pattern(regexp = "\\d{10}", message = "Sender account number must be 10 digits")
	@JsonProperty(access = Access.WRITE_ONLY)
    private String senderAccountNo;

    @NotBlank(message = "Receiver account number is mandatory")
    //@Pattern(regexp = "\\d{10}", message = "Receiver account number must be 10 digits")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String receiverAccountNo;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private double amount;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

//    @NotBlank(message = "Status of payment is mandatory")
//    //@Pattern(regexp = "Pending|Completed|Failed", message = "Status of payment must be one of: Pending, Completed, Failed")
//    private String statusOfPayment;

    @NotNull(message = "Transaction type is mandatory")
    private TransType type;
    
    @NotBlank(message = "Status of payment is mandatory")
    private String IFSCCode;
	
	
	
	
	
	
	
	

}
