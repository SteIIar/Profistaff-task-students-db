package ru.profistaff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentsCRUDApp extends Application {

    private static final String TITLE_APP = "Profistaff Students CRUD Application";


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle(TITLE_APP);
        GridPane gridPane = FXMLLoader.load(getClass().getResource("/students_crud.fxml"));
        primaryStage.setScene(new Scene(gridPane));


        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
