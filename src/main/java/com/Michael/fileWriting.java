package com.Michael;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class fileWriting {
    public static void writeToFile(String fileName, String text) {
        try {
            // Replace characters that are not allowed in file names
            fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            Path path = Paths.get("C:\\Users\\Michael\\Documents\\Java Projects\\" +
                    "WebScraper\\WebScraper\\src\\main\\PokemonDocs" + fileName + ".txt");
            Files.write(path, text.getBytes());
        } catch (
        IOException e) {
            e.printStackTrace();
        }
    }
}
