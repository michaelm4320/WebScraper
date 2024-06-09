package com.Michael;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WebScraperApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        Button button = new Button("Scrape Docs");
        Button btnLoad = new Button("Load Folder");

        // Set actions for buttons
        button.setOnAction(e -> WebScraper.scraper());
        // Add your action for btnLoad here

        // Create HBox for buttons
        HBox hbox = new HBox(10); // spacing between buttons
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setStyle("-fx-background-color: #336699;");
        hbox.getChildren().addAll(button, btnLoad);

        // Create VBox for layout
        VBox layout = new VBox(10); // spacing between elements
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(hbox, textArea);

        Scene scene = new Scene(layout, 400, 300); // Increased size for better layout

        primaryStage.setScene(scene);
        primaryStage.setTitle("Basic JavaFX App");
        primaryStage.show();
    }
}
