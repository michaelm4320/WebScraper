package com.Michael;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://essentialsdocs.fandom.com/wiki/Essentials_Docs_Wiki").get();
            System.out.println(doc.title());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
