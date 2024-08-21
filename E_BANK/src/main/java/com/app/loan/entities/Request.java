package com.app.loan.entities;

import com.app.entity.account.Account;
import com.app.loan.idGenerator.StringPrefixedSequenceIdGenerator;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a loan request made by an account holder.
 * Each request includes details such as the amount requested, duration, and status.
 */
@Entity
@Table(name="request")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Request extends BaseEntity{
	
	/**
     * Unique identifier for the loan request, generated using a custom sequence.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_sequence")
	@GenericGenerator(
		        name = "request_sequence", 
		        strategy = "com.app.loan.idGenerator.StringPrefixedSequenceIdGenerator", 
		        parameters = {
		            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
		            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "R_"),
		            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d") }
		        )
	@Column(name="request_id")
	private String requestId;

	
    /**
     * The amount of money requested in the loan request.
     */
	@Column(name = "loan_amount")
	private int loanAmount;
	
	
	/**
     * The duration of the loan requested, in months.
     */
	@Column(name="loan_duration")
	private int loanDuration;

	
	
	/**
     * The status of the loan request.
     * Status values are typically defined in an enum, e.g., 'R' for Requested, 'P' for PENDING, 'A' for APPROVED, 'D' DECLINED.
     */
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private Status status = Status.R;
	
	
	 /**
     * The account associated with this loan request. -> For Review
     * Each request is linked to a specific account.
     */
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name="account_number", nullable = false)
	private Account account;
	
	
	/**
     * Details about the type of loan requested. -> For Review
     * Each request references specific loan details.
     */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="loan_type", nullable = false)
	private LoanDetails details;

	
	/**
     * List of collateral associated with this loan request.
     * A loan request can have multiple collateral linked to it.
     */
	@OneToMany(mappedBy = "request", cascade = CascadeType.ALL,  orphanRemoval = true)
	@JsonIgnore
	private List<Collateral> collaterals = new ArrayList<>();
	
	
	/**
     * Adds a collateral to the list of collateral for this loan request.
     * Also sets the reference of this loan request on the collateral.
     * 
     * @param collateral The collateral to be added.
     */
	public void addCollateral(Collateral collateral) {
		this.collaterals.add(collateral);
		collateral.setRequest(this);
	}
	
}

