package com.przychodnia.rejestracja.domain;
import com.przychodnia.rejestracja.domain.Person;

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
public class PersonDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Person> data;

	@Autowired
    DoctorDataOnDemand doctorDataOnDemand;

	public Person getNewTransientPerson(int index) {
        Person obj = new Person();
        setAddress(obj, index);
        setBirthDate(obj, index);
        setCardNumber(obj, index);
        setFatherName(obj, index);
        setFirstName(obj, index);
        setLegitimationID(obj, index);
        setMaidenSurname(obj, index);
//        setMail(obj, index);
        setNfz(obj, index);
        setPesel(obj, index);
        setSurname(obj, index);
        setTelephoneNumber(obj, index);
        setDoctor(obj, index);
        return obj;
    }
	
	private void setDoctor(Person obj, int index) {
		Doctor doctor = doctorDataOnDemand.getNewTransientDoctor(index);
		obj.setAttendingPhysician(doctor);
	}

	public void setAddress(Person obj, int index) {
        String address = "address_" + index;
        if (address.length() > 20) {
            address = address.substring(0, 20);
        }
        obj.setAddress(address);
    }

	public void setBirthDate(Person obj, int index) {
        Date birthDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setBirthDate(birthDate);
    }

	public void setCardNumber(Person obj, int index) {
        String cardNumber = "cardNumber_" + index;
        if (cardNumber.length() > 20) {
            cardNumber = cardNumber.substring(0, 20);
        }
        obj.setCardNumber(cardNumber);
    }

	public void setFatherName(Person obj, int index) {
        String fatherName = "fatherName_" + index;
        if (fatherName.length() > 50) {
            fatherName = fatherName.substring(0, 50);
        }
        obj.setFatherName(fatherName);
    }

	public void setFirstName(Person obj, int index) {
        String firstName = "firstName_" + index;
        if (firstName.length() > 20) {
            firstName = firstName.substring(0, 20);
        }
        obj.setFirstName(firstName);
    }

	public void setLegitimationID(Person obj, int index) {
        String legitimationID = "legitimationID_" + index;
        if (legitimationID.length() > 20) {
            legitimationID = legitimationID.substring(0, 20);
        }
        obj.setLegitimationID(legitimationID);
    }

	public void setMaidenSurname(Person obj, int index) {
        String maidenSurname = "maidenSurname_" + index;
        if (maidenSurname.length() > 20) {
            maidenSurname = maidenSurname.substring(0, 20);
        }
        obj.setMaidenSurname(maidenSurname);
    }

//	public void setMail(Person obj, int index) {
//        String mail = "mail_" + index;
//        if (mail.length() > 20) {
//            mail = mail.substring(0, 20);
//        }
//        obj.setMail(mail);
//    }

	public void setNfz(Person obj, int index) {
        String nfz = "nfz_" + index;
        if (nfz.length() > 20) {
            nfz = nfz.substring(0, 20);
        }
        obj.setNfz(nfz);
    }

	public void setPesel(Person obj, int index) {
        String pesel = "peselxxx_" + index;
        if (pesel.length() > 12) {
            pesel = pesel.substring(0, 12);
        }
        obj.setPesel(pesel);
    }

	public void setSurname(Person obj, int index) {
        String surname = "surname_" + index;
        if (surname.length() > 50) {
            surname = surname.substring(0, 50);
        }
        obj.setSurname(surname);
    }

	public void setTelephoneNumber(Person obj, int index) {
        String telephoneNumber = "telephoneNumber_" + index;
        if (telephoneNumber.length() > 20) {
            telephoneNumber = telephoneNumber.substring(0, 20);
        }
        obj.setTelephoneNumber(telephoneNumber);
    }

	public Person getSpecificPerson(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Person obj = data.get(index);
        Long id = obj.getId();
        return Person.findPerson(id);
    }

	public Person getRandomPerson() {
        init();
        Person obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Person.findPerson(id);
    }

	public boolean modifyPerson(Person obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to =  AllTests.NUMBER_OF_OBJECTS_CREATED;
        data = Person.findPersonEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Person' illegally returned null");
        }
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<Person>();
        for (int i = 0; i <  AllTests.NUMBER_OF_OBJECTS_CREATED; i++) {
            Person obj = getNewTransientPerson(i);
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
