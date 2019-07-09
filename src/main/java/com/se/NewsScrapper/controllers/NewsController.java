package com.se.NewsScrapper.controllers;

import com.se.NewsScrapper.models.News;
import com.se.NewsScrapper.services.ScrapperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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

    @GetMapping
    public String start() {
        return "index";
    }

    @ModelAttribute("dataList")
    public List<News> setNews() {
        return new ArrayList<>();
    }
}
