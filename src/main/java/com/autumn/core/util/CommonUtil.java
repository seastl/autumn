package com.autumn.core.util;

import com.autumn.core.model.HistoricalQuote;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class CommonUtil {
  
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
      String[] result = csvResult.split(",");
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
      String[] result = csvResult.split(",");
      String symbol = removeDoubleQuotes(result[0]);
      String participation = participations.get(symbol) ? "*" : " ";
      sb.append(participation);
      for (int i = 0; i < headers.length; i++) {
        if (headers[i] != null && !headers[i].isEmpty()) {
          sb.append(headers[i] + ":" + removeDoubleQuotes(result[i]) + " ");
        } else {
          sb.append(removeDoubleQuotes(result[i]) + " ");
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
      String[] result = csvResult.split(",");
      String symbol = removeDoubleQuotes(result[0]);
      String participation = participations.get(symbol) ? "*" : " ";
      sb.append(participation);
      for (int i = 0; i < headers.length; i++) {
        if (headers[i] != null && !headers[i].isEmpty()) {
          sb.append(headers[i] + ":" + removeDoubleQuotes(result[i]) + " ");
        } else {
          sb.append(removeDoubleQuotes(result[i]) + " ");
        }
      }
      Map<Date,HistoricalQuote> securityHistQuotes = securitiesHistQuotes.get(symbol);
      sb.append("5d" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "5d")).append(" ");
      sb.append("10d" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "10d")).append(" ");
      sb.append("1m" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "1m")).append(" ");
      sb.append("3m" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "3m")).append(" ");
      sb.append("6m" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "6m")).append(" ");
      sb.append("9m" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "9m")).append(" ");
      sb.append("1y" + getPercentDisplayForWeekdaysAgo(securityHistQuotes, "1y")).append(" ");
      sb.append("\n");
    }
    return sb.toString();
  }
  

  /**
   * Remove double quotes from the string if the string does not contain spaces.
   * @param in
   * @return 
   */
  public String removeDoubleQuotes(String in) {
    if (in.contains(" ")) {
      return in;
    } else {
      return in.replace("\"", "");
    }
  }
  

  public String getPercentDisplayForWeekdaysAgo(Map<Date, HistoricalQuote> historicalQuotes, String pastWorkPeriod) {
    String percentForDaysAgo = null;
    Date[] dateRange = getDateRangeForPastWorkPeriod(pastWorkPeriod);
    HistoricalQuote quoteDaysAgo = historicalQuotes.get(dateRange[0]);
    HistoricalQuote quoteToday = historicalQuotes.get(dateRange[1]);
    if (quoteDaysAgo != null && quoteToday != null) {
      float openDaysAgo = quoteDaysAgo.getOpen();
      float closeToday = quoteToday.getClose();
      float percentChange = ((closeToday - openDaysAgo) * 100) / openDaysAgo;
      percentForDaysAgo = String.format("%+.2f%%", percentChange);
    } else {
      percentForDaysAgo = "n/a";
    }
    
    return percentForDaysAgo;
  }
  

  public StringBuilder createHtmlBegin(StringBuilder sb) {
    sb.append("<html>\n")
      .append("  <head>\n")
      .append("    <style>\n")
      .append("      table, th, td {border: 1px solid white; border-collapse: collapse;}\n")
      .append("      th,td {padding: 1px;}\n")
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
      String[] results = csvResult.split(",");
      for (String result : results) {
        sb.append("      <td>").append(removeDoubleQuotes(result)).append("</td>\n");
      }
      sb.append("    </tr>\n");
    }
    sb.append("  </table>\n");
    
    return sb;
  }
  
  
  public StringBuilder createHtmlTable(StringBuilder sb, String caption, String[] headers, Map<String,Boolean> participations, List<String> csvResults, Map<String,Map<Date,HistoricalQuote>> securitiesHistQuotes) {
    sb.append("  <table id='t01' style='width:500px'>\n")
      .append("    <caption><h3>").append(caption).append("</h3></caption>\n")
      .append("    <tr>\n");
    
    for (String header : headers) {
      sb.append("      <th>").append(header).append("</th>\n");
    }
    sb.append("    </tr>\n");
    
    for (String csvResult : csvResults) {
      String[] splitResults = csvResult.split(",");
      String symbol = removeDoubleQuotes(splitResults[0]);
      
      sb.append("    <tr>\n");
      for (String splitResult : splitResults) {
        if (participations.get(symbol)) {
          sb.append("      <td><b>").append(removeDoubleQuotes(splitResult)).append("</b></td>\n");
        } else {
          sb.append("      <td>").append(removeDoubleQuotes(splitResult)).append("</td>\n");
        }
      }
      
      Map<Date,HistoricalQuote> securityHistQuotes = securitiesHistQuotes.get(symbol);
      sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(securityHistQuotes, "5d")).append("</td>\n");
      sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(securityHistQuotes, "10d")).append("</td>\n");
      sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(securityHistQuotes, "1m")).append("</td>\n");
      sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(securityHistQuotes, "3m")).append("</td>\n");
      sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(securityHistQuotes, "6m")).append("</td>\n");
      sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(securityHistQuotes, "9m")).append("</td>\n");
      sb.append("      <td>").append(getPercentDisplayForWeekdaysAgo(securityHistQuotes, "1y")).append("</td>\n");
      
      sb.append("    </tr>\n");
    }
    return sb;
  }
  

}
