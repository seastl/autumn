package com.autumn.app;

import com.autumn.configuration.AutumnConfiguration;
import com.autumn.core.service.SecurityService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Monitor {
  
  public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(AutumnConfiguration.class);
    SecurityService securityService = ctx.getBean(SecurityService.class);
    securityService.check();
  }
  
}
