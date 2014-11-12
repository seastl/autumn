package com.autumn.app;

import com.autumn.configuration.AutumnConfiguration;
import com.autumn.core.service.SecurityService;
import com.autumn.core.util.EmailUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Monitor {
  
  public static void main(String[] args) throws Exception {
    boolean sendEmail = false;
    
    if (args.length == 0) {
      sendEmail = false;
    } else if (args.length == 3 && !args[0].isEmpty() && !args[1].isEmpty() && !args[2].isEmpty()) {
      sendEmail = true;
    } else {
      System.out.println("Usage: java Monitor <emailUser> <emailPassword> <toEmail>");
      System.exit(0);
    }
    
    ApplicationContext ctx = new AnnotationConfigApplicationContext(AutumnConfiguration.class);

    if (sendEmail) {
      EmailUtil emailUtil = ctx.getBean(EmailUtil.class);
      emailUtil.setGoogleUserName(args[0]);
      emailUtil.setGooglePassword(args[1]);
      emailUtil.setToEmail(args[2]);
    }
    
    // The following should be moved to SecurityServiceImpl later.
    SecurityService securityService = ctx.getBean(SecurityService.class);
    securityService.setSendEmail(sendEmail);
    //securityService.check();
    securityService.checkForEndOfDay();

  }
  
}
