package com.autumn.app;

import com.autumn.core.dao.impl.AlphaVantageQuoteDaoImpl;
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
  private AlphaVantageQuoteDaoImpl avQuoatDao;
          
  @Autowired
  private SecurityService securityService;
          
  @RequestMapping("/mo")
  public String checkForMarketOpen(Map<String, Object> model) {
    securityService.checkForDailyOpen();
    model.put("message", "mo request finished.");
    return "welcome";
  }


  @RequestMapping("/mone")
  public String checkForMarketOpenNoEmail(Map<String, Object> model) {
    securityService.setSendEmail(false);
    securityService.checkForDailyOpen();
    model.put("message", "mone request finished.");
    return "welcome";
  }


  @RequestMapping("/mc")
  public String checkForMarketClose(Map<String, Object> model) {
    securityService.checkForDailyClose();
    model.put("message", "mc request finished.");
    return "welcome";
  }


  @RequestMapping("/mcne")
  public String checkForMarketCloseNoEmail(Map<String, Object> model) {
    securityService.setSendEmail(false);
    securityService.checkForDailyClose();
    model.put("message", "mcne request finished.");
    return "welcome";
  }


  @RequestMapping("/id")
  public String checkForIntraDay(Map<String, Object> model) {
    securityService.checkForIntraDay();
    model.put("message", "id request finished.");
    return "welcome";
  }


  @RequestMapping("/idne")
  public String checkForIntraDayNoEmail(Map<String, Object> model) {
    securityService.setSendEmail(false);
    securityService.checkForIntraDay();
    model.put("message", "idne request finished.");
    return "welcome";
  }


  @RequestMapping("/ed")
  public String checkForEndDay(Map<String, Object> model) {
    securityService.checkForEndOfDay();
    model.put("message", "ed request finished.");
    return "welcome";
  }


  @RequestMapping("/edne")
  public String checkForEndDayNoEmail(Map<String, Object> model) {
    securityService.setSendEmail(false);
    securityService.checkForEndOfDay();
    model.put("message", "edne request finished.");
    return "welcome";
  }


  @RequestMapping("/j")
  public String testJsoup(Map<String, Object> model) {

    //jsoupTest.simpleParse();
    jsoupTest.testParseLiveQuote();

    //logger.debug("Welcome {}, {}, {}", app, global, microsoftShipment);

    model.put("message", "Js test executed");
    return "welcome";
  }


  @RequestMapping("/s")
  public String testSelenium(Map<String, Object> model) {

    seleniumWebDriverTest.simpleParse();

    //logger.debug("Welcome {}, {}, {}", app, global, microsoftShipment);

    model.put("message", "Selenium test executed");
    return "welcome";
  }


  @RequestMapping("/av")
  public String testAlphaVantage(Map<String, Object> model) {

    avQuoatDao.getHisoricalQuotes("Test");
            
    //logger.debug("Welcome {}, {}, {}", app, global, microsoftShipment);

    model.put("message", "AV test executed");
    return "welcome";
  }
  
}
