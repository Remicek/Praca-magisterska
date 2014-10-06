package com.przychodnia.rejestracja.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Configurable;

import com.przychodnia.rejestracja.jsf.util.DateUtils;
import com.przychodnia.rejestracja.jsf.util.Day;

@FacesConverter("com.przychodnia.rejestracja.jsf.converter.DayOfWeekConverter")
@Configurable
public class DayOfWeekConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		
		  if (value == null) {
	            return ""; // Or an empty string, can also.
	        }

	        if (!(value instanceof Integer)) {
	            throw new ConverterException("Value is not an integer");
	        }

	    	return DateUtils.getWeekDayForDisplay(value.toString());
	    }
	
}
