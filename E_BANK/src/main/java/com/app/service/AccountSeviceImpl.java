package com.app.service;

import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AccountDao;
import com.app.dao.AccountTypeDao;
import com.app.dao.BankDao;
import com.app.dao.CustomerDao;
import com.app.entity.account.Account;
import com.app.entity.account.AccountType;
import com.app.entity.bank.Bank;
import com.app.entity.customer.Customer;
import com.app.entity.enums.AccountStatus;
import com.app.exceptions.ResourceNotFoundException;
import com.app.miscellaneous.mail.MailSend;
import com.app.miscellaneous.mail.RegistrationMailSender;

@Service
@Transactional
public class AccountSeviceImpl implements AccountSevice {
    
    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private BankDao bankDao;
    
    @Autowired
    private AccountTypeDao accountTypeDao;
    
    @Autowired
    private CustomerDao customerDao;
    
    /**
     * Adds a new account for the specified customer and bank with the given account type.
     * 
     * @param customer the customer to which the account will be linked
     * @param bank the bank where the account will be created
     * @param accType the type of the account to be created
     * @return the created account
     */
    @Override
    public Account addAccount(Customer customer, Bank bank, String accType) {
        AccountType accountType = accountTypeDao.findByAccTypeName(accType)
                .orElseThrow(() -> new ResourceNotFoundException("Account type not found"));
        
        Account account = new Account();
        account.setBank(bank);
        account.setCustomer(customer);
        account.setStatus(AccountStatus.ACTIVATED);
        
        accountType.linkAccount(account);
        customer.addAccountToCustomer(account);
        
        Account createdAccount = accountDao.save(account);
        customerDao.save(customer);
        
        RegistrationMailSender reg =new RegistrationMailSender(customer.getEmail(),createdAccount.getAccountNo(),createdAccount.getBalance(),customer.getCustomerId(), accountType.getAccTypeName(),bank.getBankName());
		//MailSend mail =new MailSend();
		
		MailSend.sendEmail(reg.getMessage(), reg.getSubject(), reg.getTo());
        return createdAccount;
    }

    /**
     * Retrieves all deactivated accounts.
     * 
     * @return a list of all deactivated accounts
     * @throws ResourceNotFoundException if no deactivated accounts are found
     */
    @Override
    public List<Account> getAllDeactivatedAccount() {
        List<Account> deactivatedAccounts = accountDao.getAllDeactivatedAccount();
        if (deactivatedAccounts.isEmpty()) {
           return null;
        }
        return deactivatedAccounts;
    }

    /**
     * Retrieves all activated accounts.
     * 
     * @return a list of all activated accounts
     * @throws ResourceNotFoundException if no activated accounts are found
     */
    @Override
    public List<Account> getAllActivatedAccount() {
        List<Account> activatedAccounts = accountDao.getAllActivatedAccount();
        if (activatedAccounts.isEmpty()) {
            return null;
        }
        return activatedAccounts;
    }
    
    /**
     * Deposits the specified amount into the account with the given ID.
     * 
     * @param accountId the ID of the account
     * @param amount the amount to be deposited
     * @throws IllegalArgumentException if the deposit amount is not positive
     * @throws ResourceNotFoundException if the account is not found
     */
    @Override
    public void depositToAccount(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        
        Account account = accountDao.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        
        account.setBalance(account.getBalance() + amount);
        accountDao.save(account);
    }

    /**
     * Withdraws the specified amount from the account with the given ID.
     * 
     * @param accountId the ID of the account
     * @param amount the amount to be withdrawn
     * @throws IllegalArgumentException if the withdrawal amount is not positive or if the account has insufficient balance
     * @throws ResourceNotFoundException if the account is not found
     */
    @Override
    public void withdrawFromAccount(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        
        Account account = accountDao.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        
        account.setBalance(account.getBalance() - amount);
        accountDao.save(account);
    }

    /**
     * Checks the balance of the account with the given ID.
     * 
     * @param accountId the ID of the account
     * @return the balance of the account
     * @throws ResourceNotFoundException if the account is not found
     */
    @Override
    public double checkBalance(String accountId) {
        Account account = accountDao.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        
        return account.getBalance();
    }

    /**
     * Retrieves all suspended accounts.
     * 
     * @return a list of all suspended accounts
     * @throws ResourceNotFoundException if no suspended accounts are found
     */
    @Override
    public List<Account> getAllSuspendedAccount() {
        List<Account> suspendedAccounts = accountDao.getAllSuspendedAccount();
        if (suspendedAccounts.isEmpty()) {
            return null;
        }
        return suspendedAccounts;
    }

    /**
     * Changes the status of a suspended account to activated.
     * 
     * @param accountId the ID of the account
     * @return a message indicating whether the update was successful
     * @throws ResourceNotFoundException if the account is not found or if its status is not suspended
     */
    @Override
    public String changeStatusOfSuspendedAccount(String accountId) {
        Account account = accountDao.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        
        if (account.getStatus() == AccountStatus.SUSPENDED) {
            account.setStatus(AccountStatus.ACTIVATED);
            accountDao.save(account);
            return "Account status updated to activated";
        }
        return "Failed to update account status";
    }

    /**
     * Changes the status of a deactivated account to activated.
     * 
     * @param accountId the ID of the account
     * @return a message indicating whether the update was successful
     * @throws ResourceNotFoundException if the account is not found or if its status is not deactivated
     */
    @Override
    public String changeStatusOfDeactivatedAccount(String accountId) {
        Account account = accountDao.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        
        if (account.getStatus() == AccountStatus.DEACTIVATED) {
            account.setStatus(AccountStatus.ACTIVATED);
            accountDao.save(account);
            return "Account status updated to activated";
        }
        return "Failed to update account status";
    }

    /**
     * Changes the status of an activated account to deactivated.
     * 
     * @param accountId the ID of the account
     * @return a message indicating whether the update was successful
     * @throws ResourceNotFoundException if the account is not found or if its status is not activated
     */
    @Override
    public String changeStatusOfActivatedAccount(String accountId) {
        Account account = accountDao.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        
        if (account.getStatus() == AccountStatus.ACTIVATED) {
            account.setStatus(AccountStatus.DEACTIVATED);
            accountDao.save(account);
            return "Account status updated to deactivated";
        }
        return "Failed to update account status";
    }

    /**
     * Checks if the account should be suspended based on the last updated date.
     * 
     * @param account the account to be checked
     * @return false if the account is suspended, true otherwise
     */
    @Override
    public boolean checkAccountSuspension(Account account) {
        LocalDateTime lastUpdated = account.getUpdatedOn();
        LocalDateTime now = LocalDateTime.now();
        long daysSinceUpdate = ChronoUnit.DAYS.between(lastUpdated, now);
        
        if (daysSinceUpdate > 180) {
            account.setStatus(AccountStatus.SUSPENDED);
            accountDao.save(account);
            return false;
        }
        
        return true;
    }
    
    //loan
    /**
     * Adds a new account to the system.
     * This method saves the provided Account entity to the database using the AccountDao.
     *
     * @param acc The Account entity to be added. It should contain all necessary details for account creation.
     * @return The saved Account entity, which may include generated values or additional information from the database.
     */
	@Override
	public Account addAccount(Account acc) {
		
        // Save the account entity using the DAO and return the saved entity
		return accountDao.save(acc);
	}

	@Override
	public Account getAccountDetails(String accId) {
		// TODO Auto-generated method stub
		Account acc = accountDao.findById(accId).orElseThrow(()-> new ResourceNotFoundException("Given account id is not found"));
		return acc;
	}
}
