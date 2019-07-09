package com.se.NewsScrapper.services;

import com.se.NewsScrapper.models.News;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ScrapperService {
    private WebDriver driver;

    public ScrapperService() {
        System.setProperty("webdriver.chrome.driver", "/Users/yiseboge/chromedriver");
    }

    private ChromeDriver chromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");
        chromeOptions.addArguments("no-sandbox");
        return new ChromeDriver(chromeOptions);
    }

    private boolean getUrl(String type, String url) {
        long timeout = 100;
        driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
        try {
            driver.get(url);
        } catch (Exception e) {
            System.out.println("The " + type + " site couldn't be loaded");
            driver.close();
            return true;
        }
        return false;
    }

    private News makeNews(String type, String title, String description, String date) {
        News fanaPost = new News();
        fanaPost.setDate(date);
        fanaPost.setTitle(title);
        fanaPost.setDescription(description);
        fanaPost.setSource(type);
        return fanaPost;
    }

    public List<News> findFanaNews() {
        String type = "Fana";
        ArrayList<String> topicsFana = new ArrayList<>();
        List<News> fanaPosts = new ArrayList<>();
        driver = chromeDriver();

        String url = "https://fanabc.com/category/localnews/";
        if (getUrl(type, url)) return new ArrayList<>();

        List<WebElement> fainaTrending = driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/div[1]/div[1]"));
        for (WebElement i : fainaTrending) {
            topicsFana.add(i.getText());
        }
        String[] fromFana = topicsFana.get(0).split("\n");
        int fanaTitleInd = 1, fanaDescInd = 4, fanaDateInd = 2;
        for (String ignored : fromFana) {
            if (fanaDateInd <= 30 && fanaDescInd <= 30 && fanaTitleInd <= 30) {
                fanaPosts.add(makeNews(type, fromFana[fanaTitleInd], fromFana[fanaDescInd], fromFana[fanaDateInd]));
            }
            fanaDateInd += 5;
            fanaDescInd += 5;
            fanaTitleInd += 5;
        }

        driver.close();
        return fanaPosts;
    }

    public List<News> findReporterNews() {
        String type = "Reporter";
        ArrayList<String> topicsReporter = new ArrayList<>();
        List<News> reporterPosts = new ArrayList<>();
        driver = chromeDriver();

        String url = "https://www.ethiopianreporter.com/index.php/zena";
        if (getUrl(type, url)) return new ArrayList<>();

        List<WebElement> reporterZena = driver.findElements(By.xpath("//*[@id=\"block-gavias-kama-content\"]/div/div/div/div"));
        for (WebElement i : reporterZena) {
            topicsReporter.add(i.getText());
        }
        String[] reporterSplited = topicsReporter.get(0).split("\n");
        int fi = 0, se = 1, ti = 2;
        for (int j = 0; j <= 10; j++) {
            if (fi <= 30 && se <= 30 && ti <= 30) {
                reporterPosts.add(makeNews(type, reporterSplited[se], reporterSplited[ti], reporterSplited[fi]));
            }
            fi += 3;
            se += 3;
            ti += 3;
        }

        driver.close();
        return reporterPosts;
    }

    public List<News> findBBCNews() {
        String type = "BBC - Amharic";
        List<News> bbcPosts = new ArrayList<>();
        ArrayList<String> topicsBbc = new ArrayList<>();
        driver = chromeDriver();

        String url = "https://www.bbc.com/amharic/topics/e986aff5-6b26-4638-b468-371d1d9617b4";
        if (getUrl(type, url)) return new ArrayList<>();

        List<WebElement> bbcAmharic = driver.findElements(By.xpath("//*[@id=\"page\"]/div/div[2]/div/div[1]/div"));
        for (WebElement i : bbcAmharic) {
            topicsBbc.add(i.getText());
            System.out.println(i.getText());
        }
        String[] bbcSplited = topicsBbc.get(0).split("\n");
        int dateInd = 2, descInd = 1, titleInd = 0;
        for (String ignored : bbcSplited) {
            if (dateInd <= 60 && descInd <= 60 && titleInd <= 60) {
                bbcPosts.add(makeNews(type, bbcSplited[titleInd], bbcSplited[descInd], bbcSplited[dateInd]));
                dateInd += 3;
                descInd += 3;
                titleInd += 3;
            }
        }

        driver.close();
        return bbcPosts;
    }

    public List<News> findAllNews() {

        List<News> allNews = new ArrayList<>();
        allNews.addAll(findFanaNews());
        allNews.addAll(findReporterNews());
        allNews.addAll(findBBCNews());

        return allNews;
    }

    public List<News> searchNews(String keyword) {
        List<News> allNews = findAllNews();
        List<News> filteredNews = new ArrayList<>();

        for (News news :
                allNews) {
            if (news.getTitle().contains(keyword) || news.getDescription().contains(keyword) || news.getSource().contains(keyword))
                filteredNews.add(news);
        }

        return filteredNews;
    }
}
