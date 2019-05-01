package com.babylon.demo.services

import com.babylon.demo.httpclient.WebClient
import spock.lang.Specification

class CrawlerServiceTest extends Specification{
    private BufferedReader br
    private WebClient webClient

    private CrawlerService crawlerService;

    void setup() {
        br =  CrawlerServiceTest.class.getResourceAsStream("/babylonIndex.html").newReader()
        crawlerService = new CrawlerService()
        webClient = Mock(WebClient) {
            getHtmlPage("https://www.babylonhealth.com") >> br
        }
        crawlerService.webClient = webClient
    }
    def" should return a  set containing the links for the given web url"(){
        given :"the web url to crawl"
        String pageUrl = "https://www.babylonhealth.com/"

        when : "ask the link in the page"
        Set<String> siteMap = crawlerService.getPageAsGraph(pageUrl)

        then :"the links"
        siteMap.size() == 39

    }
}
