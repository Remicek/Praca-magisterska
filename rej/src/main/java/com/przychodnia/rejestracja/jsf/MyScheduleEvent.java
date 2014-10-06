package com.przychodnia.rejestracja.jsf;

import java.util.Date;

import com.przychodnia.rejestracja.domain.Doctor;
import com.przychodnia.rejestracja.domain.Person;

public interface MyScheduleEvent extends org.primefaces.model.ScheduleEvent
{
    // Custom attributes
	public void setStyleClass(String styleClass);
	public String getStyleClass();
	
	public void setStartTime(Date start);
	public void setEndTime(Date end);
	public Date getStartTime();
	public Date getEndTime();
	public void setIdAppointment(long idAppointment);
	public long getIdAppointment();
	public Person getPatient();
	public void setPatient(Person patient);
	public Doctor getDoctor();
	public void setDoctor(Doctor doctor);
    public void setStartDate(Date startDate);
    public void setEndDate(Date endDate);
    public void setTitle(String title);
    public String getTitle();
}

