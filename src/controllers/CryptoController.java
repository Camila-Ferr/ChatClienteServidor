package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class CryptoController {

    public Button roomButton;
    public Button helpButton;
    public Label CliNickname;
    public VBox vbox_messages;
    public ScrollPane scrollPane_chat;
    public Label normalChat;
    public Label chavePublica;
    public Label cryptoChat;
    public TextField textField_client;
    public Button button_send;
    @FXML
    private Button chatButton;
    @FXML
    private boolean ChangeScene;



    // Metódos que mudam a cena
    public void changeSceneToChat(ActionEvent event) throws Exception {
        if (this.ChangeScene) {
            URL chat = getClass().getResource("/views/SceneChat.fxml");
            if (chat == null) return;

            Parent chatParent = FXMLLoader.load(chat);
            Scene chatScene = new Scene(chatParent);

            // Pega a informção da cena
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(chatScene);
            window.show();
        }
    }

    public void changeSceneToRooms(ActionEvent event) throws Exception {
        if (this.ChangeScene) {
            URL rooms = getClass().getResource("/views/SceneRooms.fxml");
            if (rooms == null) return;

            Parent chatParent = FXMLLoader.load(rooms);
            Scene chatScene = new Scene(chatParent);

            // Pega a informção da cena
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(chatScene);
            window.show();
        }
    }
}
