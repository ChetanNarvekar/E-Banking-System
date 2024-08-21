package com.app.entity.customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.app.entity.account.Account;
import com.app.entity.base.BaseEntity;
import com.app.entity.enums.Gender;
import com.app.entity.enums.Role;
import com.app.entity.payment.TransactionHistory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Customer extends BaseEntity {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long customerId;
	    @Column(length=20,nullable = false)
	    private String firstName;
	    @Column(length=20,nullable = false)
	    private String middleName;
	    @Column(length=20,nullable = false)
	    private String lastName;
	    @Column(nullable = false, unique = true)
	    private String email;
	    @Column(length=10,nullable = false, unique = true)
	    private String phoneNumber;
	    @Column(nullable=false)
		private String password;
	    @Column(nullable = false)
	    private LocalDate dateOfBirth;
	    @Enumerated(EnumType.STRING)
	    private Gender gender;
	    @Column(length=12,nullable = false)
	    private String adharNo;
	    @Column(length=10)
	    private String panNo;
	    @Enumerated(EnumType.STRING)
		private Role role=Role.ROLE_CUSTOMER;
	    @Column(nullable = false)
	    private boolean status=false;
	    @Column(length=6)
	    private String otp;
	    @Column(nullable = false)
	    private String accountType;
	    
	    @Column(nullable=false)
		private String tpin;
	    
	    @OneToOne(mappedBy = "customer")
	    @JsonIgnore
	    private Account account;

	    
	    public void addAccountToCustomer(Account account)
	    {
	    	this.setAccount(account);	
	    	account.setCustomer(this);
	    }

	    public boolean verifyTpin(String inputTpin) {
	        return this.tpin.equals(inputTpin);
	    }
	    
}
