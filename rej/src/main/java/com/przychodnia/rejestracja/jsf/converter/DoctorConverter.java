package com.przychodnia.rejestracja.jsf.converter;
import com.przychodnia.rejestracja.domain.Doctor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@FacesConverter("com.przychodnia.rejestracja.jsf.converter.DoctorConverter")
public class DoctorConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Doctor.findDoctor(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value instanceof Doctor ? ((Doctor) value).getId().toString() : "";
    }
}
