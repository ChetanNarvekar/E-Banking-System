package com.app.dto;

import java.time.LocalDateTime;
import com.app.entity.enums.TransType;
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
public class TransactionHistoryDTO {

    private String transactionId;
    private TransType transactionType;
    private double amount;
    private String status;
    private String description;
    private double balance;
    private LocalDateTime createdOn;
    private String senderAccountNo;
    private String receiverAccountNo;
    private String accountId; // Simplified to just the ID
    private String paymentId; // Simplified to just the ID
    private String payment_id;
	

}

