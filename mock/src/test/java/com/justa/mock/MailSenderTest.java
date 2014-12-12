package com.justa.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.subethamail.wiser.Wiser;

/** this will demo how to use Wiser to do email sending and receiving in unit test env <br/> 
  Wiser is the best in-Memory smtp server<br/>
  it is designed for unit test: 
  <ul>   
	<li>light weight</li>
	<li>pure java</li>
	<li>in memory</li>
	<li>latest and most recently player in the area</li>
	<li>fast</li>
	<li>easy to use</li>
 </ul>
 For details, please see:  
 <a href="http://www.javatronic.fr/articles/2014/02/15/a_smtp_server_in_java_for_unit_test.html"> here</a>


 * 
 * */
public class MailSenderTest {

	private Wiser wiser;

	@Before
	public void setup() {
		wiser = new Wiser();
		wiser.setPort(3025);
		wiser.setHostname("localhost");
		wiser.start();

	}
	
	@After
	public void after() {
	    this.wiser.stop();
	}

	//@Test
	public void testGmail() throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				String userName = "justinwu003@gmail.com";
				String password = "tun21gle";
				return new PasswordAuthentication(userName, password);
			}
		};
		
		InternetAddress[] tos = InternetAddress.parse("justin01.wu@gmail.com");
		new MailSender(props, authenticator).send(tos, "Testing Subject", "Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
	}

	@Test
	public void test2() throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.host", "localhost");
		props.put("mail.smtp.port", "3025");

		InternetAddress[] tos = InternetAddress.parse("justin01.wu@gmail.com");
		new MailSender(props, null).send(tos, "Testing Subject 1234", "Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");

		assertTrue(wiser.getMessages().size() == 1);
		MimeMessage message = wiser.getMessages().iterator().next().getMimeMessage();

		assertEquals(message.getSubject(), "Testing Subject 1234");
	}
}
