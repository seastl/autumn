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
    security = new SecurityLogType(new Security(1, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00"), Time.valueOf("13:05")),
                                   Boolean.TRUE); // Dow
    security = new SecurityLogType(new Security(2, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00"), Time.valueOf("13:05")),
                                   Boolean.TRUE); // Nasdaq
    security = new SecurityLogType(new Security(3, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00"), Time.valueOf("13:05")),
                                   Boolean.TRUE); // S&P 500
    
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForStartOfDayLogging() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00"), null),
                                   Boolean.TRUE); // Dow
    security = new SecurityLogType(new Security(2, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00"), null),
                                   Boolean.TRUE); // Nasdaq
    security = new SecurityLogType(new Security(3, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00"), null),
                                   Boolean.TRUE); // S&P 500
    
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForEndOfDayLogging() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05")),
                                   Boolean.TRUE); // Dow
    security = new SecurityLogType(new Security(2, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05")),
                                   Boolean.TRUE); // Nasdaq
    security = new SecurityLogType(new Security(3, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05")),
                                   Boolean.TRUE); // S&P 500
    
    return securities;
  }
  
}
