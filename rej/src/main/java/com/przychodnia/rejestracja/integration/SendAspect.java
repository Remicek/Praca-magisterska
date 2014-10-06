package com.przychodnia.rejestracja.integration;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.przychodnia.rejestracja.jsf.util.Literals;
import com.przychodnia.rejestracja.jsf.util.MessageFactory;

@Configurable
@Aspect
public class SendAspect {
	@Autowired
 private EmailManager emailManager;
 
	@After("execution(* com.przychodnia.rejestracja.integration.EmailManager.*(..))  && !execution(* com.przychodnia.rejestracja.integration.EmailManager.sendMail(..)) && !execution(* com.przychodnia.rejestracja.integration.EmailManager.sendAllMails(..))")
	public void sendAfterMessageAdded(JoinPoint joinPoint) {
		FacesMessage facesMessage = MessageFactory.getMessage("mail_added_to_draft");		
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	@After("execution(* com.przychodnia.rejestracja.integration.EmailManager.sendMail(..))")
	public void sendAfterMessageSend(JoinPoint joinPoint) {
		FacesMessage facesMessage = MessageFactory.getMessage("mail_added_to_send");		
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}
 
}