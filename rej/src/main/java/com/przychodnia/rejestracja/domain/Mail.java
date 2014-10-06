package com.przychodnia.rejestracja.domain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ManyToOne;

@Configurable
@Entity
public class Mail {

	public Mail(String subject, String body, Boolean send, Date dateMail ){
		
	}
	
	public Mail(Appointment appointment, String description){
	this.appointment=appointment;
	this.dateMail=new Date();
	this.send = false;
	this.description = description;
	}
	
	public Mail(){}
    /**
     */
    private String subject;

    /**
     */
    private String body;

    /**
     */
    private Boolean send = false;

    private String description;
    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "yyyy-MM-dd hh:mm")
    private Date dateMail;

    /**
     */
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @ManyToOne
    private Appointment appointment;

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("subject", "body", "send", "dateMail", "appointment");

	public static final EntityManager entityManager() {
        EntityManager em = new Mail().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countMails() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Mail o", Long.class).getSingleResult();
    }

	public static List<Mail> findUnsentMails() {
        return entityManager().createQuery("SELECT o FROM Mail o WHERE o.send=false", Mail.class).getResultList();
    }
	
	public static List<Mail> findSentMails() {
        return entityManager().createQuery("SELECT o FROM Mail o WHERE o.send=true", Mail.class).getResultList();
    }

	public static List<Mail> findAllMails(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Mail o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Mail.class).getResultList();
    }

	public static Mail findMail(Long id) {
        if (id == null) return null;
        return entityManager().find(Mail.class, id);
    }

	public static List<Mail> findMailEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Mail o", Mail.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Mail> findMailEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Mail o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Mail.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Mail attached = Mail.findMail(this.id);
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
    public Mail merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Mail merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getSubject() {
        return this.subject;
    }

	public void setSubject(String subject) {
        this.subject = subject;
    }

	public String getBody() {
        return this.body;
    }

	public void setBody(String body) {
        this.body = body;
    }

	public Boolean getSend() {
        return this.send;
    }

	public void setSend(Boolean send) {
        this.send = send;
    }

	public Date getDateMail() {
        return this.dateMail;
    }

	public void setDateMail(Date dateMail) {
        this.dateMail = dateMail;
    }

	public Appointment getAppointment() {
        return this.appointment;
    }

	public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}
