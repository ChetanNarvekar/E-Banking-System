package com.app.entity.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.app.entity.base.BaseEntity;
import com.app.entity.payment.TransactionHistory;
import com.app.id.generator.StringPrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="account_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class AccountType extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_type_seq")
    @GenericGenerator(
        name = "acc_type_seq", 
        strategy = "com.app.id.generator.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "AT_"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d") })
	private String accTypeId;
	@Column(name="acc_type_name", length=10,nullable = false)
	private String accTypeName;
	@Column(name="interest_rate",nullable = false)
	private float interestRate;
	
	@OneToMany(mappedBy = "accType", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
    private List<Account> accountList = new ArrayList<>();
	
	
	public void linkAccount(Account account)
	{
		this.accountList.add(account);
		account.setAccType(this);
	}
	

}
