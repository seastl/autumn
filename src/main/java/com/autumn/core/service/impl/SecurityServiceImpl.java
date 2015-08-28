package com.autumn.core.service.impl;

import com.autumn.core.dao.LogDao;
import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.dao.YfDao;
import static com.autumn.core.dao.YfDao.LST_TRD;
import static com.autumn.core.dao.YfDao.NAME;
import static com.autumn.core.dao.YfDao.PCT_CHG;
import static com.autumn.core.dao.YfDao.PREVIOUS_CLOSE;
import static com.autumn.core.dao.YfDao.SYMBOL;
import com.autumn.core.model.HistoricalQuote;
import com.autumn.core.model.SecurityLogType;
import com.autumn.core.service.SecurityService;
import com.autumn.core.util.CommonUtil;
import com.autumn.core.util.EmailUtil;
import java.text.SimpleDateFormat;
import java.util.*;
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
    final String[] HEADERS = {"Sym","Name","Prv","Ask","%Chg"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForDailyOpen();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);
    csvResults = sortByColumn(csvResults, 4, true);
    
    StringBuilder sb = new StringBuilder();
    sb = commonUtil.createHtmlBegin(sb);
    sb = commonUtil.createInfoTable(sb);
    sb = commonUtil.createHtmlTable(sb, "Indexes", HEADERS, csvResults);
    sb = commonUtil.createHtmlEnd(sb);
    String message = sb.toString();
    
    if (sendEmail) {
      emailUtil.sendHtmlEmailThruGoogle("- " + getTimestamp(), message);
    } else {
      commonUtil.writeToFile("test_test.html", message);
    }
    System.out.println(new Date() + message);
  }

  
  @Override
  public void checkForIntraDay() {
    final String REQUESTS = SYMBOL + NAME + PREVIOUS_CLOSE + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"Sym","Name","Prv","Ask","%Chg"};
    
    StringBuilder sb = new StringBuilder();
    sb = commonUtil.createHtmlBegin(sb);
    sb = commonUtil.createInfoTable(sb);
    
    // Indexes
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForIntraDay();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);
    csvResults = sortByColumn(csvResults, 4, true);
    sb = commonUtil.createHtmlTable(sb, "Indexes", HEADERS, csvResults);
    sb = commonUtil.createHtmlEnd(sb);
    
    // Dow30
    securities = securityLogTypeDao.getSecuritiesForDow30();
    symbols = getSymbols(securities);
    csvResults = yfDao.getQuote(symbols, REQUESTS);
    csvResults = sortByColumn(csvResults, 4, true);
    sb = commonUtil.createHtmlTable(sb, "Dow30", HEADERS, csvResults);
    sb = commonUtil.createHtmlEnd(sb);
    
    String message = sb.toString();
    
    if (sendEmail) {
      emailUtil.sendHtmlEmailThruGoogle("-- " + getTimestamp(), message);
    } else {
      commonUtil.writeToFile("test_test.html", message);
    }
    System.out.println(new Date() + message);
  }
  
  
  @Override
  public void checkForDailyClose() {
    final String REQUESTS = SYMBOL + NAME + PREVIOUS_CLOSE + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"Sym","Name","Prv","Ask","%Chg"};
    
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForDailyOpen();
    List<String> symbols = getSymbols(securities);
    List<String> csvResults = yfDao.getQuote(symbols, REQUESTS);
    csvResults = sortByColumn(csvResults, 4, true);

    StringBuilder sb = new StringBuilder();
    sb = commonUtil.createHtmlBegin(sb);
    sb = commonUtil.createInfoTable(sb);
    sb = commonUtil.createHtmlTable(sb, "Indexes", HEADERS, csvResults);
    sb = commonUtil.createHtmlEnd(sb);
    String message = sb.toString();
    
    if (sendEmail) {
      emailUtil.sendHtmlEmailThruGoogle("--- " + getTimestamp(), message);
    } else {
      commonUtil.writeToFile("test_test.html", message);
    }
    System.out.println(new Date() + message);
  }
  
  
  @Override
  public void checkForEndOfDay() {
    final String REQUESTS = SYMBOL + NAME + LST_TRD + PCT_CHG;
    final String[] HEADERS = {"Sym","Name","Close","%Chg","5d","10d","1m","3m","6m","9m","1y","STX","MTX","LTX","Note"};
    
    StringBuilder sb = new StringBuilder();
    sb = commonUtil.createHtmlBegin(sb);
    sb = commonUtil.createInfoTable(sb);

    Map<String,Map<Date,HistoricalQuote>> securitiesHistQuotes = null;
    
    // indexes
    List<SecurityLogType> dailyCloseSecurities = securityLogTypeDao.getSecuritiesForDailyClose();
    List<String> dailyCloseSymbols = getSymbols(dailyCloseSecurities);
    Map<String, Boolean> dailyCloseParticipations = getParticipations(dailyCloseSecurities);
    Map<String, String> dailyCloseNotes = getNotes(dailyCloseSecurities);
    List<String> dailyCloseCsvResults = yfDao.getQuote(dailyCloseSymbols, REQUESTS);
    dailyCloseCsvResults = sortByColumn(dailyCloseCsvResults, 3, true);
    
    securitiesHistQuotes = new HashMap();
    for (String dailyCloseSymbol : dailyCloseSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(dailyCloseSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(dailyCloseSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Indexes & Sectors", HEADERS, dailyCloseParticipations, dailyCloseNotes, dailyCloseCsvResults, securitiesHistQuotes);

    // dow 30
    List<SecurityLogType> dowSecurities = securityLogTypeDao.getSecuritiesForDow30();
    List<String> dowSymbols = getSymbols(dowSecurities);
    Map<String, Boolean> dowParticipations = getParticipations(dowSecurities);
    Map<String, String> dowNotes = getNotes(dowSecurities);
    List<String> dowCsvResults = yfDao.getQuote(dowSymbols, REQUESTS);
    dowCsvResults = sortByColumn(dowCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String dowSymbol : dowSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(dowSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(dowSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Dow30", HEADERS, dowParticipations, dowNotes, dowCsvResults, securitiesHistQuotes);
    
    // fid equity
    List<SecurityLogType> fidEqSecurities = securityLogTypeDao.getSecuritiesForFidEquity();
    List<String> fidEqSymbols = getSymbols(fidEqSecurities);
    Map<String, Boolean> fidEqParticipations = getParticipations(fidEqSecurities);
    Map<String, String> fidEqNotes = getNotes(fidEqSecurities);
    List<String> fidEqCsvResults = yfDao.getQuote(fidEqSymbols, REQUESTS);
    fidEqCsvResults = sortByColumn(fidEqCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String fidEqSymbol : fidEqSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(fidEqSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(fidEqSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Fid Equity", HEADERS, fidEqParticipations, fidEqNotes, fidEqCsvResults, securitiesHistQuotes);
    
    // fid international
    List<SecurityLogType> fidInlSecurities = securityLogTypeDao.getSecuritiesForFidInternational();
    List<String> fidInlSymbols = getSymbols(fidInlSecurities);
    Map<String, Boolean> fidInlParticipations = getParticipations(fidInlSecurities);
    Map<String, String> fidInlNotes = getNotes(fidInlSecurities);
    List<String> fidInlCsvResults = yfDao.getQuote(fidInlSymbols, REQUESTS);
    fidInlCsvResults = sortByColumn(fidInlCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String fidInlSymbol : fidInlSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(fidInlSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(fidInlSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Fid International", HEADERS, fidInlParticipations, fidInlNotes, fidInlCsvResults, securitiesHistQuotes);
    
    // fid sector
    List<SecurityLogType> fidSecSecurities = securityLogTypeDao.getSecuritiesForFidSector();
    List<String> fidSecSymbols = getSymbols(fidSecSecurities);
    Map<String, Boolean> fidSecParticipations = getParticipations(fidSecSecurities);
    Map<String, String> fidSecNotes = getNotes(fidSecSecurities);
    List<String> fidSecCsvResults = yfDao.getQuote(fidSecSymbols, REQUESTS);
    fidSecCsvResults = sortByColumn(fidSecCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String fidSecSymbol : fidSecSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(fidSecSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(fidSecSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Fid Sector", HEADERS, fidSecParticipations, fidSecNotes, fidSecCsvResults, securitiesHistQuotes);
    
    // fid ishares
    List<SecurityLogType> fidIshrSecurities = securityLogTypeDao.getSecuritiesForFidIShares();
    List<String> fidIshrSymbols = getSymbols(fidIshrSecurities);
    Map<String, Boolean> fidIshrParticipations = getParticipations(fidIshrSecurities);
    Map<String, String> fidIshrNotes = getNotes(fidIshrSecurities);
    List<String> fidIshrCsvResults = yfDao.getQuote(fidIshrSymbols, REQUESTS);
    fidIshrCsvResults = sortByColumn(fidIshrCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String fidIshrSymbol : fidIshrSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(fidIshrSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(fidIshrSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Fid iShares", HEADERS, fidIshrParticipations, fidIshrNotes, fidIshrCsvResults, securitiesHistQuotes);
    
    // nn
    List<SecurityLogType> nnSecurities = securityLogTypeDao.getSecuritiesForNn();
    List<String> nnSymbols = getSymbols(nnSecurities);
    Map<String, Boolean> nnParticipations = getParticipations(nnSecurities);
    Map<String, String> nnNotes = getNotes(nnSecurities);
    List<String> nnCsvResults = yfDao.getQuote(nnSymbols, REQUESTS);
    nnCsvResults = sortByColumn(nnCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String nnSymbol : nnSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(nnSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(nnSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Nn Funds", HEADERS, nnParticipations, nnNotes, nnCsvResults, securitiesHistQuotes);
    
    // lb
    List<SecurityLogType> lbSecurities = securityLogTypeDao.getSecuritiesForLb();
    List<String> lbSymbols = getSymbols(lbSecurities);
    Map<String, Boolean> lbParticipations = getParticipations(lbSecurities);
    Map<String, String> lbNotes = getNotes(lbSecurities);
    List<String> lbCsvResults = yfDao.getQuote(lbSymbols, REQUESTS);
    lbCsvResults = sortByColumn(lbCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String lbSymbol : lbSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(lbSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(lbSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Lb Funds", HEADERS, lbParticipations, lbNotes, lbCsvResults, securitiesHistQuotes);
    
    // Ic
    List<SecurityLogType> icSecurities = securityLogTypeDao.getSecuritiesForIc();
    List<String> icSymbols = getSymbols(icSecurities);
    Map<String, Boolean> icParticipations = getParticipations(icSecurities);
    Map<String, String> icNotes = getNotes(icSecurities);
    List<String> icCsvResults = yfDao.getQuote(icSymbols, REQUESTS);
    icCsvResults = sortByColumn(icCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String icSymbol : icSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(icSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(icSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Ic Funds", HEADERS, icParticipations, icNotes, icCsvResults, securitiesHistQuotes);
    
    // sg
    List<SecurityLogType> sgSecurities = securityLogTypeDao.getSecuritiesForSg();
    List<String> sgSymbols = getSymbols(sgSecurities);
    Map<String, Boolean> sgParticipations = getParticipations(sgSecurities);
    Map<String, String> sgNotes = getNotes(sgSecurities);
    List<String> sgCsvResults = yfDao.getQuote(sgSymbols, REQUESTS);
    sgCsvResults = sortByColumn(sgCsvResults, 3, true);

    securitiesHistQuotes = new HashMap();
    for (String sgSymbol : sgSymbols) {
      Map<Date, HistoricalQuote> securityHistQuotes = yfDao.getHisoricalQuotes(sgSymbol, "1y", YfDao.DAILY_INCREMENT);
      securitiesHistQuotes.put(sgSymbol, securityHistQuotes);
    }
    sb = commonUtil.createHtmlTable(sb, "Sg Funds", HEADERS, sgParticipations, sgNotes, sgCsvResults, securitiesHistQuotes);
    
    sb = commonUtil.createHtmlEnd(sb);
    
    if (sendEmail) {
      emailUtil.sendHtmlEmailThruGoogle("---- " + getTimestamp(), sb.toString());
    } else {
      commonUtil.writeToFile("test_test.html", sb.toString());
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
  
  
  private Map<String,String> getNotes(List<SecurityLogType> securities) {
    Map<String,String> notes = new HashMap();
    for (SecurityLogType security : securities) {
      notes.put(security.getSecurity().getYahooSymbol(), security.getSecurity().getNote());
    }
    return notes;
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
      String[] splittedStrings = csvString.split(CommonUtil.COMMA_SPLIT_REGEX);
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
  

  /**
   * Get timestamp in 2015-08-28 Fri 12:59PM
   * @return 
   */
  private String getTimestamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEE hh:mm a");
    String timestamp = sdf.format(new Date());
    return timestamp;
  }
  
}
