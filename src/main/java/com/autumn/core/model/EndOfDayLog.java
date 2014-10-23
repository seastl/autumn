package com.autumn.core.model;

public class EndOfDayLog {
  private Integer id;
  private Integer securityId;
  private Float open;
  private Float close;
  private Float daysHigh;
  private Float daysLow;
  private Integer volume;
  private Float closePercentChange;
  private Float volumePercentChange;
  private Boolean isAlertSent;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSecurityId() {
    return securityId;
  }

  public void setSecurityId(Integer securityId) {
    this.securityId = securityId;
  }

  public Float getOpen() {
    return open;
  }

  public void setOpen(Float open) {
    this.open = open;
  }

  public Float getClose() {
    return close;
  }

  public void setClose(Float close) {
    this.close = close;
  }

  public Float getDaysHigh() {
    return daysHigh;
  }  


  public void setDaysHigh(Float daysHigh) {
    this.daysHigh = daysHigh;
  }

  public Float getDaysLow() {
    return daysLow;
  }

  public void setDaysLow(Float daysLow) {
    this.daysLow = daysLow;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  public Float getClosePercentChange() {
    return closePercentChange;
  }

  public void setClosePercentChange(Float closePercentChange) {
    this.closePercentChange = closePercentChange;
  }

  public Float getVolumePercentChange() {
    return volumePercentChange;
  }

  public void setVolumePercentChange(Float volumePercentChange) {
    this.volumePercentChange = volumePercentChange;
  }

  public Boolean getIsAlertSent() {
    return isAlertSent;
  }

  public void setIsAlertSent(Boolean isAlertSent) {
    this.isAlertSent = isAlertSent;
  }

}
