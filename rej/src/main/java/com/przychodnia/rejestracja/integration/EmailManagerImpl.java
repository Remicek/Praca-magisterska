package com.przychodnia.rejestracja.integration;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.przychodnia.rejestracja.domain.Appointment;
import com.przychodnia.rejestracja.domain.Doctor;
import com.przychodnia.rejestracja.domain.Mail;
import com.przychodnia.rejestracja.domain.Person;
import com.przychodnia.rejestracja.jsf.MailBean;
import com.przychodnia.rejestracja.jsf.util.Literals;
import com.przychodnia.rejestracja.jsf.util.MessageFactory;

@Service
public class EmailManagerImpl implements EmailManager{
	 private MailSender mailSender;
	 private SimpleMailMessage templateMessage;
	 private SimpleMailMessage[] mailMessages;
	 
	Mail mail;
	List <Mail> mailList;
	public void addUpdateAppointmentMail(Appointment appointment){
		Person patient = appointment.getPatient();
		Doctor doctor = appointment.getDoctor();
		mail = new Mail(appointment, Literals.UPDATE_APPOINTMENT_MAIL);
		mail.setSubject("Zmiana terminu wizyty w poradni");
		mail.setBody("Szanowny pacjencie " + patient.getFirstName() + " "
				+ patient.getSurname() + "\nWizyta z doktorem: " +  doctor.getFirstName() + " " + doctor.getSurname()+
				" została przesunięta na termin: " + appointment.getAppointmentStartDate());
		mail.persist();
	}
	
	public void addCreateAppointmentMail(Appointment appointment){
		Person patient = appointment.getPatient();
		Doctor doctor = appointment.getDoctor();
		mail = new Mail(appointment, Literals.CREATE_APPOINTMENT_MAIL);
		mail.setSubject("Nowa wizyta w poradni");
		mail.setBody("Szanowny pacjencie " + patient.getFirstName() + " "
				+ patient.getSurname() + "\nZostała ustalona wizyta z doktorem: " +  doctor.getFirstName() + " " + doctor.getSurname()+
				" w terminie: " + appointment.getAppointmentStartDate());
		mail.persist();
	}
	
	public void addRemindAppointmentMail(Appointment appointment){
		Person patient = appointment.getPatient();
		Doctor doctor = appointment.getDoctor();
		mail = new Mail(appointment, Literals.REMIND_APPOINTMENT_MAIL);
		mail.setSubject("Przypomnienie o wizycie");
		mail.setBody("Szanowny pacjencie " + patient.getFirstName() + " "
				+ patient.getSurname() + "\nPrzypominamy o wizycie u doktora " +  doctor.getFirstName() + " " + doctor.getSurname()+
				" w terminie: " + appointment.getAppointmentStartDate());
		mail.persist();
	}
	
	public SimpleMailMessage convertMailToSimpleMessage(Mail mail){
		Person patient =  mail.getAppointment().getPatient();
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		
		msg.setTo(patient.getMail());
		msg.setSubject(mail.getSubject());
		msg.setText(mail.getBody());
		return msg;
	}
	
	public SimpleMailMessage[] convertMailsToSimpleMessagesArray(List<Mail> mails){
		Mail [] mailMessages = 	 mails.toArray(new Mail[mails.size()]);
		SimpleMailMessage[] simpleMailMessages = new SimpleMailMessage[mails.size()] ;
		
		for (int i=0;i<mails.size(); i++){
			simpleMailMessages[i] =convertMailToSimpleMessage(mailMessages[i]);
		}
		return simpleMailMessages;
	}
	
	@Override
	public void sendMail(List<Mail> messages) {
		mailSender.send(convertMailsToSimpleMessagesArray(messages));
	}
	
	//TODO Poprawic, dodatkowaa metoda dla ktorej aspekt dodawania powiadomien (growl) nie uruchomi sie.
	@Override
	public void sendAllMails(List<Mail> messages) {
		mailSender.send(convertMailsToSimpleMessagesArray(messages));
	}
	
	@Override
	public void sendMail(Mail message) {
		mailSender.send(convertMailToSimpleMessage(message));
	}
	
	//Gettery i settery
	
	public Mail getMail() {
		if (mail == null) {
			mail = new Mail();
		}
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public List<Mail> getMailList() {
		return mailList;
	}

	public void setMailList(List<Mail> mailList) {
		this.mailList = mailList;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public SimpleMailMessage[] getMailMessageList() {
		return mailMessages;
	}

	public void setMailMessageList(SimpleMailMessage[] mailMessages) {
		this.mailMessages = mailMessages;
	}

	public SimpleMailMessage getTemplateMessage() {
		return templateMessage;
	}

	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	//TODO Pokrywa sie z metoda z MailBean
	@Override
	public void sendAllMails() {
		List<Mail> unsentMail= Mail.findUnsentMails();
		if (unsentMail.size()>0){
		sendAllMails(unsentMail);
		for(Mail mail :unsentMail){
			mail.setSend(true);
			mail.merge();
		}
	}
	}



}
