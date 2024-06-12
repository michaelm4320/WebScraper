package com.Michael;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        //https://stackoverflow.com/questions/27982895/how-to-use-keyevent-in-javafx-project

        /*btnSend.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("Enter Pressed");
                }
            }
        });*/
        //https://stackoverflow.com/questions/42512513/adding-event-listener-to-mainscene-in-javafx-using-fxml
    }

    public BorderPane getRootPane() {
        return rootPane;
    }
}
