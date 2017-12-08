package com.autumn.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class JsoupTest {
  public String simpleParse() {
    String texts = null;
    String url = "https://finance.yahoo.com/quote/GOOG/history?p=GOOG";

    try {
      //Document doc = Jsoup.connect(url).timeout(60000).maxBodySize(0).get();
      Document doc = Jsoup.connect(url)
              .header("Accept-Encoding", "gzip, defalte")
              .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
              .timeout(60000)
              .maxBodySize(0)
              .get();
      texts = doc.title();
      System.out.println("*** KL: texts=" + texts);

      Elements elements = doc.getElementsByTag("tbody");
      Element dataTbody = elements.get(1);
      Elements rows = dataTbody.getElementsByTag("tr");
      for (Element row : rows) {
        Elements cols = row.getElementsByTag("td");
        for (Element col : cols) {
          Element span = col.getElementsByTag("span").get(0);
          System.out.println("KL: span=" + span.ownText());
          System.out.println("KL: row=" + row.data());
        }
      }

      System.out.println("*** KL: texts=" + elements);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return texts;
  }
  
}