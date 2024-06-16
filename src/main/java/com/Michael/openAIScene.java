package com.Michael;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import okhttp3.*;

import java.io.IOException;

// Other imports

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

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setStyle("-fx-background-color: #336699;");
        hBox.getChildren().addAll(btnFile, btnSend);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(hBox, textArea, sendMessageArea);

        rootPane.setTop(hBox);
        rootPane.setLeft(vBox);

        sendMessageArea.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage(sendMessageArea, textArea);
            }
        });

        btnSend.setOnAction(e -> sendMessage(sendMessageArea, textArea));
    }

    private void sendMessage(TextArea sendMessageArea, TextArea textArea) {
        String userInput = sendMessageArea.getText().trim();
        if (userInput.isEmpty()) {
            return;
        }
        sendMessageArea.clear();
        textArea.appendText("You: " + userInput + "\n");

        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            textArea.appendText("Error: API key not set\n");
            return;
        }

        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        String url = "https://api.openai.com/v1/chat/completions";
        String json = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [{\"role\": \"user\", \"content\": \"" + userInput + "\"}]\n" +
                "}";

        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() -> textArea.appendText("Failed to fetch response: " + e.getMessage() + "\n"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Platform.runLater(() -> {
                        try {
                            textArea.appendText("Failed to fetch response: \nResponse Code: " + response.code() + "\nResponse Body: " + response.body().string() + "\n");
                        } catch (IOException e) {
                            textArea.appendText("Failed to fetch response: " + e.getMessage() + "\n");
                        }
                    });
                } else {
                    String responseBody = response.body().string();
                    JsonNode jsonNode = objectMapper.readTree(responseBody);
                    String assistantMessage = jsonNode.get("choices").get(0).get("message").get("content").asText();
                    Platform.runLater(() -> textArea.appendText("AI: " + assistantMessage + "\n"));
                }
            }
        });
    }

    public BorderPane getRootPane() {
        return rootPane;
    }
}
