package com.autumn.core.dao;

import com.autumn.core.model.SecurityLogType;
import java.util.List;

public interface SecurityLogTypeDao {
  List<SecurityLogType> getSecuritiesForIndexesAndSectors();
  List<SecurityLogType> getSecuritiesForDow30();
  List<SecurityLogType> getSecuritiesForGeneral();
  List<SecurityLogType> getSecuritiesForNn();
  List<SecurityLogType> getSecuritiesForLb();
  List<SecurityLogType> getSecuritiesForSg();
  List<SecurityLogType> getSecuritiesForIc();
  List<SecurityLogType> getSecuritiesForFidEquity();
  List<SecurityLogType> getSecuritiesForFidInternational();
  List<SecurityLogType> getSecuritiesForFidSector();
  List<SecurityLogType> getSecuritiesForFidIShares();
  
}
