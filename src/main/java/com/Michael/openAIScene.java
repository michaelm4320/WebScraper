package com.Michael;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class openAIScene {
    private final BorderPane rootPane;

    public openAIScene(Stage primaryStage, WebScraperApp app) {
        rootPane = new BorderPane();

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        TextArea sendMessageArea = new TextArea();
        sendMessageArea.setEditable(true);

        textArea.setPrefHeight(600);
        textArea.setPrefWidth(750);

        sendMessageArea.setPrefHeight(30);

        Button btnSend = new Button("Submit");
        Button btnFile = new Button("View Files");
        btnFile.setOnAction(e -> {
            primaryStage.setScene(new Scene(app.createMainLayout(primaryStage), 800, 600));
        });

        // New button for fetching data using OkHttp
        Button fetchButton = new Button("Fetch Data");
        fetchButton.setOnAction(e -> fetchData(textArea));

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setStyle("-fx-background-color: #336699;");
        hBox.getChildren().addAll(btnFile, btnSend, fetchButton);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(hBox, textArea, sendMessageArea);

        rootPane.setTop(hBox);
        rootPane.setLeft(vBox);

        sendMessageArea.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String userInput = sendMessageArea.getText().trim();
                sendMessageArea.clear();
                textArea.appendText("You: " + userInput + "\n");
            }
        });

    }

    public BorderPane getRootPane() {
        return rootPane;
    }

    private void fetchData(TextArea textArea) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com")
                .build();

        new Thread(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Platform.runLater(() -> textArea.setText(responseData));
                } else {
                    Platform.runLater(() -> textArea.setText("Request failed: " + response.code()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> textArea.setText("An error occurred: " + e.getMessage()));
            }
        }).start();
    }
}
