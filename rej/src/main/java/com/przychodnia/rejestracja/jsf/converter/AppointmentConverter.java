package com.przychodnia.rejestracja.jsf.converter;
import com.przychodnia.rejestracja.domain.Appointment;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;

@FacesConverter("com.przychodnia.rejestracja.jsf.converter.AppointmentConverter")
@Configurable
public class AppointmentConverter  implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Appointment.findAppointment(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Appointment ? ((Appointment) value).getId().toString() : "";
    }
}
