package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class ChangeSceneController {

    private boolean ChangeScene;


    public void changeSceneButtonPushed(ActionEvent event, String url) throws Exception {
        if (this.ChangeScene) {
            URL resource = getClass().getResource(url);
            if (resource == null) return;

            Parent chatParent = FXMLLoader.load(resource);
            Scene chatScene = new Scene(chatParent);

            // Pega a informção da cena
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(chatScene);
            window.show();
        }
    }
}
