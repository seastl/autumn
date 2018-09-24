package com.autumn.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Import({AutumnConfiguration.DefaultConfig.class})
public class AutumnConfiguration {
  
  @Configuration
  @PropertySources({
    @PropertySource(value = "file:/opt/autumn/conf/autumn.properties"),
    @PropertySource(value = "file:/opt/autumn/conf/email.properties"),
  })
  public static class DefaultConfig { }
  
}
