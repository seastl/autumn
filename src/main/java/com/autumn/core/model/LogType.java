package com.autumn.core.model;

import java.sql.Time;

public class LogType {
  public static final int START_DAY_LOG = 1;
  public static final int END_DAY_LOG = 2;
  public static final int INTRA_DAY_LOG_ALWAYS = 3;
  public static final int INTRA_DAY_LOG_0p5 = 4;
  public static final int INTRA_DAY_LOG_1p0 = 5;
  public static final int INTRA_DAY_LOG_1p5 = 6;
  public static final int INTRA_DAY_LOG_2p0 = 7;
  public static final int INTRA_DAY_LOG_3p0 = 8;

  private Integer id;
  private String description;
  private Float upThresholdPercent;
  private Float downThresholdPrecent;
  private Time startTime;
  private Time endTime;

  public LogType(Integer id, String description, Float upThresholdPercent, Float downThresholdPrecent, Time startTime, Time endTime) {
    this.id = id;
    this.description = description;
    this.upThresholdPercent = upThresholdPercent;
    this.downThresholdPrecent = downThresholdPrecent;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getUpThresholdPercent() {
    return upThresholdPercent;
  }

  public void setUpThresholdPercent(Float upThresholdPercent) {
    this.upThresholdPercent = upThresholdPercent;
  }

  public Float getDownThresholdPrecent() {
    return downThresholdPrecent;
  }

  public void setDownThresholdPrecent(Float downThresholdPrecent) {
    this.downThresholdPrecent = downThresholdPrecent;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }
  
}
