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

public class ExchangeSharedEventFinder {
	
	private ExchangeService service;
	
	public ExchangeSharedEventFinder() throws URISyntaxException {
		service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		//service = new ExchangeService(ExchangeVersion.Exchange2007_SP1); //depending on the version of your Exchange. 
        service.setUrl(new URI(EWSSetting.ServerUrl));		
        
		String pass = new String(Base64.getDecoder().decode(EWSSetting.PasswordEnc));
        ExchangeCredentials credentials = new WebCredentials(EWSSetting.UserName, pass, EWSSetting.DOMAIN_NAME);
        service.setCredentials(credentials);

	}
	
    public boolean findTargetEventWithUserNameAndSpecialSubject(String targetUsername, String containedString, Date targetDate) throws Exception {

		
		Date startDate;
		Calendar cal;
		if(targetDate == null) {
			// use now as start date
			
	        cal = Calendar.getInstance();
	        // those two line just for unit testing
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			cal.setTime(df.parse("2018-09-21T14:00:00"));

			startDate = cal.getTime();
			
		}else {
			cal = Calendar.getInstance();
			cal.setTime(targetDate);
			startDate = targetDate;
			
		}
        
		cal.add(Calendar.MINUTE, 30);
        Date endDate = cal.getTime();        
			
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
        	
        	System.out.println(appointment.getCategories().toString());
        	

        	return true;
            
        }
            
        return false;
    }
    
    public static void main(String[] args) throws Exception {
        
    	ExchangeSharedEventFinder finder = new ExchangeSharedEventFinder();
    	
    	
    	// find Mike's event
    	String targetUserName = "Resource Waterloo";  // for some reason, the shared calendar will update the orignalModifyName to itself sometimes
    	String subjectSubStr = "Mike - WFH";
    	boolean result = finder.findTargetEventWithUserNameAndSpecialSubject(targetUserName, subjectSubStr, null);
    	if(result) {
    		System.out.println("Find "+ subjectSubStr);
    	}else {
    		System.out.println("Didn't find "+ subjectSubStr);
    	}
    	
    	// find Justin's event
    	targetUserName = "Justin Wu";
    	subjectSubStr = "Justin test event 2234";
    	result = finder.findTargetEventWithUserNameAndSpecialSubject(targetUserName, subjectSubStr, null);
    	if(result) {
    		System.out.println("Find "+ subjectSubStr);
    	}else {
    		System.out.println("Didn't find "+ subjectSubStr);
    	}
    	
    	// find event on target date
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar now = Calendar.getInstance();
		now.setTime(df.parse("2018-09-23T14:00:00"));
    	targetUserName = "Huaning Nie";
    	subjectSubStr = "Mike test event mac";
    	result = finder.findTargetEventWithUserNameAndSpecialSubject(targetUserName, subjectSubStr, now.getTime());
    	if(result) {
    		System.out.println("Find "+ subjectSubStr);
    	}else {
    		System.out.println("Didn't find "+ subjectSubStr);
    	}

    }

	

}
