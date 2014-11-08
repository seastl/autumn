package com.autumn.core.dao.impl;

import com.autumn.core.dao.SecurityLogTypeDao;
import static com.autumn.core.dao.YfDao.*;
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
    security = new SecurityLogType(new Security(1, "Dow Jones index", DOW, SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00:00"), Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(2, "Nasdaq index", NASDAQ, SecurityType.US_INDEX, Sector.NASDAQ),
                                   new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00:00"), Time.valueOf("13:05:00")),
                                   Boolean.TRUE); // Nasdaq
    securities.add(security);
    security = new SecurityLogType(new Security(3, "S&P 500 index", SP500, SecurityType.US_INDEX, Sector.SP_500),
                                   new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00:00"), Time.valueOf("13:05:00")),
                                   Boolean.TRUE); // S&P 500
    securities.add(security);
    
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForStartOfDayLogging() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1, "Dow Jones index", DOW, SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(2, "Nasdaq index", NASDAQ, SecurityType.US_INDEX, Sector.NASDAQ),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(3, "S&P 500 index", SP500, SecurityType.US_INDEX, Sector.SP_500),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForEndOfDayLogging() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1, "Dow Jones index", DOW, SecurityType.US_INDEX, Sector.DOW),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(2, "Nasdaq index", NASDAQ, SecurityType.US_INDEX, Sector.NASDAQ),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(3, "S&P 500 index", SP500, SecurityType.US_INDEX, Sector.SP_500),
                                   new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00:00"), null),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;
  }

  @Override
  public List<SecurityLogType> getSecuritiesForNn() {
    List<SecurityLogType> securities = new ArrayList();

    SecurityLogType security = null;
    security = new SecurityLogType(new Security(1001, "Columbia Acorn Select Z", "ACTWX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1002, "AllianzGI NFJ International Value Instl", "ANJIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1003, "Artisan International Investor", "ARTIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
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
    security = new SecurityLogType(new Security(1006, "American Century Capital Preser", "CPFXX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1007, "Dodge & Cox Stock", "DODGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1008, "Dodge & Cox Income", "DODIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1009, "Columbia International Value Z", "EMIEX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1010, "JPMorgan Mid Cap Value Instl", "FLMVX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1011, "Templeton Foreign Smaller Companies Adv", "FTFAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1012, "Ivy Mid Cap Growth I", "IYMIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1013, "JHancock Disciplined Value R6", "JDVWX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1014, "Janus Global Research I", "JRGIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1015, "PIMCO All Asset Instl", "PAAIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1016, "Columbia Select Large Cap Growth Z", "UMLGX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1017, "Vanguard STAR Inv", "VGSTX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1018, "Victory Inst Diversified Stock", "VIDSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1019, "Vanguard Institutional Index I", "VINIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
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
    security = new SecurityLogType(new Security(1104, "DREYFUS S&P 500 INDX", "PEOPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
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
    security = new SecurityLogType(new Security(1111, "FA SMALL CAP A", "FSCDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
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
    security = new SecurityLogType(new Security(1127, "AM CENT DIV BOND A", "ADFAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
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
    security = new SecurityLogType(new Security(1201, "AllianceBern Discovery Value I", "ABSIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1202, "Invesco Equity and Income A", "ACEIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1203, "Invesco Comstock A", "ACSTX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1204, "Buffalo Small Cap", "BUFSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1205, "Goldman Sachs Small Cap Value Instl", "GSSIX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1206, "Lord Abbett Short Duration Income A", "LALDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1207, "Lazard Emerging Markets Equity Instl", "LZEMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1208, "MainStay Large Cap Growth R1", "MLRRX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1209, "Prudential Jennison Mid Cap Growth A", "PEEAX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1210, "Dreyfus MidCap Index", "PESPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1211, "T. Rowe Price GNMA", "PRGMX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1212, "PIMCO Total Return D", "PTTDX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1213, "American Funds Europacific Growth R4", "REREX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1214, "Schwab International Index", "SWISX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1215, "Schwab S&P 500 Index", "SWPPX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1216, "Schwab Small Cap Index", "SWSSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    security = new SecurityLogType(new Security(1217, "Schwab Total Stock Market Index", "SWTSX", SecurityType.US_MUTUAL_FUND, Sector.UNKNOWN),
                                   new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05:00")),
                                   Boolean.TRUE);
    securities.add(security);
    
    return securities;  
  }
  
  
}
