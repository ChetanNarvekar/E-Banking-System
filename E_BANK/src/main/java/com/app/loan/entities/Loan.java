package com.app.loan.entities;

import com.app.entity.account.Account;
import com.app.loan.idGenerator.StringPrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Represents a loan entity in the system. Each loan is associated with an account,
 * has a specified amount, EMI, start and end dates, status, and can have associated
 * collateral and loan payments.
 */

@Entity
@Table(name = "loan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Loan extends BaseEntity{
	
	/**
     * Unique identifier for the loan, generated using a custom sequence.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_seq")
    @GenericGenerator(
        name = "loan_seq", 
        strategy = "com.app.loan.idGenerator.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "L_"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d") 
        }
    )
	@Column(name="loan_no")
	private String loanNo;

	
	/**
     * The account associated with this loan.
     * Each loan is linked to a specific account.
     */
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name="account_id", nullable = false)
	private Account account;
	
	
	/**
     * Total amount of the loan.(Total Amount = Principle Amount + Interest Amount)
     */
	@Column(name="loan_amount")
	private float loanAmount;
	
	/**
     * Total amount of the loan.(Total Amount = Principle Amount + Interest Amount)
     */
	@Column(name="remaining_amount")
	private float remainingAmount;
	
	 /**
     * The Equated Monthly Installment (EMI) amount for the loan.
     */
	@Column(name="emi")
	private float emi;
	
	
	/**
     * The date when the loan starts.
     */
	@Column(name="start_date")
	private LocalDate startDate;
	
	
	/**
     * The date when the loan ends.
     */
	@Column(name="end_date")
	private LocalDate endDate;
	
	
	/**
     * The current status of the loan. Default is 'O' (Open).
     */
	@Column(name="loan_status")
	private char loanStatus= 'O';
		
	
	/**
     * Details about the type of loan.
     * Each loan is associated with specific loan details.
     * For Review
     */
	@ManyToOne()
	@JoinColumn(name="loan_type", nullable = false)
	private LoanDetails loanDetails;
	
	
	/**
     * Collateral associated with the loan.
     * Each loan can have a specific collateral linked to it.
     */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="collateral_id", nullable = false)
	private Collateral collateralId;
	
	
	/**
     * List of loan payments associated with this loan.
     * A loan can have multiple payments associated with it.
     */
	@JsonIgnore
	@OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LoanPayment> loanPayment = new ArrayList<>();
	
	
	/**
     * Adds a loan payment to the list of loan payments.
     * Also sets the reference of this loan on the payment.
     * 
     * @param loanPayment The loan payment to be added.
     */
	public void addLoanPayment(LoanPayment loanPayment) {
		this.loanPayment.add(loanPayment);
		loanPayment.setLoan(this);
	}
	
	
	/**
     * Constructor with parameters for initializing essential fields.
     * 
     * @param account_id The account associated with the loan.
     * @param loanAmount The total amount of the loan.
     * @param emi The EMI amount for the loan.
     * @param startDate The start date of the loan.
     * @param endDate The end date of the loan.
     * @param loanDetails The details about the loan type.
     * @param collateralId The collateral associated with the loan.
     */
	public Loan(Account account_id, float loanAmount, float emi, LocalDate startDate, LocalDate endDate,
			LoanDetails loanDetails, Collateral collateralId) {
		super();
		this.account = account_id;
		this.loanAmount = loanAmount;
		this.emi = emi;
		this.startDate = startDate;
		this.endDate = endDate;
		this.loanDetails = loanDetails;
		this.collateralId = collateralId;
		this.remainingAmount = loanAmount;
	}

}
