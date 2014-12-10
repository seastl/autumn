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
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

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
  
  
  public String buildMessage(String[] headers, List<String> csvResults) {
    StringBuilder sb = new StringBuilder();
    for (String csvResult : csvResults) {
      String[] result = csvResult.split(COMMA_SPLIT_REGEX);
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

  
  public String buildHtmlMessage(String[] headers, List<String> csvResults) {
    StringBuilder sb = new StringBuilder();
    sb = createHtmlBegin(sb);
    sb = createHtmlTable(sb, "Indexes", headers, csvResults);
    sb = createHtmlEnd(sb);
    return sb.toString();
  }

  
  public String buildMessage(String[] headers, Map<String,Boolean> participations, List<String> csvResults) {
    StringBuilder sb = new StringBuilder();
    for (int j = 0; j < csvResults.size(); j++) {
      String csvResult = csvResults.get(j);
      String[] result = csvResult.split(COMMA_SPLIT_REGEX);
      String symbol = removeDoubleQuotes(result[0], false);
      String participation = participations.get(symbol) ? "*" : " ";
      sb.append(participation);
      for (int i = 0; i < headers.length; i++) {
        if (headers[i] != null && !headers[i].isEmpty()) {
          sb.append(headers[i] + ":" + removeDoubleQuotes(result[i], false) + " ");
        } else {
          sb.append(removeDoubleQuotes(result[i], false) + " ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  

  public String buildMessage(String[] headers, Map<String,Boolean> participations, List<String> csvResults, Map<String,Map<Date,HistoricalQuote>> securitiesHistQuotes) {
    StringBuilder sb = new StringBuilder();
    for (int j = 0; j < csvResults.size(); j++) {
      String csvResult = csvResults.get(j);
      String[] result = csvResult.split(COMMA_SPLIT_REGEX);
      String symbol = removeDoubleQuotes(result[0], false);
      String participation = participations.get(symbol) ? "*" : " ";
      sb.append(participation);
      for (int i = 0; i < headers.length; i++) {
        if (headers[i] != null && !headers[i].isEmpty()) {
          sb.append(headers[i] + ":" + removeDoubleQuotes(result[i], false) + " ");
        } else {
          sb.append(removeDoubleQuotes(result[i], false) + " ");
        }
      }
      Map<Date,HistoricalQuote> securityHistQuotes = securitiesHistQuotes.get(symbol);
      sb.append("5d" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "5d", true)).append(" ");
      sb.append("10d" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "10d", true)).append(" ");
      sb.append("1m" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "1m", true)).append(" ");
      sb.append("3m" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "3m", true)).append(" ");
      sb.append("6m" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "6m", true)).append(" ");
      sb.append("9m" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "9m", true)).append(" ");
      sb.append("1y" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "1y", true)).append(" ");
      sb.append("\n");
    }
    return sb.toString();
  }
  

  /**
   * Remove double quotes from the string.
   * If includePhrase is false, double quotes won't be removed if it contains space.
   * @param in
   * @return 
   */
  public String removeDoubleQuotes(String in, boolean includePhrase) {
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
    Date[] dateRange = getDateRangeForPastWorkPeriod(pastWorkPeriod);
    HistoricalQuote quoteDaysAgo = historicalQuotes.get(dateRange[0]);
    if (quoteDaysAgo != null && lastQuote != null) {
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
  
  
  private StringBuilder createHtmlTable(StringBuilder sb, String caption, String[] headers, List<String> csvResults) {
    sb.append("  <table id='t01' style='width:500px'>\n")
      .append("    <caption><h3>").append(caption).append("</h3></caption>\n")
      .append("    <tr>\n");
    
    for (String header : headers) {
      sb.append("      <th>").append(header).append("</th>\n");
    }
    sb.append("    </tr>\n");
    
    for (String csvResult : csvResults) {
      sb.append("    <tr>\n");
      String[] results = csvResult.split(COMMA_SPLIT_REGEX);
      for (String result : results) {
        sb.append("      <td>").append(removeDoubleQuotes(result, true)).append("</td>\n");
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
    sb.append("  <table id='t01' style='width:800px'>\n")
      .append("    <caption><h3>").append(caption).append("</h3></caption>\n")
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
      String lastQuote = removeDoubleQuotes(splitResults[2], true);
      
      sb.append("    <tr>\n");
      for (int i = 0; i < splitResults.length; i++) {
        String splitResult = splitResults[i];
        if (participations.get(symbol)) {
          if (i == 0) {
            sb.append("      <td><b><a href='" + YfDao.BASE_QUOTE_DETAIL_URL + symbol + "' target='_blank'>").append(removeDoubleQuotes(splitResult, true)).append("</a></b></td>\n");
          } else {
            sb.append("      <td><b>").append(removeDoubleQuotes(splitResult, true)).append("</b></td>\n");
          }
        } else {
          if (i == 0) {
            sb.append("      <td><a href='" + YfDao.BASE_QUOTE_DETAIL_URL + symbol + "' target='_blank'>").append(removeDoubleQuotes(splitResult, true)).append("</a></td>\n");
          } else {
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
}
