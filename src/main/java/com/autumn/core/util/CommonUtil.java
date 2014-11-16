package com.autumn.core.util;

import java.util.Date;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class CommonUtil {
  
  /**
   * Returns an array of two Date objects. 
   * The 1st one is the start date and the 2nd one is the end date(today).
   * They are inclusive of the number days specified.
   * 
   * @param numberOfPastWeekdays
   * @return 
   */
  public Date[] getDateRangeForNumberOfPastWeekdays(int numberOfPastWeekdays) {
//    Date endDate = new LocalDate().toDateTimeAtStartOfDay().toDate();
    
    // TODO:
    Date endDate = new LocalDate().minusDays(1).toDateTimeAtStartOfDay().toDate();  // For testing in weekends
    
    LocalDate newDate = new LocalDate();
    int i = 0;
    while (i < numberOfPastWeekdays) {
      newDate = newDate.minusDays(1);
      if (newDate.getDayOfWeek() <= DateTimeConstants.FRIDAY) {
        i++;
      }
    }
    Date startDate = newDate.toDateTimeAtStartOfDay().toDate();
    
    Date[] dateRange = new Date[]{startDate, endDate};
    return dateRange;
  }
  
}
