package com.przychodnia.rejestracja.jsf;
import com.przychodnia.rejestracja.domain.Appointment;
import com.przychodnia.rejestracja.domain.Doctor;
import com.przychodnia.rejestracja.domain.Person;
import com.przychodnia.rejestracja.jsf.converter.DoctorConverter;
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
import javax.faces.validator.LengthValidator;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@ManagedBean(name = "personBean")
@SessionScoped
public class PersonBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = Literals.PATIENTS;

	private Person person;

	private List<Person> allPeople;
	private List<Person> filteredPeople;

	private boolean dataVisible = false;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
    }

	public String getName() {
        return name;
    }


	public List<Person> getAllPeople() {
        return allPeople;
    }

	public void setAllPeople(List<Person> allPeople) {
        this.allPeople = allPeople;
    }

	public List<Person> getFilteredPeople() {
        return filteredPeople;
    }

	public void setFilteredPeople(List<Person> filteredPeople) {
        this.filteredPeople = filteredPeople;
    }
	
	public String findAllPeople() {
        allPeople = Person.findAllPeople();
        dataVisible = !allPeople.isEmpty();
        return null;
    }

	public boolean isDataVisible() {
        return dataVisible;
    }

	public void setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }

	public Person getPerson() {
        if (person == null) {
            person = new Person();
        }
        return person;
    }

	public void setPerson(Person person) {
        this.person = person;
    }

	public List<Doctor> completeAttendingPhysician(String query) {
        List<Doctor> suggestions = new ArrayList<Doctor>();
        for (Doctor doctor : Doctor.findAllDoctors()) {
            String doctorStr = String.valueOf(doctor.getSurname() + " " + doctor.getFirstName() + " "  + doctor.getProfession());
            if (doctorStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(doctor);
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
        findAllPeople();
        return "person";
    }

	public String displayCreateDialog() {
        person = new Person();
        createDialogVisible = true;
        findAllPeople();
        return "person";
    }

	public String persist() {
        String message = "";
        if (person.getId() != null) {
            person.merge();
            message = "message_successfully_updated";
        } else {
            person.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, Literals.PATIENT);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllPeople();
    }

	public String delete() {
        person.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", Literals.PATIENT);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
//		else {
//			FacesMessage facesMessage = MessageFactory.getMessage("message_notsuccesfully_deleted", Literals.PATIENT);
//	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
//		}
        
        reset();
        return findAllPeople();
    }

	public void reset() {
        person = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
