package com.przychodnia.rejestracja.domain;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class AppointmentDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Appointment> data;

	@Autowired
    DoctorDataOnDemand doctorDataOnDemand;

	@Autowired
    PersonDataOnDemand personDataOnDemand;

	public Appointment getNewTransientAppointment(int index) {
        Appointment obj = new Appointment();
        setAppointmentDetails(obj, index);
        setAppointmentEndtDate(obj, index);
        setAppointmentStartDate(obj, index);
        setAppointmentPatient(obj, index);
        setAppointmentDoctor(obj, index);
        return obj;
    }

	private void setAppointmentPatient(Appointment obj, int index) {
		Person patient = personDataOnDemand.getNewTransientPerson(index);
		obj.setPatient(patient);
	}
	
	private void setAppointmentDoctor(Appointment obj, int index) {
		Doctor doctor = doctorDataOnDemand.getNewTransientDoctor(index);
		obj.setDoctor(doctor);
	}

	public void setAppointmentDetails(Appointment obj, int index) {
        String appointmentDetails = "appointmentDetails_" + index;
        obj.setAppointmentDetails(appointmentDetails);
    }

	public void setAppointmentEndtDate(Appointment obj, int index) {
        Date appointmentEndtDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setAppointmentEndtDate(appointmentEndtDate);
    }

	public void setAppointmentStartDate(Appointment obj, int index) {
        Date appointmentStartDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setAppointmentStartDate(appointmentStartDate);
    }

	public Appointment getSpecificAppointment(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Appointment obj = data.get(index);
        Long id = obj.getId();
        return Appointment.findAppointment(id);
    }

	public Appointment getRandomAppointment() {
        init();
        Appointment obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Appointment.findAppointment(id);
    }

	public boolean modifyAppointment(Appointment obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = AllTests.NUMBER_OF_OBJECTS_CREATED;
        data = Appointment.findAppointmentEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Appointment' illegally returned null");
        }
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<Appointment>();
        for (int i = 0; i < AllTests.NUMBER_OF_OBJECTS_CREATED; i++) {
            Appointment obj = getNewTransientAppointment(i);
            try {
                obj.persist();
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
}
