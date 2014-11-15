package com.autumn.configuration;

import com.autumn.core.dao.LogDao;
import com.autumn.core.dao.SecurityLogTypeDao;
import com.autumn.core.dao.YfDao;
import com.autumn.core.dao.impl.LogDaoImpl;
import com.autumn.core.dao.impl.SecurityLogTypeDaoImpl;
import com.autumn.core.dao.impl.YfDaoImpl;
import com.autumn.core.service.SecurityService;
import com.autumn.core.service.impl.SecurityServiceImpl;
import com.autumn.core.util.CommonUtil;
import com.autumn.core.util.EmailUtil;
import com.autumn.core.util.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
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
  
  @Bean
  public Scheduler scheduler() {
    return new Scheduler();
  }
  
  @Bean
  public EmailUtil emailUtil() {
    return new EmailUtil();

  }
  
  @Bean
  public CommonUtil commonUtil() {
    return new CommonUtil();

  }
  
}
