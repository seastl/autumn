package com.autumn.core.model;

import java.util.Date;

public class HistoricalQuote {
  private Date date;
  private Float open;
  private Float high;
  private Float low;
  private Float close;
  private Long volume;
  private Float adjClose;

  public HistoricalQuote() {
  }
  
  public HistoricalQuote(Date date, Float open, Float high, Float low, Float close, Float adjClose, Long volume) {
    this.date = date;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.adjClose = adjClose;
    this.volume = volume;
  }
  
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

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

  public Long getVolume() {
    return volume;
  }

  public void setVolume(Long volume) {
    this.volume = volume;
  }

  public Float getAdjClose() {
    return adjClose;
  }

  public void setAdjClose(Float adjClose) {
    this.adjClose = adjClose;
  }
  
}
