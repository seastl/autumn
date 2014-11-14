package com.autumn.core.dao.impl;

import com.autumn.core.dao.YfDao;
import com.autumn.core.model.HistoricalQuote;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class YfDaoImpl implements YfDao {

  @Override
  public List<String> getQuote(List<String> symbols, String requests) {
    List<String> results = new ArrayList();
    String url = buildQuoteUrl(symbols, requests);
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(url);
    request.addHeader("User-Agent", USER_AGENT);
    try {
      HttpResponse response = client.execute(request);
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
  public Map<Date, HistoricalQuote> getHisoricalQuotes(String symbol, Date fromDate, Date toDate, String interval) {
    List<String> results = new ArrayList();
    String[] fromMDYYYY = getMDYYYY(fromDate);
    String[] toMDYYYY = getMDYYYY(toDate);
    String url = buildHistoricalQuoteUrl(symbol, fromMDYYYY, toMDYYYY, interval);
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(url);
    request.addHeader("User-Agent", USER_AGENT);
    try {
      HttpResponse response = client.execute(request);
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
    
    Map<Date, HistoricalQuote> histQuotes = convertToQuoteObjects(results);
    return histQuotes;
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
  
  
  private String[] getMDYYYY(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("M-d-yyyy");
    String[] mdyyyy = sdf.format(date).split("-");
    return mdyyyy;
  }
  
  
  private Map<Date, HistoricalQuote> convertToQuoteObjects(List<String> results) {
    Map<Date, HistoricalQuote> quotes = new HashMap();
    for (String result : results) {
      
    }
    return null;
  }
  
          
  private String buildHistoricalQuoteUrl(String symbol, String[] fromMDYYYY, String[] toMDYYYY, String interval) {
    StringBuilder sb = new StringBuilder(YfDao.BASE_HISTORICAL_QUOTE_URL);
    sb.append("?s=").append(symbol).append("&a=").append(fromMDYYYY[0]).append("&b=").append(fromMDYYYY[1]).append("&c=").append(fromMDYYYY[2])
                                   .append("&d=").append(toMDYYYY[0]).append("&e=").append(toMDYYYY[1]).append("&f=").append(toMDYYYY[2])
                                   .append("&g=").append(interval).append("&ignore=.cvs");
    return sb.toString();
  }
  
}
