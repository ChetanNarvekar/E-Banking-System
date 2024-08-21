package com.app.loan.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AccountDao;
import com.app.dao.BankDao;
import com.app.dao.TransactionHistoryDao;
import com.app.entity.account.Account;
import com.app.entity.bank.Bank;
import com.app.entity.payment.TransactionHistory;
import com.app.loan.dao.LoanDao;
import com.app.loan.dto.ApiResponse;
import com.app.loan.dto.LoanResponseDto;
import com.app.loan.entities.Loan;
import com.app.loan.entities.LoanPayment;
import com.app.loan.entities.TransactionStatus;
import com.app.loan.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class LoanPaymentServiceImpl implements LoanPaymentService{

	
	@Autowired
	private LoanDao loanDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private TransactionHistoryDao transDao;
	
	@Autowired 
	private AccountDao accDao;
	@Autowired 
	private ModelMapper mapper;
	
	@Override
	public ApiResponse addPayment(String loan) {
		// TODO Auto-generated method stub
		// Retrieve bank details
        Bank bank = bankDao.getBankDetails().orElseThrow(() -> new ResourceNotFoundException("Invalid bank details"));
		String bankid = bank.getBankBranchId();
		Loan loanDet = loanDao.findById(loan).orElseThrow(()-> new ResourceNotFoundException("Loan Details Not Found for given Id"+ loan));
		//Retrieve account;
		Account loanPayerAccount =loanDet.getAccount();
		if(loanDet.getRemainingAmount() > 0) {
			LoanPayment loanPayment= new LoanPayment(loanDet, (loanDet.getRemainingAmount()-loanDet.getEmi()), loanDet.getLoanAmount(), TransactionStatus.DEBIT);
			loanDet.addLoanPayment(loanPayment);
			loanDet.setRemainingAmount(loanDet.getRemainingAmount()-loanDet.getEmi());
			//Fund transfer
			//Bank bank = account.getBank();
			loanPayerAccount.withdraw(loanDet.getEmi());
			bank.addFundAvailable(loanDet.getEmi());
			bank.addLoanRecovered(loanDet.getEmi());
			bank.subtractLoanExpected(loanDet.getEmi());
			
			//Transaction
			TransactionHistory senderTransactionHistory = new TransactionHistory();
			loanPayerAccount.addTransaction(loanPayment, senderTransactionHistory,"SUCCESS");
			senderTransactionHistory.setReceiverAccountNo(bankid);
			senderTransactionHistory.setAccount(loanPayerAccount);
			senderTransactionHistory.setCreatedOn(LocalDateTime.now());
			loanPayment.getTransactionHistories().add(senderTransactionHistory);
			senderTransactionHistory.setLoanPayment(loanPayment);
			transDao.save(senderTransactionHistory);
			//
			loanDao.save(loanDet);
			return new ApiResponse("Money Debited Successfully");
		}else {
			if(loanDet.getRemainingAmount() == 0) {
				loanDet.setLoanStatus('C');
				return new ApiResponse("You have already paid your loan amount");
			}else {
				return new ApiResponse("Some issue remaining amount cannot be negative");
			}
		}
	}
	@Override
	public List<LoanResponseDto> getLoanDetailsByAccountNo(String accountNo) {
	    // Fetch the Account entity by account number
	    Account account = accDao.findByAccountNo(accountNo);
	    if (account == null) {
	        throw new ResourceNotFoundException("Account not found with account number: " + accountNo);
	    }

	    // Get the list of Loan entities associated with the Account
	    List<Loan> loans = account.getLoan();
	    if (loans == null || loans.isEmpty()) {
	        throw new ResourceNotFoundException("No loans found for account number: " + accountNo);
	    }

	    // Convert the list of Loan entities to a list of LoanResponseDto
	    List<LoanResponseDto> loanResponseDtos = loans.stream()
	        .map(loan -> mapper.map(loan, LoanResponseDto.class))
	        .collect(Collectors.toList());

	    return loanResponseDtos;
	}

}
