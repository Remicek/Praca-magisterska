package com.przychodnia.rejestracja.jsf.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.przychodnia.rejestracja.domain.Appointment;
import com.przychodnia.rejestracja.domain.Bhours;
import com.przychodnia.rejestracja.domain.Doctor;
public class TimeSpanTest {
         
    public static void show (List <TimeSpan> timeSpanList){
    	for (TimeSpan ts :timeSpanList)
    		System.out.println("Stat Date:  " + ts.getStartDate().toString() + "  End Date:  " + ts.getEndDate().toString());
    		System.out.println();
    }
    
    //TODO currentAppointment przechowuje dane o wyborze filtrowania
    public static List<TimeSpan> getAvailableDates(Appointment currentAppointment){
       	Date from = currentAppointment.getAppointmentStartDate();
    	Date to = currentAppointment.getAppointmentEndtDate();
    	Doctor doctor = currentAppointment.getDoctor();
    	List<Appointment> appointmentList = Appointment.findAppointmentForFilter(currentAppointment);
    			
    	List<TimeSpan> initialTimeSpanList = createTimeSpans(from,to,doctor);
    	List<TimeSpan> finalTimeSpanList = initialTimeSpanList;
    	for (Appointment appointment : appointmentList){
    		finalTimeSpanList = subtract(finalTimeSpanList,appointment);
    	}
    	show(finalTimeSpanList);
		return finalTimeSpanList;
    }
    
    public static List<TimeSpan> createTimeSpans(Date from, Date to, Doctor doctor){
    	List<Date> daysList = DateUtils.getDaysBetween(from, to);
    	List <TimeSpan> timeSpanList = new ArrayList<>();
    	
    	for (Date day: daysList){
    		List<Bhours> bhoursList = DateUtils.getBHoursByDateAndDoctor(day,doctor);
    		if(bhoursList!=null)
    		for (Bhours bhour : bhoursList){
    			timeSpanList.add(new TimeSpan(bhour,day));
    		}
    	}
//    	show(timeSpanList);
    	return timeSpanList;
    }
    
        public static List<TimeSpan> subtract(List<TimeSpan> times,Appointment appointment){
        	TimeSpan appointmentTimeSlot = appointmentToTimeSlot(appointment);
                List<TimeSpan> newTimes = new ArrayList<>();;
                
                for(TimeSpan time : times){
                        if(!time.contain(appointmentTimeSlot) && !time.getStartDate().equals(time.getEndDate())){
                                newTimes.add(time);
                        }
                        else{
                                TimeSpan alfa = new TimeSpan(time.getStartDate(), appointmentTimeSlot.getStartDate());
                                TimeSpan beta = new TimeSpan(appointmentTimeSlot.getEndDate(), time.getEndDate());
                                if(!(alfa.getStartDate().equals(alfa.getEndDate())))
                                newTimes.add(alfa);
                                if(beta.getStartDate().getTime()!=beta.getEndDate().getTime())
                                newTimes.add(beta);
                        }
                }
//                show(newTimes);
                return newTimes;
        }
        
        public static TimeSpan appointmentToTimeSlot(Appointment appointment){
        return new TimeSpan(appointment.getAppointmentStartDate(),appointment.getAppointmentEndtDate());
        }
        
}