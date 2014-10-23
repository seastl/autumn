package com.autumn.core.model;

import java.util.Date;

public class IntraDayLog {
  private Integer id;
  private Integer securityId;
  private Date timeStamp;
  private Float ask;
  private Float bid;
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

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public Float getAsk() {
    return ask;
  }

  public void setAsk(Float ask) {
    this.ask = ask;
  }

  public Float getBid() {
    return bid;
  }

  public void setBid(Float bid) {
    this.bid = bid;
  }

  public Boolean getIsAlertSent() {
    return isAlertSent;
  }

  public void setIsAlertSent(Boolean isAlertSent) {
    this.isAlertSent = isAlertSent;
  }

}
