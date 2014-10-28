package com.autumn.core.service.impl;

import com.autumn.core.dao.LogDao;
import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.dao.YfDao;
import com.autumn.core.model.SecurityLogType;
import com.autumn.core.service.SecurityService;
import com.autumn.core.util.EmailUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceImpl implements SecurityService {

  private SecurityLogTypeDao securityLogTypeDao;
  private LogDao logDao;
  private YfDao yfDao;
  private EmailUtil emailUtil;

  @Autowired
  public void setSecurityLogTypeDao(SecurityLogTypeDao securityLogTypeDao) {
    this.securityLogTypeDao = securityLogTypeDao;
  }

  @Autowired
  public void setLogDao(LogDao logDao) {
    this.logDao = logDao;
  }

  @Autowired
  public void setYfDao(YfDao yfDao) {
    this.yfDao = yfDao;
  }

  @Autowired
  public void setEmailUtil(EmailUtil emailUtil) {
    this.emailUtil = emailUtil;
  }

  
  @Override
  public void check() {
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForIntraDayLogging();
    
    for (SecurityLogType security : securities) {
      System.out.println("Security=" + security.getSecurity().getYahooSymbol() + " LogType=" + security.getLogType().getDescription());
    }
    
    List<String> symbols = Arrays.asList("msft","fb");
//    String REQUESTS = YfDao.SYMBOL + YfDao.DAYS_LO + YfDao.DAYS_HI + YfDao.BID_RT + YfDao.ASK_RT + YfDao.PREVIOUS_CLOSE + YfDao.LST_TRD;
//    String requests = YfDao.SYMBOL + YfDao.NAME + YfDao._50D_MOVING_AVG + YfDao.PCT_CHG_FRM_50D_MOVING_AVG + YfDao.CHG + YfDao.PCT_CHG + YfDao._1Y_TARGET_PRICE;
    String requests = YfDao.SYMBOL + YfDao.PREVIOUS_CLOSE + YfDao.LST_TRD;
    List<String> results = yfDao.getQuote(symbols, requests);
    for (String result : results) {
      System.out.println(result);
    }
  }

  
  @Override
  public void checkForStartOfDay() {
    final String REQUESTS = YfDao.SYMBOL + YfDao.PREVIOUS_CLOSE + YfDao.LST_TRD;
    final String[] HEADERS = {"","prv","ask"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForStartOfDayLogging();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);
    
    String message = buildMessage(HEADERS, csvResults);
 //   emailUtil.sendEmailThruGoogle(message, message, message, message);
    System.out.println(message);
  }
  
  
  @Override
  public void checkForEndOfDay() {
    final String REQUESTS = YfDao.SYMBOL + YfDao.PREVIOUS_CLOSE + YfDao.LST_TRD;
    final String[] HEADERS = {"","prv","ask"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForStartOfDayLogging();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);
    
    String message = buildMessage(HEADERS, csvResults);
//    emailUtil.sendEmailThruGoogle(message, message, message, message);
  }
  
  
  private List<String> getSymbols(List<SecurityLogType> securities) {
    List<String> symbols = new ArrayList();
    for (SecurityLogType security : securities) {
      symbols.add(security.getSecurity().getYahooSymbol());
    }
    return symbols;
  }
  
  
  private String buildMessage(String[] headers, List<String> csvResults) {
    StringBuilder sb = new StringBuilder();
    for (String csvResult : csvResults) {
      String[] result = csvResult.split(",");
      for (int i = 0; i < headers.length; i++) {
        sb.append(headers[i] + ":" + result[i] + " ");
      }
    }
    return sb.toString();
  }
  
}
