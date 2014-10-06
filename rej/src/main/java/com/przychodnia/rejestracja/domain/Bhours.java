package com.przychodnia.rejestracja.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import com.przychodnia.rejestracja.jsf.util.Day;
import com.przychodnia.rejestracja.jsf.util.DateUtils;

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

@Configurable
@Entity
public class Bhours {

	private Boolean working;
	/**
     */
	private int weekDay;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "hh:mm")
	private java.util.Date open_time;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "hh:mm")
	private java.util.Date close_time;

	/**
     */
	// @Cascade(value = CascadeType.ALL )
	@ManyToOne()
	private Doctor doctor;

	public Bhours() {
		this.open_time = DateUtils.getInitialDate();
		this.close_time = DateUtils.getInitialDate();
		this.open_time.setHours(8);
		this.close_time.setHours(16);
	}

	// TODO Poprawic string i int
	public String toString() {
		String str = "";
		str += DateUtils.getWeekDayForDisplay(Integer.toString(getWeekDay()))
				+ " " + DateUtils.getTimeOnlyAsString(getOpen_time()) + "-"
				+ DateUtils.getTimeOnlyAsString(getClose_time());
		return str;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("weekDay", "open_time", "close_time", "doctor");

	public static final EntityManager entityManager() {
		EntityManager em = new Bhours().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countBhourses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Bhours o",
				Long.class).getSingleResult();
	}

	public static List<Bhours> findAllBhourses() {
		return entityManager().createQuery("SELECT o FROM Bhours o",
				Bhours.class).getResultList();
	}

	public static List<Bhours> findAllBhourses(String sortFieldName,
			String sortOrder) {
		String jpaQuery = "SELECT o FROM Bhours o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Bhours.class)
				.getResultList();
	}

	public static Bhours findBhours(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Bhours.class, id);
	}

	public boolean isBhoursTaken() {
		Date startTime = this.getOpen_time();
		Date endTime = this.getClose_time();
		Doctor doctor = this.getDoctor();
		int weekDay = this.getWeekDay();
		Long id = this.getId();
		String query = "SELECT o FROM Bhours AS o WHERE (:weekDay = o.weekDay) AND (:doctor = o.doctor)";

		if (id != null)
			query += " AND  (o.id != :id)";
		query += " AND"
				+ "(((:startTime BETWEEN  o.open_time AND o.close_time) OR "
				+ "(:endTime BETWEEN o.open_time AND o.close_time )) OR (( o.open_time BETWEEN  :startTime AND :endTime) OR "
				+ "( o.close_time BETWEEN  :startTime AND :endTime)))";
		EntityManager em = Appointment.entityManager();
		TypedQuery<Bhours> q = em.createQuery(query, Bhours.class);

		if (id != null)
			q.setParameter("id", id);
		if (startTime != null)
			q.setParameter("startTime", startTime);
		if (endTime != null)
			q.setParameter("endTime", endTime);
		if (doctor != null)
			q.setParameter("doctor", doctor);
		q.setParameter("weekDay", weekDay);

		return !q.getResultList().isEmpty();
	}

	public static List<Bhours> findBhoursesByDoctor(Doctor doctor) {
		if (doctor == null)
			throw new IllegalArgumentException(
					"The doctor argument is required");
		EntityManager em = Bhours.entityManager();
		TypedQuery<Bhours> q = em
				.createQuery(
						"SELECT o FROM Bhours AS o WHERE o.doctor = :doctor ORDER BY o.weekDay ASC",
						Bhours.class);
		q.setParameter("doctor", doctor);
		return q.getResultList();
	}

	public static Long countFindBhoursesByDoctor(Doctor doctor) {
		if (doctor == null)
			throw new IllegalArgumentException(
					"The doctor argument is required");
		EntityManager em = Bhours.entityManager();
		TypedQuery<Long> q = em.createQuery(
				"SELECT COUNT(o) FROM Bhours AS o WHERE o.doctor = :doctor",
				Long.class);
		q.setParameter("doctor", doctor);
		return ((Long) q.getSingleResult());
	}

	public static List<Bhours> findBhoursEntries(int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM Bhours o", Bhours.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static List<Bhours> findBhoursEntries(int firstResult,
			int maxResults, String sortFieldName, String sortOrder) {
		String jpaQuery = "SELECT o FROM Bhours o";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		return entityManager().createQuery(jpaQuery, Bhours.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public Long countFindBhoursesByWeekDay(int weekDay) {
		EntityManager em = Bhours.entityManager();
		TypedQuery q = em.createQuery(
				"SELECT COUNT(o) FROM Bhours AS o WHERE o.weekDay = :weekDay",
				Long.class);
		q.setParameter("weekDay", weekDay);
		return ((Long) q.getSingleResult());
	}

	public static List<Bhours> findBhoursesByWeekDayAndDoctor(int weekDay,
			Doctor doctor) {
		EntityManager em = Bhours.entityManager();
		TypedQuery<Bhours> q = em
				.createQuery(
						"SELECT o FROM Bhours AS o WHERE o.weekDay = :weekDay AND o.doctor = :doctor",
						Bhours.class);
		q.setParameter("weekDay", weekDay);
		q.setParameter("doctor", doctor);
		return q.getResultList();
	}

	public TypedQuery<Bhours> findBhoursesByWeekDay(int weekDay,
			String sortFieldName, String sortOrder) {
		EntityManager em = Bhours.entityManager();
		String jpaQuery = "SELECT o FROM Bhours AS o WHERE o.weekDay = :weekDay";
		if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
			jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
			if ("ASC".equalsIgnoreCase(sortOrder)
					|| "DESC".equalsIgnoreCase(sortOrder)) {
				jpaQuery = jpaQuery + " " + sortOrder;
			}
		}
		TypedQuery<Bhours> q = em.createQuery(jpaQuery, Bhours.class);
		q.setParameter("weekDay", weekDay);
		return q;
	}

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
			Bhours attached = Bhours.findBhours(this.id);
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
	public Bhours merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Bhours merged = this.entityManager.merge(this);
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

	public int getWeekDay() {
		return this.weekDay;
	}

	public void setWeekDay(int weekDay) {
		this.weekDay = weekDay;
	}

	public Date getOpen_time() {
		return this.open_time;
	}

	public void setOpen_time(Date open_time) {
		this.open_time = open_time;
	}

	public Date getClose_time() {
		return this.close_time;
	}

	public void setClose_time(Date close_time) {
		this.close_time = close_time;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Boolean getWorking() {
		return working;
	}

	public void setWorking(Boolean working) {
		this.working = working;
	}
}
