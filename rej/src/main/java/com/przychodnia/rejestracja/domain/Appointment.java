package com.przychodnia.rejestracja.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import com.przychodnia.rejestracja.jsf.MyDefaultScheduleEvent;
import com.przychodnia.rejestracja.jsf.MyScheduleEvent;
import com.przychodnia.rejestracja.jsf.util.DateUtils;

@Configurable
@Entity
@OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
public class Appointment {

	/**
     */
	private String appointmentDetails;

	@ManyToOne
	private Doctor doctor;

	@ManyToOne
	private Person patient;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "yyyy-MM-dd hh:mm")
	private java.util.Date appointmentStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "yyyy-MM-dd hh:mm")
	private java.util.Date appointmentEndtDate;

	public Appointment(MyScheduleEvent event) {
		super();
		this.appointmentDetails = event.getTitle();
		this.doctor = event.getDoctor();
		this.patient = event.getPatient();
		this.appointmentStartDate = event.getStartDate();
		this.appointmentEndtDate = event.getEndDate();

		// TODO przeniesc do util
		if (event.getStartTime() != null && event.getEndTime() != null) {
			this.appointmentStartDate = DateUtils.changeHourAndMinute(
					appointmentStartDate, event.getStartTime());
			this.appointmentEndtDate = DateUtils.changeHourAndMinute(
					appointmentEndtDate, event.getEndTime());
		}

		if (event.getIdAppointment() > 0)
			this.id = event.getIdAppointment();

	}

	public Appointment() {
	};

	public String getAppointmentDetails() {
		return this.appointmentDetails;
	}

	public void setAppointmentDetails(String appointmentDetails) {
		this.appointmentDetails = appointmentDetails;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Person getPatient() {
		return this.patient;
	}

	public void setPatient(Person patient) {
		this.patient = patient;
	}

	public Date getAppointmentStartDate() {
		return this.appointmentStartDate;
	}

	public void setAppointmentStartDate(Date appointmentStartDate) {
		this.appointmentStartDate = appointmentStartDate;
	}

	// TODO zmienic nazwe gettera na prawidlowa
	public Date getAppointmentEndtDate() {
		return this.appointmentEndtDate;
	}

	public void setAppointmentEndtDate(Date appointmentEndtDate) {
		this.appointmentEndtDate = appointmentEndtDate;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("appointmentDetails", "doctor", "patient",
					"appointmentStartDate", "appointmentEndtDate");

	public static final EntityManager entityManager() {
		EntityManager em = new Appointment().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countAppointments() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM Appointment o", Long.class)
				.getSingleResult();
	}

	public static Boolean isDoctorSaveToDelete(Doctor doctor) {
		String query = "SELECT o FROM Appointment o WHERE (:doctor = doctor) ";
		EntityManager em = Appointment.entityManager();

		if (doctor != null) {
			TypedQuery<Appointment> q = em
					.createQuery(query, Appointment.class);
			q.setParameter("doctor", doctor);

			return q.getResultList().isEmpty();
		}
		return true;
	}

	public static List<Appointment> findAllAppointments() {
		return entityManager().createQuery("SELECT o FROM Appointment o",
				Appointment.class).getResultList();
	}

	public static List<Appointment> findAllAppointments(String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM Appointment o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Appointment.class)
				.getResultList();
	}

	public static Appointment findAppointment(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Appointment.class, id);
	}

	public static List<Appointment> findAppointmentEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM Appointment o", Appointment.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<Appointment> findAppointmentEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Appointment o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Appointment.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	// TODO Poprawic
	public boolean isDayCorrect() {
		return isDoctorWorking() && !isDoctorBusy();
	}

	// TODO Poprawic
	public boolean isDoctorWorking() {
		int selectedDayOfWeek = this.getAppointmentStartDate().getDay();
		Date startDate = this.getAppointmentStartDate();
		Date endDate = this.getAppointmentEndtDate();
		Boolean flag = false;
		List<Bhours> officeHours = Bhours
				.findBhoursesByDoctor(this.getDoctor());
		// Ten sam dzien tygodnia AND czas poczatku spotkania > czasu otwarcia
		// AND czas zamkniecia > od czas konca spotkania
		for (Bhours officeHour : officeHours) {
			if (officeHour.getWeekDay() == selectedDayOfWeek
					&& DateUtils.compareTimes(startDate,
							officeHour.getOpen_time())
					&& DateUtils.compareTimes(officeHour.getClose_time(),
							endDate))
				return true;
		}
		return false;
	}

	public boolean isDoctorBusy() {
		Doctor doctor = this.getDoctor();
		Date appointmentStartDate = this.getAppointmentStartDate();
		Date appointmentEndDate = this.getAppointmentEndtDate();
		String query = "SELECT o FROM Appointment AS o WHERE (:doctor = doctor) AND "
				+ "(((:startDate >  o.appointmentStartDate AND :startDate < o.appointmentEndtDate) OR "
				+ "(:endDate >  o.appointmentStartDate AND :endDate < o.appointmentEndtDate))"
				+ "OR (( o.appointmentStartDate >  :startDate AND o.appointmentStartDate  < :endDate) OR "
				+ "( o.appointmentEndtDate >  :startDate AND o.appointmentEndtDate < :endDate)))";
		EntityManager em = Appointment.entityManager();
		if (id != null)
			query += " AND " + "(o.id != :id)";
		TypedQuery<Appointment> q = em.createQuery(query, Appointment.class);

		if (doctor != null)
			q.setParameter("doctor", doctor);
		if (appointmentStartDate != null)
			q.setParameter("startDate", appointmentStartDate);
		if (appointmentStartDate != null)
			q.setParameter("endDate", appointmentEndDate);
		if (id != null) {
			q.setParameter("id", this.id);
		}

		return !q.getResultList().isEmpty();
	}

	public static List<Appointment> findAppointmentForFilter(
			Appointment appointment) {
		Date minAppointmentStartDate = appointment.getAppointmentStartDate();
		Date maxAppointmentStartDate = appointment.getAppointmentEndtDate();
		String appointmentDetails = appointment.getAppointmentDetails();
		Person patient = appointment.getPatient();
		Doctor doctor = appointment.getDoctor();

		String query = "SELECT o FROM Appointment AS o";
		Boolean first = true;
		if (appointmentDetails != null && appointmentDetails.length() > 0) {
			appointmentDetails = appointmentDetails.replace('*', '%');
			if (appointmentDetails.charAt(0) != '%') {
				appointmentDetails = "%" + appointmentDetails;
			}
			if (appointmentDetails.charAt(appointmentDetails.length() - 1) != '%') {
				appointmentDetails = appointmentDetails + "%";
			}
			query += (first ? " WHERE " : " AND ");
			query += "LOWER(o.appointmentDetails) LIKE LOWER(:appointmentDetails)";
			first = false;
		}
		if (minAppointmentStartDate != null && maxAppointmentStartDate == null) {
			query += (first ? " WHERE " : " AND ");
			query += "o.appointmentStartDate >= :minAppointmentStartDate";
			first = false;
		}

		if (minAppointmentStartDate == null && maxAppointmentStartDate != null) {
			query += (first ? " WHERE " : " AND ");
			query += "o.appointmentStartDate <= :maxAppointmentStartDate";
			first = false;
		}

		if (minAppointmentStartDate != null && maxAppointmentStartDate != null) {
			query += (first ? " WHERE " : " AND ");
			query += "o.appointmentStartDate BETWEEN :minAppointmentStartDate AND :maxAppointmentStartDate ";
			first = false;
		}

		if (patient != null) {
			query += (first ? " WHERE " : " AND ");
			query += "o.patient = :patient ";
			first = false;
		}
		if (doctor != null) {
			query += (first ? " WHERE " : " AND ");
			query += "o.doctor = :doctor";
			first = false;
		}

		EntityManager em = Appointment.entityManager();
		TypedQuery<Appointment> q = em.createQuery(query, Appointment.class);

		if (minAppointmentStartDate == null && maxAppointmentStartDate != null) {
			q.setParameter("maxAppointmentStartDate", maxAppointmentStartDate);
		}
		if (minAppointmentStartDate != null && maxAppointmentStartDate == null) {
			q.setParameter("minAppointmentStartDate", minAppointmentStartDate);
		}
		if (minAppointmentStartDate != null && maxAppointmentStartDate != null) {
			q.setParameter("minAppointmentStartDate", minAppointmentStartDate);
			q.setParameter("maxAppointmentStartDate", maxAppointmentStartDate);
		}
		if (appointmentDetails != null && appointmentDetails.length() > 0)
			q.setParameter("appointmentDetails", appointmentDetails);
		if (patient != null)
			q.setParameter("patient", patient);
		if (doctor != null)
			q.setParameter("doctor", doctor);
		return q.getResultList();

	}

	// Custom finders
	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			Appointment attached = Appointment.findAppointment(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public Appointment merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Appointment merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String toStringAppointment() {
		return getDoctor().getSurname() + "\n" + getPatient().getSurname()
				+ "\n" + getAppointmentStartDate();
	}
	
	

}
