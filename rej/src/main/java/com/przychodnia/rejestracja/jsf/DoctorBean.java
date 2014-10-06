package com.przychodnia.rejestracja.jsf;
import com.przychodnia.rejestracja.domain.Appointment;
import com.przychodnia.rejestracja.domain.Doctor;
import com.przychodnia.rejestracja.domain.Person;
import com.przychodnia.rejestracja.jsf.util.Literals;
import com.przychodnia.rejestracja.jsf.util.MessageFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Configurable;

@ManagedBean(name = "doctorBean")
@SessionScoped
@Configurable
public class DoctorBean  implements Serializable {

	private String name = Literals.DOCTOR_LABEL;

	private Doctor doctor;
	private static List<Doctor> allDoctors;
	private List<Doctor> filteredDoctors;
	private static boolean dataVisible = false;

	private boolean createDialogVisible = false;

	private List<Person> selectedListPatient;

	@PostConstruct
    public void init() {
       
    }

	public String getName() {
        return name;
    }

	public List<Doctor> getAllDoctors() {
        return allDoctors;
    }

	public void setAllDoctors(List<Doctor> allDoctors) {
        this.allDoctors = allDoctors;
    }
	
	public List<Doctor> getFilteredDoctors() {
        return filteredDoctors;
    }

	public void setFilteredDoctors(List<Doctor> filteredDoctors) {
        this.filteredDoctors = filteredDoctors;
    }

	//TODO wywolywane statycznie zeby BhourBean mogl zainicjowac liste pacjentow
	public static String findAllDoctors() {
        allDoctors = Doctor.findAllDoctors();
        dataVisible = !allDoctors.isEmpty();
        return null;
    }

	public boolean isDataVisible() {
        return dataVisible;
    }

	public void setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }

	public Doctor getDoctor() {
        if (doctor == null) {
            doctor = new Doctor();
        }
        return doctor;
    }

	public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

	public List<Person> getSelectedListPatient() {
        return selectedListPatient;
    }

	public void setSelectedListPatient(List<Person> selectedListPatient) {
        if (selectedListPatient != null) {
            doctor.setListPatient(new HashSet<Person>(selectedListPatient));
        }
        this.selectedListPatient = selectedListPatient;
    }

	//Obsluga listenerow
	
	public String onEdit() {
        if (doctor != null && doctor.getListPatient() != null) {
        	//TODO Do przemyslenia razem z fetch lazy czy eager
            selectedListPatient = new ArrayList<Person>(doctor.getListPatient());
        }
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
        findAllDoctors();
        return "doctor";
    }

	public String displayCreateDialog() {
        doctor = new Doctor();
        createDialogVisible = true;
        findAllDoctors();
        return "doctor";
    }

	public String persist() {
        String message = "";
        if (doctor.getId() != null) {
            doctor.merge();
            message = "message_successfully_updated";
        } else {
            doctor.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, Literals.DOCTOR);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDoctors();
    }

	public String delete() {
//		if(Appointment.isDoctorSaveToDelete(doctor) && Person.isDoctorSaveToDelete(doctor)){
        doctor.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", Literals.DOCTOR);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
//		}
//		else {
//			FacesMessage facesMessage = MessageFactory.getMessage("message_notsuccesfully_deleted", Literals.DOCTOR);
//	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
//		}
        reset();
        return findAllDoctors();
    }

	public void reset() {
        doctor = null;
        selectedListPatient = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
	
	private static final long serialVersionUID = 1L;
	
	//TODO poprawic
	public List<Person> getPatientList(){
		Set<Person> patientsSet = getDoctor().getListPatient();
		List <Person> patientsList = new ArrayList<>();
		for (Person p : patientsSet){
		System.out.println(p);
		patientsList.add(p);
		}
	
		return patientsList;
	}
	
}



