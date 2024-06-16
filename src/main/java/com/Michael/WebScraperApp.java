package com.Michael;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WebScraperApp extends Application {
    private VBox vBox; // Class-level variable

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        vBox = createMainLayout(primaryStage);

        Scene scene = new Scene(vBox, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Basic JavaFX App");
        primaryStage.show();
    }

    private String directoryPath;

    public VBox createMainLayout(Stage primaryStage) {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        TextArea textAreaTwo = new TextArea();
        textAreaTwo.setEditable(true);

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

        Button chatScene = new Button("ChatGPT");
        chatScene.setOnAction(e -> {
            openAIScene scene = new openAIScene(primaryStage, this);
            primaryStage.setScene(new Scene(scene.getRootPane(), 800, 600));
        });

        Button btnCreate = new Button("Create");
        Button btnSave = new Button("Save");
        Button btnDelete = new Button("Delete");

        Button button = new Button("Scrape Docs");
        button.setOnAction(e -> WebScraper.scraper());

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setStyle("-fx-background-color: #336699;");
        hBox.getChildren().addAll(button, btnLoad, btnCreate, btnSave, btnDelete, chatScene);

        HBox hBoxTwo = new HBox(10);
        hBoxTwo.setPadding(new Insets(15, 12, 15, 12));
        hBoxTwo.getChildren().addAll(textArea, textAreaTwo);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(hBox, hBoxTwo);

        textArea.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String selectedFileName = getSelectedFileName(textArea);
            if (selectedFileName != null) {
                File selectedFile = new File(directoryPath, selectedFileName);
                if (selectedFile.isFile()) {
                    try {
                        String content = new String(Files.readAllBytes(selectedFile.toPath()));
                        textAreaTwo.setText(content);
                    } catch (IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Could not read file");
                        alert.setContentText("An error occurred while reading the file.");
                        alert.showAndWait();
                    }
                }
            }
        });

        btnCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Create New File");
                fileChooser.setInitialFileName("newfile.txt");

                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    try {
                        if (file.createNewFile()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("File Created");
                            alert.setContentText("The file " + file.getName() + " was created successfully.");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("File Exists");
                            alert.setContentText("The file " + file.getName() + " already exists.");
                            alert.showAndWait();
                        }
                    } catch (IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Could not create file");
                        alert.setContentText("An error occurred while creating the file.");
                        alert.showAndWait();
                    }
                }
            }
        });

        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String selectedFileName = getSelectedFileName(textArea);
                File selectedFile = new File(directoryPath, selectedFileName);
                if (selectedFile.isFile()) {
                    try {
                        String content = textAreaTwo.getText();
                        Files.write(selectedFile.toPath(), content.getBytes());
                    } catch (IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Could not read file");
                        alert.setContentText("An error occurred while reading the file.");
                        alert.showAndWait();
                    }
                }
            }
        });

        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String selectedFileName = getSelectedFileName(textArea);
                File selectedFile = new File(directoryPath, selectedFileName);
                selectedFile.delete();
                displayDirectoryContents(selectedFile.getParentFile(), textArea);
            }
        });

        return vBox;
    }

    private void displayDirectoryContents(File directory, TextArea textArea) {
        directoryPath = directory.getAbsolutePath();
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

    private String getSelectedFileName(TextArea textArea) {
        int caretPosition = textArea.getCaretPosition();
        String[] lines = textArea.getText().split("\n");
        int cumulativeLength = 0;
        for (String line : lines) {
            cumulativeLength += line.length() + 1;
            if (caretPosition <= cumulativeLength) {
                return line.replace("File: ", "").trim();
            }
        }
        return null;
    }

    public VBox getRootPane() {
        return createMainLayout(new Stage());
    }

    public String getFileContent(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

}
