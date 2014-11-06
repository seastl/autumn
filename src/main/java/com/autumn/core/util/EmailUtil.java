package com.autumn.core.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {
  private static final String SMTP_HOST = "smtp.googlemail.com";
  private static final int SMTP_PORT = 465;

  private String googleUserName;
  private String googlePassword;
  private String toEmail;

  public void setGoogleUserName(String googleUserName) {
    this.googleUserName = googleUserName;
  }

  public void setGooglePassword(String googlePassword) {
    this.googlePassword = googlePassword;
  }

  public void setToEmail(String toEmail) {
    this.toEmail = toEmail;
  }

  
  public EmailUtil() {
  }
  
  public EmailUtil(String googleUserName, String googlePassword, String toEmail) {
    this.googleUserName = googleUserName;
    this.googlePassword = googlePassword;
    this.toEmail = toEmail;
  }
  
  public void sendEmailThruGoogle(String subject, String message) {
    try {
      Email email = new SimpleEmail();
      email.setHostName(SMTP_HOST);
      email.setSmtpPort(SMTP_PORT);
      email.setAuthenticator(new DefaultAuthenticator(this.googleUserName, this.googlePassword));
      email.setSSLOnConnect(true);
      email.setFrom(googleUserName);
      email.setSubject(subject);
      email.setMsg(message);
      email.addTo(toEmail);
      email.send();
    } catch (Exception ex) {
      ex.printStackTrace();
    }   
  }

  
  public static final void main(String[] args) {
    if (args.length != 3) {
      System.out.println("Usage: java com.autumn.core.util.EmailUtil <google_user> <google_password> <toEmail>");
      System.exit(0);
    }
    String googleUser = args[0];
    String googlePassword = args[1];
    String toEmail = args[2];
    System.out.println("Starting ...");
    String message = "This is a test email.\nhttp://www.yahoo.com\n";
    EmailUtil email = new EmailUtil(googleUser, googlePassword, toEmail);
    email.sendEmailThruGoogle("Test Email", message);
    System.out.println("Email sent!");
  }
}
