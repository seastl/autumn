package com.autumn.core.service;

public interface SecurityService {
  public void check();
  public void checkForDailyOpen();
  public void checkForDailyClose();
  public void checkForEndOfDay();
}
