package com.justa.library.test.ews;

import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
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
import microsoft.exchange.webservices.data.property.complex.availability.Suggestion;
import microsoft.exchange.webservices.data.property.complex.availability.TimeSuggestion;
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
    public Map<String, String> readAppointment(Appointment appointment) throws ServiceLocalException {
        Map<String, String> appointmentData = new HashMap<>();
        appointmentData.put("appointmentItemId", appointment.getId().toString());
        appointmentData.put("appointmentSubject", appointment.getSubject());
        appointmentData.put("appointmentStartTime", appointment.getStart() + "");
        appointmentData.put("appointmentEndTime", appointment.getEnd() + "");
        
        appointmentData.put("appointmentOrganizer", appointment.getOrganizer().getName());
        appointmentData.put("appointmentOrganizerAddress", appointment.getOrganizer().getAddress());
        //appointmentData.put("appointmentBody", appointment.getBody().toString());
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
        cView.setPropertySet(new PropertySet(AppointmentSchema.Subject, AppointmentSchema.Start, AppointmentSchema.End, AppointmentSchema.Organizer));// we can set other properties 
        // as well depending upon our need.
        FindItemsResults<Appointment> appointments = calendarFolder.findAppointments(cView);
        int i = 1;
        List<Appointment> appList = appointments.getItems();
        for (Appointment appointment : appList) {
            System.out.println("\nAPPOINTMENT #" + (i++) + ":");
            Map<String, String> appointmentData = readAppointment(appointment);
            System.out.println("subject : " + appointmentData.get("appointmentSubject").toString());
            System.out.println("On : " + appointmentData.get("appointmentStartTime").toString());
            System.out.println("end : " + appointmentData.get("appointmentEndTime").toString());
            System.out.println("organizer : " + appointmentData.get("appointmentOrganizer").toString());
            System.out.println("organizerAdd : " + appointmentData.get("appointmentOrganizerAddress").toString());
            
            
            apntmtDataList.add(appointmentData);
        }
            
        return apntmtDataList;
    }
    
    public List<Map<String, String>> readAppointmentsFromSharedResource() throws Exception {
        List <Map<String, String>> apntmtDataList = new ArrayList <> ();
        Calendar now = Calendar.getInstance();
        Date startDate = Calendar.getInstance().getTime();
        now.add(Calendar.DATE, 30);
        Date endDate = now.getTime();

        Mailbox target = new Mailbox("resource.waterloo@validusre.bm");
        FolderId folderToAccess = new FolderId(WellKnownFolderName.Calendar, target);
        CalendarFolder calendarFolder = CalendarFolder.bind(service, folderToAccess);
        
        CalendarView cView = new CalendarView(startDate, endDate, 5);
        cView.setPropertySet(new PropertySet(AppointmentSchema.Subject, AppointmentSchema.Start, AppointmentSchema.End, AppointmentSchema.Organizer));// we can set other properties 
        // as well depending upon our need.
        FindItemsResults<Appointment> appointments = calendarFolder.findAppointments(cView);
        int i = 1;
        List<Appointment> appList = appointments.getItems();
        for (Appointment appointment : appList) {
            System.out.println("\nAPPOINTMENT #" + (i++) + ":");
            Map<String, String> appointmentData = readAppointment(appointment);
            System.out.println("subject : " + appointmentData.get("appointmentSubject").toString());
            System.out.println("On : " + appointmentData.get("appointmentStartTime").toString());
            System.out.println("end : " + appointmentData.get("appointmentEndTime").toString());
            System.out.println("organizer : " + appointmentData.get("appointmentOrganizer").toString());
            System.out.println("organizerAdd : " + appointmentData.get("appointmentOrganizerAddress").toString());
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
    public void getSuggestedMeetingTimesAndFreeBusyInfo() throws Exception {
    	
        // Create a collection of attendees. 
        List<AttendeeInfo> attendees = new ArrayList<>(); 
     
        attendees.add(new AttendeeInfo("justin.wu@validusresearch.com", MeetingAttendeeType.Organizer, true)); 
        attendees.add(new AttendeeInfo("Arpit.Jain@validusresearch.com", MeetingAttendeeType.Required, true));
     
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
    	endDate.add(Calendar.DAY_OF_MONTH, 3);
        availabilityOptions.setDetailedSuggestionsWindow( new TimeWindow(startDate, endDate.getTime()));
        availabilityOptions.setRequestedFreeBusyView(FreeBusyViewType.FreeBusy);
     
        // Return free/busy information and a set of suggested meeting times. 
        // This method results in a GetUserAvailabilityRequest call to EWS.
        GetUserAvailabilityResults results = service.getUserAvailability(attendees, 
                                                                         availabilityOptions.getDetailedSuggestionsWindow(), 
                                                                         AvailabilityData.FreeBusyAndSuggestions, 
                                                                         availabilityOptions); 
        // Display suggested meeting times. 
        System.out.println(String.format("Availability for %s and %s", attendees.get(0).getSmtpAddress(), attendees.get(1).getSmtpAddress())); 
         
     
        for (Suggestion suggestion : results.getSuggestions()) { 
        	System.out.println(String.format("Suggested date: %s\n", suggestion.getDate().toString())); 
        	System.out.println("Suggested meeting times:\n");
            for (TimeSuggestion timeSuggestion : suggestion.getTimeSuggestions()) { 
            	System.out.println(String.format("\t %s - %s\n",  timeSuggestion.getMeetingTime().toString(), " not done")); 
                                          //timeSuggestion.getMeetingTime().Add(TimeSpan.FromMinutes(availabilityOptions.getMeetingDuration()).ToShortTimeString()); 
            } 
        }
                
        int i = 0;
        // Display free/busy times.
        for (AttendeeAvailability availability : results.getAttendeesAvailability()) {
        	System.out.println(String.format("Availability information for %s:\n", attendees.get(i).getSmtpAddress()));
            for (CalendarEvent calEvent : availability.getCalendarEvents()){
            	System.out.println(String.format("\tBusy from %s to %s \n", calEvent.getStartTime().toString(), calEvent.getEndTime().toString()));
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
        
    	msees.getSuggestedMeetingTimesAndFreeBusyInfo();
    	
    	
    	
    	//msees.getOutOfOfficeStatus();

        //msees.sendEmails();
    }
}
