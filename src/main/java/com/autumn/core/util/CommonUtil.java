package com.autumn.core.util;

import java.util.Date;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class CommonUtil {
  
  /**
   * Returns an array of two Date objects. 
   * The 1st one is the start date that is # days before the end date.
   * The 2nd one is the end date(today- 1weekday).
   * They are inclusive of the number days specified.
   * 
   * @param numberOfPastWeekdays
   * @return 
   */
  public Date[] getDateRangeForNumberOfPastWeekdays(int numberOfPastWeekdays) {
    Date startDate = null;
    Date endDate = null;
    
    LocalDate newDate = new LocalDate();
    int i = 0;
    while (i < numberOfPastWeekdays + 1) {
      newDate = newDate.minusDays(1);
      if (newDate.getDayOfWeek() <= DateTimeConstants.FRIDAY) {
        i++;
      }
      if (i == 1) {
        endDate = newDate.toDateTimeAtStartOfDay().toDate();
      }
    }
    startDate = newDate.toDateTimeAtStartOfDay().toDate();
    
    Date[] dateRange = new Date[]{startDate, endDate};
    return dateRange;
  }
  

  /**
   * 
   * @param pastPeriod e.g. 1d, 10d, 2m, 1y
   * @return 
   */
  public Date[] getDateRangeForPeriod(String pastPeriod) {
    String timeUnit = pastPeriod.substring(pastPeriod.length()-1);
    int timeValue = Integer.parseInt(pastPeriod.substring(0, pastPeriod.length()-1));
            
    if (!pastPeriod.endsWith("d") && !pastPeriod.endsWith("w") && !pastPeriod.endsWith("m") && !pastPeriod.endsWith("y")) {
      throw new RuntimeException("Past period must end with d, m, y.");
    }
    
    LocalDate newDate = new LocalDate();
    
    // Determines endDate
    while (newDate.getDayOfWeek() > DateTimeConstants.FRIDAY) {
      newDate = newDate.minusDays(1);
    }
    Date endDate = newDate.toDateTimeAtStartOfDay().toDate();
    
    // Determines startDate
    if (timeUnit.equals("d")) {
      newDate = newDate.minusDays(timeValue);
    } else if (timeUnit.equals("w")) {
      newDate = newDate.minusWeeks(timeValue);
    } else if (timeUnit.equals("m")) {
      newDate = newDate.minusMonths(timeValue);
    } else if (timeUnit.equals("y")) {
      newDate = newDate.minusYears(timeValue);
    }

    // Move backward to a weekday
    while (newDate.getDayOfWeek() > DateTimeConstants.FRIDAY) {
      newDate = newDate.minusDays(1);
    }
    Date startDate = newDate.toDateTimeAtStartOfDay().toDate();
    
    Date[] dateRange = new Date[]{startDate, endDate};
    return dateRange;
  }
  
}
