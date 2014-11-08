package com.autumn.core.service.impl;

import com.autumn.core.dao.LogDao;
import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.dao.YfDao;
import static com.autumn.core.dao.YfDao.*;
import com.autumn.core.model.SecurityLogType;
import com.autumn.core.service.SecurityService;
import com.autumn.core.util.EmailUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    
    List<String> symbols = Arrays.asList("msft", "fb", NASDAQ, SP500, DOW, IDX_TECHOLOGY, SP500_VOLATILITY);
    String requests = SYMBOL + NAME + PREVIOUS_CLOSE + LST_TRD + PCT_CHG;
    List<String> results = yfDao.getQuote(symbols, requests);
    for (String result : results) {
      System.out.println(result);
    }
  }

  
  @Override
  public void checkForDailyOpen() {
    final String REQUESTS = SYMBOL + PREVIOUS_CLOSE + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"","prv","ask","pct"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForStartOfDayLogging();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);
    
    String message = buildMessage(HEADERS, csvResults);
    emailUtil.sendEmailThruGoogle("-", message);
    System.out.println(new Date() + message);
  }
  
  
  @Override
  public void checkForDailyClose() {
    final String REQUESTS = SYMBOL + PREVIOUS_CLOSE + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"","prv","ask","pct"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForStartOfDayLogging();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);

    String message = buildMessage(HEADERS, csvResults);
    emailUtil.sendEmailThruGoogle("-", message);
    System.out.println(new Date() + message);
  }
  
  
  @Override
  public void checkForEndOfDay() {
    final String REQUESTS = SYMBOL + NAME + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"","","",""};
    
    List<SecurityLogType> nnSecurities = securityLogTypeDao.getSecuritiesForNn();
    List<String> nnSymbols = getSymbols(nnSecurities);
    List<String> nnCsvResults = yfDao.getQuote(nnSymbols, REQUESTS);

    List<SecurityLogType> lbSecurities = securityLogTypeDao.getSecuritiesForLb();
    List<String> lbSymbols = getSymbols(lbSecurities);
    List<String> lbCsvResults = yfDao.getQuote(lbSymbols, REQUESTS);

    List<SecurityLogType> icSecurities = securityLogTypeDao.getSecuritiesForIc();
    List<String> icSymbols = getSymbols(icSecurities);
    List<String> icCsvResults = yfDao.getQuote(icSymbols, REQUESTS);

    String message = "\n";
    message += "Nn Funds:\n" + buildMessage(HEADERS, nnCsvResults) + "\n";
    message += "Lb Funds:\n" + buildMessage(HEADERS, lbCsvResults) + "\n";
    message += "Ic Funds:\n" + buildMessage(HEADERS, icCsvResults) + "\n";
    
    emailUtil.sendEmailThruGoogle("-", message);
    System.out.println(new Date() + message);
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
        if (headers[i] != null && !headers[i].isEmpty()) {
          sb.append(headers[i] + ":" + result[i] + " ");
        } else {
          sb.append(result[i] + " ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  
}
