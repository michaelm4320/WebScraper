package com.Michael;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public static void main(String[] args) {
        try {
            String url = "https://essentialsdocs.fandom.com/wiki/Essentials_Docs_Wiki";
            Document document = Jsoup.connect(url).get();

            // Select all 'a' tags within 'li' elements and print their 'href' attributes
            Elements links = document.select("tbody li a");
            for (Element link : links) {
                System.out.println(link.attr("abs:href")); // 'abs:href' gets the absolute URL
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


