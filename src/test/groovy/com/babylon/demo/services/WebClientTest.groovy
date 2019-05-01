package com.babylon.demo.services

import com.babylon.demo.httpclient.WebClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class WebClientTest extends Specification {

    @Autowired
    WebClient webClient

    def" should return the  page as buffer reader "(){
        given: "a page url to fetch"
        String url = "https://www.babylonhealth.com:"

        when : "fetch the page"
        BufferedReader br = webClient.getHtmlPage(url)

        then : "get the page as a Buffer Reader"
        br != null
    }

    def" should throw a exception when the site is not accessible "(){
        given: "a non accessible web page"
        String url = "http://localhost:9000/"

        when : "fetch the page"
        webClient.getHtmlPage(url)

        then : "throw a IOException"
        thrown IOException
    }
}
