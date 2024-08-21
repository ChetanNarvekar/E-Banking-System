package com.app.entity.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.app.entity.account.Account;
import com.app.entity.enums.TransType;
import com.app.id.generator.StringPrefixedSequenceIdGenerator;
import com.app.loan.entities.LoanPayment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="transaction_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class TransactionHistory  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    @GenericGenerator(
        name = "acc_seq", 
        strategy = "com.app.id.generator.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "TRF_"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	@Column(name="transaction_id")
	private String transactionId;
	@Enumerated(EnumType.STRING)
	private TransType transactionType;
	
	@Column(name="amount",nullable = false)
	private double amount;
	@Column(name="status",nullable = false)
	private String status;
	@Column(name="description",length=50)
	private String description;
	@Column(name="balance",nullable = false)
	private double balance;
	@Column(name="created_on")
	 @CreationTimestamp
	 private LocalDateTime createdOn;
	
	private String receiverAccountNo;
	private String senderAccountNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ref_id")
	private Payment payment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_ref_id")
	private LoanPayment loanPayment;

}
