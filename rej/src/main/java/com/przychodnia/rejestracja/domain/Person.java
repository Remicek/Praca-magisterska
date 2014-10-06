package com.przychodnia.rejestracja.domain;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ManyToOne;

@Entity
@Configurable
public class Person {

    /**
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String firstName;

     /**
     */
    @NotNull
    @Size(min = 3, max = 50)
    private String surname;

    @NotNull
    @Size(min = 3, max = 50)
    private String fatherName;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private java.util.Date birthDate;

    @NotNull
    @Size(min = 10, max = 12)
    private String pesel;

    /**
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String maidenSurname;

    /**
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String cardNumber;

    /**
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String nfz;

    /**
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String legitimationID;

    /**
     */
    @NotNull
    @Size(min = 3, max = 40)
    private String address;

    /**
     */
    @NotNull
    @Size(min = 3, max = 20)
    private String telephoneNumber;

    /**
     */
    @Email
    private String mail;

    /**
     */
    @ManyToOne
    private Doctor attendingPhysician;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	public String toStringNameAndSurname() {
        return getFirstName() + " " + getSurname();
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("firstName", "surname", "fatherName", "birthDate", "pesel", "maidenSurname", "cardNumber", "nfz", "legitimationID", "address", "telephoneNumber", "mail", "attendingPhysician");

	public static final EntityManager entityManager() {
        EntityManager em = new Person().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPeople() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Person o", Long.class).getSingleResult();
    }

	public static List<Person> findAllPeople() {
        return entityManager().createQuery("SELECT o FROM Person o", Person.class).getResultList();
    }

	public static List<Person> findAllPeople(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Person o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Person.class).getResultList();
    }

	public static Person findPerson(Long id) {
        if (id == null) return null;
        return entityManager().find(Person.class, id);
    }
	public static Boolean isDoctorSaveToDelete(Doctor doctor) {
		String query = "SELECT o FROM Person o WHERE (:attendingPhysician = doctor) ";
		EntityManager em = Appointment.entityManager();

		if (doctor != null) {
			TypedQuery<Appointment> q = em
					.createQuery(query, Appointment.class);
			q.setParameter("doctor", doctor);
			return q.getResultList().isEmpty();
		}
		return true;
	}
	
	public static List<Person> findPersonEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Person o", Person.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Person> findPersonEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Person o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Person.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Person attached = Person.findPerson(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Person merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Person merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getFirstName() {
        return this.firstName;
    }

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	public String getSurname() {
        return this.surname;
    }

	public void setSurname(String surname) {
        this.surname = surname;
    }

	public String getFatherName() {
        return this.fatherName;
    }

	public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

	public Date getBirthDate() {
        return this.birthDate;
    }

	public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

	public String getPesel() {
        return this.pesel;
    }

	public void setPesel(String pesel) {
        this.pesel = pesel;
    }

	public String getMaidenSurname() {
        return this.maidenSurname;
    }

	public void setMaidenSurname(String maidenSurname) {
        this.maidenSurname = maidenSurname;
    }

	public String getCardNumber() {
        return this.cardNumber;
    }

	public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

	public String getNfz() {
        return this.nfz;
    }

	public void setNfz(String nfz) {
        this.nfz = nfz;
    }

	public String getLegitimationID() {
        return this.legitimationID;
    }

	public void setLegitimationID(String legitimationID) {
        this.legitimationID = legitimationID;
    }

	public String getAddress() {
        return this.address;
    }

	public void setAddress(String address) {
        this.address = address;
    }

	public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

	public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

	public String getMail() {
        return this.mail;
    }

	public void setMail(String mail) {
        this.mail = mail;
    }

	public Doctor getAttendingPhysician() {
        return this.attendingPhysician;
    }

	public void setAttendingPhysician(Doctor attendingPhysician) {
        this.attendingPhysician = attendingPhysician;
    }
}
