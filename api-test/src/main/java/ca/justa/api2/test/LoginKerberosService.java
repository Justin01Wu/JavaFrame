package ca.justa.api2.test;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.client.CookieStore;


//it comes from 
//http://stackoverflow.com/questions/21629132/httpclient-set-credentials-for-kerberos-authentication
public class LoginKerberosService {
	
	public static void login(String user, String passwordBase64, final String url, final CookieStore httpCookieStore) throws LoginException {
		
        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(passwordBase64);
        
        String password = new String(decodedBytes); // my windows domain password

		CallbackHandler callbackHandler = new KerberosCallBackHandler(user, password);

		//LoginContext loginContext = new LoginContext("Krb5JustinTest", callbackHandler);
		LoginContext loginContext = new LoginContext("spnego-client", callbackHandler);
		
        loginContext.login();
            
        MyPrivilegedAction sendAction = new MyPrivilegedAction(httpCookieStore, url);

        Subject.doAs(loginContext.getSubject(), sendAction);

    }



}
