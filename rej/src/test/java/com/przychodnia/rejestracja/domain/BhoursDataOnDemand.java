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
@Configurable
@Component
public class BhoursDataOnDemand {
	@Autowired
    DoctorDataOnDemand doctorDataOnDemand;
    private Random rnd = new SecureRandom();
    
    private List<Bhours> data;
    
    public Bhours getNewTransientBhours(int index) {
        Bhours obj = new Bhours();
        setBhourOpenTime(obj,index);
        setBhoursClose(obj, index);
        setBhoursDoctor(obj,index);
        return obj;
    }
    
	public void setBhourOpenTime(Bhours obj, int index) {
        Date open_time = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setOpen_time(open_time);
    }

	public void setBhoursClose(Bhours obj, int index) {
        Date close_time = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setClose_time(close_time);
    }
	
    private void setBhoursDoctor(Bhours obj, int index) {
		Doctor doctor = doctorDataOnDemand.getNewTransientDoctor(index);
		obj.setDoctor(doctor);
	}
    
    public Bhours getSpecificBhours(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Bhours obj = data.get(index);
        Long id = obj.getId();
        return Bhours.findBhours(id);
    }
    
    public Bhours getRandomBhours() {
        init();
        Bhours obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Bhours.findBhours(id);
    }
    
    public boolean modifyBhours(Bhours obj) {
        return false;
    }
    
    public void init() {
        int from = 0;
        int to =  AllTests.NUMBER_OF_OBJECTS_CREATED;
        data = Bhours.findBhoursEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Bhours' illegally returned null");
        }
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<Bhours>();
        for (int i = 0; i <  AllTests.NUMBER_OF_OBJECTS_CREATED; i++) {
            Bhours obj = getNewTransientBhours(i);
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
