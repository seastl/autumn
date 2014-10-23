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
    if (args.length != 2) {
      System.out.println("Usage: java com.autumn.core.util.EmailUtil <google_user> <google_password>");
      System.exit(0);
    }
    String googleUser = args[0];
    String googlePassword = args[1];
    System.out.println("Starting ...");
    String message = "This is a test email.\nhttp://www.yahoo.com\n";
    EmailUtil email = new EmailUtil("smtp.googlemail.com", 465, googleUser, googlePassword);
    email.sendEmailThruGoogle("eccskc.schedules@gmail.com", "liangtech@yahoo.com", "Test Email", message);
    System.out.println("Email sent!");
  }
}
