package com.autumn.core.model;

public class SecurityType {
  public static final int US_INDEX = 1;
  public static final int US_STOCK = 2;
  public static final int US_MUTUAL_FUND = 3;
  public static final int US_ETF = 4;
  
  private Integer id;
  private String description;

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
  
}
