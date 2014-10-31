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
    //System.out.println("Execute every 5sec. Time=" + new Date() + ".");
    securityService.checkForStartOfDay();
  }
  
  //@Scheduled(cron = "*/1 * * * * ?")
  public void executeEvery1Sec() {
    System.out.println("Execute every sec. Time=" + new Date() + ".");
  }

  
  /**
   * Executes Mon-Fri @6:35PM
   */
  //@Scheduled(cron = "0 35 6 * * MON-FRI")
  public void executeForStartOfDay() {
  }


  /**
   * Executes Mon-Fri @1:05PM
   */
  //@Scheduled(cron = "0 5 13 * * MON-FRI")
  public void executeForEndOfDay() {
  }


  /**
   * Executes hourly Mon-Fri
   */
  //@Scheduled(cron = "0 0 */1 * * MON-FRI")
  public void executeHourly() {
  }

}
