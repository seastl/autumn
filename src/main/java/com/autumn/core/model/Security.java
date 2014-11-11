package com.autumn.core.model;

public class Security {
  private Integer id;
  private String description;
  private String yahooSymbol;
  private String otherSymbol1;
  private String otherSymbol2;
  private Integer typeId;
  private Integer sectorId;
  private Integer pe;
  private float marketCap;
  private boolean isParticipated;

  public Security(Integer id, String description, String yahooSymbol, Integer typeId, Integer sectorId, boolean isParticipated) {
    this.id = id;
    this.description = description;
    this.yahooSymbol = yahooSymbol;
    this.typeId = typeId;
    this.sectorId = sectorId;
    this.isParticipated = isParticipated;
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

  public String getYahooSymbol() {
    return yahooSymbol;
  }

  public void setYahooSymbol(String yahooSymbol) {
    this.yahooSymbol = yahooSymbol;
  }

  public String getOtherSymbol1() {
    return otherSymbol1;
  }

  public void setOtherSymbol1(String otherSymbol1) {
    this.otherSymbol1 = otherSymbol1;
  }

  public String getOtherSymbol2() {
    return otherSymbol2;
  }

  public void setOtherSymbol2(String otherSymbol2) {
    this.otherSymbol2 = otherSymbol2;
  }

  public Integer getTypeId() {
    return typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public Integer getSectorId() {
    return sectorId;
  }

  public void setSectorId(Integer sectorId) {
    this.sectorId = sectorId;
  }

  public Integer getPe() {
    return pe;
  }

  public void setPe(Integer pe) {
    this.pe = pe;
  }

  public float getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(float marketCap) {
    this.marketCap = marketCap;
  }

  public boolean isParticipated() {
    return isParticipated;
  }

  public void setIsParticipated(boolean isParticipated) {
    this.isParticipated = isParticipated;
  }

  
}
