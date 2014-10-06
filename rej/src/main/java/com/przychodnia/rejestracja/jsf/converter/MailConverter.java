package com.przychodnia.rejestracja.jsf.converter;
import com.przychodnia.rejestracja.domain.Mail;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;

@FacesConverter("com.przychodnia.rejestracja.domain.converter.MailConverter")
@Configurable
public class MailConverter implements Converter  {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Mail.findMail(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Mail ? ((Mail) value).getId().toString() : "";
    }
}
