package com.autumn.core.util;

import com.autumn.core.service.SecurityService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class Scheduler {
  private SecurityService securityService;

  @Autowired
  public void setSecurityService(SecurityService securityService) {
    this.securityService = securityService;
  }

  
  //@Scheduled(cron = "*/5 * * * * ?")
  public void executeEvery5Secs() {
    securityService.checkForDailyOpen();
  }
  
  //@Scheduled(cron = "*/1 * * * * ?")
  public void executeEvery1Sec() {
    System.out.println("Execute every sec. Time=" + new Date() + ".");
  }

  
  /**
   * Executes Mon-Fri @6:35AM
   */
  @Scheduled(cron = "0 35 6 * * MON-FRI")
  public void executeForDailyOpen() {
    securityService.checkForDailyOpen();
  }


  /**
   * Executes Mon-Fri @1:05PM
   */
  @Scheduled(cron = "0 5 13 * * MON-FRI")
  public void executeForDailyCLose() {
    securityService.checkForDailyClose();
  }


  /**
   * Executes Mon-Fri @4:05PM
   */
  @Scheduled(cron = "0 5 16 * * MON-FRI")
  public void executeForEndOfDay() {
    securityService.checkForEndOfDay();
  }


  /**
   * Executes every hour at the 5th minute between 7AM and 1PM Mon-Fri
   */
  @Scheduled(cron = "0 5 7-13 * * MON-FRI")
  public void executeHourly() {
    securityService.checkForDailyOpen();
  }

}
