package com.autumn.core.model;

public class SecurityLogType {
  private Security security;
  private LogType logType;
  private Boolean isActive;

  public SecurityLogType(Security security, LogType logType, Boolean isActive) {
    this.security = security;
    this.logType = logType;
    this.isActive = isActive;
  }

  public Security getSecurity() {
    return security;
  }

  public void setSecurity(Security security) {
    this.security = security;
  }

  public LogType getLogType() {
    return logType;
  }

  public void setLogType(LogType logType) {
    this.logType = logType;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }
  
}
