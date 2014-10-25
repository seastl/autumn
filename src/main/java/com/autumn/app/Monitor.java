package com.autumn.app;

import com.autumn.configuration.AutumnConfiguration;
import com.autumn.core.service.SecurityService;
import com.autumn.core.util.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Monitor {
  
  public static void main(String[] args) throws Exception {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(AutumnConfiguration.class);

    SecurityService securityService = ctx.getBean(SecurityService.class);
    securityService.check();
    
    Scheduler scheduler = ctx.getBean(Scheduler.class);
    scheduler.executeEvery5Secs();
  }
  
}
