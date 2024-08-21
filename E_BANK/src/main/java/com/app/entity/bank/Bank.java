package com.app.entity.bank;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.app.id.generator.StringPrefixedSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Bank {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_seq")
    @GenericGenerator(
        name = "bank_seq",
        strategy = "com.app.id.generator.StringPrefixedSequenceIdGenerator",
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "BANK_"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
        }
    )
    private String bankBranchId;

    @Column(name = "bank_name", length = 50, nullable = false, unique = true)
    private String bankName;

    @Column(name = "bank_email", length = 50, nullable = false, unique = true)
    private String contactEmail;

    // FUNDS
    @Column
    private double fundAvailable = 0;
    
    @Column
    private double fundReceived = 0;
    
    @Column
    private double fundToPay = 0;

    // Loan
    @Column
    private double loanDisbursed = 0;
    
    @Column
    private double loanRecovered = 0;
    
    @Column
    private double loanExpected = 0;

    // Methods for managing funds
    public boolean addFundAvailable(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.fundAvailable += amount;
        return true;
    }

    public boolean subtractFundAvailable(double amount) {
        double newFundAvailable = this.fundAvailable - amount;
        double combinedAmount = this.loanExpected - this.loanRecovered + newFundAvailable;

        if (newFundAvailable < 0 || combinedAmount <= 0 || combinedAmount > 0.3 * (this.loanExpected  + this.fundAvailable)) {
            return false;
        }

        this.fundAvailable = newFundAvailable;
        return true;
    }

    public boolean addFundReceived(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.fundReceived += amount;
        return true;
    }

    public boolean subtractFundReceived(double amount) {
        if (this.fundReceived - amount < 0) {
            return false;
        }
        this.fundReceived -= amount;
        return true;
    }

    public boolean addFundToPay(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.fundToPay += amount;
        return true;
    }

    public boolean subtractFundToPay(double amount) {
        if (this.fundToPay - amount < 0) {
            return false;
        }
        this.fundToPay -= amount;
        return true;
    }

    public boolean addLoanDisbursed(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.loanDisbursed += amount;
        return true;
    }

    public boolean subtractLoanDisbursed(double amount) {
        if (this.loanDisbursed - amount < 0) {
            return false;
        }
        this.loanDisbursed -= amount;
        return true;
    }

    public boolean addLoanRecovered(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.loanRecovered += amount;
        return true;
    }

    public boolean subtractLoanRecovered(double amount) {
        if (this.loanRecovered - amount < 0) {
            return false;
        }
        this.loanRecovered -= amount;
        return true;
    }

    public boolean addLoanExpected(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.loanExpected += amount;
        return true;
    }

    public boolean subtractLoanExpected(double amount) {
        if (this.loanExpected - amount < 0) {
            return false;
        }
        this.loanExpected -= amount;
        return true;
    }
}

