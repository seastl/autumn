package com.autumn.core.dao;

import com.autumn.core.model.SecurityLogType;
import java.util.List;

public interface SecurityLogTypeDao {
  List<SecurityLogType> getSecuritiesForIntraDayLogging();
  List<SecurityLogType> getSecuritiesForStartOfDayLogging();
  List<SecurityLogType> getSecuritiesForEndOfDayLogging();
}
