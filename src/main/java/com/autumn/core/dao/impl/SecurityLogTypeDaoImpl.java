package com.autumn.core.dao.impl;

import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.model.*;
import com.autumn.core.util.JsonUtils;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityLogTypeDaoImpl implements SecurityLogTypeDao {
  private List<SecurityConf> securitiesGeneralList = new ArrayList();
  private List<SecurityConf> securitiesNnList = new ArrayList();
  private List<SecurityConf> securitiesLbList = new ArrayList();
  private List<SecurityConf> securitiesSgList = new ArrayList();
  private List<SecurityConf> securitiesIcList = new ArrayList();
  private List<SecurityConf> securitiesFidEqtyList = new ArrayList();
  private List<SecurityConf> securitiesFidIntrList = new ArrayList();
  private List<SecurityConf> securitiesFidSectorList = new ArrayList();
  private List<SecurityConf> securitiesFidISharesList = new ArrayList();
  private List<SecurityConf> securitiesDow30List = new ArrayList();
  
  @Value("${autumn.securitiesGeneral}")
  private String securitiesGeneral; 
          
  @Value("${autumn.securitiesNn}")
  private String securitiesNn; 
          
  @Value("${autumn.securitiesLb}")
  private String securitiesLb; 
          
  @Value("${autumn.securitiesSg}")
  private String securitiesSg; 
          
  @Value("${autumn.securitiesIc}")
  private String securitiesIc; 
          
  @Value("${autumn.securitiesFidEquity}")
  private String securitiesFidEquities; 
          
  @Value("${autumn.securitiesFidInternational}")
  private String securitiesFidInternational; 
          
  @Value("${autumn.securitiesFidSector}")
  private String securitiesFidSector; 
          
  @Value("${autumn.securitiesFidIShares}")
  private String securitiesFidIShares; 
          
  @Value("${autumn.securitiesDow30}")
  private String securitiesDow30; 
          
  @PostConstruct
  public void postConstruct() throws Exception {
    securitiesGeneralList = JsonUtils.getObjectListFromJSONStrings(securitiesGeneral, SecurityConf.class);
    securitiesNnList = JsonUtils.getObjectListFromJSONStrings(securitiesNn, SecurityConf.class);
    securitiesLbList = JsonUtils.getObjectListFromJSONStrings(securitiesLb, SecurityConf.class);
    securitiesSgList = JsonUtils.getObjectListFromJSONStrings(securitiesSg, SecurityConf.class);
    securitiesIcList = JsonUtils.getObjectListFromJSONStrings(securitiesIc, SecurityConf.class);
    securitiesFidEqtyList = JsonUtils.getObjectListFromJSONStrings(securitiesFidEquities, SecurityConf.class);
    securitiesFidIntrList = JsonUtils.getObjectListFromJSONStrings(securitiesFidInternational, SecurityConf.class);
    securitiesFidSectorList = JsonUtils.getObjectListFromJSONStrings(securitiesFidSector, SecurityConf.class);
    securitiesFidISharesList = JsonUtils.getObjectListFromJSONStrings(securitiesFidIShares, SecurityConf.class);
    securitiesDow30List = JsonUtils.getObjectListFromJSONStrings(securitiesDow30, SecurityConf.class);
    
    //System.out.println("*** KL: SecurityLogTypeDaoImpl postConstruct securitiesGeneral=" + securitiesGeneral);
    //System.out.println("*** KL: name=" + securitiesGeneralList.get(30).getName() + " note=" + securitiesGeneralList.get(30).getNote());
  }
  

  @Override
  public List<SecurityLogType> getSecuritiesForIntraDay() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesGeneralList) {
      if (sec.isIntraDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), null, null, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00:00"), Time.valueOf("13:05:00")),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForDailyOpen() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesGeneralList) {
      if (sec.isOpen()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), null, null, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForDailyClose() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesGeneralList) {
      if (sec.isClose()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), null, null, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.END_DAY_LOG, "End of day log", null, null, Time.valueOf("06:00:00"), null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForNn() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesNnList) {
      if (sec.isEndOfDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.END_DAY_LOG, "End of day log", null, null, null, null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
     return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForLb() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesLbList) {
      if (sec.isEndOfDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.END_DAY_LOG, "End of day log", null, null, null, null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;  
  }

  @Override
  public List<SecurityLogType> getSecuritiesForIc() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesIcList) {
      if (sec.isEndOfDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.END_DAY_LOG, "End of day log", null, null, null, null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;  
  }
  
  @Override
  public List<SecurityLogType> getSecuritiesForSg() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesSgList) {
      if (sec.isEndOfDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.END_DAY_LOG, "End of day log", null, null, null, null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForFidEquity() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesFidEqtyList) {
      if (sec.isEndOfDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.END_DAY_LOG, "End of day log", null, null, null, null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForFidInternational() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesFidIntrList) {
      if (sec.isEndOfDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.END_DAY_LOG, "End of day log", null, null, null, null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForFidSector() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesFidSectorList) {
      if (sec.isEndOfDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.END_DAY_LOG, "End of day log", null, null, null, null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForFidIShares() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesFidISharesList) {
      if (sec.isEndOfDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), SecurityType.US_ETF, Sector.UNKNOWN, sec.isParticipated(), sec.getNote()),
                                       new LogType(LogType.END_DAY_LOG, "End of day log", null, null, null, null),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForDow30() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesDow30List) {
      if (sec.isIntraDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), null, null),
                                       new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00:00"), Time.valueOf("13:05:00")),
                                       Boolean.TRUE);
        securities.add(security);
      }
    }
    return securities;
  }

}

class SecurityConf{
  private String name;
  private String symbol;
  private boolean open;
  private boolean intraDay;
  private boolean close;
  private boolean endOfDay;
  private boolean participated;
  private String note;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public boolean isOpen() {
    return open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }

  public boolean isIntraDay() {
    return intraDay;
  }

  public void setIntraDay(boolean intraDay) {
    this.intraDay = intraDay;
  }

  public boolean isClose() {
    return close;
  }

  public void setClose(boolean close) {
    this.close = close;
  }

  public boolean isEndOfDay() {
    return endOfDay;
  }

  public void setEndOfDay(boolean endOfDay) {
    this.endOfDay = endOfDay;
  }

  public boolean isParticipated() {
    return participated;
  }

  public void setParticipated(boolean participated) {
    this.participated = participated;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
  
}