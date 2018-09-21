package com.justa.library.test.ews;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

public class ExchangeEventFinder {
	
	private ExchangeService service;
	
	public ExchangeEventFinder() throws URISyntaxException {
		service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		//service = new ExchangeService(ExchangeVersion.Exchange2007_SP1); //depending on the version of your Exchange. 
        service.setUrl(new URI(EWSSetting.ServerUrl));		
        
		String pass = new String(Base64.getDecoder().decode(EWSSetting.PasswordEnc));
        ExchangeCredentials credentials = new WebCredentials(EWSSetting.UserName, pass, EWSSetting.DOMAIN_NAME);
        service.setCredentials(credentials);

	}
	
    public boolean findTargetEventWithUserNameAndSpecialSubject(String targetUsername, String containedString) throws Exception {

        Calendar now = Calendar.getInstance();

        // those two line just for unit testing
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		now.setTime(df.parse("2018-09-21T14:00:00"));

        Date startDate = now.getTime();
        now.add(Calendar.MINUTE, 30);
        Date endDate = now.getTime();        
			
        Mailbox target = new Mailbox("resource.waterloo@validusre.bm");
        FolderId folderToAccess = new FolderId(WellKnownFolderName.Calendar, target);
        CalendarFolder calendarFolder = CalendarFolder.bind(service, folderToAccess);
        
        CalendarView cView = new CalendarView(startDate, endDate, 20);
        PropertySet propertySet = MSExchangeEmailService.getPropertySet();
        cView.setPropertySet(propertySet); 
        // as well depending upon our need.
        FindItemsResults<Appointment> appointments = calendarFolder.findAppointments(cView);
        List<Appointment> appList = appointments.getItems();
        
        //System.out.println("all found events:" + appList.size());
        
        for (Appointment appointment : appList) {

        	if(!appointment.getSubject().contains(containedString)) {
        		continue;
        	};

        	if(!appointment.getLastModifiedName().equals(targetUsername)) {
        		continue;
        	};
        	return true;
            
        }
            
        return false;
    }
    
    public static void main(String[] args) throws Exception {
        
    	ExchangeEventFinder finder = new ExchangeEventFinder();
    	
    	String targetUserName = "Resource Waterloo";  // for some reason, the shared calendar will update the orignalModifyName to itself sometimes
    	String subjectSubStr = "Mike - WFH";
    	boolean result = finder.findTargetEventWithUserNameAndSpecialSubject(targetUserName, subjectSubStr);
    	if(result) {
    		System.out.println("Find Mike is WFH");
    	}else {
    		System.out.println("Didn't find Mike is WFH");
    	}
    	
    	targetUserName = "Justin Wu";
    	subjectSubStr = "Justin test event 2234";
    	result = finder.findTargetEventWithUserNameAndSpecialSubject(targetUserName, subjectSubStr);
    	if(result) {
    		System.out.println("Find Justin test event 2234");
    	}else {
    		System.out.println("Didn't find Justin test event 2234");
    	}
    }

	

}
