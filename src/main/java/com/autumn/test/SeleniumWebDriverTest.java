package com.autumn.test;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class SeleniumWebDriverTest {
  public String simpleParse() {
    String result = null;

    try {
//      System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
      WebDriver driver = new ChromeDriver();
//      driver.manage().window().maximize();

      doWork(driver, "https://finance.yahoo.com/quote/GOOG/history?p=GOOG");
      doWork(driver, "https://finance.yahoo.com/quote/MSFT/history?p=MSFT");
      doWork(driver, "https://finance.yahoo.com/quote/AMZN/history?p=AMZN");
      doWork(driver, "https://finance.yahoo.com/quote/FB/history?p=FB");
      doWork(driver, "https://finance.yahoo.com/quote/FUSVX/history?p=FUSVX");
      driver.quit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return result;
  }


  private void doWork(WebDriver driver, String url) {
    driver.get(url);


    JavascriptExecutor jse = (JavascriptExecutor) driver;
//      jse.executeScript("scrollTo(0, 50000);");
//      jse.executeScript("scrollTo(0, 50000);");

    WebElement element2 = driver.findElement(By.xpath("//*[@id=\"Col1-1-HistoricalDataTable-Proxy\"]/section/div[2]/table/tfoot/tr/td/span[1]"));
    jse.executeScript("arguments[0].scrollIntoView(true);", element2);
    waitToLoad(driver, 100);
    jse.executeScript("arguments[0].scrollIntoView(true);", element2);
    waitToLoad(driver, 200);

    // tbody
    WebElement tbody = driver.findElement(By.xpath("//*[@id='Col1-1-HistoricalDataTable-Proxy']/section/div[2]/table/tbody"));
    List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    System.out.println("*** KL: size=" + rows.size());

    // 1st row
    WebElement eDate  = driver.findElement(By.xpath("//*[@id='Col1-1-HistoricalDataTable-Proxy']/section/div[2]/table/tbody/tr[1]/td[1]/span"));
    WebElement eClose = driver.findElement(By.xpath("//*[@id='Col1-1-HistoricalDataTable-Proxy']/section/div[2]/table/tbody/tr[1]/td[5]/span"));
    System.out.println("*** KL: test=" + eDate.getText() + " " + eClose.getText());

    // last row
    eDate  = driver.findElement(By.xpath("//*[@id='Col1-1-HistoricalDataTable-Proxy']/section/div[2]/table/tbody/tr[" + rows.size() + "]/td[1]/span\n"));
    eClose = driver.findElement(By.xpath("//*[@id='Col1-1-HistoricalDataTable-Proxy']/section/div[2]/table/tbody/tr[" + rows.size() + "]/td[5]/span\n"));
    System.out.println("*** KL: test=" + eDate.getText() + " " + eClose.getText());
  }


  private void waitToLoad(WebDriver driver, int rowNumToWait) {
    // Wait
    WebDriverWait myWait = new WebDriverWait(driver, 5); // 60sec timeout
    ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver webDriver) {
        return !webDriver.findElement(By.xpath("//*[@id='Col1-1-HistoricalDataTable-Proxy']/section/div[2]/table/tbody/tr[" + rowNumToWait + "]/td[1]/span")).getText().isEmpty();
      }
    };
    myWait.until(condition);
  }

  
}
