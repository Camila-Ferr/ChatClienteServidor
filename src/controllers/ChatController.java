package controllers;

import Cliente.ChatCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;

public class ChatController {

    @FXML
    private Label label;
    @FXML
    private Label CliNickname;
    public ChatCliente chatCliente;
    public void initialize() {
        this.chatCliente = LoginController.chatCliente;
        CliNickname.setText(chatCliente.getNickname());

    }

    // Metódo que muda a cena
    public void changeSceneButtonPushed(ActionEvent event) throws Exception {
        URL login = getClass().getResource("/views/SceneLogin.fxml");
        if (login == null) return;

        Parent chatParent = FXMLLoader.load(login);
        Scene chatScene = new Scene(chatParent);

        // Pega a informção da cena
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(chatScene);
        window.show();
    }




   @FXML
    private TextField chatUsuario;
}