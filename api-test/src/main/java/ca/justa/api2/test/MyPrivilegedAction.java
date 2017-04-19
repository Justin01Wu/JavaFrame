package ca.justa.api2.test;

import java.io.IOException;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Set;

import javax.security.auth.Subject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


//it comes from 
//http://stackoverflow.com/questions/21629132/httpclient-set-credentials-for-kerberos-authentication
public class MyPrivilegedAction implements PrivilegedAction<Boolean> {

	private CookieStore httpCookieStore;
	private String url;
	
	public MyPrivilegedAction(CookieStore httpCookieStore, String url){
		this.httpCookieStore = httpCookieStore;
		this.url = url;
	}
	
    @Override
    public Boolean run() {
        try {

            Subject current = Subject.getSubject(AccessController.getContext());
            System.out.println("----------------------------------------");
            Set<Principal> principals = current.getPrincipals();
            for (Principal next : principals) {
                System.out.println("DOAS Principal: " + next.getName());
            }
            System.out.println("----------------------------------------");

            call(url, httpCookieStore);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private static void call(String url, CookieStore httpCookieStore) throws IOException {
    	

        try (CloseableHttpClient httpclient = getHttpClient(httpCookieStore)){

            HttpUriRequest request = new HttpGet(url);
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");

            System.out.println("STATUS >> " + response.getStatusLine());

            if (entity != null) {
                System.out.println("RESULT >> " + EntityUtils.toString(entity));
            }

            System.out.println("----------------------------------------");

            EntityUtils.consume(entity);
        } 
    }
    
    private static CloseableHttpClient getHttpClient(CookieStore httpCookieStore) {

        Credentials use_jaas_creds = new Credentials() {
            public String getPassword() {
                return null;
            }

            public Principal getUserPrincipal() {
                return null;
            }
        };

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(null, -1, null), use_jaas_creds);
        Registry<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder.<AuthSchemeProvider>create().register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory(true)).build();
        
        
        HttpClientBuilder builder = HttpClients.custom().setDefaultAuthSchemeRegistry(authSchemeRegistry).setDefaultCredentialsProvider(credsProvider);
        builder = builder.setDefaultCookieStore(httpCookieStore);
        CloseableHttpClient httpclient = builder.build();

        return httpclient;
    }
}
