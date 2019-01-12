package com.djwilde.studenciaki;

import com.djwilde.studenciaki.io.Faculties;
import com.djwilde.studenciaki.io.Students;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Studentz");
        primaryStage.setScene(new Scene(root, 800, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        Runnable facultiesTask = () -> {
            try {
                Faculties.getInstance().loadFromFile();
            } catch (IOException e) {
                Platform.runLater(() -> {
                    Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                    errorDialog.setTitle("Error");
                    errorDialog.setContentText("Error reading the file! Make sure your file isn't corrupted or if it exists.");
                    errorDialog.showAndWait();
                });
            }
        };
        Runnable studentsTask = () -> {
            try {
                Students.getInstance().loadFromFile();
            } catch (IOException e) {
                Platform.runLater(() -> {
                    Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                    errorDialog.setTitle("Error");
                    errorDialog.setContentText("Error reading the file! Make sure your file isn't corrupted or if it exists.");
                    errorDialog.showAndWait();
                });
            }
        };
        new Thread(facultiesTask).start();
        new Thread(studentsTask).start();
    }

    @Override
    public void stop() throws Exception {
        Runnable studentsTask = () -> {
            try {
                Students.getInstance().saveStudents();
            } catch (IOException e) {
                Platform.runLater(() -> {
                    Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                    errorDialog.setTitle("Error");
                    errorDialog.setContentText("Error writing to file! Make sure your file isn't corrupted or if it exists.");
                    errorDialog.showAndWait();
                });
            }
        };
        new Thread(studentsTask).start();
    }
}
