package com.se.NewsScrapper.controllers;

import com.se.NewsScrapper.models.News;
import com.se.NewsScrapper.services.ScrapperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
public class NewsController {

    private ScrapperService scrapperService;

    @Autowired
    public NewsController(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @ModelAttribute("dataList")
    public List<News> setNews(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        switch (keyword) {
            case "":
                return scrapperService.findAllNews();
            case "Fana":
                return scrapperService.findFanaNews();
            case "Reporter":
                return scrapperService.findReporterNews();
            case "BBC":
                return scrapperService.findBBCNews();
            default:
                return scrapperService.searchNews(keyword);
        }
    }

    @GetMapping
    public String start() {

        return "index";
    }
}
