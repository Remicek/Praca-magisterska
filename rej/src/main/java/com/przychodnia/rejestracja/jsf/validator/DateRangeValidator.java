package com.przychodnia.rejestracja.jsf.validator;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
 


import org.primefaces.component.calendar.Calendar;
import org.primefaces.validate.ClientValidator;
 
public class DateRangeValidator implements Validator, ClientValidator {
     
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
         
        //Leave the null handling of startDate to required="true"
        Object startDateValue = component.getAttributes().get("startDate");
        if (startDateValue==null) {
            return;
        }
         
        Date startDate = (Date)startDateValue;
        Date endDate = (Date)value;
        if (endDate.before(startDate)) {
//            throw new ValidatorException(
//                    FacesMessageUtil.newBundledFacesMessage(FacesMessage.SEVERITY_ERROR, "", "msg.dateRange", ((Calendar)component).getLabel(), startDate));
        	   throw new ValidatorException(new FacesMessage(
                       FacesMessage.SEVERITY_ERROR, "Start date may not be after end date.", null));
        }
    }

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		// TODO Auto-generated method stub
		return "custom.emailValidator";
	}
}