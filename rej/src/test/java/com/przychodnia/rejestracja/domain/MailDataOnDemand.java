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
@Component
@Configurable
public class MailDataOnDemand {
	 private Random rnd = new SecureRandom();
	    
	    private List<Mail> data;
	    
	    public Mail getNewTransientMail(int index) {
	        Mail obj = new Mail();
	        return obj;
	    }
	    
	    public Mail getSpecificMail(int index) {
	        init();
	        if (index < 0) {
	            index = 0;
	        }
	        if (index > (data.size() - 1)) {
	            index = data.size() - 1;
	        }
	        Mail obj = data.get(index);
	        Long id = obj.getId();
	        return Mail.findMail(id);
	    }
	    
	    public Mail getRandomMail() {
	        init();
	        Mail obj = data.get(rnd.nextInt(data.size()));
	        Long id = obj.getId();
	        return Mail.findMail(id);
	    }
	    
	    public boolean modifyMail(Mail obj) {
	        return false;
	    }
	    
	    public void init() {
	        int from = 0;
	        int to =  AllTests.NUMBER_OF_OBJECTS_CREATED;
	        data = Mail.findMailEntries(from, to);
	        if (data == null) {
	            throw new IllegalStateException("Find entries implementation for 'Mail' illegally returned null");
	        }
	        if (!data.isEmpty()) {
	            return;
	        }
	        
	        data = new ArrayList<Mail>();
	        for (int i = 0; i <  AllTests.NUMBER_OF_OBJECTS_CREATED; i++) {
	            Mail obj = getNewTransientMail(i);
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
