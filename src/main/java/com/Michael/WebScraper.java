package com.Michael;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public static void main(String[] args) {
        int count = 0;
        try {
            String url = "https://essentialsdocs.fandom.com/wiki/Essentials_Docs_Wiki";
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .get();

            Elements links = document.select("tbody li a");
            for (Element link : links) {
                count++;
                String linkUrl = link.attr("abs:href");
                try {
                    Document individualPage = Jsoup.connect(linkUrl)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .referrer("http://www.google.com")
                            .get();

                    String title = individualPage.select("h1").first().text();
                    Element content = individualPage.getElementById("bodyContent");

                    fileWriting.writeToFile(title, content.text());
                } catch (Exception e) {
                    System.err.println("Error processing link: " + linkUrl);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }
}
