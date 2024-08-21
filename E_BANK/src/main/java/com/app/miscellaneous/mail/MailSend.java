package com.app.miscellaneous.mail;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Hello world!
 *
 */
public class MailSend 
{
//    public static void main( String[] args )
//    {
//        System.out.println( "Hello World!" );
//        String message="message aaya kya!";
//        String subject ="Bank: Verification";
//        String to="agsahu222@gmail.com";
//        String from="verifyuserdetails.001@gmail.com";
//        sendEmail(message,subject,to,from);
//    }

	public static void sendEmail(String message, String subject, String to) {
	//variableforgmailhost
		String host="smtp.gmail.com";
		
		//get the system properties
	Properties properties=	System.getProperties();
	System.out.println("properties"+properties);
	//setting important information to properties object
	properties.put("mail.smtp.host", host);
	properties.put("mail.smtp.port","465");//port
	properties.put("mail.smtp.ssl.enable","true");
	properties.put("mail.smtp.auth","true");
	
	//step:1 to get the session object
	Session session=Session.getInstance(properties,new Authenticator() {

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			
			return new PasswordAuthentication("verifyuserdetails.001@gmail.com","dupy tpmx rksb dull");
		}
		
		
	});
	session.setDebug(true);
		//step:2 compose the message
	MimeMessage mymessage=new MimeMessage(session);
	//from email
	try {
		mymessage.setFrom("verifyuserdetails.001@gmail.com");
		//add reciepent to message
		mymessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		//adding subject to message
		mymessage.setSubject(subject);
		//adding text to message
		mymessage.setText(message);
		//send
		//step3: send msg using transport class
		Transport.send(mymessage);
		System.out.println("sent success");
		
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
}
