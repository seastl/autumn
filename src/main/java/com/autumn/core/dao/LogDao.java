package com.autumn.core.dao;

import com.autumn.core.model.EndOfDayLog;
import com.autumn.core.model.IntraDayLog;

public interface LogDao {
  public void createIntraLog(IntraDayLog intraDayLog);
  public void createEndOfDayLog(EndOfDayLog endOfDayLog);
}
