package com.justa.mock;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	
	private Properties props;
	private Authenticator authenticator;
		
	public MailSender(Properties props, Authenticator authenticator){
		this.props= props;
		this.authenticator = authenticator;
	}
	
	public void send(InternetAddress[] tos, String title, String msg) throws AddressException, MessagingException{
		
		Session session = Session.getInstance(props, authenticator);
		
		Message message = new MimeMessage(session);
		//message.setFrom(new InternetAddress("wuyg719@gmail.com"));
		message.setRecipients(Message.RecipientType.TO, tos);
		message.setSubject(title);
		message.setText(msg);
		Transport.send(message);

		System.out.println("Done");
		
	}

}
