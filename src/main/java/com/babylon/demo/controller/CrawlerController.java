package com.babylon.demo.controller;

import com.babylon.demo.services.CrawlerService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CrawlerController {

  @Autowired
  CrawlerService crawlerService;

  @PostMapping(value="/crawler")
  public String greeting(HttpServletRequest request, Model model) throws Exception {
    String urlToCrawl = request.getParameter("urlToCrawl");
    Set<String> siteMap = crawlerService.getPageAsGraph(urlToCrawl);
    model.addAttribute("lists", siteMap);
    return "pagegraph";
  }
}
