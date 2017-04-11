package ca.justa.api2.test;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.http.client.CookieStore;


//it comes from 
//http://stackoverflow.com/questions/21629132/httpclient-set-credentials-for-kerberos-authentication
public class LoginKerberosService {
	
	public static void login(String user, String password, final String url, final CookieStore httpCookieStore) throws LoginException {

		LoginContext loginContext = new LoginContext("Krb5JustinTest", new KerberosCallBackHandler(user, password));
        loginContext.login();
            
        MyPrivilegedAction sendAction = new MyPrivilegedAction(httpCookieStore, url);

        Subject.doAs(loginContext.getSubject(), sendAction);

    }



}
