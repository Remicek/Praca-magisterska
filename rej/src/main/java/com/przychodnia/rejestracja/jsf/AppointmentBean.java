package com.przychodnia.rejestracja.jsf;

import com.przychodnia.rejestracja.domain.Appointment;
import com.przychodnia.rejestracja.domain.Bhours;
import com.przychodnia.rejestracja.domain.Doctor;
import com.przychodnia.rejestracja.domain.Person;
import com.przychodnia.rejestracja.integration.EmailManager;
import com.przychodnia.rejestracja.integration.EmailManagerImpl;
import com.przychodnia.rejestracja.jsf.converter.DoctorConverter;
import com.przychodnia.rejestracja.jsf.converter.PersonConverter;
import com.przychodnia.rejestracja.jsf.util.DateUtils;
import com.przychodnia.rejestracja.jsf.util.Literals;
import com.przychodnia.rejestracja.jsf.util.MessageFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.event.ActionEvent;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@ManagedBean(name = "appointmentBean")
@SessionScoped
public class AppointmentBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Appointment appointment;

	private List<Appointment> allAppointments;
	private List<Appointment> filteredAppointments;
	private boolean dataVisible = false;

	private boolean createDialogVisible = false;

	@Autowired
	private EmailManager emailManager;;
	
	@PostConstruct
	public void init() {
	}

	public void sendMail() {
		emailManager.addRemindAppointmentMail(getAppointment());
	}

	public List<Appointment> getAllAppointments() {
		return allAppointments;
	}

	public void setAllAppointments(List<Appointment> allAppointments) {
		this.allAppointments = allAppointments;
	}

	public List<Appointment> getFilteredAppointments() {
		return filteredAppointments;
	}

	public void setFilteredAppointments(List<Appointment> filteredAppointments) {
		this.filteredAppointments = filteredAppointments;
	}

	public String findAllAppointments() {
		allAppointments = Appointment.findAllAppointments();
		dataVisible = !allAppointments.isEmpty();
		return null;
	}

	public boolean isDataVisible() {
		return dataVisible;
	}

	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

	public Appointment getAppointment() {
		if (appointment == null) {
			appointment = new Appointment();
		}
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public List<Doctor> completeDoctor(String query) {
		List<Doctor> suggestions = new ArrayList<Doctor>();
		for (Doctor doctor : Doctor.findAllDoctors()) {
			String doctorStr = String.valueOf(doctor.getSurname() + " "
					+ doctor.getFirstName() + " " + doctor.getProfession());
			if (doctorStr.toLowerCase().startsWith(query.toLowerCase())) {
				suggestions.add(doctor);
			}
		}
		return suggestions;
	}

	public List<Person> completePatient(String query) {
		List<Person> suggestions = new ArrayList<Person>();
		for (Person person : Person.findAllPeople()) {
			String personStr = String.valueOf(person.getSurname() + " "
					+ person.getFirstName() + " " + person.getFatherName()
					+ " " + person.getBirthDate());
			if (personStr.toLowerCase().startsWith(query.toLowerCase())) {
				suggestions.add(person);
			}
		}
		return suggestions;
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

	public String displayList() {
		createDialogVisible = false;
		findAllAppointments();
		return "appointment";
	}

	public String displayCreateDialog() {
		appointment = new Appointment();
		createDialogVisible = true;
		return "appointment";
	}

	public String persist() {
		if (appointment.isDayCorrect()) {
			if (appointment.getId() != null) {
				appointment.merge();
				emailManager.addCreateAppointmentMail(appointment);
			} else {
				appointment.persist();
				emailManager.addUpdateAppointmentMail(appointment);
			}
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("createDialogWidget.hide()");
			context.execute("editDialogWidget.hide()");
			
			FacesMessage facesMessage = MessageFactory.getMessage(
					"appointment_sucesfully_added",  appointment.getAppointmentStartDate());
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		} else {
			FacesMessage facesMessage = MessageFactory.getMessage(
					"appointment_not_added",  appointment.getAppointmentStartDate());
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
		reset();
		return findAllAppointments();
	}

	public String delete() {
		appointment.remove();

		FacesMessage facesMessage = MessageFactory.getMessage(
				"message_successfully_deleted", Literals.APPOINTMENT_LABEL);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);

		reset();
		return findAllAppointments();
	}

	public void reset() {
		appointment = null;
		createDialogVisible = false;
	}

	public void handleDialogClose(CloseEvent event) {
		reset();
	}
}
