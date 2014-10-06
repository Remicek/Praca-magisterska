package com.przychodnia.rejestracja.domain;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class DoctorDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Doctor> data;

	public Doctor getNewTransientDoctor(int index) {
        Doctor obj = new Doctor();
        setFirstName(obj, index);
        setProfession(obj, index);
        setSurname(obj, index);
        return obj;
    }

	public void setFirstName(Doctor obj, int index) {
        String firstName = "firstName_" + index;
        if (firstName.length() > 20) {
            firstName = firstName.substring(0, 20);
        }
        obj.setFirstName(firstName);
    }

	public void setProfession(Doctor obj, int index) {
        String profession = "profession_" + index;
        if (profession.length() > 20) {
            profession = profession.substring(0, 20);
        }
        obj.setProfession(profession);
    }

	public void setSurname(Doctor obj, int index) {
        String surname = "surname_" + index;
        if (surname.length() > 20) {
            surname = surname.substring(0, 20);
        }
        obj.setSurname(surname);
    }

	public Doctor getSpecificDoctor(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Doctor obj = data.get(index);
        Long id = obj.getId();
        return Doctor.findDoctor(id);
    }

	public Doctor getRandomDoctor() {
        init();
        Doctor obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Doctor.findDoctor(id);
    }

	public boolean modifyDoctor(Doctor obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to =  AllTests.NUMBER_OF_OBJECTS_CREATED;
        data = Doctor.findDoctorEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Doctor' illegally returned null");
        }
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<Doctor>();
        for (int i = 0; i <  AllTests.NUMBER_OF_OBJECTS_CREATED; i++) {
            Doctor obj = getNewTransientDoctor(i);
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
