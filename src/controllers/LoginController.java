package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Cliente.ChatCliente;

import java.io.IOException;
import java.net.URL;

public class LoginController {
    @FXML
    private TextField Nickname;

    @FXML
    private TextField ServerNumber;

    @FXML
    private Label NumberLabel;

    @FXML
    private Label Response;
    private boolean ChangeScene;

    private ChatCliente chatCliente = new ChatCliente();

    // Metódo que muda a cena
    public void changeSceneButtonPushed(ActionEvent event) throws Exception {
        if (this.ChangeScene) {
            URL chat = getClass().getResource("/views/SceneBuilder.fxml");
            if (chat == null) return;

            Parent chatParent = FXMLLoader.load(chat);
            Scene chatScene = new Scene(chatParent);

            // Pega a informção da cena
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(chatScene);
            window.show();
        }
        else {
            Response.setText("Não foi possível se conectar.");
        }
    }
    //Confirma um número
    public void submit() throws Exception {
       String numero = ServerNumber.getText();
       String apelido = Nickname.getText();
       boolean continua = chatCliente.confirma(numero,apelido);
       if (continua){
           ActionEvent event = new ActionEvent();
           Response.setText("Conectado");
           this.ChangeScene = true;
       }
       else {
           Response.setText("Erro ao se conectar, conexão fechada.");
       }
    }
    //Gera um número
    public void number() throws IOException {
        this.ChangeScene = false;
        chatCliente.start();
        NumberLabel.setText(chatCliente.geraNumero());


    }
}