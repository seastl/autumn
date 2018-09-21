package com.autumn.core.dao.impl;

import com.autumn.core.dao.QuoteDao;
import com.autumn.core.model.HistoricalQuote;
import com.autumn.core.util.JsonUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

@Component
public class AlphaVantageQuoteDaoImpl implements QuoteDao {
  private static final String baseUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&outputsize=full&apikey=G0KZMSNUA281CM53";
  
  @Override
  public List<String> getQuote(List<String> symbols) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Map<Date, HistoricalQuote> getHisoricalQuotes(String symbol) {
    StringBuilder sb = new StringBuilder();
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(baseUrl);
    request.addHeader("User-Agent", USER_AGENT);
    try {
      HttpResponse response = client.execute(request);
      if (response.getStatusLine().getStatusCode() != 200) {
        throw new RuntimeException("Unable to retrieve quotes. The request URL is " + baseUrl + ".");
      }
      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      String line = null;
      while ((line=rd.readLine()) != null) {
        if (line.equals("Missing Symbols List.")) {
          throw new RuntimeException("Failed to retrieve quotes. Message from API: " + line + ".");
        }
        sb.append(line + "\n");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    String json = sb.toString();
    
    AlphaVantageQuoteDaoImpl.Results results = JsonUtils.getObjectFromJSONString(json, AlphaVantageQuoteDaoImpl.Results.class);
    
    return null;
  }
  
  
  private class Results {
    private MetaData metaData;
    private List<Quote> quotes;

    public MetaData getMetaData() {
      return metaData;
    }

    public void setMetaData(MetaData metaData) {
      this.metaData = metaData;
    }

    public List<Quote> getQuotes() {
      return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
      this.quotes = quotes;
    }
    
  }
  
  
  private class MetaData {
    private String information;
    private String symbol;
    private Date lastRefreshed;
    private String outputSize;
    private String timeZone;

    public String getInformation() {
      return information;
    }

    public void setInformation(String information) {
      this.information = information;
    }

    public String getSymbol() {
      return symbol;
    }

    public void setSymbol(String symbol) {
      this.symbol = symbol;
    }

    public Date getLastRefreshed() {
      return lastRefreshed;
    }

    public void setLastRefreshed(Date lastRefreshed) {
      this.lastRefreshed = lastRefreshed;
    }

    public String getOutputSize() {
      return outputSize;
    }

    public void setOutputSize(String outputSize) {
      this.outputSize = outputSize;
    }

    public String getTimeZone() {
      return timeZone;
    }

    public void setTimeZone(String timeZone) {
      this.timeZone = timeZone;
    }
    
    
  }
  
  
  private class Quote {
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Integer volume;

    public Float getOpen() {
      return open;
    }

    public void setOpen(Float open) {
      this.open = open;
    }

    public Float getHigh() {
      return high;
    }

    public void setHigh(Float high) {
      this.high = high;
    }

    public Float getLow() {
      return low;
    }

    public void setLow(Float low) {
      this.low = low;
    }

    public Float getClose() {
      return close;
    }

    public void setClose(Float close) {
      this.close = close;
    }

    public Integer getVolume() {
      return volume;
    }

    public void setVolume(Integer volume) {
      this.volume = volume;
    }
  }
  
}
