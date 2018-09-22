package com.autumn.core.dao.impl;

import com.autumn.core.dao.QuoteDao;
import com.autumn.core.model.HistoricalQuote;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.Daily;
import org.patriques.output.timeseries.data.StockData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AlphaVantageQuoteDaoImpl implements QuoteDao {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  // This is shared with all threads
  private static int size = 0;
  
  private static final int BATCH_SIZE = 4;
  private static final long SLEEP_MILLI = 1000*60; // 1min
  private static final String apiKey = "G0KZMSNUA281CM53";
  private static final int timeout = 3000;
  private final AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
  private final TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
  
  @Override
  public List<String> getQuote(List<String> symbols) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Map<Date, HistoricalQuote> getHisoricalQuotes(String symbol) {
    
    Map<Date,HistoricalQuote> results = new TreeMap();
    
    try {
      if (size == BATCH_SIZE) {
        logger.info("*** KL: Sleep for " + SLEEP_MILLI + "ms.");
        Thread.sleep(SLEEP_MILLI);
        size = 0;
      }
      
      logger.info("*** KL: AlphaVantage for " + symbol + ".");
      Daily response = stockTimeSeries.daily(symbol, OutputSize.FULL);
      List<StockData> stockData = response.getStockData();
      LocalDateTime oneYearAgo = stockData.get(0).getDateTime().minusYears(1);
      stockData.forEach(sd -> {
        // Only fetch quotes for the past 365 days
        if (sd.getDateTime().compareTo(oneYearAgo) > 0) {
          Date date = Date.from(sd.getDateTime().atZone(ZoneId.systemDefault()).toInstant());
          HistoricalQuote quote = new HistoricalQuote(date,
                                                      (float)sd.getOpen(),
                                                      (float)sd.getHigh(),
                                                      (float)sd.getLow(),
                                                      (float)sd.getClose(),
                                                      (float)sd.getAdjustedClose(),
                                                      sd.getVolume());
          results.put(date, quote);
        }
      });
      size++;
    } catch (AlphaVantageException ex1) {
      logger.error("Failed executing AlphaVantage API for symbol " + symbol + ".", ex1);
    } catch (InterruptedException ex2) {
      logger.error("Exception while sleeping.", ex2);
    }
    
    return results;
  }
  
}
