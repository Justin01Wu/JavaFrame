package ca.justa.api2.test;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.client.CookieStore;


//it comes from 
//http://stackoverflow.com/questions/21629132/httpclient-set-credentials-for-kerberos-authentication

// please also see https://docs.huihoo.com/java/j2ee/jaas.html
public class LoginKerberosService {
	
	//private static String clientName = "Krb5JustinTest";
	private static String clientName = "spnego-client";  // the client name in config file
	
	public static void login(String user, String passwordBase64, final String url, final CookieStore httpCookieStore) throws LoginException {
		
        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(passwordBase64);
        
        String password = new String(decodedBytes); // my windows domain password
        //password="wrong password";

		CallbackHandler callbackHandler = new KerberosCallBackHandler(user, password);

		LoginContext loginContext = new LoginContext(clientName, callbackHandler);  
		
        loginContext.login();  // JAAS login
            
        MyPrivilegedAction sendAction = new MyPrivilegedAction(httpCookieStore, url);

        Subject.doAs(loginContext.getSubject(), sendAction);

    }



}
