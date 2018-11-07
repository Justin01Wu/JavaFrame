package com.justa.library.test.ews;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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
	
	private static Logger LOG = Logger.getLogger(ExchangeEventUtil.class.getName());
    
    public static ExchangeService getExchangeService() throws URISyntaxException, IllegalArgumentException {
    	ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);      
    	
    	String url = EWSSetting.ServerUrl;
    	url = "https://outlook.westernworld.com/ews/Exchange.asmx";
    	
        service.setUrl(new URI(url));
		
        String passEnc = EWSSetting.PasswordEnc;
        passEnc = "UDNkbVdrWmY=";
        String pass = new String(Base64.getDecoder().decode(passEnc));		
		
		String userName = EWSSetting.UserName;
		
		userName = "svcVCAPS_RO";   // from Robin 62789322 
		String domain = EWSSetting.DOMAIN_NAME;
		domain = "Global";
        
		ExchangeCredentials credentials = new WebCredentials(userName, pass, domain);
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
    	LOG.info(  	"query range: " + startDate + " - " +  endDate.getTime());
    	TimeWindow timeWindow = new TimeWindow(startDate, endDate.getTime());
        availabilityOptions.setDetailedSuggestionsWindow( timeWindow);
        availabilityOptions.setRequestedFreeBusyView(FreeBusyViewType.Detailed);
     
        GetUserAvailabilityResults results = service.getUserAvailability(attendees, 
                                                                         availabilityOptions.getDetailedSuggestionsWindow(), 
                                                                         AvailabilityData.FreeBusyAndSuggestions, 
                                                                         availabilityOptions); 
                
        
        boolean found  = findWorkingElsewhereEvent(results, attendees, startDate);
        if(testMode) {
        	printEvents(results, attendees);
        }     
        return found;
        

    }
    private static boolean findWorkingElsewhereEvent(GetUserAvailabilityResults results, List<AttendeeInfo> attendees, Date startDate){
        
        for (AttendeeAvailability availability : results.getAttendeesAvailability()) {
        	
            for (CalendarEvent calEvent : availability.getCalendarEvents()){
            	if(calEvent.getFreeBusyStatus() == null) {
            		LOG.info(String.format("\t %s to %s \n", calEvent.getStartTime().toString(), calEvent.getEndTime().toString()));
            		
            		if(calEvent.getStartTime().getTime() <= startDate.getTime() ) {
            			if(calEvent.getEndTime().getTime() >= startDate.getTime()) {
            				return true;	
            			}
            				
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
        	LOG.fine(String.format("Availability information for %s:\n", attendees.get(i).getSmtpAddress()));
            for (CalendarEvent calEvent : availability.getCalendarEvents()){
            	if(calEvent.getFreeBusyStatus() == null) {
            		// it is WorkingElsewhere event, but EWS Java lib is a little old, so can't get this type
            		LOG.info(String.format("\t     %s to %s \n", 
            				calEvent.getStartTime().toString(), 
            				calEvent.getEndTime().toString()));
            		
            	}else {
            		LOG.info(String.format("\t %s from %s to %s \n", 
            				calEvent.getFreeBusyStatus().name(), 
            				calEvent.getStartTime().toString(), 
            				calEvent.getEndTime().toString()));
            	}
            	if(calEvent.getDetails() != null) {
            		// no permission to read others subject
            		LOG.info(calEvent.getDetails().getSubject());  	
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
    		LOG.info(email + " is working elsewhere");	
    	}else {
    		LOG.info(email + " is NOT working elsewhere");
    	}
    	
    }

}
