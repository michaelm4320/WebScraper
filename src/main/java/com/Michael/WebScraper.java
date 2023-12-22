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
                String linkUrl = link.attr("abs:href");
                Document individualPage = Jsoup.connect(linkUrl).get();

                String title = individualPage.select("h1").first().text();
                Element content = individualPage.getElementById("bodyContent");

                // Call writeToFile from your fileWriting class
                fileWriting.writeToFile(title, content.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


