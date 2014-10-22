package com.autumn.core.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {

  private final String hostName;
  private final int smtpPort;
  private final String googleUserName;
  private final String googlePassword;
  
  public EmailUtil(String hostName, int smtpPort, String googleUserName, String googlePassword) {
    this.hostName = hostName;
    this.smtpPort = smtpPort;
    this.googleUserName = googleUserName;
    this.googlePassword = googlePassword;
  }
  
  public void sendEmailThruGoogle(String from, String to, String subject, String message) {
    try {
      Email email = new SimpleEmail();
      email.setHostName(this.hostName);
      email.setSmtpPort(this.smtpPort);
      email.setAuthenticator(new DefaultAuthenticator(this.googleUserName, this.googlePassword));
      email.setSSLOnConnect(true);
      email.setFrom(from);
      email.setSubject(subject);
      email.setMsg(message);
      email.addTo(to);
      email.send();
    } catch (Exception ex) {
      ex.printStackTrace();
    }   
  }

  
  public static final void main(String[] args) {
    System.out.println("Starting ...");
    String message = "This is a test email.\nhttp://www.yahoo.com\n";
    EmailUtil email = new EmailUtil("smtp.googlemail.com", 465, "??????", "??????");
    email.sendEmailThruGoogle("eccskc.schedules@gmail.com", "liangtech@yahoo.com.com", "Test Email", message);
    System.out.println("Done");
  }
}
