package com.przychodnia.rejestracja.jsf.util;
import java.util.Date;
import com.przychodnia.rejestracja.domain.Bhours;
public class TimeSpan {
    final Date startDate;
    final Date endDate;
   
    public TimeSpan(Date startDate, Date endDate){
            this.startDate = startDate;
            this.endDate = endDate;
    }
    
    public TimeSpan(Bhours bhour, Date date ){
        this.startDate = DateUtils.changeTime(date, bhour.getOpen_time());
        this.endDate = DateUtils.changeTime(date, bhour.getClose_time());
}
   
//    public boolean contain(TimeSpan timeSpan){
//            if(timeSpan.getStartDate().after(startDate)
//                            && timeSpan.getStartDate().before(endDate) &&
//                            timeSpan.getEndDate().after(startDate) &&
//                            timeSpan.getEndDate().before(endDate))
//                    return true;
//            return false;
//    }
    
    public boolean contain(TimeSpan timeSpan){
        if(DateUtils.afterInclusive(timeSpan.getStartDate(), startDate)
                        && DateUtils.beforeInclusive(timeSpan.getStartDate(), endDate) &&
                       DateUtils.afterInclusive(timeSpan.getEndDate(), startDate)&&
                      DateUtils.beforeInclusive(  timeSpan.getEndDate(), endDate))
                return true;
        return false;
}
   
    public Date getStartDate() {
            return startDate;
    }

    public Date getEndDate() {
            return endDate;
    }

   
}