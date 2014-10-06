package com.przychodnia.rejestracja.integration;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;

import com.przychodnia.rejestracja.domain.Appointment;
import com.przychodnia.rejestracja.domain.Mail;

public interface EmailManager {
void sendMail(List<Mail> messages);
void sendMail(Mail message);
void sendAllMails();
void addCreateAppointmentMail(Appointment appointment);
void addUpdateAppointmentMail(Appointment appointment);
void addRemindAppointmentMail(Appointment appointment);
void sendAllMails(List<Mail> messages);
}
