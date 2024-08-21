package com.app.entity.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.app.entity.account.Account;
import com.app.entity.base.BaseEntity;

import com.app.id.generator.StringPrefixedSequenceIdGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Payment {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pay_seq")
	    @GenericGenerator(
	            name = "pay_seq", 
	            strategy = "com.app.id.generator.StringPrefixedSequenceIdGenerator", 
	            parameters = {
	                @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
	                @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "PID"),
	                @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d") })
	@Column(name="ref_id")
	private String refId;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_acc_No_In")
	private Account receicerAccount;
	
	 @Column(name="created_on")
	 @CreationTimestamp
	 private LocalDateTime createdOn;
	@Column(name="receive_acc_No")
	private String reciverAccountNo;
	
	@Column(name="sender_acc_No_Out")
	private String senderAccountNo;
	
	private String IFSCCode="BANK_00001";
	
	@Column
	private boolean status=true;
	@Column(name="amount")
	private double amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_acc_No")
	private Account senderAccount;
	
	@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionHistory> transactionHistories = new ArrayList<>();
	
	

	
}
