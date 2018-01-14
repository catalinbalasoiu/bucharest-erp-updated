package com.bucharest.ag.jobs;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.bucharest.ag.mail.SimpleMailService;
import com.bucharest.ag.model.Document;
import com.bucharest.ag.model.Function;
import com.bucharest.ag.model.Partner;
import com.bucharest.ag.model.Reminder;
import com.bucharest.ag.service.DocumentService;
import com.bucharest.ag.service.DocumentServiceImpl;
import com.bucharest.ag.service.FunctionService;
import com.bucharest.ag.service.FunctionServiceImpl;
import com.bucharest.ag.service.PartnerService;
import com.bucharest.ag.service.PartnerService;
import com.bucharest.ag.service.PartnerServiceImpl;
import com.bucharest.ag.service.ReminderService;
import com.bucharest.ag.service.ReminderServiceImpl;

/**
 * Job that runs everyday polling the database and emailing reminders
 *
 * @author Sabin Antohe
 */
@Component
public class ReminderJob implements Job {
	
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	ReminderService reminderService = new ReminderServiceImpl();
    	DocumentService documentService = new DocumentServiceImpl();
    	FunctionService functionService = new FunctionServiceImpl(); 
    	PartnerService partnerService = new PartnerServiceImpl();
    	
    	SimpleMailService simpleMailService = null;
    	
    	try {
    		simpleMailService = new SimpleMailService();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Date date = new Date();
		String todayDate = dateFormat.format(date);
	
    	List<Reminder> reminders = reminderService.getReminders(todayDate);
    	
    	if(reminders.size() != 0) {
	    	for (Reminder reminder : reminders) {
	    		Document document = documentService.getDocByReminder(reminder);
	    		Function function = functionService.getFunctionById(reminder.getFunction().getFunctionId());
	    		Partner partner = partnerService.findPartnerById(document.getPartnerId());
	    		String owner = function.getOwner();
	        	simpleMailService.addRecipient(owner);
	        	String partnerType;
	        	if (partner.getPartnerType().equals("c")) {
	        		partnerType = "clients";
	        	} else {
	        		partnerType = "suppliers";
	        	}
	        	
	        	String mailBody = "<h3>Dear users, </h3>";
	        	String partnerTypeSingular = partnerType.substring(0,1).toUpperCase() + partnerType.substring(1);
	        	 partnerTypeSingular = partnerTypeSingular.substring(0,partnerTypeSingular.length() - 1);
	        	
	        	mailBody += "<p> You are receiving this email because on the  <b>" + document.getExpDate() + "</b> a partner document will expire:</p></p>"
	        			+ "-Document Name: '<b>" + document.getDocName() + "</b>'<br>"
	        			+ "-" + partnerTypeSingular + " Name: <b>" + partner.getName() + "</b><br>"
	        			+ "-Expiration Date: <b>" + document.getExpDate() + "</b></p>"
	        			+ "<p>To view the details of the document, please <a href='http://localhost:8080/bucharest-project/view-partner-info?type="+partnerType+"&partnerId="+partner.getPartnerId()+"&doc-id="+document.getDocId() + "'>"
	        			+ "Click here</a>";
	        	mailBody += "<p>The document needs to be renewed prior to expiration.</p>"
		    			+ "<p>Regards,<br>"
		    			+ "American Greetings Team</p>"
		    			+ "<img src='https://ak.imgag.com/imgag/agbeta/header/american-greetings-logo.png'>";
		    	
		    	String mailSubject = "Expiration Notice - Document: " + document.getDocName() + ", " + partnerTypeSingular + ": " + partner.getName() + ", Expiration Date: " + document.getExpDate();
		    	simpleMailService.setSubject(mailSubject);
		    	simpleMailService.setBodyText(mailBody);
		    	simpleMailService.send();
	    		
	    	}
	    	
    	}
    }
 }
