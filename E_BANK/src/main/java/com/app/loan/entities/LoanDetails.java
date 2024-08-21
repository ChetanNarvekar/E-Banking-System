package com.app.loan.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Represents the details of a loan type in the system.
 * Each loan type includes information such as interest rate, tenure, and amount limits.
 */
@Entity
@Table(name="details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class LoanDetails extends BaseEntity{
		
	
		/**
		 * The unique name or identifier of the loan type.
		 * This field serves as the primary key.
		*/
		@Id
		@Column(name="loan_name", nullable = false, unique = true)
		private String loanName;
		
		
		/**
	     * The interest rate for the loan type.
	     */
		@Column(name="interest_rate", nullable = false)
		private float interestRate;
		
		
		/**
	     * The tenure (in months) for the loan type.
	     */
		@Column(name="tenure", nullable = false)
		private int tenure;
		
		
		/**
	     * The maximum amount that can be loaned under this loan type.
	     */
		@Column(name="max_amount", nullable = false)
		private int maxAmount;
		
		
		/**
	     * The minimum amount that can be loaned under this loan type.
	     */
		@Column(name="min_amount", nullable = false)
		private int minAmount;
		
		
		 /**
	     * List of requests associated with this loan type.
	     * This is a bidirectional relationship where each request references this loan type.
	     */
		@JsonIgnore
		@OneToMany(mappedBy = "details", cascade = CascadeType.ALL, orphanRemoval = true)
		private List<Request>requests = new ArrayList<>();
		
		
		/**
	     * Adds a request to the list of requests for this loan type.
	     * Also sets the reference of this loan type on the request.
	     * 
	     * @param request The request to be added.
	     */
		public void addRequestLoanType(Request requests) {
			this.requests.add(requests);
			requests.setDetails(this);
		}
		
		
		/**
	     * Constructor for initializing a LoanDetails instance with specific values.
	     * 
	     * @param interestRate The interest rate for the loan type.
	     * @param tenure The tenure (in months) for the loan type.
	     * @param maxAmount The maximum loan amount for this loan type.
	     * @param minAmount The minimum loan amount for this loan type.
	     * @param requests The list of requests associated with this loan type.
	     */
		public LoanDetails(float interestRate, int tenure, int maxAmount, int minAmount, List<Request> requests) {
			super();
			this.interestRate = interestRate;
			this.tenure = tenure;
			this.maxAmount = maxAmount;
			this.minAmount = minAmount;
			this.requests = requests;
		}
		
	}
	

