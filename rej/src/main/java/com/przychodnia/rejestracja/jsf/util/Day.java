package com.przychodnia.rejestracja.jsf.util;

import java.util.HashMap;
import java.util.Map;

public enum Day {
	SUNDAY(0,"Niedziela"),
	MONDAY(1,"Poniedziałek"),
	TUESDAY(2,"Wtorek"),
	WEDNESDAY(3,"Środa"),
	THURSDAY(4,"Czwartek"),
	FRIDAY(5,"Piątek"),
	SATURDAY(6, "Sobota");
	
private int value;
private String local;

private Day(int value, String local) {
	this.value = value;
	this.local = local;
}

public int getValue() {
	return this.value;
}

public String getLocal() {
	return local;
}

public void setLocal(String local) {
	this.local = local;
}

}