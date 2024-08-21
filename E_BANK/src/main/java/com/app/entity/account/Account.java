package com.app.entity.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;


import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.sound.midi.Receiver;
import javax.transaction.Transaction;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.app.dto.PaymentDTO;
import com.app.entity.bank.Bank;
import com.app.entity.base.BaseEntity;
import com.app.entity.customer.Customer;

import com.app.entity.enums.AccountStatus;
import com.app.entity.enums.TransType;
import com.app.entity.payment.Payment;
import com.app.entity.payment.TransactionHistory;
import com.app.id.generator.StringPrefixedSequenceIdGenerator;
import com.app.loan.entities.Loan;
import com.app.loan.entities.LoanDetails;
import com.app.loan.entities.LoanPayment;
import com.app.loan.entities.Request;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Account extends BaseEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    @GenericGenerator(
        name = "acc_seq", 
        strategy = "com.app.id.generator.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "AC"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	@Column(name="accNo")
	private String accountNo;
	
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
	
	@Column(name="balance")
	private double balance=0;

	@Column
	private boolean isDeleted=false;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankId",nullable=false)
	@JsonIgnore
	private Bank bank;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "acc_typeId")
	private AccountType accType;
	
	@Column(length=6)
    private String OTP;

	
	@OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Payment> paymentsMade = new ArrayList<>();
	
	@OneToMany(mappedBy = "receicerAccount", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
    private List<Payment> paymentsReceive = new ArrayList<>();
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<TransactionHistory> transactionHistories = new ArrayList<>();
	
	@OneToOne
	@JsonBackReference
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	
	@JsonManagedReference
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
    private List<Loan> loan = new ArrayList<>();
	
	public void addLoan(Loan loan) {
		this.loan.add(loan);
		loan.setAccount(this);
	}
	//Loan
	/**
     * List of requests associated with the account.
     * The list is managed by the account entity, with cascading and orphan removal enabled.
     */
	@JsonManagedReference
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch =  FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Request>request = new ArrayList<>();
	
	
	/**
     * Adds a request to the account.
     * Also sets the account reference on the request.
     * 
     * @param request The request to be added.
     */
	//helper method
	public void addRequest(Request request) {
		this.request.add(request);
		request.setAccount(this);
	}
	
	//Loan End

	
	
	 // Deposit method
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    // Withdraw method
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (this.balance < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        this.balance -= amount;
    }

	public void addPayment(Payment payment, Account receiverAccount) {
		
		this.getPaymentsMade().add(payment);
		payment.setSenderAccount(this);
		receiverAccount.getPaymentsReceive().add(payment);
		payment.setReceicerAccount(receiverAccount);
		
		
	}
	public void addPayment(Payment payment) {
		
		this.getPaymentsMade().add(payment);
		payment.setSenderAccount(this);
		
		
		
	}

	public void addTransaction(Payment payment, TransactionHistory senderTransHistory, PaymentDTO pay, String transStatus) {
		senderTransHistory.setDescription(pay.getDescription());
		senderTransHistory.setTransactionType(TransType.DEBIT);
		senderTransHistory.setAmount(pay.getAmount());
		senderTransHistory.setBalance(this.getBalance());
		senderTransHistory.setStatus(transStatus);
		senderTransHistory.setSenderAccountNo(pay.getSenderAccountNo());
		senderTransHistory.setReceiverAccountNo(pay.getReceiverAccountNo());;
		this.getTransactionHistories().add(senderTransHistory);
		senderTransHistory.setAccount(this);
		payment.getTransactionHistories().add(senderTransHistory);
		senderTransHistory.setPayment(payment);
		
	}

	public void addTransaction(LoanPayment loanPayment, TransactionHistory senderTransactionHistory, String string) {
		// TODO Auto-generated method stub
		
		senderTransactionHistory.setDescription("EMI payment");
		senderTransactionHistory.setTransactionType(TransType.EMI);
		senderTransactionHistory.setAmount(loanPayment.getLoanAmount());
		senderTransactionHistory.setBalance(loanPayment.getLoan().getRemainingAmount());
		senderTransactionHistory.setStatus(string);
}

	public void addTransaction(Request entity, LoanDetails loanDetails, TransactionHistory transactionHistory) {
		// TODO Auto-generated method stub
		transactionHistory.setAmount(entity.getLoanAmount());
        transactionHistory.setBalance(this.getBalance());
        transactionHistory.setCreatedOn(LocalDateTime.now());
        transactionHistory.setDescription("Loan Disbursed for "+loanDetails.getLoanName());
        transactionHistory.setStatus("SUCCESS");
        transactionHistory.setTransactionType(TransType.LOAN_DISBURSEMENT);
        transactionHistory.setReceiverAccountNo(this.getAccountNo());
        transactionHistory.setAccount(this);
       // this.getTransactionHistories().add(transactionHistory);
	}

	public void addTransaction(Payment payment, TransactionHistory receiverTransactionHistory, PaymentDTO payments,
			String success, TransType credit, Account receiverAccount) {
		receiverTransactionHistory.setDescription(payments.getDescription());
		receiverTransactionHistory.setTransactionType(credit);
		receiverTransactionHistory.setAmount(payments.getAmount());
		receiverTransactionHistory.setBalance(receiverAccount.getBalance());
		receiverTransactionHistory.setStatus(success);
		receiverTransactionHistory.setReceiverAccountNo(payments.getReceiverAccountNo());;
		this.getTransactionHistories().add(receiverTransactionHistory);
		receiverTransactionHistory.setAccount(receiverAccount);
		payment.getTransactionHistories().add(receiverTransactionHistory);
		receiverTransactionHistory.setPayment(payment);
	}

	

	






	
	

	
	
	
	
	

}
