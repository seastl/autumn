package com.autumn.configuration;

import com.autumn.core.dao.LogDao;
import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.dao.YfDao;
import com.autumn.core.dao.impl.LogDaoImpl;
import com.autumn.core.dao.impl.SecurityLogTypeDaoImpl;
import com.autumn.core.dao.impl.YfDaoImpl;
import com.autumn.core.service.SecurityService;
import com.autumn.core.service.impl.SecurityServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutumnConfiguration {
  
  @Bean
  public LogDao logDao() {
    return new LogDaoImpl();
  } 
  
  @Bean
  public SecurityLogTypeDao securityLogTypeDao() {
    return new SecurityLogTypeDaoImpl();
  } 
  
  @Bean
  public YfDao yfDao() {
    return new YfDaoImpl();
  } 
  
  @Bean
  public SecurityService securityService() {
    return new SecurityServiceImpl();
  } 
}
