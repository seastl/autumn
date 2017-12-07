package com.autumn.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.autumn"})
public class AutumnMain {
  
  public static void main(String[] args) throws Exception {
    SpringApplication.run(AutumnMain.class, args);
  }
    
}
