package com.justa.library.test.ews;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.availability.AvailabilityData;
import microsoft.exchange.webservices.data.core.enumeration.availability.FreeBusyViewType;
import microsoft.exchange.webservices.data.core.enumeration.availability.MeetingAttendeeType;
import microsoft.exchange.webservices.data.core.enumeration.availability.SuggestionQuality;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.response.AttendeeAvailability;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.availability.AttendeeInfo;
import microsoft.exchange.webservices.data.misc.availability.AvailabilityOptions;
import microsoft.exchange.webservices.data.misc.availability.GetUserAvailabilityResults;
import microsoft.exchange.webservices.data.misc.availability.TimeWindow;
import microsoft.exchange.webservices.data.property.complex.availability.CalendarEvent;

public class ExchangeEventUtil {
	
    
    public static ExchangeService getExchangeService() throws URISyntaxException, IllegalArgumentException {
    	ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);            
        service.setUrl(new URI(EWSSetting.ServerUrl));
		String pass = new String(Base64.getDecoder().decode(EWSSetting.PasswordEnc));
		String userName = EWSSetting.UserName;
		//pass="xx";
		//userName = "xx";
        ExchangeCredentials credentials = new WebCredentials(userName, pass, EWSSetting.DOMAIN_NAME);
        service.setCredentials(credentials);
        return service;
    	
    }
    
	
    public static boolean getWorkingElsewhereStatus(ExchangeService service, String email) throws Exception {
		boolean testMode = false;
		return getWorkingElsewhereStatus(service, email, testMode);
	}
	
    protected static boolean getWorkingElsewhereStatus(ExchangeService service, String email, boolean testMode) throws Exception {
    	
        List<AttendeeInfo> attendees = new ArrayList<>();
        attendees.add(new AttendeeInfo(email, MeetingAttendeeType.Required, true));
     
        AvailabilityOptions availabilityOptions = new AvailabilityOptions(); 
        availabilityOptions.setGoodSuggestionThreshold(49); 
        availabilityOptions.setMaximumNonWorkHoursSuggestionsPerDay(0);
        availabilityOptions.setMaximumSuggestionsPerDay(1);
        
        availabilityOptions.setMinimumSuggestionQuality(SuggestionQuality.Excellent); 
        availabilityOptions.setMeetingDuration(30);
        
    	Date startDate = new Date();
    	Calendar endDate =  Calendar.getInstance();
    	if(testMode) {
    		endDate.add(Calendar.DAY_OF_MONTH, 5);    		
    	}else {
    		endDate.add(Calendar.HOUR_OF_DAY, 12);	
    	}  
    	
        availabilityOptions.setDetailedSuggestionsWindow( new TimeWindow(startDate, endDate.getTime()));
        availabilityOptions.setRequestedFreeBusyView(FreeBusyViewType.Detailed);
     
        GetUserAvailabilityResults results = service.getUserAvailability(attendees, 
                                                                         availabilityOptions.getDetailedSuggestionsWindow(), 
                                                                         AvailabilityData.FreeBusyAndSuggestions, 
                                                                         availabilityOptions); 
                
        
        boolean found  = findWorkingElsewhereEvent(results, attendees, startDate, testMode);
        if(testMode) {
        	printEvents(results, attendees);
        }     
        return found;
        

    }
    private static boolean findWorkingElsewhereEvent(GetUserAvailabilityResults results, List<AttendeeInfo> attendees, Date startDate, boolean testMode){
        
    	long allowedTimeGap = testMode? 72l*3600000l:3600000l;  // 72 hour  in test mode, one hour in real mode
        for (AttendeeAvailability availability : results.getAttendeesAvailability()) {
        	
            for (CalendarEvent calEvent : availability.getCalendarEvents()){
            	if(calEvent.getFreeBusyStatus() == null) {
            		System.out.println(String.format("\t %s to %s \n", calEvent.getStartTime().toString(), calEvent.getEndTime().toString()));
            		if(calEvent.getStartTime().getTime() - startDate.getTime() < allowedTimeGap) {
            			return true;	
            		}           		            		
            	}
            }
        }
        return false;

    }
    
    private static void printEvents(GetUserAvailabilityResults results, List<AttendeeInfo> attendees){
        int i = 0;
        // Display free/busy times.
        for (AttendeeAvailability availability : results.getAttendeesAvailability()) {
        	System.out.println(String.format("Availability information for %s:\n", attendees.get(i).getSmtpAddress()));
            for (CalendarEvent calEvent : availability.getCalendarEvents()){
            	if(calEvent.getFreeBusyStatus() == null) {
            		// it is WorkingElsewhere event, but EWS Java lib is a little old, so can't get this type
            		System.out.println(String.format("\t     %s to %s \n", 
            				calEvent.getStartTime().toString(), 
            				calEvent.getEndTime().toString()));
            		
            	}else {
            		System.out.println(String.format("\t %s from %s to %s \n", 
            				calEvent.getFreeBusyStatus().name(), 
            				calEvent.getStartTime().toString(), 
            				calEvent.getEndTime().toString()));
            	}
            	if(calEvent.getDetails() != null) {
            		// no permission to read others subject
            		System.out.println(calEvent.getDetails().getSubject());  	
            	}
            	
            	
            }
            i++;
        }

    }

    
    public static void main(String[] args) throws Exception {
        
    	boolean testMode = false;  // test mode make  event scope big, it helps to testing
    	String email="justin.wu@validusresearch.com";
    	boolean isWorkingElsewhere = false;
    	try(ExchangeService service = ExchangeEventUtil.getExchangeService()){
    		isWorkingElsewhere = ExchangeEventUtil.getWorkingElsewhereStatus(service, email, testMode);	
    	}
    	
    	if(isWorkingElsewhere) {
    		System.out.println(email + " is working elsewhere");	
    	}
	

    	
    }

}
