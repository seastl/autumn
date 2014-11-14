package com.autumn.core.dao.impl;

import com.autumn.core.dao.YfDao;
import com.autumn.core.model.HistoricalQuote;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
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
    String url = buildUrl(symbols, requests);
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
  public Map<Date, HistoricalQuote> getHisoricalQuotes(String symbols, Date fromDate, Date toDate, String interval) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  
  private String buildUrl(List<String> symbols, String requests) {
    StringBuilder sb = new StringBuilder(YfDao.BASE_QUOTE_URL);
    sb.append("?s=");
    for (String symbol : symbols) {
      sb.append(symbol + "+");
    }
    sb.append("&f=" + requests);
    return sb.toString();
  }
  
}
