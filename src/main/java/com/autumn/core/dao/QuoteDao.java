package com.autumn.core.dao;

import com.autumn.core.model.HistoricalQuote;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface QuoteDao {
  public List<String> getQuote(List<String> symbols);
  public Map<Date,HistoricalQuote> getHisoricalQuotes(String symbol);
}
