package com.app.service;

import java.util.List;

import com.app.entity.account.Account;
import com.app.entity.account.AccountType;
import com.app.entity.bank.Bank;
import com.app.entity.customer.Customer;
import com.app.entity.enums.AccountStatus;

public interface AccountSevice {
	
	Account addAccount(Customer customer, Bank bank, String accType);
	
	List<Account> getAllDeactivatedAccount();
	
	List<Account> getAllActivatedAccount();
	
	List<Account> getAllSuspendedAccount();
	
	String changeStatusOfSuspendedAccount(String accId);
	
	String changeStatusOfDeactivatedAccount(String accId);

	String changeStatusOfActivatedAccount(String accId);

	void depositToAccount(String accountId, double amount);

	void withdrawFromAccount(String accountId, double amount);

	double checkBalance(String accountId);

	boolean checkAccountSuspension(Account account);

	/**
	 * Adds a new account to the system.
	 * This method saves the provided Account entity to the database using the AccountDao.
	 *
	 * @param acc The Account entity to be added. It should contain all necessary details for account creation.
	 * @return The saved Account entity, which may include generated values or additional information from the database.
	 */
	Account addAccount(Account acc);

	Account getAccountDetails(String accId);

}