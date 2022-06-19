package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class LoginController {

    // Metódo que muda a cena
    public void changeSceneButtonPushed(ActionEvent event) throws Exception {
        URL chat = getClass().getResource("/views/SceneBuilder.fxml");
        if (chat == null) return;

        Parent chatParent = FXMLLoader.load(chat);
        Scene chatScene = new Scene(chatParent);

        // Pega a informção da cena
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(chatScene);
        window.show();
    }
}
