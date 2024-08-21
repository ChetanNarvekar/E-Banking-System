package com.app.miscellaneous.mail;

import org.springframework.beans.factory.annotation.Value;

import com.app.entity.account.AccountType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@NoArgsConstructor
public class RegistrationMailSender {
	
//	String message="Dear Customer Your account is succefully created.";
//	String subject ="Bank: Verification";
//	String to="agsahu222@gmail.com";
//	String from="verifyuserdetails.001@gmail.com";
//	String accountName;
	
	private String message;
	@Value("${personal.subject.naruto.msg=email1")
	private String subject;
	private String to;
	
	public RegistrationMailSender(String to, String accountNo,double balance,long customerId, String accountName,String bankName ) {
		this.message = "Dear Customer \nYour account is created SUCCESSFULLY...!\n"+"Account Details : \n"+"\nCustomer ID:"+customerId+"A/C Number:"+accountNo+"\nBalance:"+balance+"\nA/c Type:"+accountName+"\nBest Regards,\n"+bankName+"\n\n***You Can Log in using Credentials Now.";
		this.subject = "Bank: Account Creation";
		this.to = to;
	}
	
	public RegistrationMailSender(String to, String accountNo, String requestId, String bankName) {
		this.message = "Dear Customer \nYour Loan request for Request Id: "+ requestId +"is Accepted.\n" + "Please provide Collateral information to proceed with your Loan Application.\n"+  "\n Best Regards \n"+ bankName;
		this.subject = "Bank: Loan Application";
		this.to = to;
	}
	
	public RegistrationMailSender(String to, String accountNo, String requestId, String bankName, String loanId) {
		this.message = "Dear Customer\nYour Loan request for Request Id: "+ requestId +"is APPROVED..! \n"+ "Loan ID: "+ loanId + "\n"+ "You will Recieve money in short time.\nMoney will be transfered @A/c No : "+ accountNo + "\n Best Regards \n"+ bankName;
		this.subject = "Bank: Verification";
		this.to = to;
	}
	
	public RegistrationMailSender(String to, String requestId, String bankName) {
		this.message = "Dear Customer your Loan request for Request Id: "+ requestId +"is DECLINED.\n"+ "Please contact Bank Officials for any Query.\n"+ "\n Best Regards \n"+ bankName;
		this.subject = "Bank: Loan Declined";
		this.to = to;
	}
	
	
	
}
