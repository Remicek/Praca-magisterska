package com.przychodnia.rejestracja.jsf.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.przychodnia.rejestracja.domain.Bhours;
import com.przychodnia.rejestracja.domain.Doctor;

//TODO do sprawdzenia
public class DateUtils {
	static Locale localeObject=new Locale("pl"); 
	public Date getRandomDate(Date base) {
	        Calendar date = Calendar.getInstance();
	        date.setTime(base);
	        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
	        return date.getTime();
	    }
	     
	  public static Date getInitialDate() {
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
	         
	        return calendar.getTime();
	    }
	    
	    public static Calendar today() {
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
	 
	        return calendar;
	    }
	    
	    public static int getNumberOfDaysBetweenDates(Date dateStart, Date dateEnd){
		return (int) Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
	    }
	    
	    public static Date changeTime(Date date, Date time){
		Date dateTime = new Date(0,0,0,0,0,0);
		dateTime.setYear(date.getYear());
		dateTime.setMonth(date.getMonth());
		dateTime.setDate(date.getDate());
		dateTime.setHours(time.getHours());
		dateTime.setMinutes(time.getMinutes());
		dateTime.setSeconds(0);
		 return dateTime;
	    }
	    
	    public static Date changeHourAndMinute(Date date, Date time){
	    	Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY,time.getHours());
			cal.set(Calendar.MINUTE,time.getMinutes());
			date = cal.getTime();
		return date;
	    }
	    
	    public static List<Bhours> getBHoursByDateAndDoctor(Date date,Doctor doctor){
	    	List<Bhours> bhours = Bhours.findBhoursesByWeekDayAndDoctor(DateUtils.getDayOfWeekNumber(date), doctor);
			return bhours;
	    }
	    
	    public static List<Date> getDaysBetween(Date startDate, Date endDate){
			int numberOfDays = getNumberOfDaysBetweenDates(startDate,endDate);
			List<Date> dates = new ArrayList<Date>();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			//W przypadku gdy przedzial jest w jednym dniu
			if (numberOfDays==0){
				 dates.add(cal.getTime());
				 return dates;
			}
				
			while (cal.getTime().before(endDate)) {
			    dates.add(cal.getTime());
			    cal.add(Calendar.DATE, 1);
			}
			return dates;
		}
		
	    public static int getDayOfWeekNumber(Date date){
	    	Calendar c = Calendar.getInstance();
	    	c.setTime(date);
	    	int dayOfWeek = (c.get(Calendar.DAY_OF_WEEK) - 1); //0 dla niedzieli;
	    	return dayOfWeek;
	    }
	    
	    //TODO Poprawic na prostsze
		public static Date getTimeOnly(Date hour){
		        Date finalDate = new Date();
		         
		         Calendar calendarDateRef=Calendar.getInstance();
		         calendarDateRef.setTimeInMillis(0);
		         
		         Calendar calendarDateHour=Calendar.getInstance();
		         calendarDateHour.setTime(hour); 
		  
		        calendarDateRef.set(Calendar.HOUR_OF_DAY,calendarDateHour.get(Calendar.HOUR_OF_DAY));
		        calendarDateRef.set(Calendar.MINUTE,calendarDateHour.get(Calendar.MINUTE));
				return finalDate;
		}
		
		public static boolean compareTimes(Date d1,Date d2) {
            if (d1.getHours() < d2.getHours()) {
                return false;
            }
            if (d1.getHours() >= d2.getHours()) {
                return true;

            } else {
                return (d1.getMinutes() >= d2.getMinutes());
            }
};
		
public static boolean afterInclusive(Date d1, Date d2){
		if (d1.getTime()>=d2.getTime())
			return true;
		return false;
			
	    }

public static boolean beforeInclusive(Date d1, Date d2){
	if (d1.getTime()<=d2.getTime())
		return true;
	return false;
		
    }

	    public static String getTimeOnlyAsString(Date dateInput){
	    	
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			   String shortTimeStr = sdf.format(dateInput);
			   System.out.println(shortTimeStr);
			return shortTimeStr;
	    }
	    
	    public static String formatDate(Date dateInput){
	    	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEEE: dd-MM-yyyy", localeObject);
	        String date = DATE_FORMAT.format(dateInput);
	        System.out.println("Today in dd-MM-yyyy format : " + date);
			return date;
	    }
       
		
		public static String getWeekDayForDisplay(String day) {
	        String dayName = null;
			switch (day){
			default:
				dayName="Eror";
				break;
			case "0":
				dayName=Day.SUNDAY.getLocal();
				break;
			case "1":
				dayName=Day.MONDAY.getLocal();
				break;
			case "2":
				dayName=Day.TUESDAY.getLocal();
				break;
			case "3":
				dayName=Day.WEDNESDAY.getLocal();
				break;
			case "4":
				dayName =Day.THURSDAY.getLocal();
				break;
			case "5":
				dayName=Day.FRIDAY.getLocal();
				break;
			case "6":
				dayName=Day.SATURDAY.getLocal();
				break;
				
			}
			return dayName;
	    }
}

