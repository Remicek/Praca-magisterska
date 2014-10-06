package com.przychodnia.rejestracja.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.przychodnia.rejestracja.domain.Appointment;
import com.przychodnia.rejestracja.domain.Doctor;
import com.przychodnia.rejestracja.domain.Person;
import com.przychodnia.rejestracja.integration.EmailManager;
import com.przychodnia.rejestracja.integration.EmailManagerImpl;
import com.przychodnia.rejestracja.jsf.util.DateUtils;
import com.przychodnia.rejestracja.jsf.util.Literals;
import com.przychodnia.rejestracja.jsf.util.MessageFactory;
import com.przychodnia.rejestracja.jsf.util.TimeSpan;
import com.przychodnia.rejestracja.jsf.util.TimeSpanTest;

@Configurable
@ManagedBean(name = "scheduleBean")
@SessionScoped
public class ScheduleBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private ScheduleModel eventModel;
	private Appointment appointmentForFilter;
	private MyScheduleEvent event;
	private List<Appointment> allAppointments;
	private List<Appointment> filteredAppointments;
	private List<TimeSpan> availableAppointments;
	private TimeSpan availableAppointment;
	@Autowired
	private EmailManager emailManager;
	
	public void convertAvailableAppointment(){
	setEvent(new MyDefaultScheduleEvent());
	getEvent().setStartDate(getAvailableAppointment().getStartDate());
	getEvent().setEndDate(getAvailableAppointment().getEndDate());
	getEvent().setStartTime(getAvailableAppointment().getStartDate());
	getEvent().setEndTime(getAvailableAppointment().getEndDate());
	getEvent().setDoctor(getAppointmentForFilter().getDoctor());
	}
	
	public void findAvaiableDates() {
		availableAppointments = TimeSpanTest.getAvailableDates(appointmentForFilter);
		updateAllEvents();
	}

	public String displaySchedule() {
		return "schedule";
	}

	@PostConstruct
	public void init() {
		appointmentForFilter = new Appointment();
		eventModel = getEventModel();
		eventModel = new DefaultScheduleModel();
		loadAllEvents();
		event = new MyDefaultScheduleEvent();
	}

	// Czyszczenie, ładowanie eventow, filtryowanie

	public void loadAllEvents() {
		clearAllEvents();
		findAllAppointments();
		for (Appointment appointment : allAppointments) {
			getEventModel().addEvent(AppointmentToEventConverter(appointment));
		}
	}

	public void updateAllEvents() {
		filteredAppointments = Appointment
				.findAppointmentForFilter(appointmentForFilter);
		clearAllEvents();
		for (Appointment appointment : filteredAppointments) {
			getEventModel().addEvent(AppointmentToEventConverter(appointment));
		}

		if (filteredAppointments.isEmpty()) {
			FacesMessage facesMessage = MessageFactory.getMessage("no_results",
					"no_results_filter");		
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}

	public void clearAllEvents() {
		getEventModel().clear();
	}

	public String findAllAppointments() {
		allAppointments = Appointment.findAllAppointments();
		return null;
	}

	public void clearAllAppointments() {
		allAppointments.clear();
	}

	public void resetFilter() {
		setAppointmentForFilter(new Appointment());
		loadAllEvents();
	}

	public void reset(Appointment appointment) {
		appointment = null;
	}

	// Update schedule
	public void updateScheduleModel(MyScheduleEvent event) {
		getEventModel().updateEvent(event);
	}

	//TODO To nie jest update tworzy nowa instancje
	public MyScheduleEvent updateEvent(MyScheduleEvent event,
			Appointment appointment) {
		MyScheduleEvent newEvent = AppointmentToEventConverter(appointment);
		newEvent.setId(event.getId());
		return newEvent;
	}

	// Konwertery
	public MyScheduleEvent AppointmentToEventConverter(Appointment appointment) {
		return new MyDefaultScheduleEvent(appointment);
	}

	public Appointment EventToAppointmentConverter(MyScheduleEvent event) {
		return new Appointment(event);
	}

	// Dodawanie usuwanie i modyfikacje event i appointment
	public void deleteEvent(ActionEvent actionEvent) {
		Appointment appointment = Appointment.findAppointment(getEvent()
				.getIdAppointment());
		getEventModel().deleteEvent(getEvent());
		if (appointment != null){
			appointment.remove();
			availableAppointments = TimeSpanTest.getAvailableDates(appointmentForFilter);
		}
		FacesMessage facesMessage = MessageFactory.getMessage("appointment_deleted",appointment.getAppointmentStartDate() );		
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		
	}
	
	public void addEvent(ActionEvent actionEvent) {
		Appointment appointment = EventToAppointmentConverter(getEvent());
		
		if (appointment.isDayCorrect()) {
			if (event.getId() == null) {
				appointment.persist();
				getEventModel().addEvent(updateEvent(event, appointment));
				emailManager.addCreateAppointmentMail(appointment);
			} else {
				Appointment appointmentFromBase = Appointment.findAppointment(event.getIdAppointment());
				appointmentFromBase.setAppointmentDetails(appointment.getAppointmentDetails());
				appointmentFromBase.setAppointmentStartDate(appointment.getAppointmentStartDate());
				appointmentFromBase.setAppointmentEndtDate(appointment.getAppointmentEndtDate());
				appointmentFromBase.setDoctor(appointment.getDoctor());
				appointmentFromBase.setPatient(appointment.getPatient());
				appointmentFromBase.merge();
				getEventModel().updateEvent(updateEvent(event, appointment));
				emailManager.addUpdateAppointmentMail(appointmentFromBase);
			}
			event.setIdAppointment(appointment.getId());
			FacesMessage facesMessage = MessageFactory.getMessage("appointment_sucesfully_added",appointment.getAppointmentStartDate());		
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		
		} 
		else{
			Appointment appointmentOld = Appointment.findAppointment(event
					.getIdAppointment());

			if (appointmentOld != null) {
				MyScheduleEvent updatedEvent = updateEvent(event,
						appointmentOld);
				updateScheduleModel(updatedEvent);
			} 
			else {
				if (event.getId()!=null){
				MyScheduleEvent updatedEvent = updateEvent(event, appointment);
				updateScheduleModel(updatedEvent);
				}
			}
			FacesMessage facesMessage = MessageFactory.getMessage("appointment_notsucesfully_added");		
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
		//TODO tymczasowe rozwiazanie
		if (appointmentForFilter.getAppointmentStartDate() !=null) 
		availableAppointments = TimeSpanTest.getAvailableDates(appointmentForFilter);
	}

	// Listenery na evenrty
	public void onEventSelect(SelectEvent selectEvent) {
		MyScheduleEvent eventObj = (MyScheduleEvent) selectEvent.getObject();
		setEvent(eventObj);
		MessageFactory.showDoctorWorkingHours(eventObj.getDoctor());
	}

	public void onDateSelect(SelectEvent selectEvent) {
		setEvent(new MyDefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject()));
	}

	public void onDoctorSelect(SelectEvent event) {
		MessageFactory.showDoctorWorkingHours((Doctor) event.getObject());
	}

	// Dzialania na eventy move i resize
	private boolean onEventMoveOrResizeAction(
			MyDefaultScheduleEvent scheduleEvent) {

		Appointment appointmentOld = Appointment.findAppointment(scheduleEvent
				.getIdAppointment());

		Appointment appointmentNew = EventToAppointmentConverter(scheduleEvent);

		if (appointmentNew.isDayCorrect()) {
			appointmentOld.setAppointmentStartDate(appointmentNew
					.getAppointmentStartDate());
			appointmentOld.setAppointmentEndtDate(appointmentNew
					.getAppointmentEndtDate());
			appointmentOld.merge();
			emailManager.addUpdateAppointmentMail(appointmentOld);
			//TODO tymczasowe rozwiazanie odswiezania
			if (appointmentForFilter.getId()!=null)
			availableAppointments = TimeSpanTest.getAvailableDates(appointmentForFilter);
			return true;
		} else {
			FacesMessage facesMessage = MessageFactory.getMessage("appointment_notsucesfully_changed","appointment_notsucesfully_changed_reason");		
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			
		}
		MessageFactory.showDoctorWorkingHours(scheduleEvent.getDoctor());

		MyScheduleEvent updatedEvent = updateEvent(scheduleEvent,
				appointmentOld);
		updateScheduleModel(updatedEvent);
		return false;
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		if (onEventMoveOrResizeAction((MyDefaultScheduleEvent) event
				.getScheduleEvent())) {
			FacesMessage facesMessage = MessageFactory.getMessage("appointment_moved");		
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		if (onEventMoveOrResizeAction((MyDefaultScheduleEvent) event
				.getScheduleEvent())) {
			FacesMessage facesMessage = MessageFactory.getMessage("appointment_changed");		
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			
		}
	}

	// Gettery i setter

	public Appointment getAppointmentForFilter() {
		return appointmentForFilter;
	}

	public List<TimeSpan> getAvailableAppointments() {
		return availableAppointments;
	}

	public void setAvailableAppointments(List<TimeSpan> availableAppointments) {
		this.availableAppointments = availableAppointments;
	}

	public void setAppointmentForFilter(Appointment appointmentForFilter) {
		this.appointmentForFilter = appointmentForFilter;
	}

	public List<Appointment> getFilteredAppointments() {
		return filteredAppointments;
	}

	public TimeSpan getAvailableAppointment() {
		return availableAppointment;
	}

	public void setAvailableAppointment(TimeSpan availableAppointment) {
		this.availableAppointment = availableAppointment;
	}

	public void setFilteredAppointments(List<Appointment> filteredAppointments) {
		this.filteredAppointments = filteredAppointments;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public MyScheduleEvent getEvent() {
		return event;
	}

	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}

	public void setEvent(MyScheduleEvent event) {
		this.event = event;
	}

	public List<Appointment> getAllAppointments() {
		return allAppointments;
	}

	public void setAllAppointments(List<Appointment> allAppointments) {
		this.allAppointments = allAppointments;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}
}