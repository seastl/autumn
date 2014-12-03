package com.autumn.core.dao.impl;

import com.autumn.core.dao.YfDao;
import com.autumn.core.model.HistoricalQuote;
import com.autumn.core.util.CommonUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class YfDaoImpl implements YfDao {

  private CommonUtil commonUtil;
  
  @Autowired
  public void setCommonUtil(CommonUtil commonUtil) {
    this.commonUtil = commonUtil;
  }
  
  
  @Override
  public List<String> getQuote(List<String> symbols, String requests) {
    if (!requests.startsWith(SYMBOL)) {
      throw new RuntimeException("Requests parameter must start with SYMBOL \"s\".");
    }
    
    List<String> results = new ArrayList();
    String url = buildQuoteUrl(symbols, requests);
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(url);
    request.addHeader("User-Agent", USER_AGENT);
    try {
      HttpResponse response = client.execute(request);
      if (response.getStatusLine().getStatusCode() != 200) {
        throw new RuntimeException("Unable to retrieve quotes. The request URL is " + url + ".");
      }
      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      String line = null;
      while ((line=rd.readLine()) != null) {
        if (line.equals("Missing Symbols List.")) {
          throw new RuntimeException("Failed to retrieve quotes. Message from API: " + line + ".");
        }
        results.add(line);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    return results;
  }

  
  @Override
  public Map<Date, HistoricalQuote> getHisoricalQuotes(String symbol, Date startDate, Date endDate, String increment) {
    List<String> csvResults = new ArrayList();
    String[] fromMDYYYY = getMDYYYY(startDate);
    String[] toMDYYYY = getMDYYYY(endDate);
    String url = buildHistoricalQuoteUrl(symbol, fromMDYYYY, toMDYYYY, increment);
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(url);
    request.addHeader("User-Agent", USER_AGENT);
    try {
      HttpResponse response = client.execute(request);
      if (response.getStatusLine().getStatusCode() != 200) {
        System.out.println("Error: Unable to retrieve historical quotes for symbol " + symbol + ". " +
                                  "Make sure the symbol is correct. " +
                                  "The request URL is " + url + ".");
      } else {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = null;
        while ((line=rd.readLine()) != null) {
          csvResults.add(line);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    Map<Date, HistoricalQuote> histQuotes = convertToQuoteObjects(csvResults);
    return histQuotes;
  }

  
  @Override
  public Map<Date, HistoricalQuote> getHisoricalQuotes(String symbol, String pastPeriod, String increment) {
    Date[] dateRange = commonUtil.getDateRangeForPastWorkPeriod(pastPeriod);
    Date fromDate = dateRange[0];
    Date toDate = dateRange[1];
    return getHisoricalQuotes(symbol, fromDate, toDate, increment);
  }
  
  
  private String buildQuoteUrl(List<String> symbols, String requests) {
    StringBuilder sb = new StringBuilder(YfDao.BASE_QUOTE_URL);
    sb.append("?s=");
    for (String symbol : symbols) {
      sb.append(symbol + "+");
    }
    sb.append("&f=" + requests);
    return sb.toString();
  }
  
  
  /**
   * M is 0 based. D is 1 based, YYYY is 1 based.
   * @param date
   * @return 
   */
  private String[] getMDYYYY(Date date) {
    final SimpleDateFormat sdf = new SimpleDateFormat("M-d-yyyy");
    String[] mdyyyy = sdf.format(date).split("-");
    mdyyyy[0] = Integer.toString(Integer.parseInt(mdyyyy[0]) - 1);
    return mdyyyy;
  }
  
  
  private Map<Date, HistoricalQuote> convertToQuoteObjects(List<String> csvResults) {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Map<Date, HistoricalQuote> quotes = new TreeMap();
    for (int i = 1; i < csvResults.size(); i++) {
      String[] splitted = csvResults.get(i).split(",");
      try {
        HistoricalQuote quote = new HistoricalQuote();
        Date date = sdf.parse(splitted[0]);
        quote.setDate(date);
        quote.setOpen(Float.parseFloat(splitted[1]));
        quote.setHigh(Float.parseFloat(splitted[2]));
        quote.setLow(Float.parseFloat(splitted[3]));
        quote.setClose(Float.parseFloat(splitted[4]));
        quote.setVolume(Long.parseLong(splitted[5]));
        quote.setAdjClose(Float.parseFloat(splitted[6]));
        quotes.put(date, quote);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return quotes;
  }
  
          
  private String buildHistoricalQuoteUrl(String symbol, String[] fromMDYYYY, String[] toMDYYYY, String interval) {
    StringBuilder sb = new StringBuilder(YfDao.BASE_HISTORICAL_QUOTE_URL);
    sb.append("?s=").append(symbol).append("&a=").append(fromMDYYYY[0]).append("&b=").append(fromMDYYYY[1]).append("&c=").append(fromMDYYYY[2])
                                   .append("&d=").append(toMDYYYY[0]).append("&e=").append(toMDYYYY[1]).append("&f=").append(toMDYYYY[2])
                                   .append("&g=").append(interval).append("&ignore=.cvs");
    return sb.toString();
  }
  
}
