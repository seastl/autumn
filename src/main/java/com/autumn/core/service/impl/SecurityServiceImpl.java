package com.autumn.core.service.impl;

import com.autumn.core.dao.LogDao;
import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.dao.YfDao;
import static com.autumn.core.dao.YfDao.*;
import com.autumn.core.model.HistoricalQuote;
import com.autumn.core.model.SecurityLogType;
import com.autumn.core.service.SecurityService;
import com.autumn.core.util.EmailUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceImpl implements SecurityService {

  private SecurityLogTypeDao securityLogTypeDao;
  private LogDao logDao;
  private YfDao yfDao;
  private EmailUtil emailUtil;
  private boolean sendEmail = false;

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
  public void setSendEmail(boolean sendEmail) {
    this.sendEmail = sendEmail;
  }

  
  @Override
  public void check() {
    /*
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
    */
    
    Map<Date, HistoricalQuote> quotes = yfDao.getHisoricalQuotes("msft", 5, YfDao.DAILY_INCREMENT);
    for (Map.Entry<Date, HistoricalQuote> quote : quotes.entrySet()) {
      System.out.println(quote.getKey() + ": " + quote.getValue().getClose());
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
    if (sendEmail) {
      emailUtil.sendEmailThruGoogle("-", message);
    }
    System.out.println(new Date() + message);
  }

  
  @Override
  public void checkForIntraDay() {
    final String REQUESTS = SYMBOL + PREVIOUS_CLOSE + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"","prv","ask","pct"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForStartOfDayLogging();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);
    
    String message = buildMessage(HEADERS, csvResults);
    if (sendEmail) {
      emailUtil.sendEmailThruGoogle("--", message);
    }
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
    if (sendEmail) {
      emailUtil.sendEmailThruGoogle("---", message);
    }
    System.out.println(new Date() + message);
  }
  
  
  @Override
  public void checkForEndOfDay() {
    final String REQUESTS = SYMBOL + NAME + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"","","",""};
    
    List<SecurityLogType> nnSecurities = securityLogTypeDao.getSecuritiesForNn();
    List<String> nnSymbols = getSymbols(nnSecurities);
    List<Boolean> nnParticipations = getParticipations(nnSecurities);
    List<String> nnCsvResults = yfDao.getQuote(nnSymbols, REQUESTS);
    nnCsvResults = sortByColumn(nnCsvResults, 3, true);

    List<SecurityLogType> lbSecurities = securityLogTypeDao.getSecuritiesForLb();
    List<String> lbSymbols = getSymbols(lbSecurities);
    List<Boolean> lbParticipations = getParticipations(lbSecurities);
    List<String> lbCsvResults = yfDao.getQuote(lbSymbols, REQUESTS);
    lbCsvResults = sortByColumn(lbCsvResults, 3, true);

    List<SecurityLogType> icSecurities = securityLogTypeDao.getSecuritiesForIc();
    List<String> icSymbols = getSymbols(icSecurities);
    List<Boolean> icParticipations = getParticipations(icSecurities);
    List<String> icCsvResults = yfDao.getQuote(icSymbols, REQUESTS);
    icCsvResults = sortByColumn(icCsvResults, 3, true);

    List<SecurityLogType> sgSecurities = securityLogTypeDao.getSecuritiesForSg();
    List<String> sgSymbols = getSymbols(sgSecurities);
    List<Boolean> sgParticipations = getParticipations(sgSecurities);
    List<String> sgCsvResults = yfDao.getQuote(sgSymbols, REQUESTS);
    sgCsvResults = sortByColumn(sgCsvResults, 3, true);

    String message = "\n";
    message += "Nn Funds:\n" + buildMessage(HEADERS, nnParticipations, nnCsvResults) + "\n";
    message += "Lb Funds:\n" + buildMessage(HEADERS, lbParticipations, lbCsvResults) + "\n";
    message += "Ic Funds:\n" + buildMessage(HEADERS, icParticipations, icCsvResults) + "\n";
    message += "Sg Funds:\n" + buildMessage(HEADERS, sgParticipations, sgCsvResults) + "\n";
    
    if (sendEmail) {
      emailUtil.sendEmailThruGoogle("----", message);
    }
    System.out.println(new Date() + message);
  }
  
  
  private List<String> getSymbols(List<SecurityLogType> securities) {
    List<String> symbols = new ArrayList();
    for (SecurityLogType security : securities) {
      symbols.add(security.getSecurity().getYahooSymbol());
    }
    return symbols;
  }
  
  
  private List<Boolean> getParticipations(List<SecurityLogType> securities) {
    List<Boolean> participations = new ArrayList();
    for (SecurityLogType security : securities) {
      participations.add(security.getSecurity().isParticipated());
    }
    return participations;
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

  
  private String buildMessage(String[] headers, List<Boolean> participations, List<String> csvResults) {
    StringBuilder sb = new StringBuilder();
    for (int j = 0; j < csvResults.size(); j++) {
      String csvResult = csvResults.get(j);
      String participation = participations.get(j) ? "*" : " ";
      String[] result = csvResult.split(",");
      sb.append(participation);
      for (int i = 0; i < headers.length; i++) {
        if (headers[i] != null && !headers[i].isEmpty()) {
          sb.append(headers[i] + ":" + removeQuotes(result[i]) + " ");
        } else {
          sb.append(removeQuotes(result[i]) + " ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  

  /**
   * Remove double quotes from the string if the string does not contain spaces.
   * @param in
   * @return 
   */
  private String removeQuotes(String in) {
    if (in.contains(" ")) {
      return in;
    } else {
      return in.replace("\"", "");
    }
  }
  

  /**
   * 
   * @param in
   * @param columnToSort
   * @param isAcending
   * @return 
   */
  private List<String> sortByColumn(List<String> csvStrings, int sortColIndex, boolean isAcending) {
    Map<Float,String> unsortedMap = new HashMap();
    for (String csvString : csvStrings) {
      String[] splittedStrings = csvString.split(",");
      unsortedMap.put(convertToInteger(splittedStrings[sortColIndex]), csvString);
    }
    
    Map<Float,String> sortedMap = new TreeMap<Float,String>(
      new Comparator<Float>() {
        @Override
        public int compare(Float num1, Float num2) {
          return num2.compareTo(num1);
        }
      }
    );
    sortedMap.putAll(unsortedMap);
    
    List<String> sortedCsvStrings = new ArrayList<String>(sortedMap.values());
    return sortedCsvStrings;
  }
  
  
  private Float convertToInteger(String in) {
    String stripped = in.replace("\"", "");
    stripped = stripped.replace("%", "");
    Float convertedFloat = null;
    try {
      convertedFloat = Float.parseFloat(stripped);
    } catch (Exception ex) {
      convertedFloat = -100.0F;
    }
    return convertedFloat;
  }
  
  
}
