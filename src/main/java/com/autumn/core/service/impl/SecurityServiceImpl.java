package com.autumn.core.service.impl;

import com.autumn.core.dao.LogDao;
import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.dao.YfDao;
import static com.autumn.core.dao.YfDao.*;
import com.autumn.core.model.HistoricalQuote;
import com.autumn.core.model.SecurityLogType;
import com.autumn.core.service.SecurityService;
import com.autumn.core.util.CommonUtil;
import com.autumn.core.util.EmailUtil;
import java.util.ArrayList;
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
  private CommonUtil commonUtil;
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

  @Autowired
  public void setCommonUtil(CommonUtil commonUtil) {
    this.commonUtil = commonUtil;
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
    
    Map<Date, HistoricalQuote> quotes = yfDao.getHisoricalQuotes("dodgx", "1y", YfDao.DAILY_INCREMENT);
    for (Map.Entry<Date, HistoricalQuote> quote : quotes.entrySet()) {
      System.out.println(quote.getKey() + ": " + quote.getValue().getClose());
    }
    
    Date[] dateRange = commonUtil.getDateRangeForPastWorkPeriod("5d");
    HistoricalQuote quote5d = quotes.get(dateRange[0]);
    System.out.println("5d " + quote5d.getDate() + " " + quote5d.getClose());
    System.out.println("5d%: " + commonUtil.getPercentDisplayForWeekdaysAgo(quotes, "5d", true));
  }

  
  @Override
  public void checkForDailyOpen() {
    final String REQUESTS = SYMBOL + NAME + PREVIOUS_CLOSE + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"Sym","Name","Prv","Ask","Pct"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForDailyOpen();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);
    
    String message = commonUtil.buildHtmlMessage(HEADERS, csvResults);
    if (sendEmail) {
      emailUtil.sendHtmlEmailThruGoogle("-", message);
    }
    System.out.println(new Date() + message);
  }

  
  @Override
  public void checkForIntraDay() {
    final String REQUESTS = SYMBOL + NAME + PREVIOUS_CLOSE + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"Sym","Name","Prv","Ask","Pct"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForDailyOpen();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);
    
    String message = commonUtil.buildMessage(HEADERS, csvResults);
    if (sendEmail) {
      emailUtil.sendEmailThruGoogle("--", message);
    }
    System.out.println(new Date() + message);
  }
  
  
  @Override
  public void checkForDailyClose() {
    final String REQUESTS = SYMBOL + PREVIOUS_CLOSE + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"","prv","ask","pct"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForDailyOpen();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);

    String message = commonUtil.buildMessage(HEADERS, csvResults);
    if (sendEmail) {
      emailUtil.sendEmailThruGoogle("---", message);
    }
    System.out.println(new Date() + message);
  }
  
  
  @Override
  public void checkForEndOfDay() {
    final String REQUESTS = SYMBOL + NAME + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"Sym","Name","Close","%Chg","5d","10d","1m","3m","6m","9m","1y","STX","MTX","LTX"};
    
    StringBuilder sb = new StringBuilder();
    sb = commonUtil.createHtmlBegin(sb);

    Map<String,Map<Date,HistoricalQuote>> securitiesHistQuotes = null;
    
    // nn
    List<SecurityLogType> nnSecurities = securityLogTypeDao.getSecuritiesForNn();
    List<String> nnSymbols = getSymbols(nnSecurities);
    Map<String, Boolean> nnParticipations = getParticipations(nnSecurities);
    List<String> nnCsvResults = yfDao.getQuote(nnSymbols, REQUESTS);
    nnCsvResults = sortByColumn(nnCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String nnSymbol : nnSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(nnSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(nnSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Nn Funds", HEADERS, nnParticipations, nnCsvResults, securitiesHistQuotes);
    
    // lb
    List<SecurityLogType> lbSecurities = securityLogTypeDao.getSecuritiesForLb();
    List<String> lbSymbols = getSymbols(lbSecurities);
    Map<String, Boolean> lbParticipations = getParticipations(lbSecurities);
    List<String> lbCsvResults = yfDao.getQuote(lbSymbols, REQUESTS);
    lbCsvResults = sortByColumn(lbCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String lbSymbol : lbSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(lbSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(lbSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Lb Funds", HEADERS, lbParticipations, lbCsvResults, securitiesHistQuotes);
    
    // Ic
    List<SecurityLogType> icSecurities = securityLogTypeDao.getSecuritiesForIc();
    List<String> icSymbols = getSymbols(icSecurities);
    Map<String, Boolean> icParticipations = getParticipations(icSecurities);
    List<String> icCsvResults = yfDao.getQuote(icSymbols, REQUESTS);
    icCsvResults = sortByColumn(icCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String icSymbol : icSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(icSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(icSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Ic Funds", HEADERS, icParticipations, icCsvResults, securitiesHistQuotes);
    
    // sg
    List<SecurityLogType> sgSecurities = securityLogTypeDao.getSecuritiesForSg();
    List<String> sgSymbols = getSymbols(sgSecurities);
    Map<String, Boolean> sgParticipations = getParticipations(sgSecurities);
    List<String> sgCsvResults = yfDao.getQuote(sgSymbols, REQUESTS);
    sgCsvResults = sortByColumn(sgCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String sgSymbol : sgSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(sgSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(sgSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Sg Funds", HEADERS, sgParticipations, sgCsvResults, securitiesHistQuotes);
    
    sb = commonUtil.createHtmlEnd(sb);
    
    if (sendEmail) {
      emailUtil.sendHtmlEmailThruGoogle("----", sb.toString());
    }
    System.out.println(new Date() + sb.toString());
  }

  
  private List<String> getSymbols(List<SecurityLogType> securities) {
    List<String> symbols = new ArrayList();
    for (SecurityLogType security : securities) {
      symbols.add(security.getSecurity().getYahooSymbol());
    }
    return symbols;
  }
  
  
  private Map<String,Boolean> getParticipations(List<SecurityLogType> securities) {
    Map<String,Boolean> participations = new HashMap();
    for (SecurityLogType security : securities) {
      participations.put(security.getSecurity().getYahooSymbol(), security.getSecurity().isParticipated());
    }
    return participations;
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
      Float key = convertToFloat(splittedStrings[sortColIndex]);
      if (unsortedMap.containsKey(key)) {
        unsortedMap.put(key+0.0001F, csvString);
      } else {
        unsortedMap.put(key, csvString);
      }
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
  
  
  private Float convertToFloat(String in) {
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
