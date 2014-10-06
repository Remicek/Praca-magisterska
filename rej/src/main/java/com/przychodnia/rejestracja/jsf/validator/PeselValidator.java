package com.przychodnia.rejestracja.jsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("PeselValidator")
public class PeselValidator implements Validator {
   
private static int[] WEIGHTS = new int[] { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3 };
   
      @Override
      public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
          if (!isPesel(value.toString())) {
              FacesMessage msg = new FacesMessage("PESEL validation failed", "Invalid PESEL");
              msg.setSeverity(FacesMessage.SEVERITY_ERROR);
              throw new ValidatorException(msg);
         }
      }
   
      private boolean isPesel(String pesel) {
          if (pesel.length() != 11) {
              return false;
          }
         int sum = 0;
          int currentCheck = 0;
          int check = new Integer(pesel.substring(pesel.length() - 1));
          for (int i = 0; i < pesel.length() - 1; i++) {
              sum += new Integer(String.valueOf(pesel.charAt(i))) * WEIGHTS[i];
          }
          currentCheck = 10 - (sum % 10);
          if (currentCheck > 9) {
              currentCheck = 0;
          }
          return (currentCheck == check);
      }
   
  }
