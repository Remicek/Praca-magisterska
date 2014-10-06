package com.przychodnia.rejestracja.jsf.converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.convert.DateTimeConverter;
import org.springframework.beans.factory.annotation.Configurable;

@FacesConverter("org.primefaces.convert.DateTimeConverter")
@Configurable
public class DefaultDateConverter extends DateTimeConverter {

    public DefaultDateConverter() {
        setPattern("MM/dd/yyyy");
    }

}