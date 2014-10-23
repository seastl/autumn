package com.autumn.core.dao.impl;

import com.autumn.core.dao.YfDao;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class YfDaoImpl implements YfDao {

  @Override
  public String getQuote(String symbols, String requests) {
    StringBuilder result = new StringBuilder();
    String url = "http://download.finance.yahoo.com/d/?s=msft+fb&f=sgmb2b3pl1";
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(url);
    request.addHeader("User-Agent", USER_AGENT);
    try {
      HttpResponse response = client.execute(request);
      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      String line = null;
      while ((line=rd.readLine()) != null) {
        result.append(line + "\n");
      }
    } catch (Exception ex) {
      
    }
    
    return result.toString();
  }
  
}
