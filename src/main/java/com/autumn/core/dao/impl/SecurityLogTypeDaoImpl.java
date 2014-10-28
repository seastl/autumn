package com.autumn.core.dao.impl;

import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.model.LogType;
import com.autumn.core.model.Sector;
import com.autumn.core.model.Security;
import com.autumn.core.model.SecurityLogType;
import com.autumn.core.model.SecurityType;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SecurityLogTypeDaoImpl implements SecurityLogTypeDao {

  @Override
  public List<SecurityLogType> getSecuritiesForIntraDayLogging() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1, "Dow Jones index", "MSFT", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00:00"), Time.valueOf("13:05:00")),
                                   Boolean.TRUE); // Dow
    securities.add(security);
    security = new SecurityLogType(new Security(2, "Dow Jones index", "FB", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00:00"), Time.valueOf("13:05:00")),
                                   Boolean.TRUE); // Nasdaq
    securities.add(security);
    security = new SecurityLogType(new Security(3, "Dow Jones index", "^IXIC", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00:00"), Time.valueOf("13:05:00")),
                                   Boolean.TRUE); // S&P 500
    securities.add(security);
    
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForStartOfDayLogging() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1, "Dow Jones index", "&#94;IXIC", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                   Boolean.TRUE); // Dow
    securities.add(security);
    security = new SecurityLogType(new Security(2, "Dow Jones index", "MSFT", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                   Boolean.TRUE); // Nasdaq
    securities.add(security);
    security = new SecurityLogType(new Security(3, "Dow Jones index", "FB", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                   Boolean.TRUE); // S&P 500
    securities.add(security);
    
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForEndOfDayLogging() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE); // Dow
    securities.add(security);
    security = new SecurityLogType(new Security(2, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE); // Nasdaq
    securities.add(security);
    security = new SecurityLogType(new Security(3, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE); // S&P 500
    securities.add(security);
    
    return securities;
  }
  
}
