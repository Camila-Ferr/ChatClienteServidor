package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class BasicApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = getClass().getResource("SceneLogin.fxml");
        if (resource == null) return;

        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Cliente");
        stage.setWidth(1026);
        stage.setHeight(770);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}