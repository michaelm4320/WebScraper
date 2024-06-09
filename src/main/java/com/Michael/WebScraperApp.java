package com.Michael;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class WebScraperApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        TextArea textAreaTwo = new TextArea();
        textAreaTwo.setEditable(false);

        textAreaTwo.setPrefHeight(400);
        textAreaTwo.setPrefWidth(600);

        Button btnLoad = new Button("Load Folder");
        btnLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                DirectoryChooser dc = new DirectoryChooser();
                dc.setInitialDirectory(new File(System.getProperty("user.home")));
                File choice = dc.showDialog(primaryStage);
                if (choice == null || !choice.isDirectory()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Could not open directory");
                    alert.setContentText("The file is invalid.");
                    alert.showAndWait();
                } else {
                    displayDirectoryContents(choice, textArea);
                }
            }
        });

        Button button = new Button("Scrape Docs");


        button.setOnAction(e -> WebScraper.scraper());

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setStyle("-fx-background-color: #336699;");
        hBox.getChildren().addAll(button, btnLoad);

        HBox hBoxTwo = new HBox(10);
        hBoxTwo.setPadding(new Insets(15, 12, 15, 12));
        hBoxTwo.getChildren().addAll(textArea, textAreaTwo);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(hBox, hBoxTwo);

        Scene scene = new Scene(vBox, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Basic JavaFX App");
        primaryStage.show();
    }

    private void displayDirectoryContents(File directory, TextArea textArea) {
        StringBuilder content = new StringBuilder();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                content.append("Directory: ").append(file.getName()).append("\n");
            } else {
                content.append("File: ").append(file.getName()).append("\n");
            }
        }
        textArea.setText(content.toString());
    }
}
