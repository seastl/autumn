package com.autumn.core.service.impl;

import com.autumn.core.dao.LogDao;
import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.dao.YfDao;
import com.autumn.core.model.SecurityLogType;
import com.autumn.core.service.SecurityService;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceImpl implements SecurityService {

  private SecurityLogTypeDao securityLogTypeDao;
  private LogDao logDao;
  private YfDao yfDao;

  @Autowired
  public void setSecurityLogTypeDao(SecurityLogTypeDao securityLogTypeDao) {
    this.securityLogTypeDao = securityLogTypeDao;
  }

  @Autowired
  public void setLogDao(LogDao logDao) {
    this.logDao = logDao;
  }

  @Autowired
  public void setYfDao(YfDao yfDao) {
    this.yfDao = yfDao;
  }

  
  @Override
  public void check() {
    List<SecurityLogType> securities = securityLogTypeDao.getSecuritiesForIntraDayLogging();
    
    for (SecurityLogType security : securities) {
      System.out.println("Security=" + security.getSecurity().getYahooSymbol() + " LogType=" + security.getLogType().getDescription());
    }
    
    List<String> symbols = Arrays.asList("msft","fb");
//    String requests = YfDao.SYMBOL + YfDao.DAYS_LO + YfDao.DAYS_HI + YfDao.BID_RT + YfDao.ASK_RT + YfDao.PREVIOUS_CLOSE + YfDao.LST_TRD;
    String requests = YfDao.SYMBOL + YfDao.NAME + YfDao._50D_MOVING_AVG + YfDao.PCT_CHG_FRM_50D_MOVING_AVG + YfDao.CHG + YfDao.PCT_CHG + YfDao._1Y_TARGET_PRICE;
    List<String> results = yfDao.getQuote(symbols, requests);
    for (String result : results) {
      System.out.println(result);
    }
  }
  
}
