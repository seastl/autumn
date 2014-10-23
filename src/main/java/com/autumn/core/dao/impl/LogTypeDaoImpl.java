package com.autumn.core.dao.impl;

import com.autumn.core.dao.LogTypeDao;
import com.autumn.core.model.LogType;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class LogTypeDaoImpl implements LogTypeDao {

  @Override
  public List<LogType> getLogTypes() {
    List<LogType> logTypes = new ArrayList();
    
    LogType logType = null;
    logType = new LogType(LogType.START_DAY_LOG, "Start of day log", null, null, Time.valueOf("06:00"), null);
    logTypes.add(logType);
    logType = new LogType(LogType.END_DAY_LOG, "Start of day log", null, null, null, Time.valueOf("13:05"));
    logTypes.add(logType);
    logType = new LogType(LogType.INTRA_DAY_LOG_ALWAYS, "Intra day log - always", 0F, -0F, Time.valueOf("06:00"), Time.valueOf("13:05"));
    logTypes.add(logType);
    logType = new LogType(LogType.INTRA_DAY_LOG_0p5, "Intra day log - 0.5 % threshold", 0.5F, -0.5F, Time.valueOf("06:00"), Time.valueOf("13:05"));
    logTypes.add(logType);
    logType = new LogType(LogType.INTRA_DAY_LOG_1p0, "Intra day log - 1.0 % threshold", 1.0F, -1.0F, Time.valueOf("06:00"), Time.valueOf("13:05"));
    logTypes.add(logType);
    logType = new LogType(LogType.INTRA_DAY_LOG_1p5, "Intra day log - 1.5 % threshold", 1.5F, -1.5F, Time.valueOf("06:00"), Time.valueOf("13:05"));
    logTypes.add(logType);
    logType = new LogType(LogType.INTRA_DAY_LOG_2p0, "Intra day log - 2.0 % threshold", 2.0F, -2.0F, Time.valueOf("06:00"), Time.valueOf("13:05"));
    logTypes.add(logType);
    logType = new LogType(LogType.INTRA_DAY_LOG_3p0, "Intra day log - 3.0 % threshold", 3.0F, -3.0F, Time.valueOf("06:00"), Time.valueOf("13:05"));
    logTypes.add(logType);
    
    return logTypes;
  }
  
}
