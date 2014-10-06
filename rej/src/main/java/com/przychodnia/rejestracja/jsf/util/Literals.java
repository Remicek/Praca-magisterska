package com.przychodnia.rejestracja.jsf.util;

public abstract class Literals {
	public static final String INVALID_EMAIL = "Nie właściwy email";
	public static final String VALIDATION_ERROR = "Bład walidacji";

	//Menu
	public static final String SCHEDULE_SHOW = "Pokaż terminarz";
	public static final String APOINTMENTS_SHOW = "Wyswietl terminy";
	
	public static final String BHOURS_MENU = "bhours";
	public static final String SCHEDULE = "schedule";
	public static final String APPOINTMENT = "appointment";
	
	public static final String APPOINTMENT_MENU = "Termin";
	
	public static final Object CREATE = "Utwórz";
	
	public static final String CANCELED = "Anulowano zmiane";
	public static final String CHANGED = "Zmieniono";
	public static final String SHOW = "Wyświetl liste";
	public static final String SHOW_ALL = "Pokaż wszystkie";
	//PersonBean
	public static final String PATIENTS = "Pacjenci" ;
	public static final String PATIENT = "Pacjenta" ;
	
	//DoctorBean
	public static final String DOCTOR = "Doktora" ;
	public static final String SELECT_DOCTOR = "Wybrano doktora" ;
	public static final String UNSELECT_DOCTOR = "Odznaczono doktora" ;
	//Bhours Bean
	public static final String BHOURS = "Godziny przyjęć";
	public static final String NOT_WORKING = "Nie pracuje";
	public static final String WORKING = "Pracuje";
	public static final String BHOURS_ADD_FAILED = "Dodanie godzin pracy nie powiodło się";
	public static final String BHOURS_ADD_FAIL_REASON = "Godziny pracy lekarza koliduja z obecnymi";
	
	//MailBean
		public static final String Mail = "Wiadomość" ;
		public static final String CREATE_APPOINTMENT_MAIL = "Stworzono" ;
		public static final String UPDATE_APPOINTMENT_MAIL = "Zmodyfikowano";
		public static final String REMIND_APPOINTMENT_MAIL = "Przypomnienie";
		public static final String ADDED_TO_WORKING_VERSION = "Dodano do maili roboczych";
		public static final String ADDED_TO_SENT = "Dodano do maili wysłanych";
		public static final String SEND = "WYSŁANO";
		public static final String ADD = "Mail wymaga potwierdzenia przed wysłaniem";
		
	//Application bean
	public static final String OFFICE_HOURS_LABEL = "Godziny przyjęć";
	public static final String SCHEDULE_LABEL = "Terminarz";
	
	// AppointmentBean
	public static final String FAIL_TO_CHANGE_APPOINTMENT_DATE = "Nie mozna zmienic spotkania";
	public static final String INVALID_OFFICE_HOURS = "Spotkanie poza godzinami przyjęć lekarza:";
	public static final String APPOINTMENT_ADD_FAIL = "Spotkanie poza godzinami przyjęć lekarza lub lekarz przyjmje pacjenta w tym terminie";
	public static final String FROM_DAY = "Z dnia:";
	public static final String APPOINTMENT_ADDED = "Dodano spotkanie:";
	public static final String APPOINTMENT_CHANGED = "Zmieniono spotkanie:";
	public static final String FAIL_TO_ADD_APPOINTMENT = "Dodanie spotkania nie powiodło się";
	public static final String APPOINTMENTLABEL= "Wizyte";
	public static final String APPOINTMENT_LABEL = "Umówione wizyty";
	public static final String APPOINTMENT_MOVED = "Przesunięto spotkanie" ;
	public static final String APPOINTMENT_DELETED = "Usunięto spotkanie";
	public static final String NO_RESULTS = "Nie znaleziono wyników ";
	public static final String NO_RESULTS_FILTER = "Nie znaleziono wyników dla zadanego filtra";
	public static final String MINUTE_DELTA = " O minut: ";
	public static final String DAYS_DELTA = " O dni: ";
	
	public static final String DOCTOR_LABEL = "Doktorzy";
	public static final String PATIENT_LABEL= "Pacjenci";
	
	//Schedule bean

	//Walidatory
	public static final String EMAIL_NOT_VALID = "Nie prawidłowy adres email";
	
	//Messages 
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";
    public static final String FATAL = "FATAL";
}
