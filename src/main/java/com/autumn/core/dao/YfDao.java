package com.autumn.core.dao;

import com.autumn.core.model.HistoricalQuote;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface YfDao {
  public static final String BASE_QUOTE_URL = "http://download.finance.yahoo.com/d/";
  public static final String BASE_HISTORICAL_QUOTE_URL = "http://ichart.yahoo.com/table.csv";
  public static final String BASE_QUOTE_DETAIL_URL = "https://finance.yahoo.com/q?s=";

  public static final String DAILY_INCREMENT = "d";
  public static final String WEEKLY_INCREMENT = "w";
  public static final String MONTHLY_INCREMENT = "m";
  
  public static final String CARET = "%5E"; // character ^
  public static final String DOW = CARET+"DJI";
  public static final String NASDAQ = CARET+"IXIC";
  public static final String SP500 = CARET+"GSPC";
  public static final String RUSSELL_1000 = CARET+"RUI";
  public static final String RUSSELL_2000 = CARET+"RUT";
  public static final String RUSSELL_3000 = CARET+"RUA";
  public static final String SP500_VOLATILITY = CARET+"VIX";
  public static final String IDX_TECHOLOGY = "XLK";
  public static final String IDX_CONSUMER_DISCRETE_SELECT = "XLY";
  public static final String IDX_HEALTH_CARE = "XLV";
  public static final String IDX_MATERIALS = "XLB";
  public static final String IDX_INDUSTRIAL = "XLI";
  public static final String IDX_CONSUMER_STABLES = "XLP";
  public static final String IDX_ENGERY = "XLE";
  public static final String IDX_UTILITIES = "XLU";
  public static final String IDX_FINANCE = "XLF";
  public static final String IDX_RETAIL = "XRT";
  public static final String IDX_HOME_BUILDERS = "XHB";
  public static final String IDX_HEALTH_CARE_EQUIPMENT = "XHE";
  public static final String IDX_HEALTH_CARE_SERVICES = "XHS";
  
  public static final String ASK = "a";
  public static final String AVG_DAILY_VOL = "a2";
  public static final String ASK_SIZE = "a5";
  public static final String BID = "b";
  public static final String ASK_RT = "b2";
  public static final String BID_RT = "b3";
  public static final String BOOK_VAL = "b4";
  public static final String BOOK_SIZE = "b6";
  public static final String CHG_AND_PCT_CHG = "c";
  public static final String CHG = "c1";
  public static final String COMMISSION = "c3";
  public static final String CHG_RT = "c6";
  public static final String AH_CHG_RT = "c8";
  public static final String DIV = "d";
  public static final String LST_TRD_DATE = "d1";
  public static final String TRD_DATE = "d2";
  public static final String EARNINGS = "e";
  public static final String ERR_INDICATE = "e1";
  public static final String EPS_EST_CY = "e7";
  public static final String EPS_EST_NY = "e8";
  public static final String EPS_EST_NQ = "e9";
  public static final String FLOAT_SHARES = "f6";
  public static final String DAYS_LO = "g";
  public static final String DAYS_HI = "h";
  public static final String _52WK_LO = "j";
  public static final String _52WK_HI = "k";
  public static final String HOLDINGS_GAIN_PCT = "g1";
  public static final String ANNU_GAIN = "g3";
  public static final String HOLDINGS_GAIN = "g4";
  public static final String HOLDINGS_GAIN_PCT_RT = "g5";
  public static final String HOLDINGS_GAIN_RT = "g6";
  public static final String MORE_INFO = "i";
  public static final String ORDER_BOOK_RT = "i5";
  public static final String MARKET_CAP = "j1";
  public static final String MARKET_CAP_RT = "j3";
  public static final String EBITDA = "j4";
  public static final String CHG_FRM_52WK_LO = "j5";
  public static final String PCT_CHG_FRM_52WK_LO = "j6";
  public static final String LST_TRD_RT = "k1";
  public static final String CHG_PCT_RT = "k2";
  public static final String LST_TRD_SIZE = "k3";
  public static final String CHG_FRM_52W_HI = "k4";
  public static final String PCT_CHG_FRM_52W_HI = "k5";
  public static final String LST_TRD_AND_TIME = "l";
  public static final String LST_TRD = "l1";
  public static final String HI_LMT = "l2";
  public static final String LO_LMT = "l3";
  public static final String DAYS_RANGE = "m";
  public static final String DAYS_RANGE_RT = "m2";
  public static final String _50D_MOVING_AVG = "m3";
  public static final String _200D_MOVING_AVG = "m4";
  public static final String CHG_FRM_200D_MOVING_AVG = "m5";
  public static final String PCT_CHG_FRM_200D_MOVING_AVG = "m6";
  public static final String CHG_FRM_50D_MOVING_AVG = "m7";
  public static final String PCT_CHG_FRM_50D_MOVING_AVG = "m8";
  public static final String NAME = "n";
  public static final String NOTES = "n4";
  public static final String OPEN = "o";
  public static final String PREVIOUS_CLOSE = "p";
  public static final String PRICE_PAID = "p1";
  public static final String PCT_CHG = "p2";
  public static final String PRICE_SALES = "p5";
  public static final String PRICE_BOOK = "p6";
  public static final String EX_DIV_DATE = "q";
  public static final String PE = "r";
  public static final String DIV_PAY_DATE = "r1";
  public static final String PE_RT = "r2";
  public static final String PEG_RATIO = "r5";
  public static final String PRICE_EPS_EST_CY = "r6";
  public static final String PRICE_EPS_EST_NY = "r7";
  public static final String SYMBOL = "s";
  public static final String SHARES_OWNED = "s1";
  public static final String SHORT_RATIO = "s7";
  public static final String LST_TRD_TIME = "t1";
  public static final String TRD_LINKS = "t6";
  public static final String TICKER_TREND = "t7";
  public static final String _1Y_TARGET_PRICE = "t8";
  public static final String VOL = "v";
  public static final String HOLDINGS_VAL = "v1";
  public static final String HOLDINGS_VAL_RT = "v7";
  public static final String _52WK_RANGE = "w";
  public static final String DAYS_VAL_CHG = "w1";
  public static final String DAYS_VAL_CHG_RT = "w4";
  public static final String STOCK_EXCHANGE = "x";
  public static final String DIV_YIELD = "y";
  
  // Get quote using old yahoo finance api
  public List<String> getQuote(List<String> symbols, String requests);
  
  // Get quote from yahoo screen using jsoup
  public List<String> getQuote(List<String> symbols);
  
  public Map<Date,HistoricalQuote> getHisoricalQuotes(String symbol, Date startDate, Date endDate, String increment);
  public Map<Date,HistoricalQuote> getHisoricalQuotes(String symbol, String pastPeriod, String increment);
  // Get historic quotes from yahoo screen using selenium
  public Map<Date,HistoricalQuote> getHisoricalQuotes(String symbol);
}
