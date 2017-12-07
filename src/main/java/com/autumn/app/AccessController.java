package com.autumn.app;

import com.autumn.core.service.SecurityService;
import com.autumn.test.JsoupTest;
import com.autumn.test.SeleniumWebDriverTest;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccessController {

  private static final Logger logger = LoggerFactory.getLogger(AccessController.class);

  @Autowired
  private JsoupTest jsoupTest;

  @Autowired
  private SeleniumWebDriverTest seleniumWebDriverTest;

  @Autowired
  private SecurityService securityService;
          
  @RequestMapping("/openNE")
  public String checkForOpenNoEmail(Map<String, Object> model) {
    securityService.setSendEmail(false);
    securityService.checkForDailyOpen();

    model.put("message", "checkForOpen finished.");
    return "welcome";
  }


  @RequestMapping("/j")
  public String testJsoup(Map<String, Object> model) {

    jsoupTest.simpleParse();

    //logger.debug("Welcome {}, {}, {}", app, global, microsoftShipment);

    model.put("message", "Js test");
    return "welcome";
  }


  @RequestMapping("/s")
  public String testSelenium(Map<String, Object> model) {

    seleniumWebDriverTest.simpleParse();

    //logger.debug("Welcome {}, {}, {}", app, global, microsoftShipment);

    model.put("message", "Selenium test");
    return "welcome";
  }
  
}
