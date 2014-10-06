package com.przychodnia.rejestracja.jsf;
/*
 * Copyright 2009-2012 Prime Teknoloji.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.przychodnia.rejestracja.domain.Appointment;
import com.przychodnia.rejestracja.domain.Doctor;
import com.przychodnia.rejestracja.domain.Person;
import com.przychodnia.rejestracja.jsf.util.DateUtils;

public class MyDefaultScheduleEvent implements MyScheduleEvent, Serializable {

	private String id;
	private long idAppointment;
	private String title;
	
	 private Person patient;
	 private Doctor doctor;
	    
	private Date startDate;
	private Date startTime;
	
	private Date endDate;
	private Date endTime;
	
	private boolean allDay = false;
	
	private String styleClass;
	
	private Object data;
    
    private boolean editable = true;

	public MyDefaultScheduleEvent() {
	}

	public MyDefaultScheduleEvent(Appointment appointment) {
		super();
		this.idAppointment = appointment.getId();
		this.title = appointment.getAppointmentDetails();
		this.patient = appointment.getPatient();
		this.doctor = appointment.getDoctor();
		this.startDate = appointment.getAppointmentStartDate();
		this.endDate = appointment.getAppointmentEndtDate();
		this.allDay = false;

		if(this.getStartTime() == null && this.getEndTime() ==null){
			this.startTime = startDate;
			this.endTime = endDate;
		}

	}
	
	public MyDefaultScheduleEvent(Long idAppointment,String title, Person patient, Doctor doctor,
			Date startDate, Date endDate, boolean allDay) {
		super();
		this.idAppointment = idAppointment;
		this.title = title;
		this.patient = patient;
		this.doctor = doctor;
		this.startDate = startDate;
		this.endDate = endDate;
		this.allDay = allDay;
	}

	public MyDefaultScheduleEvent(String title, Person patient, Doctor doctor,  Date start, Date end, Date startHour, Date endDate, boolean allDay ) {
	}
	
	public MyDefaultScheduleEvent(String title, Date start, Date end) {
		this.title = title;
		this.startDate = start;
		this.endDate = end;
		this.startTime=DateUtils.getInitialDate();
		this.endTime=DateUtils.getInitialDate();
		this.startTime.setHours(8);
		this.endTime.setHours(16);
	}
	
	public MyDefaultScheduleEvent(String title, Date start, Date end, boolean allDay) {
		this.title = title;
		this.startDate = start;
		this.endDate = end;
		this.allDay = allDay;
	}
	
	public MyDefaultScheduleEvent(String title, Date start, Date end, String styleClass) {
		this.title = title;
		this.startDate = start;
		this.endDate = end;
		this.styleClass = styleClass;
	}
	
	public MyDefaultScheduleEvent(String title, Date start, Date end, Object data) {
		this.title = title;
		this.startDate = start;
		this.endDate = end;
		this.data = data;
	}

	public String getId() {
		return id;
	}
	
	public long getIdAppointment() {
		return idAppointment;
	}

	public void setIdAppointment(long idAppointment) {
		this.idAppointment = idAppointment;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Person getPatient() {
		return patient;
	}

	public void setPatient(Person patient) {
		this.patient = patient;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getStyleClass() {
		return styleClass;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }   

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyDefaultScheduleEvent other = (MyDefaultScheduleEvent) obj;
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if (this.startDate != other.startDate && (this.startDate == null || !this.startDate.equals(other.startDate))) {
            return false;
        }
        if (this.endDate != other.endDate && (this.endDate == null || !this.endDate.equals(other.endDate))) {
            return false;
        }
        return true;
    }

	
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 61 * hash + (this.startDate != null ? this.startDate.hashCode() : 0);
        hash = 61 * hash + (this.endDate != null ? this.endDate.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "DefaultScheduleEvent{title=" + title + ",startDate=" + startDate + ",endDate=" + endDate + "}";
    }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}


		}