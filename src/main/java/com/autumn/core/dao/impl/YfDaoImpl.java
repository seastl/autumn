package com.autumn.core.dao.impl;

import com.autumn.core.dao.YfDao;
import com.autumn.core.model.HistoricalQuote;
import com.autumn.core.util.CommonUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YfDaoImpl implements YfDao {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

  
  /**
   * Retrives results in csv format: symbol,name,prev_close,last_trade,percent_change
   * @param symbols
   * @return 
   */
  @Override
  public List<String> getQuote(List<String> symbols) {
    logger.info("*** KL: Yf for " + symbols + ".");
    
    final String baseUrl = "https://finance.yahoo.com/quote/";
    List<String> results = new ArrayList();
    

    // Get from yahoo
    for (String symbol : symbols) {
      try {
        StringBuilder csvResult = null;
        csvResult = new StringBuilder();
        String yUrl = baseUrl + symbol;
        //Document doc = Jsoup.connect(yUrl).timeout(60000).maxBodySize(0).get();
        Document doc = Jsoup.connect(yUrl)
                .header("Accept-Encoding", "gzip, defalte")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                .timeout(60000)
                .maxBodySize(0)
                .get();

        Elements names = doc.getElementsByClass("D(ib) Fz(18px)");
        String name = removeComma(names.get(0).text());
        // Symbol
        csvResult.append(getSymbol(name));
        csvResult.append(",");

        // Name
        csvResult.append(getName(name));
        csvResult.append(",");

        // Previous close
        Elements preCloses = doc.getElementsByClass("Trsdu(0.3s) ");
        csvResult.append(removeComma(preCloses.get(0).text()));
        csvResult.append(",");

        // Last trade
        Elements quotes = doc.getElementsByClass("Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)");
        csvResult.append(removeComma(quotes.get(0).text()));
        csvResult.append(",");

        // Percent change
        Elements percents = doc.getElementsByClass("Trsdu(0.3s) Fw(500) Pstart(10px) Fz(24px) C($dataGreen)");
        if (percents == null || percents.size() == 0) {
          percents = doc.getElementsByClass("Trsdu(0.3s) Fw(500) Pstart(10px) Fz(24px) C($dataRed)");
        }
        if (percents == null || percents.size() == 0) {
          percents = doc.getElementsByClass("Trsdu(0.3s) Fw(500) Pstart(10px) Fz(24px)");
        }
        csvResult.append(removeComma(getPercent(percents.get(0).text())));
        
        results.add(csvResult.toString());
      } catch (Exception ex) {
        logger.error("Unable to get quote for symbol " + symbol);
      }
    }
    
    return results;
  }


  /**
   * Input:  S&P 500 (^GSPC)
   * Output: ^GSPC
   * @param in
   * @return 
   */
  private String getSymbol(String in) {
    int start = in.indexOf("(")+1;
    int end= in.indexOf(")");
    return in.substring(start, end);
  }
  
  
  /**
   * Input:  S&P 500 (^GSPC)
   * Output: S&P 500
   * @param in
   * @return 
   */
  private String getName(String in) {
    int start = 0;
    int end= in.indexOf("(")-1;
    return in.substring(start, end);
  }
  
  
  /**
   * Input:  +1.23 (+0.67%)  -1.23 (-0.67%)  0.00 (0.00%)
   * Output: +0.67%          -0.67%          0.00%
   * @return 
   */
  private String getPercent(String in) {
    int start = in.indexOf("(")+1;
    int end= in.indexOf(")");
    return in.substring(start, end);
  }


  private String removeComma(String str) {
    return str.replaceAll(",", "");
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
  
  
  /**
   * Get historic quotes using selenium
   * @param symbol
   * @return 
   */
  @Override
  public Map<Date, HistoricalQuote> getHisoricalQuotes(String symbol) {
    final String baseUrl = "https://finance.yahoo.com/quote/";
    final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
    Map<Date, HistoricalQuote> histQuotes = new TreeMap();
    
    String url = baseUrl + symbol + "/history";
    
    WebDriver driver = new ChromeDriver();
    driver.get(url);
    JavascriptExecutor jse = (JavascriptExecutor) driver;

    try {
      // Scrolls to the bottom of page until the texts "Close price adjusted for splits" is shown
      WebElement bottomElement = driver.findElement(By.xpath("//*[@id=\"Col1-1-HistoricalDataTable-Proxy\"]/section/div[2]/table/tfoot/tr/td/span[1]"));
      jse.executeScript("arguments[0].scrollIntoView(true);", bottomElement);
      waitToLoad(driver, 100);
      jse.executeScript("arguments[0].scrollIntoView(true);", bottomElement);
      waitToLoad(driver, 200);

      // Get the tbody element of the historic data
      WebElement historicDataTbody = driver.findElement(By.xpath("//*[@id='Col1-1-HistoricalDataTable-Proxy']/section/div[2]/table/tbody"));
      List<WebElement> rows = historicDataTbody.findElements(By.tagName("tr"));
      for (WebElement row : rows) {
        List<WebElement> cols = row.findElements(By.tagName("td"));
        String dateStr = cols.get(0).findElement(By.tagName("span")).getText(); 
        String openStr = cols.get(1).findElement(By.tagName("span")).getText();
        String highStr = cols.get(2).findElement(By.tagName("span")).getText();
        String lowStr = cols.get(3).findElement(By.tagName("span")).getText();
        String closeStr = cols.get(4).findElement(By.tagName("span")).getText();
        //String adjCloseStr = cols.get(5).findElement(By.tagName("span")).getText();
        //String volumeStr = cols.get(6).findElement(By.tagName("span")).getText();
        Date date = sdf.parse(dateStr);
        Float open = Float.parseFloat(openStr.replace(",", ""));
        Float high = Float.parseFloat(highStr.replace(",", ""));
        Float low = Float.parseFloat(lowStr.replace(",", ""));
        Float close = Float.parseFloat(closeStr.replace(",", ""));
        Float adjClose = 0F; //Float.parseFloat(adjCloseStr.replace(",", ""));
        Long volume = 0L; //Long.parseLong(volumeStr.replace(",", ""));
        histQuotes.put(date, new HistoricalQuote(date, open, high, low, close, adjClose, volume));
      }
      
      driver.quit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
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
      String[] splitted = csvResults.get(i).split(CommonUtil.COMMA_SPLIT_REGEX);
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

  
  private void waitToLoad(WebDriver driver, final int rowNumToWait) {
    // Wait
    WebDriverWait myWait = new WebDriverWait(driver, 5); // 60sec timeout
    ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver webDriver) {
        return !webDriver.findElement(By.xpath("//*[@id='Col1-1-HistoricalDataTable-Proxy']/section/div[2]/table/tbody/tr[" + rowNumToWait + "]/td[1]/span")).getText().isEmpty();
      }
    };
    myWait.until(condition);
  }

  
}
