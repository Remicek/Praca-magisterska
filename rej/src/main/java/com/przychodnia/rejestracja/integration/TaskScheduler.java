package com.przychodnia.rejestracja.integration;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Service
public class TaskScheduler {
	
	    public void sendAllMails()
	    {
	    	emailManager.sendAllMails();
	    }
	    
	    @Autowired
	    EmailManager emailManager;

		public EmailManager getEmailManager() {
			return emailManager;
		}

		public void setEmailManager(EmailManager emailManager) {
			this.emailManager = emailManager;
		}
	    
}
