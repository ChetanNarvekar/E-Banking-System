package com.app.service;

import javax.transaction.Transactional;

import org.apache.coyote.BadRequestException;
import org.hibernate.internal.build.AllowSysOut;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AccountDao;
import com.app.dao.BankDao;
import com.app.dao.PaymentDao;
import com.app.dao.TransactionHistoryDao;
import com.app.dto.PaymentDTO;
import com.app.entity.account.Account;
import com.app.entity.bank.Bank;
import com.app.entity.enums.AccountStatus;
import com.app.entity.enums.TransType;
import com.app.entity.payment.Payment;
import com.app.entity.payment.TransactionHistory;
import com.app.exceptions.ResourceNotFoundException;
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDao payDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private AccountDao accDao;
	@Autowired
	private TransactionHistoryDao transDao;
	
	@Autowired
	private ModelMapper mapper;
	
	
	
	
	@Override
	public boolean paymentWithinBank(PaymentDTO payments) throws BadRequestException {
	    try {
	        // Retrieve bank details
	        Bank bank = bankDao.getBankDetails().orElseThrow(() -> new ResourceNotFoundException("Invalid bank details"));
	        payments.setIFSCCode(bank.getBankBranchId());
	        System.out.println("Retrieved bank details successfully");

	        // Map PaymentDTO to Payment entity
	        Payment payment = mapper.map(payments, Payment.class);
	        payment.setReciverAccountNo(payments.getReceiverAccountNo());
	        
	        // Retrieve sender and receiver accounts
	        Account senderAccount = accDao.findById(payments.getSenderAccountNo())
	            .orElseThrow(() -> new ResourceNotFoundException("Invalid sender account"));
	        Account receiverAccount = accDao.findById(payments.getReceiverAccountNo())
	            .orElseThrow(() -> new ResourceNotFoundException("Invalid receiver account"));
	        System.out.println("Retrieved sender and receiver accounts successfully");

	        // Validate account statuses
	        if (senderAccount.getStatus() == AccountStatus.SUSPENDED || senderAccount.getStatus() == AccountStatus.DEACTIVATED
	            || receiverAccount.getStatus() == AccountStatus.DEACTIVATED) {
	            throw new BadRequestException("Invalid Request: One or both accounts are deactivated or suspended");
	        }

	        double amount = payments.getAmount();

	        // Check sender's balance
	        if (senderAccount.getBalance() < amount) {
	            throw new BadRequestException("Insufficient Funds");
	        }

	        // Apply fee if the payment amount exceeds the limit
	        if (amount > 200000) {
	            double fee = amount * 0.0005;
	            senderAccount.withdraw(fee);
	            bank.addFundAvailable(fee);
	            
	            bankDao.save(bank);
	            System.out.println("Fee applied and bank updated successfully");
	        }

	        // Perform the transfer
	        senderAccount.withdraw(amount);
	        receiverAccount.deposit(amount);
	       
	        //Transaction History for sender
	        TransactionHistory senderTransactionHistory = new TransactionHistory();
	        senderAccount.addTransaction(payment, senderTransactionHistory, payments,"SUCCESS");
	      //Transaction History for receiver
	        TransactionHistory receiverTransactionHistory = new TransactionHistory();
	        receiverAccount.addTransaction(payment, receiverTransactionHistory, payments,"SUCCESS");
	        receiverTransactionHistory.setTransactionType(TransType.CREDIT);
	        receiverTransactionHistory.setReceiverAccountNo(payments.getSenderAccountNo());
	        
	        
	        
	        
	        
	        // Save payment details
	        senderAccount.addPayment(payment, receiverAccount);
	        payDao.save(payment);
	        transDao.save(senderTransactionHistory);
	        transDao.save(receiverTransactionHistory);
	        // Save updated accounts
	        accDao.save(senderAccount);
	        accDao.save(receiverAccount);

	        System.out.println("Payment processed and accounts updated successfully");
	        return true;

	    } catch (Exception e) {
	        // Log the exception and throw a custom error if needed
	        e.printStackTrace();
	        throw new RuntimeException("An error occurred while processing the payment", e);
	    }
	}




	@Override
	public boolean paymentOutsideBank(PaymentDTO payments) throws BadRequestException {
	    try {
	    	String receiverAccNo = payments.getReceiverAccountNo();
	        // Retrieve bank details
	        Bank bank = bankDao.getBankDetails().orElseThrow(() -> new ResourceNotFoundException("Invalid bank details"));
	        // Map PaymentDTO to Payment entity
	        Payment payment = mapper.map(payments, Payment.class);
	        
	        payment.setReciverAccountNo(receiverAccNo);
	        
	        // Retrieve sender account
	        Account senderAccount = accDao.findById(payments.getSenderAccountNo())
	            .orElseThrow(() -> new ResourceNotFoundException("Invalid sender account"));

	        // Validate sender account status
	        if (senderAccount.getStatus() == AccountStatus.SUSPENDED || senderAccount.getStatus() == AccountStatus.DEACTIVATED) {
	            throw new BadRequestException("Invalid Request: Sender account is deactivated or suspended");
	        }

	        double amount = payments.getAmount();

	        // Check sender's balance
	        if (senderAccount.getBalance() < amount) {
	            throw new BadRequestException("Insufficient Funds");
	        }

	        // Apply fee if the payment amount exceeds the limit
	        if (amount > 200000) {
	            double fee = amount * 0.0005;
	            senderAccount.withdraw(fee);
	            bank.addFundAvailable(fee);
	        }

	        // Perform the transfer within the bank
	        senderAccount.withdraw(amount);
	        bank.addFundToPay(amount);
	        bank.addFundAvailable(amount);
	       
	        
	        payment.setStatus(false);
	        
	      //Transaction History for sender
	        TransactionHistory senderTransactionHistory = new TransactionHistory();
	        senderAccount.addTransaction(payment, senderTransactionHistory, payments,"PENDING");
	        senderTransactionHistory.setSenderAccountNo(payments.getSenderAccountNo());

	        // Interact with the external bank to deposit the amount


	        // Save payment details
	        senderAccount.addPayment(payment); // External bank, no receiver account within our system
	        payDao.save(payment);
	        // Save bank details
	        bankDao.save(bank);
	        // Save updated sender account
	        accDao.save(senderAccount);
	        transDao.save(senderTransactionHistory);
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        return true;

	    } catch (Exception e) {
	        // Log the exception and throw a custom error if needed
	        e.printStackTrace();
	        throw new RuntimeException("An error occurred while processing the payment", e);
	    }
	}


	@Override
	public boolean depositAmountCustomer(PaymentDTO payments) throws BadRequestException {
	    try {
	    	String senderAccountNo = payments.getSenderAccountNo();
	        // Retrieve bank details
	        Bank bank = bankDao.getBankDetails().orElseThrow(() -> new ResourceNotFoundException("Invalid bank details"));
	        // Map PaymentDTO to Payment entity
	        Payment payment = mapper.map(payments, Payment.class);
	        
	        payment.setSenderAccountNo(senderAccountNo);
	        
	        // Retrieve sender account
	        Account receiverAccount = accDao.findById(payments.getReceiverAccountNo())
	            .orElseThrow(() -> new ResourceNotFoundException("Invalid sender account"));

	        // Validate sender account status
	        if (receiverAccount.getStatus() == AccountStatus.SUSPENDED || receiverAccount.getStatus() == AccountStatus.DEACTIVATED) {
	            throw new BadRequestException("Invalid Request: Sender account is deactivated or suspended");
	        }

	        double amount = payments.getAmount();

	        // Check sender's balance
	      

	        // Apply fee if the payment amount exceeds the limit
	        if (amount > 200000) {
	            double fee = amount * 0.0005;
	            receiverAccount.deposit(fee);;
	            bank.addFundAvailable(fee);
	        }

	        // Perform the transfer within the bank
	        receiverAccount.deposit(amount);
	       
	        bank.addFundAvailable(amount);
	       
	        
	        payment.setStatus(false);
	        
	      //Transaction History for sender
	        TransactionHistory receiverTransactionHistory = new TransactionHistory();
	        receiverAccount.addTransaction(payment, receiverTransactionHistory, payments,"SUCESS",TransType.CREDIT,receiverAccount);
	        receiverTransactionHistory.setSenderAccountNo(senderAccountNo);
	        payment.setReciverAccountNo(payments.getReceiverAccountNo());


	        // Interact with the external bank to deposit the amount


	        // Save payment details
	        receiverAccount.addPayment(payment); // External bank, no receiver account within our system
	        payDao.save(payment);
	        // Save bank details
	        bankDao.save(bank);
	        // Save updated sender account
	        accDao.save(receiverAccount);
	        transDao.save(receiverTransactionHistory);
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        return true;

	    } catch (Exception e) {
	        // Log the exception and throw a custom error if needed
	        e.printStackTrace();
	        throw new RuntimeException("An error occurred while processing the payment", e);
	    }
	}




	@Override
	public String paymentEMI(PaymentDTO payments) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public String paymentLoan(PaymentDTO payments) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
