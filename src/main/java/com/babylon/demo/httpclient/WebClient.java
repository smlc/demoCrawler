package com.babylon.demo.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.springframework.stereotype.Component;

@Component
public class WebClient {

  /**
   * Get the htmp page for the url
   * @param pageUrl
   * @return the page as  BufferdReader
   * @throws IOException if the link is not valid
   */
  public BufferedReader getHtmlPage(String pageUrl) throws IOException {

        URL url = new URL(pageUrl);
        BufferedReader  br = new BufferedReader(new InputStreamReader(url.openStream()));

        return br;
      }


}
