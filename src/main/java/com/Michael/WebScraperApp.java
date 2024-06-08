package com.Michael;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
        Button button = new Button("Click Me");

        //Convert WebScraper file from main to regular public class, create function to run entire code
        //then call here to see if it works.

        //button.setOnAction(e -> TestFile.messageTest());
        //button.setOnAction(e -> textArea.setText("Button Clicked!"));

        VBox layout = new VBox(10, button, textArea);
        Scene scene = new Scene(layout, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Basic JavaFX App");
        primaryStage.show();
    }


}
