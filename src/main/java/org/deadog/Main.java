package org.deadog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.deadog.controllers.MainController;
import org.deadog.utils.PropertiesReader;
import org.deadog.exceptions.PropertyException;


public class Main extends Application {
    public static void main(String[] args) {
        try {
            PropertiesReader.configure("application.properties");
        } catch (PropertyException e) {
            System.err.println(e.getMessage());
            return;
        }
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            stage.setTitle("Heads x Hands");
            stage.setHeight(750);
            stage.setWidth(1200);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/mainScene.fxml"));
            stage.setResizable(false);
            Parent root = loader.load();
            MainController mainController = loader.getController();
            Scene scene = new Scene(root);
            scene.setOnKeyPressed(mainController::keyPressed);
            stage.setScene(scene);
            stage.show();
        } catch (LoadException e) {
            System.err.println("Ошибка загрузки");
        }
    }
}
