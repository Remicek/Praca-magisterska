package com.przychodnia.rejestracja.jsf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.taglibs.standard.lang.jstl.Literal;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;
import org.springframework.beans.factory.annotation.Configurable;

import com.przychodnia.rejestracja.jsf.util.Literals;

@Configurable
@ManagedBean
@RequestScoped
public class ApplicationBean {

    public static String getColumnName(String column) {
        if (column == null || column.length() == 0) {
            return column;
        }
        final Pattern p = Pattern.compile("[A-Z][^A-Z]*");
        final Matcher m = p.matcher(Character.toUpperCase(column.charAt(0)) + column.substring(1));
        final StringBuilder builder = new StringBuilder();
        while (m.find()) {
            builder.append(m.group()).append(" ");
        }
        return builder.toString().trim();
    }

    private MenuModel menuModel;

	@PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        menuModel = new DefaultMenuModel();
     
      //Mail submenu
        DefaultSubMenu messagesMenu = new DefaultSubMenu();
        DefaultMenuItem item;
        
        item = new DefaultMenuItem();
        item.setId("createEmailMenuItem");
        item.setValue(Literals.CREATE);
        item.setCommand("#{mailBean.displayCreateDialog}");
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        messagesMenu.addElement(item);
        
        messagesMenu.setId("mailSubmenu");
        messagesMenu.setLabel(Literals.Mail);
        item = new DefaultMenuItem(Literals.SHOW);
        item.setId("mailMenuItem");
        item.setCommand("#{mailBean.displayList}");
        item.setIcon("ui-icon ui-icon-document");
        messagesMenu.addElement(item);
        menuModel.addElement(messagesMenu);
        
        //Bhours submenu
        DefaultSubMenu bhoursMenu = new DefaultSubMenu();
        
        bhoursMenu.setId("officeHoursSubmenu");
        bhoursMenu.setLabel(Literals.OFFICE_HOURS_LABEL);
        item = new DefaultMenuItem(Literals.SHOW);
        item.setId("createOfficeHoursMenuItem");
        item.setCommand("#{bhoursBean.displayList}");
        item.setIcon("ui-icon ui-icon-document");
        bhoursMenu.addElement(item);
        menuModel.addElement(bhoursMenu);
        
        //Schedule submenu
        DefaultSubMenu scheduleMenu = new DefaultSubMenu();
        
        scheduleMenu.setId("scheduleSubmenu");
        scheduleMenu.setLabel(Literals.SCHEDULE_LABEL);
        item = new DefaultMenuItem(Literals.SCHEDULE_SHOW);
        item.setId("createScheduleMenuItem");
        item.setCommand(Literals.SCHEDULE);
        item.setIcon("ui-icon ui-icon-document");
        scheduleMenu.addElement(item);
        
        item = new DefaultMenuItem();
        item.setId("listAppointmentMenuItem");
        item.setValue(Literals.APOINTMENTS_SHOW);
        item.setCommand("#{appointmentBean.displayList}");
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        
        scheduleMenu.addElement(item);
        menuModel.addElement(scheduleMenu);
        
        //Doctor submenu
        DefaultSubMenu doctorMenu = new DefaultSubMenu();
        doctorMenu.setId("doctorSubmenu");
        doctorMenu.setLabel(Literals.DOCTOR_LABEL);
        
        item = new DefaultMenuItem();
        item.setId("createDoctorMenuItem");
        item.setValue(Literals.CREATE);
        item.setCommand("#{doctorBean.displayCreateDialog}");
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        doctorMenu.addElement(item);
        
        item = new DefaultMenuItem();
        item.setId("listDoctorMenuItem");
        item.setValue(Literals.SHOW);
        item.setCommand("#{doctorBean.displayList}");
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        doctorMenu.addElement(item);
        
        menuModel.addElement(doctorMenu);
        
        //Patient submenu
        DefaultSubMenu patientMenu = new DefaultSubMenu();
        patientMenu.setId("personSubmenu");
        patientMenu.setLabel(Literals.PATIENT_LABEL);
        
        item = new DefaultMenuItem();
        item.setId("createPersonMenuItem");
        item.setValue(Literals.CREATE);
        item.setCommand("#{personBean.displayCreateDialog}");
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        patientMenu.addElement(item);
        
        item = new DefaultMenuItem();
        item.setId("listPersonMenuItem");
        item.setValue(Literals.SHOW);
        item.setCommand("#{personBean.displayList}");
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        patientMenu.addElement(item);
        
        menuModel.addElement(patientMenu);
        
    }

	public MenuModel getMenuModel() {
        return menuModel;
    }

	public String getAppName() {
        return "Rejestracja";
    }
}