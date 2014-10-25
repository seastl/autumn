package com.autumn.core.util;

import java.util.Date;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class Scheduler {
  
  @Scheduled(cron = "*/5 * * * * ?")
  public void executeEvery5Secs() {
    System.out.println("Executed. Time=" + new Date() + ".");
  }
  
}
