package com.autumn.core.service;

public interface SecurityService {
  public void setSendEmail(boolean sendEmail);
  public void check();
  public void checkForDailyOpen();
  public void checkForIntraDay();
  public void checkForDailyClose();
  public void checkForEndOfDay();
}
