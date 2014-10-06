package com.przychodnia.rejestracja.jsf;

import com.przychodnia.rejestracja.domain.Bhours;
import com.przychodnia.rejestracja.domain.Doctor;
import com.przychodnia.rejestracja.jsf.util.Day;
import com.przychodnia.rejestracja.jsf.util.Literals;
import com.przychodnia.rejestracja.jsf.util.MessageFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Configurable;

@ManagedBean(name = "bhoursBean")
@SessionScoped
@Configurable
public class BhoursBean implements Serializable {
	
	private String name = "bhours";
	private String dayOfWeekString;
	private Bhours bhours;
	private List<Bhours> allBhourses;
	private List<Bhours> filteredBhourses;
	private List<Bhours> filteredBhoursesForDoctor;
	private Doctor selectedDoctor;
	private boolean dataVisible = false;
	private boolean createDialogVisible = false;

	@PostConstruct
	public void init() {
		findAllBhourses();
		DoctorBean.findAllDoctors();
		bhours = new Bhours();
	}

	// Wyswietlanie
	public String findAllBhourses() {
		allBhourses = Bhours.findAllBhourses();
		dataVisible = !allBhourses.isEmpty();
		return null;
	}
	
	public String findBhoursForDoctor(Doctor doctor) {
		filteredBhoursesForDoctor = Bhours.findBhoursesByDoctor(doctor);
		return null;
	}

	//Obsluga listenrow
	  public void onRowSelect(SelectEvent event) {
		  findBhoursForDoctor(getSelectedDoctor());
		  
		  FacesMessage facesMessage = MessageFactory.getMessage("doctor_selected",((Doctor) event.getObject()).getSurname());		
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	    }
	 
	    public void onRowUnselect(UnselectEvent event) {
	    	FacesMessage facesMessage = MessageFactory.getMessage("doctor_unselected",((Doctor) event.getObject()).getSurname());		
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	    }
	    
	public String displayList() {
		createDialogVisible = false;
		findAllBhourses();
		return "bhours";
	}

	public String displayOfficeHours() {
		return "bhours";
	}

	public String displayCreateDialog() {
		bhours = new Bhours();
		createDialogVisible = true;
		return "bhours";
	}

	// Zapisywanie
	public void persist() {
		String message = "";
		if (!bhours.isBhoursTaken()) {
			
		if (bhours.getId() != null) {
				bhours.merge();
				message = "message_successfully_updated";
			} else {
				bhours.persist();
				message = "message_successfully_created";
			}
			FacesMessage facesMessage = MessageFactory.getMessage(message,
					Literals.BHOURS);
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			findBhoursForDoctor(bhours.getDoctor());
			reset();
		}
		else {
			
			FacesMessage facesMessage = MessageFactory.getMessage("bhours_notsucesfully_added_reason");		
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			
		findBhoursForDoctor(bhours.getDoctor());
		reset();
		}
	}

	// Usuwanie
	public void delete() {
		bhours.remove();
		FacesMessage facesMessage = MessageFactory
				.getMessage("message_successfully_deleted");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		findBhoursForDoctor(bhours.getDoctor());
		reset();
	}

	// Util
	public void reset() {
		bhours = null;
		createDialogVisible = false;
	}

	public void handleDialogClose(CloseEvent event) {
		reset();
	}

	// Gettery i settery
	public boolean isDataVisible() {
		return dataVisible;
	}

	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

	public Bhours getBhours() {
		if (bhours == null) {
			bhours = new Bhours();
		}
		return bhours;
	}

	public void setBhours(Bhours bhours) {
		this.bhours = bhours;
	}

	public Day[] getDays() {
		return Day.values();
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

	public String getName() {
		return name;
	}

	public Doctor getSelectedDoctor() {
				return selectedDoctor;
	}

	public void setSelectedDoctor(Doctor selectedDoctor) {
		this.selectedDoctor = selectedDoctor;
	}

	public List<Bhours> getAllBhourses() {
		return allBhourses;
	}

	public void setAllBhourses(List<Bhours> allBhourses) {
		this.allBhourses = allBhourses;
	}

	public List<Bhours> getFilteredBhourses() {
		return filteredBhourses;
	}

	public void setFilteredBhourses(List<Bhours> filteredBhourses) {
		this.filteredBhourses = filteredBhourses;
	}

	public List<Bhours> getFilteredBhoursesForDoctor() {
		return filteredBhoursesForDoctor;
	}

	public void setFilteredBhoursesForDoctor(List<Bhours> filteredBhoursesForDoctor) {
		this.filteredBhoursesForDoctor = filteredBhoursesForDoctor;
	}

	public String getDayOfWeekString() {
		return dayOfWeekString;
	}

	public void setDayOfWeekString(String dayOfWeekString) {
		this.dayOfWeekString = dayOfWeekString;
	}
}
