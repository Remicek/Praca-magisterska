package com.przychodnia.rejestracja.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.przychodnia.rejestracja.domain.Appointment;
import com.przychodnia.rejestracja.domain.Doctor;
import com.przychodnia.rejestracja.domain.Mail;
import com.przychodnia.rejestracja.integration.EmailManager;
import com.przychodnia.rejestracja.integration.EmailManagerImpl;
import com.przychodnia.rejestracja.integration.TaskScheduler;
import com.przychodnia.rejestracja.jsf.util.MessageFactory;

@ManagedBean(name = "mailBean")
@SessionScoped
@Configurable
public class MailBean implements Serializable {

	private Mail mail;

	private List<Mail> selectedUnsentMails;
	private List<Mail> selectedSentMails;
	private List<Mail> unsentMails;
	private List<Mail> sentMails;

	private boolean dataVisibleSentMails = false;
	private boolean dataVisibleUnsentMails = false;

	private boolean createDialogVisible = false;
	
	@Autowired
	private EmailManager mailManager;
	@Autowired
	TaskScheduler taskScheduler;
	
	@PostConstruct
	public void init() {
		sentMails = Mail.findSentMails();
		unsentMails = Mail.findUnsentMails();
	}

	public String findSentMails() {
		sentMails = Mail.findSentMails();
		dataVisibleSentMails = !sentMails.isEmpty();
		return null;
	}

	public String findUnsentMails() {
		unsentMails = Mail.findUnsentMails();
		dataVisibleUnsentMails = !unsentMails.isEmpty();
		return null;
	}

	
	public String displayList() {
		createDialogVisible = false;
		findUnsentMails();
		findSentMails();
		return "mail";
	}

	public List<Appointment> completeAppointment(String query) {
		List<Appointment> suggestions = new ArrayList<Appointment>();
		for (Appointment appointment : Appointment.findAllAppointments()) {
			String appointmentStr = String.valueOf(appointment.getDoctor()
					.getSurname()
					+ appointment.getPatient().getSurname()
					+ " "
					+ appointment.getAppointmentStartDate()
					+ " "
					+ appointment.getAppointmentEndtDate());

			if (appointmentStr.toLowerCase().startsWith(query.toLowerCase())) {
				suggestions.add(appointment);
			}
		}
		return suggestions;
	}

	public String displayCreateDialog() {
		mail = new Mail();
		createDialogVisible = true;
		findUnsentMails();
		findSentMails();
		return "mail";
	}

	public void persist() {
		String message = "";
		if (mail.getId() != null) {
			mail.merge();
			message = "message_successfully_updated";
		} else {
			mail.persist();
			message = "message_successfully_created";
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("createDialogWidget.hide()");
		context.execute("editDialogWidget.hide()");

		FacesMessage facesMessage = MessageFactory.getMessage(message, "Mail");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		reset();
		findUnsentMails();
		findSentMails();
	}

	public void delete() {
		mail.remove();
		FacesMessage facesMessage = MessageFactory.getMessage(
				"message_successfully_deleted", "Mail");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		reset();
		findUnsentMails();
		findSentMails();
	}

	public void reset() {
		mail = null;
		createDialogVisible = false;
	}

	public void handleDialogClose(CloseEvent event) {
		reset();
	}

	//Obsluga maili
	
	public void addDefaultMail() {
		getMail().setDateMail(new Date());
		getMail().setSend(false);
		persist();
	}
	
	public void deleteSentMail() {
		for(Mail mail : getSelectedSentMails()){
			mail.remove();
		}
		findSentMails();
	}

	public void deleteAllSentMail() {
		for(Mail mail : getSentMails()){
			mail.remove();
		}
		findSentMails();
	}

	public void deleteUnsentMail() {
		for(Mail mail :getSelectedUnsentMails()){
			mail.remove();
		}
		findUnsentMails();

	}

	public void deleteAllUnsentMail() {
		for(Mail mail :getUnsentMails()){
			mail.remove();
		}
		findUnsentMails();
	}

	public void sendUnsentMails() {
		for(Mail mail :getSelectedUnsentMails()){
			mail.setSend(true);
			mail.merge();
		}
		mailManager.sendMail(getSelectedUnsentMails());
		findUnsentMails();
		findSentMails();
	}

	public void sendAllUnsentMails() {
		for(Mail mail :getUnsentMails()){
			mail.setSend(true);
			mail.merge();
		}
		mailManager.sendMail(getUnsentMails());
		findUnsentMails();
		findSentMails();
	}

	// Gettery i settery
	
	public boolean isDataVisibleSentMails() {
		return dataVisibleSentMails;
	}

	public void setDataVisibleSentMails(boolean dataVisibleSentMails) {
		this.dataVisibleSentMails = dataVisibleSentMails;
	}

	public boolean isDataVisibleUnsentMails() {
		return dataVisibleUnsentMails;
	}

	public void setDataVisibleUnsentMails(boolean dataVisibleUnsentMails) {
		this.dataVisibleUnsentMails = dataVisibleUnsentMails;
	}

	public Mail getMail() {
		if (mail == null) {
			mail = new Mail();
		}
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public List<Mail> getUnsentMails() {
		return unsentMails;
	}

	public void setUnsentMails(List<Mail> unsentMails) {
		this.unsentMails = unsentMails;
	}

	public List<Mail> getSentMails() {
		return sentMails;
	}

	public void setSentMails(List<Mail> sentMails) {
		this.sentMails = sentMails;
	}

	public EmailManager getMailManager() {
		return mailManager;
	}

	public void setMailManager(EmailManager mailManager) {
		this.mailManager = mailManager;
	}

	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	public void setTaskScheduler(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}

	public List<Mail> getSelectedUnsentMails() {
		return selectedUnsentMails;
	}

	public void setSelectedUnsentMails(List<Mail> selectedUnsentMails) {
		this.selectedUnsentMails = selectedUnsentMails;
	}

	public List<Mail> getSelectedSentMails() {
		return selectedSentMails;
	}

	public void setSelectedSentMails(List<Mail> selectedSentMails) {
		this.selectedSentMails = selectedSentMails;
	}

	public String onEdit() {
		return null;
	}

	public boolean isCreateDialogVisible() {
		return createDialogVisible;
	}

	public void setCreateDialogVisible(boolean createDialogVisible) {
		this.createDialogVisible = createDialogVisible;
	}

	private static final long serialVersionUID = 1L;
}
