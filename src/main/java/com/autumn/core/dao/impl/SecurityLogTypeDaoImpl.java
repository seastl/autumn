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
  
  @Value("${autumn.securitiesGeneral}")
  private String securitiesGeneral; 
          
  @Value("${autumn.securitiesNn}")
  private String securitiesNn; 
          
  @PostConstruct
  public void postConstruct() throws Exception {
    securitiesGeneralList = JsonUtils.getObjectListFromJSONStrings(securitiesGeneral, SecurityConf.class);
    securitiesNnList = JsonUtils.getObjectListFromJSONStrings(securitiesNn, SecurityConf.class);
    
    System.out.println("*** KL: SecurityLogTypeDaoImpl postConstruct securitiesGeneral=" + securitiesGeneral);
    System.out.println("*** KL: name=" + securitiesGeneralList.get(30).getName() + " note=" + securitiesGeneralList.get(30).getNote());
  }
  

  @Override
  public List<SecurityLogType> getSecuritiesForIntraDay() {
    List<SecurityLogType> securities = new ArrayList();
    SecurityLogType security = null;
    
    for (SecurityConf sec : securitiesGeneralList) {
      if (sec.isIntraDay()) {
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), null, null),
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
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), null, null),
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
        security = new SecurityLogType(new Security(0, sec.getName(), sec.getSymbol(), null, null),
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
    security = new SecurityLogType(new Security(1001, "Goldman Sachs Small Cap Value Instl", "GSSIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1002, "Vanguard Treasury Money Market Investor", "VUSXX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1003, "Janus Global Research I", "JWWFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1004, "Baron Growth Instl", "BGRIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1005, "Calamos International Growth I", "CIGIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1006, "JPMorgan Mid Cap Growth R6", "JMGMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1007, "Dodge & Cox Income", "DODIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "39%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1008, "JPMorgan Mid Cap Value Instl", "FLMVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "16%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1009, "Templeton Foreign Smaller Companies Adv", "FTFAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1010, "JPMorgan Mid Cap Growth R6", "JMGMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1011, "JHancock Disciplined Value R6", "JDVWX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "22.5%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1012, "PIMCO All Asset Instl", "PAAIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1013, "Columbia Select Large Cap Growth Z", "UMLGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1014, "Vanguard STAR Inv", "VGSTX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1015, "Vanguard Institutional Index I", "VINIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "22.5%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForLb() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1101, "ALZGI NFJ DIV VAL A", "PNEAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1102, "BLKRK FLEX EQ A", "BMCAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1103, "COL MARSICO GROWTH A", "NMGIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1104, "DREYFUS S&P 500 INDX", "PEOPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "60%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1105, "FA EQUITY INCOME A", "FEIAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1106, "OPPHMR MS SELECT A", "OMSOX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1107, "PUTNAM VOYAGER A", "PVOYX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1108, "GS GROWTH OPPS A", "GGOAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1109, "OPPHMR MS MID CAP A", "OPMSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1110, "ABF SM CAP VAL ADV", "AASSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1111, "FA SMALL CAP A", "FSCDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "20%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1112, "INVS DEVELOP MKTS A", "GTDDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1113, "OPPHMR INTL GROWTH A", "OIGAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1114, "FA REAL ESTATE A", "FHEAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1115, "FA FREEDOM 2005 A", "FFAVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1116, "FA FREEDOM 2010 A", "FACFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1117, "FA FREEDOM 2015 A", "FFVAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1118, "FA FREEDOM 2020 A", "FDAFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1119, "FA FREEDOM 2025 A", "FATWX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1120, "FA FREEDOM 2030 A", "FAFEX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1121, "FA FREEDOM 2035 A", "FATHX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1122, "FA FREEDOM 2040 A", "FAFFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1123, "FA FREEDOM 2045 A", "FFFZX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1124, "FA FREEDOM 2050 A", "FFFLX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1125, "FA FREEDOM 2055 A", "FHFAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1126, "FA FREEDOM INC A", "FAFAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1127, "AM CENT DIV BOND A", "ADFAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "20%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1128, "JPM HIGH YIELD A", "OHYAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1129, "FID PRIME FUND", "FDAXX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;  
  }

  @Override
  public List<SecurityLogType> getSecuritiesForIc() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1201, "DODGE & COX STOCK", "DODGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".52%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1202, "FID BLUE CHIP GR", "FBGRX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".8%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1203, "SPTN 500 INDEX ADV", "FUSVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, ".07% 59%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1204, "FID MID CAP STOCK", "FMCSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".76%,30d"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1205, "SPTN MID CAP IDX ADV", "FSCKX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".2%,30d"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1206, "WFA SPL MIDCP VAL R6", "WFPRX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".81%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1207, "JANUS TRITON N", "JGMNX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, ".68% 6%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1208, "JPM US SMALL CO R6", "JUSMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".78%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1209, "SPTN SM CAP IDX ADV", "FSSVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".23%,90d"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1210, "UM BEHAVIORAL VAL R6", "UBVFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "1.48%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1211, "AF NEW WORLD R6", "RNWGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".65%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1212, "OAKMARK INTL I", "OAKIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".95%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1213, "SPTN INTL INDEX ADV", "FSIVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".17%,90d"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1214, "FID STRAT DIV & INC", "FSDIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, ".74% 21%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1215, "TRP RETIREMENT 2005", "TRRFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".59%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1216, "TRP RETIREMENT 2010", "TRRAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".59%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1217, "TRP RETIREMENT 2015", "TRRGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".63%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1218, "TRP RETIREMENT 2020", "TRRBX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".67%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1219, "TRP RETIREMENT 2025", "TRRHX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".7%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1220, "TRP RETIREMENT 2030", "TRRCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".73%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1221, "TRP RETIREMENT 2035", "TRRJX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".75%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1222, "TRP RETIREMENT 2040", "TRRDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".76%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1223, "TRP RETIREMENT 2045", "TRRKX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".76%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1224, "TRP RETIREMENT 2050", "TRRMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".76%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1225, "TRP RETIREMENT 2055", "TRRNX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".76%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1226, "TRP RETIREMENT 2060", "TRRNX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".76%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1227, "JPM LTD DURATION R6", "JUSUX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, ".4% 14%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1228, "PIMCO TOTAL RETURN D", "PTTDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".75%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1229, "TRP GNMA", "PRGMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, ".61%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;  
  }
  
  @Override
  public List<SecurityLogType> getSecuritiesForSg() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1301, "EQUITY INCOME FUND", "PRFDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "70%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1302, "GROWTH STOCK FUND", "PRGFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1303, "INTERNATIONAL GROWTH & INCOME", "TRIGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1304, "MEDIA & TELECOMMUNICATIONS", "PRMTX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1305, "MID-CAP GROWTH FUND", "RPMGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "10%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1306, "NEW HORIZONS FUND", "PRNHX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "10%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1307, "SCIENCE & TECHNOLOGY FUND", "PRSCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "10%"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1308, "SCOUT MID CAP", "UMBMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1309, "SPECTRUM GROWTH FUND", "PRSGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1310, "VANGUARD SMALL CAP INDEX, ADM", "VSMAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1311, "SPECTRUM INCOME FUND", "RPSIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1312, "RETIREMENT 2005 FUND", "TRRFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1313, "RETIREMENT 2010 FUND", "TRRAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1314, "RETIREMENT 2015 FUND", "TRRGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1315, "RETIREMENT 2020 FUND", "TRRBX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1316, "RETIREMENT 2025 FUND", "TRRHX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1317, "RETIREMENT 2030 FUND", "TRRCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1318, "RETIREMENT 2035 FUND", "TRRJX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1319, "RETIREMENT 2040 FUND", "TRRDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1320, "RETIREMENT 2045 FUND", "TRRKX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1321, "RETIREMENT 2050 FUND", "TRRMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1322, "RETIREMENT 2055 FUND", "TRRNX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1323, "RETIREMENT INCOME FUND", "TRRIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForFidEquity() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1401, "", "FBCVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1402, "", "FBGRX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "325"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1403, "", "FCNTX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "323"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1404, "", "FCPGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1405, "", "FCPVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1406, "", "FDCAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1407, "", "FDEGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1408, "", "FDEQX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1409, "", "FDFFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1410, "", "FDGFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, true, "2417"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1411, "", "FDGRX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1412, "", "FDSCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1413, "", "FDSSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1414, "", "FDSVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1415, "", "FDVLX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1416, "", "FEQIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1417, "", "FEQTX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1418, "", "FEXPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1419, "", "FFIDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1420, "", "FFNOX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1421, "", "FFTYX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1422, "", "FGRIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1423, "", "FGRTX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1424, "", "FIREX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1425, "", "FLCSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1426, "", "FLPSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1427, "", "FLVCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1428, "", "FMAGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1429, "", "FMCSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1430, "", "FMILX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1431, "", "FNCMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1432, "", "FOCPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1433, "", "FRESX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1434, "", "FRIFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1435, "", "FSCRX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1436, "", "FSEMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1437, "", "FSLCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1438, "", "FSLGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1439, "", "FSLSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1440, "", "FSLVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1441, "", "FSMGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1442, "", "FSMVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1443, "", "FSTMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1444, "", "FTQGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1445, "", "FTRNX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1446, "", "FUSEX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1447, "", "FVDFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForFidInternational() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1501, "", "FIVFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1502, "", "FICDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1503, "", "FHKCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1504, "", "FDIVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1505, "", "FEMKX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1506, "", "FECAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1507, "", "FIEUX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1508, "", "FGBLX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1509, "", "FIGRX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1510, "", "FISMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1511, "", "FJPNX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1512, "", "FJSCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1513, "", "FLATX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1514, "", "FNORX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1515, "", "FOSFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1516, "", "FPBFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1517, "", "FSEAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1518, "", "FWWFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1519, "", "FSIIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForFidSector() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1601, "", "FBIOX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Biotech"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1602, "", "FBMPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Multimedia"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1603, "", "FBSOX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "IT Services"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1604, "", "FCYIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Industrials"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1605, "", "FDCPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Computers"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1606, "", "FDFAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Consumer Staples"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1607, "", "FDLSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Leisure"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1608, "", "FIDSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Financial Services"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1609, "", "FIUIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Telecom & Utilities"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1610, "", "FNARX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Natural Resources"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1612, "", "FPHAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Pharmaceuticals"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1613, "", "FSAGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Gold"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1614, "", "FSAIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Air Transportation"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1615, "", "FSAVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Automotive"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1616, "", "FSCGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Industrial Equipment"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1617, "", "FSCHX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Chemicals"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1618, "", "FSCPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Consumer Descret"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1619, "", "FSCSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Software & Computer"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1620, "", "FSDAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Defense & Aerospace"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1621, "", "FSDCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Communication Equipment"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1622, "", "FSDPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Materials"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1623, "", "FSELX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Electronics"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1624, "", "FSENX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Energy"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1625, "", "FSESX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Energy Services"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1626, "", "FSHCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Medical Delivery"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1627, "", "FSHOX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Construction & Hsg"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1628, "", "FSLBX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Brokerage & Investment Mgmt"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1629, "", "FSLEX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Environment & Alt Energy"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1631, "", "FSMEX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Medical Equipment"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1632, "", "FSNGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Natural Gas"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1633, "", "FSPCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Insurance"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1634, "", "FSPHX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Health Care"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1635, "", "FSPTX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Technology"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1636, "", "FSRBX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Banking"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1637, "", "FSRFX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Transportation"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1638, "", "FSRPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Retailing"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1639, "", "FSTCX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Telecommunications"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1640, "", "FSUTX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Utilities"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1641, "", "FSVLX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Consumer Finance"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1642, "", "FWRLX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Wireless"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForFidIShares() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1701, "", "DVY", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Dividend"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1702, "", "IWF", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Russell 1000 Growth"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1703, "", "IWB", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Russell 1000"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1704, "", "IWD", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Russell 1000 Value"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1705, "", "IWO", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Russell 2000 Growth"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1706, "", "IWM", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Russell 2000"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1707, "", "IWN", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Russell 2000 Value"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1708, "", "IWV", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Russell 3000"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1709, "", "IVW", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "S&P 500 Growth"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1710, "", "IVV", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Core S&P 500"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1711, "", "IVE", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "S&P 500 Value"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1712, "", "IJK", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "S&P Mid-Cap 400 Growth"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1713, "", "IJH", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Core S&P Mid-Cap"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1714, "", "IJJ", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "S&P Mid-Cap 400 Value"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1715, "", "IJT", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "S&P Small-Cap 600 Growth"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1716, "", "IJR", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Core S&P Small-Cap"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1717, "", "IJS", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "S&P Small-Cap 600 Value"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1718, "", "IDV", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Intl Select Dividend"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1719, "", "ACWI", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "MSCI ACWI"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1720, "", "ACWX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "MSCI ACWI ex US"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1721, "", "EFA", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "MSCI EAFE"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1722, "", "SCZ", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "MSCI EAFE Small-Cap"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1723, "", "EEM", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "MSCI Emerging Markets"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1724, "", "AGG", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Core US Aggregate Bond"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1725, "", "TIP", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "TIPS Bond"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1726, "", "LQD", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Corp Bond"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1727, "", "HYG", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Corp Bond"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1728, "", "EMB", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Emerg Markets Bond"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1729, "", "MUB", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "Muni BOnd"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1730, "", "IYR", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN, "US Real Estate"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;  
  }

  
  @Override
  public List<SecurityLogType> getSecuritiesForDow30() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1801, "", "MMM", SecurityType.US_STOCK, Sector.INDUSTRIAL),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1802, "", "AA", SecurityType.US_STOCK, Sector.INDUSTRIAL, true, "s2000@9.561"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1803, "", "AXP", SecurityType.US_STOCK, Sector.FINANCE),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1804, "", "AAPL", SecurityType.US_STOCK, Sector.TECHNOLOGY),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1805, "", "BAC", SecurityType.US_STOCK, Sector.FINANCE),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1806, "", "BA", SecurityType.US_STOCK, Sector.INDUSTRIAL),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1807, "", "CAT", SecurityType.US_STOCK, Sector.INDUSTRIAL, true, "s200@82"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1808, "", "CVX", SecurityType.US_STOCK, Sector.MATERIALS, true, "b200@77.14, b200@101.93"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1809, "", "CSCO", SecurityType.US_STOCK, Sector.TECHNOLOGY),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1810, "", "DD", SecurityType.US_STOCK, Sector.MATERIALS),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1811, "", "XOM", SecurityType.US_STOCK, Sector.INDUSTRIAL),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1812, "", "GE", SecurityType.US_STOCK, Sector.INDUSTRIAL),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1813, "", "HPQ", SecurityType.US_STOCK, Sector.TECHNOLOGY, true, "s1590@14.39"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1814, "", "HD", SecurityType.US_STOCK, Sector.SERVICES),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1815, "", "INTC", SecurityType.US_STOCK, Sector.TECHNOLOGY, true, "s200@32.50"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1816, "", "IBM", SecurityType.US_STOCK, Sector.TECHNOLOGY),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1817, "", "JNJ", SecurityType.US_STOCK, Sector.HEALTH_CARE),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1818, "", "JPM", SecurityType.US_STOCK, Sector.FINANCE),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1819, "", "GS", SecurityType.US_STOCK, Sector.FINANCE),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1820, "", "MCD", SecurityType.US_STOCK, Sector.SERVICES),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1821, "", "MRK", SecurityType.US_STOCK, Sector.HEALTH_CARE),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1822, "", "MSFT", SecurityType.US_STOCK, Sector.TECHNOLOGY, true, "s200@46.77"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1823, "", "PFE", SecurityType.US_STOCK, Sector.HEALTH_CARE, true, "b300@32.13"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1824, "", "PG", SecurityType.US_STOCK, Sector.CONSUMER_STAPLES, true, "s130@80.91"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1825, "", "KO", SecurityType.US_STOCK, Sector.CONSUMER_STAPLES),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1826, "", "TRV", SecurityType.US_STOCK, Sector.FINANCE),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1827, "", "UTX", SecurityType.US_STOCK, Sector.INDUSTRIAL),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1828, "", "VZ", SecurityType.US_STOCK, Sector.TECHNOLOGY, true, "b200@47.96"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1829, "", "WMT", SecurityType.US_STOCK, Sector.SERVICES, true, "b100@63.95"),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1830, "", "DIS", SecurityType.US_STOCK, Sector.SERVICES),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
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