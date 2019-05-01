package com.babylon.demo.services;

import com.babylon.demo.httpclient.WebClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrawlerService {

  @Autowired
  WebClient webClient;

  public Set<String> getPageAsGraph(String pageUrl) throws Exception {
    Pattern pattern = Pattern.compile("href=\"[/|https|http][-a-zA-Z0-9+&@#/%?=~_|!:,;|.html]*[^0-9]\"");
    Matcher matcher;
    BufferedReader br = null;
    Set<String> siteMap = new HashSet<>();

    pageUrl = removeForwardSlash(pageUrl);

    try {
      br = webClient.getHtmlPage(pageUrl);
      String line = null;

      while((line = br.readLine()) != null){

        matcher = pattern.matcher(line);
        if(matcher.find()){
          String matcherResult = matcher.group(0).replace("href=", "").replace("\"", "");
          if(matcherResult.startsWith("/") || isDomainLink(pageUrl, matcherResult)){
            String link = matcherResult.startsWith("/") ? pageUrl.concat(matcherResult) :  matcherResult;
            siteMap.add(link);
          }
        }
      }
    } catch (IOException e) {
      throw new Exception("Can't get the given url, try another one please");
    }finally {
      if(br != null) {
        br.close();
      }

    }

    return siteMap;
  }

  /**
   * Check is it a domain link,
   * @param pageUrl
   * @param matcherResult
   * @return
   */
  private boolean isDomainLink(String pageUrl, String matcherResult) {
    return
        (matcherResult.startsWith("http://www") || matcherResult.startsWith("https://www"))  && matcherResult.contains(pageUrl);
  }

  /**
   * Remove forward slash for avoiding to duplicate slash
   * @param pageUrl
   * @return url without last slash
   */
  private String removeForwardSlash(String pageUrl) {
    if(pageUrl.charAt(pageUrl.length()-1) == '/'){
      pageUrl = pageUrl.substring(0, pageUrl.length() - 1);
    }
    return pageUrl;
  }
}
