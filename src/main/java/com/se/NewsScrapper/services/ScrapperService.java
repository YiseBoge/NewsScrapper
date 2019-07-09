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

@Service
public class ScrapperService {
    private WebDriver driver;
    private long sleepTime = 10000;

    public List<News> findFanaNews() throws InterruptedException {
        String type = "Fana";

        ArrayList<String> topicsFana = new ArrayList<>();
        List<News> fanaPosts = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver", "/Users/yiseboge/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        String url = "https://fanabc.com/category/localnews/";
        driver.get(url);

        Thread.sleep(sleepTime);
        List<WebElement> fainaTrending = driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/div[1]/div[1]"));

        for (WebElement i : fainaTrending) {
            topicsFana.add(i.getText());
        }
        String[] fromFana = topicsFana.get(0).split("\n");
        News fanaPost = new News();
        int fanaTitleInd = 1, fanaDescInd = 4, fanaDateInd = 2;
        for (String i : fromFana) {
            if (fanaDateInd <= 30 && fanaDescInd <= 30 && fanaTitleInd <= 30) {

                fanaPost.setDate(fromFana[fanaDateInd]);
                fanaPost.setTitle(fromFana[fanaTitleInd]);
                fanaPost.setDescription(fromFana[fanaDescInd]);
                fanaPost.setSource(type);

                fanaPosts.add(fanaPost);
            }
            fanaDateInd += 5;
            fanaDescInd += 5;
            fanaTitleInd += 5;
        }
        return fanaPosts;
    }


    public List<News> findReporterNews() throws InterruptedException {
        String type = "Reporter";

        ArrayList<String> topicsReporter = new ArrayList<>();
        List<News> reporterPosts = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver", "/Users/yiseboge/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        String url = "https://www.ethiopianreporter.com/index.php/zena";
        driver.get(url);

        Thread.sleep(sleepTime);
        List<WebElement> reporterZena = driver.findElements(By.xpath("//*[@id=\"block-gavias-kama-content\"]/div/div/div/div"));

        for (WebElement i : reporterZena) {
            topicsReporter.add(i.getText());
            //System.out.println(i.getText());
        }

        //System.out.println(topicsReporter.get(0));
        String[] reporterSplited = topicsReporter.get(0).split("\n");
        int fi = 0, se = 1, ti = 2;
        for (int j = 0; j <= 10; j++) {
            News reporterPost = new News();
            if (fi <= 30 && se <= 30 && ti <= 30) {

                reporterPost.setDate(reporterSplited[fi]);
                reporterPost.setTitle(reporterSplited[se]);
                reporterPost.setDescription(reporterSplited[ti]);
                reporterPost.setSource(type);

                reporterPosts.add(reporterPost);
            }
            fi += 3;
            se += 3;
            ti += 3;
        }

        return reporterPosts;
    }


    public List<News> findBBCNews() throws InterruptedException {
        String type = "BBC - Amharic";

        List<News> bbcPosts = new ArrayList<>();
        ArrayList<String> topicsBbc = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver", "/Users/yiseboge/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        String url = "https://www.bbc.com/amharic/topics/e986aff5-6b26-4638-b468-371d1d9617b4";
        driver.get(url);
        Thread.sleep(sleepTime);

        List<WebElement> bbcAmharic = driver.findElements(By.xpath("//*[@id=\"page\"]/div/div[2]/div/div[1]/div"));

        for (WebElement i : bbcAmharic) {
            topicsBbc.add(i.getText());
            System.out.println(i.getText());
        }
        String[] bbcSplited = topicsBbc.get(0).split("\n");
        News bbcPost = new News();

        int dateInd = 2, descInd = 1, titleInd = 0;
        for (String i : bbcSplited) {
            if (dateInd <= 60 && descInd <= 60 && titleInd <= 60) {

                bbcPost.setDate(bbcSplited[dateInd]);
                bbcPost.setSource(type);
                bbcPost.setDescription(bbcSplited[descInd]);
                bbcPost.setTitle(bbcSplited[titleInd]);

                dateInd += 3;
                descInd += 3;
                titleInd += 3;
                bbcPosts.add(bbcPost);
            }
        }

        return bbcPosts;
    }

    public List<News> findAllNews() throws InterruptedException {

        List<News> allNews = new ArrayList<>();
        allNews.addAll(findFanaNews());
        allNews.addAll(findReporterNews());
        allNews.addAll(findBBCNews());

        return allNews;
    }
}
