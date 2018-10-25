package com.autumn.core.util;

import com.autumn.core.dao.YfDao;
import com.autumn.core.model.HistoricalQuote;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
  
  public static final String COMMA_SPLIT_REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
  
  /**
   * Returns an array of two Date objects. 
   * The 1st one is the start date that is # days before the end date.
   * The 2nd one is the end date(today- 1weekday).
   * They are inclusive of the number days specified.
   * 
   * @param numberOfPastWeekdays
   * @return 
   */
  public Date[] getDateRangeForNumberOfPastWeekdays(int numberOfPastWeekdays) {
    Date startDate = null;
    Date endDate = null;
    
    LocalDate newDate = new LocalDate();
    int i = 0;
    while (i < numberOfPastWeekdays + 1) {
      newDate = newDate.minusDays(1);
      if (newDate.getDayOfWeek() <= DateTimeConstants.FRIDAY) {
        i++;
      }
      if (i == 1) {
        endDate = newDate.toDateTimeAtStartOfDay().toDate();
      }
    }
    startDate = newDate.toDateTimeAtStartOfDay().toDate();
    
    Date[] dateRange = new Date[]{startDate, endDate};
    return dateRange;
  }
  

  /**
   * 
   * @param pastWorkPeriod e.g. 1d, 10d, 2m, 1y
   * @return 
   */
  public Date[] getDateRangeForPastWorkPeriod(String pastWorkPeriod) {
    String timeUnit = pastWorkPeriod.substring(pastWorkPeriod.length()-1);
    int timeValue = Integer.parseInt(pastWorkPeriod.substring(0, pastWorkPeriod.length()-1));
            
    if (!pastWorkPeriod.endsWith("d") && !pastWorkPeriod.endsWith("w") && !pastWorkPeriod.endsWith("m") && !pastWorkPeriod.endsWith("y")) {
      throw new RuntimeException("Past period must end with d, m, y.");
    }
    
    LocalDate newDate = new LocalDate();
    
    // Determines endDate
    while (newDate.getDayOfWeek() > DateTimeConstants.FRIDAY) {
      newDate = newDate.minusDays(1);
    }
    Date endDate = newDate.toDateTimeAtStartOfDay().toDate();
    
    // Determines startDate
    if (timeUnit.equals("d")) {
      for (int i = 0; i < timeValue-1; i++) {
        newDate = newDate.minusDays(1);
        while (newDate.getDayOfWeek() > DateTimeConstants.FRIDAY) {
          newDate = newDate.minusDays(1);
        }
      }
    } else if (timeUnit.equals("w")) {
      newDate = newDate.minusWeeks(timeValue).plusDays(1);
    } else if (timeUnit.equals("m")) {
      newDate = newDate.minusMonths(timeValue).plusDays(1);
    } else if (timeUnit.equals("y")) {
      newDate = newDate.minusYears(timeValue).plusDays(1);
    }

    // Move forward to a weekday if necessary
    while (newDate.getDayOfWeek() > DateTimeConstants.FRIDAY) {
      newDate = newDate.plusDays(1);
    }
    Date startDate = newDate.toDateTimeAtStartOfDay().toDate();
    
    Date[] dateRange = new Date[]{startDate, endDate};
    return dateRange;
  }
  
  
  /**
   * Remove double quotes from the string.
   * If includePhrase is false, double quotes won't be removed if it contains space.
   * @param in
   * @return 
   */
  private String removeDoubleQuotes(String in, boolean includePhrase) {
    if (in.contains(" ")) {
      if (includePhrase) {
        return in.replace("\"", "");
      } else {
        return in;
      }
    } else {
      return in.replace("\"", "");
    }
  }
  

  /**
   * Remove not-needed texts for dividend.
   * 
   * @param in: "4.48 (3.7%)
   * @return: "3.7%"
   */
  private String removeForDividend(String in) {
    String out = null;
    if (StringUtils.isNotEmpty(in) && in.contains("(") && in.contains(")")) {
      out = StringUtils.substringBetween(in, "(", ")");
    } else {
      out = in;
    }
    return out;
  }
  

  public String getPercentDisplayForWeekdaysAgo(Map<Date, HistoricalQuote> historicalQuotes, String pastWorkPeriod, boolean formatToDisplay) {
    String percentForDaysAgo = null;
    Date[] dateRange = getDateRangeForPastWorkPeriod(pastWorkPeriod);
    HistoricalQuote quoteDaysAgo = historicalQuotes.get(dateRange[0]);
    HistoricalQuote quoteToday = historicalQuotes.get(dateRange[1]);
    if (quoteDaysAgo != null && quoteToday != null) {
      float openDaysAgo = quoteDaysAgo.getOpen();
      float closeToday = quoteToday.getClose();
      float percentChange = ((closeToday - openDaysAgo) * 100) / openDaysAgo;
      if (formatToDisplay) {
        if (percentChange > 0.0) {
          percentForDaysAgo = "<font color='green'>" + String.format("%+.2f%%", percentChange) + "</font>";
        } else if (percentChange < 0.0) {
          percentForDaysAgo = "<font color='red'>" + String.format("%+.2f%%", percentChange) + "</font>";
        } else {
          percentForDaysAgo = String.format("%+.2f%%", percentChange);
        }
      } else {
        percentForDaysAgo = String.format("%+.2f", percentChange);
      }
    } else {
      percentForDaysAgo = "n/a";
    }
    
    return percentForDaysAgo;
  }
  

  /**
   * Because today's close is not available until late of the day in the historical quote api,
   * this function uses the today's close from today quote api instead.
   * 
   * @param lastQuote
   * @param historicalQuotes
   * @param pastWorkPeriod
   * @param formatToDisplay
   * @return 
   */
  public String getPercentDisplayForWeekdaysAgo(String lastQuote, Map<Date, HistoricalQuote> historicalQuotes, String pastWorkPeriod, boolean formatToDisplay) {
    String percentForDaysAgo = null;
    
    try {
      Date[] dateRange = getDateRangeForPastWorkPeriod(pastWorkPeriod);
      HistoricalQuote quoteDaysAgo = historicalQuotes.get(dateRange[0]);
      if (quoteDaysAgo != null && quoteDaysAgo.getOpen() > 0.0 && lastQuote != null) {
        float openDaysAgo = quoteDaysAgo.getOpen();
        float closeToday = Float.parseFloat(lastQuote);
        float percentChange = ((closeToday - openDaysAgo) * 100) / openDaysAgo;
        if (formatToDisplay) {
          if (percentChange > 0.0) {
            percentForDaysAgo = "<font color='green'>" + String.format("%+.2f%%", percentChange) + "</font>";
          } else if (percentChange < 0.0) {
            percentForDaysAgo = "<font color='red'>" + String.format("%+.2f%%", percentChange) + "</font>";
          } else {
            percentForDaysAgo = String.format("%+.2f%%", percentChange);
          }
        } else {
          percentForDaysAgo = String.format("%+.2f", percentChange);
        }
      } else {
        percentForDaysAgo = "n/a";
      }
    } catch (Exception ex) {
      percentForDaysAgo = "n/a";
    }
    
    return percentForDaysAgo;
  }
  

  public StringBuilder createHtmlBegin(StringBuilder sb) {
    sb.append("\n")
      .append("<html>\n")
      .append("  <head>\n")
      .append("    <style>\n")
      .append("      table, th, td {border: 1px solid white; border-collapse: collapse;}\n")
      .append("      th,td {padding: 1px; font-family: Arial,sans-serif; font-size: 12px;}\n")
      .append("      th {text-align: left;}\n")
      .append("      table#t01 tr:nth-child(even) {background-color: #eee;}\n")
      .append("      table#t01 tr:nth-child(odd) {background-color: #fff;}\n")
      .append("      table#t01 th {color: white; background-color: gray;}\n")
      .append("    </style>\n")
      .append("  </head>\n")
      .append("  <body>\n");
    return sb;
  }
  

  public StringBuilder createHtmlEnd(StringBuilder sb) {
    sb.append("  </body>\n")
      .append("</html>\n");
    return sb;
  }
  
  
  public StringBuilder createHtmlTable(StringBuilder sb, 
                                       String caption, 
                                       String[] headers,
                                       Map<String,Boolean> participations, 
                                       Map<String,String> notes,
                                       List<String> csvResults) {
    sb.append("  <table id='t01' style='width:700px'>\n")
      .append("    <caption><h3 align='left'>").append(caption).append("</h3></caption>\n")
      .append("    <tr>\n");
    
    for (String header : headers) {
      sb.append("      <th>").append(header).append("</th>\n");
    }
    sb.append("    </tr>\n");
    
    String symbol = null;
    for (String csvResult : csvResults) {
      sb.append("    <tr>\n");
      String[] results = csvResult.split(COMMA_SPLIT_REGEX);
      try {
        symbol = URLEncoder.encode(removeDoubleQuotes(results[0], true), "UTF-8");
      } catch (Exception ex) {
        throw new RuntimeException("Failed to URL encode " + removeDoubleQuotes(results[0], true) + ".", ex);
      }
      
      for (int i = 0; i < results.length; i++) {
        String result = results[i];
        if (participations != null && participations.get(symbol)) {
          if (i == 0) { // symbol
            sb.append("      <td><b><a href='" + YfDao.BASE_QUOTE_DETAIL_URL + symbol + "' target='_blank'>").append(removeDoubleQuotes(result, true)).append("</a></b></td>\n");
          } else if (i == 1) { // name
            sb.append("      <td><b>").append(removeDoubleQuotes(result, true)).append("</b></td>\n");
          } else if (i == 2) { // dividend
            sb.append("      <td><b>").append(removeForDividend(result)).append("</b></td>\n");
          } else if (i == 3 || i == 4) { // prv, ask
            sb.append("      <td><b>").append(formatTo2Dec( removeDoubleQuotes(result, true))).append("</b></td>\n");
          } else if (i == 5) { // %chg
            sb.append("      <td><b>").append(removeDoubleQuotes(result, true)).append("</b></td>\n");
          }
        } else {
          if (i == 0) { // symbol
            sb.append("      <td><a href='" + YfDao.BASE_QUOTE_DETAIL_URL + symbol + "' target='_blank'>").append(removeDoubleQuotes(result, true)).append("</a></b></td>\n");
          } else if (i == 1) { // name
            sb.append("      <td>").append(removeDoubleQuotes(result, true)).append("</b></td>\n");
          } else if (i == 2) { // dividend
            sb.append("      <td>").append(removeForDividend(result)).append("</b></td>\n");
          } else if (i == 3 || i == 4) { // prv, ask
            sb.append("      <td>").append(formatTo2Dec( removeDoubleQuotes(result, true))).append("</b></td>\n");
          } else if (i == 5) { // %chg
            sb.append("      <td>").append(removeDoubleQuotes(result, true)).append("</b></td>\n");
          }
        }
      }
      
      if (notes != null) {
        if (participations != null && participations.get(symbol)) {
          sb.append("      <td><b>").append(disp(notes.get(symbol))).append("</b></td>\n");
        } else {
          sb.append("      <td>").append(disp(notes.get(symbol))).append("</td>\n");
        }
      } else {
        sb.append("      <td></td>\n");
      }
      sb.append("    </tr>\n");
    }
    sb.append("  </table>\n");
    
    return sb;
  }
  
  
  public StringBuilder createHtmlTable(StringBuilder sb, 
                                       String caption, 
                                       String[] headers, 
                                       Map<String,Boolean> participations, 
                                       Map<String,String> notes, 
                                       List<String> csvResults, 
                                       Map<String,Map<Date,HistoricalQuote>> securitiesHistQuotes) {
    sb.append("  <table id='t01' style='width:900px'>\n")
      .append("    <caption><h3 align='left'>").append(caption).append("</h3></caption>\n")
      .append("    <tr>\n");
    
    for (String header : headers) {
      sb.append("      <th>").append(header).append("</th>\n");
    }
    sb.append("    </tr>\n");
    
    String symbol = null;
    for (String csvResult : csvResults) {
      String[] splitResults = csvResult.split(COMMA_SPLIT_REGEX);
      try {
        symbol = URLEncoder.encode(removeDoubleQuotes(splitResults[0], true), "UTF-8");
      } catch (Exception ex) {
        throw new RuntimeException("Failed to URL encode " + removeDoubleQuotes(splitResults[0], true) + ".", ex);
      }
      String lastQuote = removeDoubleQuotes(splitResults[3], true);
      
      sb.append("    <tr>\n");
      for (int i = 0; i < splitResults.length; i++) {
        String splitResult = splitResults[i];
        if (participations != null && participations.get(symbol)) {
          if (i == 0) { // symbol
            sb.append("      <td><b><a href='" + YfDao.BASE_QUOTE_DETAIL_URL + symbol + "' target='_blank'>").append(removeDoubleQuotes(splitResult, true)).append("</a></b></td>\n");
          } else if (i == 1) { // name
            sb.append("      <td><b>").append(removeDoubleQuotes(splitResult, true)).append("</b></td>\n");
          } else if (i == 2) { // dividend
            sb.append("      <td><b>").append(removeForDividend(splitResult)).append("</b></td>\n");
          } else if (i == 4) { // close
            sb.append("      <td><b>").append( formatTo2Dec( removeDoubleQuotes(splitResult, true))).append("</b></td>\n");
          } else if (i == 5) { // %chg
            sb.append("      <td><b>").append(removeDoubleQuotes(splitResult, true)).append("</b></td>\n");
          }
        } else {
          if (i == 0) { // symbol
            sb.append("      <td><a href='" + YfDao.BASE_QUOTE_DETAIL_URL + symbol + "' target='_blank'>").append(removeDoubleQuotes(splitResult, true)).append("</a></td>\n");
          } else if (i == 1) { // name
            sb.append("      <td>").append(removeDoubleQuotes(splitResult, true)).append("</td>\n");
          } else if (i == 2) { // dividend
            sb.append("      <td>").append(removeForDividend(splitResult)).append("</td>\n");
          } else if (i == 4) { // close
            sb.append("      <td>").append( formatTo2Dec( removeDoubleQuotes(splitResult, true))).append("</td>\n");
          } else if (i == 5) { // %chg
            sb.append("      <td>").append(removeDoubleQuotes(splitResult, true)).append("</td>\n");
          }
        }
      }
      
      Map<Date,HistoricalQuote> securityHistQuotes = securitiesHistQuotes.get(symbol);
      if (participations.get(symbol)) {
        sb.append("      <td><b>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "5d", true)).append("</b></td>\n");
        sb.append("      <td><b>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "10d", true)).append("</b></td>\n");
        sb.append("      <td><b>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "1m", true)).append("</b></td>\n");
        sb.append("      <td><b>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "3m", true)).append("</b></td>\n");
        sb.append("      <td><b>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "6m", true)).append("</b></td>\n");
        sb.append("      <td><b>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "9m", true)).append("</b></td>\n");
        sb.append("      <td><b>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "1y", true)).append("</b></td>\n");
        sb.append("      <td><b>").append(getShortTermIndex(lastQuote, securityHistQuotes)).append("</b></td>\n");
        sb.append("      <td><b>").append(getMidTermIndex(lastQuote, securityHistQuotes)).append("</b></td>\n");
        sb.append("      <td><b>").append(getLongTermIndex(lastQuote, securityHistQuotes)).append("</b></td>\n");
        sb.append("      <td><b>").append(disp(notes.get(symbol))).append("</b></td>\n");
      } else {
        sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "5d", true)).append("</td>\n");
        sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "10d", true)).append("</td>\n");
        sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "1m", true)).append("</td>\n");
        sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "3m", true)).append("</td>\n");
        sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "6m", true)).append("</td>\n");
        sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "9m", true)).append("</td>\n");
        sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "1y", true)).append("</td>\n");
        sb.append("      <td>").append(getShortTermIndex(lastQuote, securityHistQuotes)).append("</b>\n");
        sb.append("      <td>").append(getMidTermIndex(lastQuote, securityHistQuotes)).append("</b>\n");
        sb.append("      <td>").append(getLongTermIndex(lastQuote, securityHistQuotes)).append("</b>\n");
        sb.append("      <td>").append(disp(notes.get(symbol))).append("</b>\n");
      }

      sb.append("    </tr>\n");
    }
    sb.append("  </table>\n");
    return sb;
  }
  

  public void writeToFile(String filePathname, String contents) {
    File file = new File(filePathname);
    try (FileWriter fw = new FileWriter(file.getAbsoluteFile());
         BufferedWriter bw = new BufferedWriter(fw); ) {
      bw.write(contents);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  
  private String getShortTermIndex(String lastQuote, Map<Date,HistoricalQuote> securityHistQuotes) {
    String _5d = getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "5d", false);
    String _10d = getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "10d", false);
    String _1m = getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "1m", false);
    
    String[] quotes = new String[] {_5d, _10d, _1m};
    return getTermIndex(quotes);
  }

  
  private String getMidTermIndex(String lastQuote, Map<Date,HistoricalQuote> securityHistQuotes) {
    String _1m = getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "1m", false);
    String _3m = getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "3m", false);
    String _6m = getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "6m", false);
    
    String[] quotes = new String[] {_1m, _3m, _6m};
    return getTermIndex(quotes);
  }

  
  private String getLongTermIndex(String lastQuote, Map<Date,HistoricalQuote> securityHistQuotes) {
    String _6m = getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "6m", false);
    String _9m = getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "9m", false);
    String _1y = getPercentDisplayForWeekdaysAgo(lastQuote, securityHistQuotes, "1y", false);
    
    String[] quotes = new String[] {_6m, _9m, _1y};
    return getTermIndex(quotes);
  }
  
  
  private String getTermIndex(String[] quotes) {
    float total = 0.0f;
    for (String quote : quotes) {
      try {
        total += Float.parseFloat(quote);
      } catch (Exception ex) {
        // Do nothing.
      }
    }
    
    int index = Math.round(total * 100);
    
    String indexString = Integer.toString(index);
    return indexString;
  }

  
  private String disp(String str) {
    if (str == null) {
      return "";
    } else {
      return str;
    }
  }


  public StringBuilder createInfoTable(StringBuilder sb) {
    sb.append("  <table id='t01' style='width:500px'>\n")
      .append("    <caption><h3 align='left'>").append("Info.").append("</h3></caption>\n")
      .append("    <tr>\n")
      .append("      <td><a href='https://finance.yahoo.com' target='_blank'>News</a></td>\n")
      .append("    </tr>\n")
      .append("    <tr>\n")
      .append("      <td><a href='https://calendar.yahoo.com' target='_blank'>Calendar</a></td>\n")
      .append("    </tr>\n")
      .append("    <tr>\n")
      .append("      <td><a href='http://mam.econoday.com/byweek.asp?cust=mam' target='_blank'>US Weekly Economic Calendar</a></td>\n")
      .append("    </tr>\n")
      .append("    <tr>\n")
      .append("      <td><a href='http://globalbasic.econoday.com/byweek.asp?cust=global-basic' target='_blank'>Global Weekly Economic Calendar</a></td>\n")
      .append("    </tr>\n")
      .append("    <tr>\n")
      .append("      <td><a href='https://finance.yahoo.com/etf/lists/' target='_blank'>ETF Research</a></td>\n")
      .append("    </tr>\n")
      .append("    <tr>\n")
      .append("      <td><a href='" + getQuickInfoUrl() + "' target='_blank'>Quick Info</a><br>A-sym, B-nam, C-cap, D-pe, E-lastTrd, F-1yTgtPr, G-eps-cy, H-eps-nq, I-eps-ny, J-%50dAvg, K-%200dAvg</td>\n")
      .append("    </tr>\n")
      .append("    <tr>\n")
      .append("      <td><a href='" + getDividendUrl() + "' target='_blank'>Dividends</a><br>A-sym, B-nam, C-div/sh, D-yield, E-prevPayDate, F-nextPayDate</td>\n")
      .append("    </tr>\n")
      .append("  </table>\n");
    return sb;
  }


  private String formatTo2Dec(String input) {
    String formattedFloat = null;
    if (input != null) {
      try {
        float f = Float.parseFloat(input);
        formattedFloat = String.format("%.2f", f);
      } catch (Exception ex) {
        formattedFloat = input;
      }
    } else {
      formattedFloat = input;
    }
    return formattedFloat;
  }
  
  
  /**
   * URL for dividend
   * sym, name, div/sh, yield, prev date, next date
   * @return 
   */
  private String getDividendUrl() {
    StringBuilder divUrl = new StringBuilder();
    divUrl.append("http://download.finance.yahoo.com/d/quotes.csv?s=");
    divUrl.append(getSymbolsQuickLinks());
    divUrl.append("&f=sndyqr1");
    
    return divUrl.toString();
  }
  
  
  /**
   * URL for quick info.
   * sym, name, cap, pe, lastTrd, 1yTgtPr, eps-cy, eps-nq, eps-ny, %50dAvg, %200dAvg
   * @return 
   */
  private String getQuickInfoUrl() {
    StringBuilder divUrl = new StringBuilder();
    divUrl.append("http://download.finance.yahoo.com/d/quotes.csv?s=");
    divUrl.append(getSymbolsQuickLinks());
    divUrl.append("&f=snj1rl1t8e7e9e8m8m6");
    
    return divUrl.toString();
  }
  
  
  private String getSymbolsQuickLinks() {
    StringBuilder symbols = new StringBuilder();
    symbols.append("MMM,");
    symbols.append("AA,");
    symbols.append("AXP,");
    symbols.append("AAPL,");
    symbols.append("BAC,");
    symbols.append("BA,");
    symbols.append("CAT,");
    symbols.append("CVX,");
    symbols.append("CSCO,");
    symbols.append("DD,");
    symbols.append("XOM,");
    symbols.append("GE,");
    symbols.append("HPQ,");
    symbols.append("HD,");
    symbols.append("INTC,");
    symbols.append("IBM,");
    symbols.append("JNJ,");
    symbols.append("JPM,");
    symbols.append("GS,");
    symbols.append("MCD,");
    symbols.append("MRK,");
    symbols.append("MSFT,");
    symbols.append("PFE,");
    symbols.append("PG,");
    symbols.append("KO,");
    symbols.append("TRV,");
    symbols.append("UTX,");
    symbols.append("VZ,");
    symbols.append("WMT,");
    symbols.append("DIS");
    
    return symbols.toString();
  }
}
