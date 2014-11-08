package com.autumn.core.model;

public class Sector {
  public static final int UNKNOWN = 0;
  public static final int DOW = 1;
  public static final int NASDAQ = 2;
  public static final int SP_500 = 3;
  public static final int RUSSELL_1000 = 4;
  public static final int RUSSELL_2000 = 5;
  public static final int RUSSELL_3000 = 6;
  public static final int TECHNOLOGY = 7;
  public static final int CONSUMER_SELECT = 8;
  public static final int HEALTH_CARE = 9;
  public static final int MATERIALS = 10;
  public static final int INDUSTRIAL = 11;
  public static final int CONSUMER_STAPLES = 12;
  public static final int ENERGY = 13;
  public static final int UTILITIES = 14;
  public static final int FINANCIAL = 15;
  public static final int VOLATILITY = 16;
  
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
