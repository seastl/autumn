package com.autumn.core.dao.impl;

import com.autumn.core.dao.SecurityDao;
import com.autumn.core.model.Sector;
import com.autumn.core.model.Security;
import com.autumn.core.model.SecurityType;
import java.util.ArrayList;
import java.util.List;

public class SecurityDaoImpl implements SecurityDao {

  @Override
  public List<Security> getSecurities() {
    List<Security> securities = new ArrayList();
    
    Security security = null;
    security = new Security(1, "Dow Jones index", "symbol", SecurityType.US_INDEX, Sector.DOW, false);
    securities.add(security);
    security = new Security(2, "Nasdaq index", "symbol", SecurityType.US_INDEX, Sector.NASDAQ, false);
    securities.add(security);
    security = new Security(3, "S&P 500", "symbol", SecurityType.US_INDEX, Sector.SP_500, false);
    securities.add(security);
    
    return securities;
  }
  
}
