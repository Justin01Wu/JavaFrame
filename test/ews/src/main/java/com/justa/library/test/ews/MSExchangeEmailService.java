package com.justa.library.test.ews;

import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.availability.AvailabilityData;
import microsoft.exchange.webservices.data.core.enumeration.availability.FreeBusyViewType;
import microsoft.exchange.webservices.data.core.enumeration.availability.MeetingAttendeeType;
import microsoft.exchange.webservices.data.core.enumeration.availability.SuggestionQuality;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.misc.TraceFlags;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.response.AttendeeAvailability;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.AppointmentSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.availability.AttendeeInfo;
import microsoft.exchange.webservices.data.misc.availability.AvailabilityOptions;
import microsoft.exchange.webservices.data.misc.availability.GetUserAvailabilityResults;
import microsoft.exchange.webservices.data.misc.availability.TimeWindow;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.property.complex.availability.CalendarEvent;
import microsoft.exchange.webservices.data.property.complex.availability.OofSettings;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;


// it comes from https://dzone.com/articles/reading-emails-ms-exchange
// use EWS to access exchange server, need domain user name and password 
public class MSExchangeEmailService {

	private static ExchangeService service;
 
    /**
     * Firstly check, whether "https://outlook.westernworld.com/ews/Services.wsdl" and "https://outlook.westernworld.com/ews/Exchange.asmx"
     * is accessible, if yes that means the Exchange Webservice is enabled on your MS Exchange.
     */
    static {
        try {
            service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
//service = new ExchangeService(ExchangeVersion.Exchange2007_SP1); //depending on the version of your Exchange. 
            
            service.setTraceListener(new TraceListener());
            
            //EnumSet<TraceFlags> settings = EnumSet.of(TraceFlags.EwsRequest, TraceFlags.EwsResponse, TraceFlags.DebugMessage, TraceFlags.AutodiscoverResponse);            
            EnumSet<TraceFlags> settings = EnumSet.of(TraceFlags.EwsResponse);
            service.setTraceFlags( settings);
            service.setTraceEnabled( true);

            service.setUrl(new URI(EWSSetting.ServerUrl));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Initialize the Exchange Credentials.
     * Don't forget to replace the "USRNAME","PWD","DOMAIN_NAME" variables.
     */
    public MSExchangeEmailService() {
		String pass = new String(Base64.getDecoder().decode(EWSSetting.PasswordEnc));
        ExchangeCredentials credentials = new WebCredentials(EWSSetting.UserName, pass, EWSSetting.DOMAIN_NAME);
        service.setCredentials(credentials);
    }
    /**
     * Reading one email at a time. Using Item ID of the email.
     * Creating a message data map as a return value.
     * @throws Exception 
     * @throws ServiceLocalException 
     */
    public Map<String, String> readEmailItem(ItemId itemId) throws ServiceLocalException, Exception {
        Map<String, String> messageData = new HashMap<>();

        Item itm = Item.bind(service, itemId, PropertySet.FirstClassProperties);
        EmailMessage emailMessage = EmailMessage.bind(service, itm.getId());
        messageData.put("emailItemId", emailMessage.getId().toString());
        messageData.put("subject", emailMessage.getSubject().toString());
        messageData.put("fromAddress", emailMessage.getFrom().getAddress().toString());
        messageData.put("senderName", emailMessage.getSender().getName().toString());
        Date dateTimeCreated = emailMessage.getDateTimeCreated();
        messageData.put("SendDate", dateTimeCreated.toString());
        Date dateTimeRecieved = emailMessage.getDateTimeReceived();
        messageData.put("RecievedDate", dateTimeRecieved.toString());
        messageData.put("Size", emailMessage.getSize() + "");
        messageData.put("emailBody", emailMessage.getBody().toString());

        return messageData;
    }
/**
 * Number of email we want to read is defined as NUMBER_EMAILS_FETCH, 
 */
    public List<Map<String, String>>  readEmails() {
        List <Map<String, String>> msgDataList = new ArrayList <> ();
        int NUMBER_EMAILS_FETCH = 5;   // only latest 5 emails/appointments are fetched.
        try {
            Folder folder = Folder.bind(service, WellKnownFolderName.Inbox);
            FindItemsResults<Item> results = service.findItems(folder.getId(), new ItemView(NUMBER_EMAILS_FETCH));
            int i = 1;
            for (Item item : results) {
                Map<String, String> messageData = new HashMap<>();
                messageData = readEmailItem(item.getId());
                System.out.println("\nEmails #" + (i++) + ":");
                System.out.println("subject : " + messageData.get("subject").toString());
                System.out.println("Sender : " + messageData.get("senderName").toString());
                msgDataList.add(messageData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgDataList;
    }
    /**
     * Reading one appointment at a time. Using Appointment ID of the email.
     * Creating a message data map as a return value.
     * @throws ServiceLocalException 
     */
    public static Map<String, String> readAppointment(Appointment appointment) throws ServiceLocalException {
        Map<String, String> appointmentData = new HashMap<>();
        appointmentData.put("ItemId", appointment.getId().toString());
        appointmentData.put("Subject", appointment.getSubject());
        appointmentData.put("StartTime", appointment.getStart() + "");
        appointmentData.put("EndTime", appointment.getEnd() + "");
        
        appointmentData.put("Organizer", appointment.getOrganizer().getName());
        appointmentData.put("OrganizerAddress", appointment.getOrganizer().getAddress());
        //appointmentData.put("appointmentBody", appointment.getBody().toString());
        appointmentData.put("IsAllDay", appointment.getIsAllDayEvent().toString());
        appointmentData.put("Type", appointment.getAppointmentType().toString());
        appointmentData.put("LastModifiedName", appointment.getLastModifiedName().toString());
        appointmentData.put("IsMeeting", appointment.getIsMeeting().toString());
        
        return appointmentData;
    }
    /**
     *
     *  Here I also considered the start data and end date which is a 30 day span.
     *  We need to set the CalendarView property depending upon the need of ours.   
     * @throws Exception 
     */
    public List<Map<String, String>> readAppointments() throws Exception {
        List <Map<String, String>> apntmtDataList = new ArrayList <> ();
        Calendar now = Calendar.getInstance();
        Date startDate = Calendar.getInstance().getTime();
        now.add(Calendar.DATE, 30);
        Date endDate = now.getTime();

        CalendarFolder calendarFolder = CalendarFolder.bind(service, WellKnownFolderName.Calendar, new PropertySet());
        CalendarView cView = new CalendarView(startDate, endDate, 5);
        
        PropertySet propertySet = getPropertySet();
        cView.setPropertySet(propertySet); 

        
        FindItemsResults<Appointment> appointments = calendarFolder.findAppointments(cView);
        int i = 1;
        List<Appointment> appList = appointments.getItems();
        for (Appointment appointment : appList) {
            System.out.println("\nAPPOINTMENT #" + (i++) + ":");
            Map<String, String> appointmentData = readAppointment(appointment);
            
            for(String key: appointmentData.keySet()) {
            	String item  = appointmentData.get(key);
            	System.out.println(key + " : " + item);
            }            
            apntmtDataList.add(appointmentData);
        }
            
        return apntmtDataList;
    }
    
    public static PropertySet getPropertySet() {
    	// we can set other properties,  as well depending upon our need.
    	return new PropertySet(
    			AppointmentSchema.Subject, 
    			AppointmentSchema.Start, 
    			AppointmentSchema.End, 
    			AppointmentSchema.Organizer, 
    			AppointmentSchema.IsAllDayEvent,
    			AppointmentSchema.LastModifiedName,
    			AppointmentSchema.IsMeeting,
    			AppointmentSchema.Categories,
    			AppointmentSchema.AppointmentType
    			);
    }
    
    public List<Map<String, String>> readAppointmentsFromSharedResource() throws Exception {
        List <Map<String, String>> apntmtDataList = new ArrayList <> ();
        Calendar now = Calendar.getInstance();
        Date startDate = Calendar.getInstance().getTime();
        now.add(Calendar.DATE, 2);
        Date endDate = now.getTime();

        Mailbox target = new Mailbox("resource.waterloo@validusre.bm");
        FolderId folderToAccess = new FolderId(WellKnownFolderName.Calendar, target);
        CalendarFolder calendarFolder = CalendarFolder.bind(service, folderToAccess);
        
        CalendarView cView = new CalendarView(startDate, endDate, 20);
        PropertySet propertySet = getPropertySet();
        cView.setPropertySet(propertySet); 
        // as well depending upon our need.
        FindItemsResults<Appointment> appointments = calendarFolder.findAppointments(cView);
        int i = 1;
        List<Appointment> appList = appointments.getItems();
        for (Appointment appointment : appList) {
            System.out.println("\nAPPOINTMENT #" + (i++) + ":");
            Map<String, String> appointmentData = readAppointment(appointment);
            
            for(String key: appointmentData.keySet()) {
            	String item  = appointmentData.get(key);
            	System.out.println(key + " : " + item);
            }    
            apntmtDataList.add(appointmentData);
        }
            
        return apntmtDataList;
    }
    
    public void sendEmails() throws Exception {
        List<String> recipientsList = new ArrayList<>();
        recipientsList.add("email.id1@domain1.com");
        recipientsList.add("email.id2@domain1.com");
        recipientsList.add("email.id3@domain2.com");
        StringBuilder strBldr = new StringBuilder();
        strBldr.append("The client submitted the SendAndSaveCopy request at:");
        strBldr.append(Calendar.getInstance().getTime().toString() + " .");
        strBldr.append("Thanks and Regards");
        strBldr.append("Shantanu Sikdar");
        EmailMessage message = new EmailMessage(service);
        message.setSubject("Test sending email");
        message.setBody(new MessageBody(strBldr.toString()));
        for (String string : recipientsList) {
            message.getToRecipients().add(string);
        }
        message.sendAndSaveCopy();
        System.out.println("message sent");
    }
    
    // comes from 
    // https://docs.microsoft.com/en-us/exchange/client-developer/exchange-web-services/how-to-get-free-busy-information-by-using-ews-in-exchange
    public void getSuggestedMeetingTimesAndFreeBusyInfo(String email) throws Exception {
    	
        // Create a collection of attendees. 
        List<AttendeeInfo> attendees = new ArrayList<>(); 
     
        attendees.add(new AttendeeInfo(email, MeetingAttendeeType.Required, true));
     
        // Specify options to request free/busy information and suggested meeting times.
        AvailabilityOptions availabilityOptions = new AvailabilityOptions(); 
        availabilityOptions.setGoodSuggestionThreshold(49); 
        availabilityOptions.setMaximumNonWorkHoursSuggestionsPerDay(0);
        availabilityOptions.setMaximumSuggestionsPerDay(2);
        
        // Note that 60 minutes is the default value for MeetingDuration, but setting it explicitly for demonstration purposes.
        availabilityOptions.setMeetingDuration(30); 
        availabilityOptions.setMinimumSuggestionQuality(SuggestionQuality.Excellent); 
        
    	Date startDate = new Date();
    	Calendar endDate =  Calendar.getInstance();
    	endDate.add(Calendar.DAY_OF_MONTH, 5);
        availabilityOptions.setDetailedSuggestionsWindow( new TimeWindow(startDate, endDate.getTime()));
        availabilityOptions.setRequestedFreeBusyView(FreeBusyViewType.Detailed);
     
        // Return free/busy information and a set of suggested meeting times. 
        // This method results in a GetUserAvailabilityRequest call to EWS.
        GetUserAvailabilityResults results = service.getUserAvailability(attendees, 
                                                                         availabilityOptions.getDetailedSuggestionsWindow(), 
                                                                         AvailabilityData.FreeBusyAndSuggestions, 
                                                                         availabilityOptions); 
        // Display suggested meeting times. 
        System.out.println(String.format("Availability for %s ", attendees.get(0).getSmtpAddress() )); 
                
        
        if(TraceListener.hasWorkingElsewhereEvent.get()) {
        	System.out.println(String.format(" user %s has WorkingElsewhere event in target period", attendees.get(0).getSmtpAddress()));
        }
        
        print(results, attendees);

    }
    
    
    
    private void print(GetUserAvailabilityResults results, List<AttendeeInfo> attendees){
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
    
    public void getOutOfOfficeStatus() throws Exception {
    	//service.setImpersonatedUserId(new ImpersonatedUserId(ConnectingIdType.SmtpAddress, "Arpit.Jain@validusresearch.com"));
    	
    	OofSettings userOOFSettings = service.getUserOofSettings("Arpit.Jain@validusresearch.com");	
    	// need some permission setting for other people on exchange server
    	
    	//OofSettings userOOFSettings = service.getUserOofSettings("justin.wu@validusresearch.com");
    	
    	System.out.println(userOOFSettings.getState());
    }
    
    
    public static void main(String[] args) throws Exception {
        
    	MSExchangeEmailService msees = new MSExchangeEmailService();
        //msees.readEmails();
        
    	//msees.readAppointments();
    	
    	//msees.readAppointmentsFromSharedResource();
    	
    	msees.getSuggestedMeetingTimesAndFreeBusyInfo("Arpit.Jain@validusresearch.com");
    	msees.getSuggestedMeetingTimesAndFreeBusyInfo("Jeff.Clements@validusre.bm");
    	msees.getSuggestedMeetingTimesAndFreeBusyInfo("kristi.champ@validusresearch.com");
    	
    	
    	
    	
    	//msees.getOutOfOfficeStatus();

        //msees.sendEmails();
    }
}
