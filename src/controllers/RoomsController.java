package controllers;

import cliente.ChatCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RoomsController {
    @FXML
    public Label serverNumber1;
    @FXML
    public Label serverNumber2;
    @FXML
    public Label serverNumber3;
    @FXML
    public Label serverNumber4;
    @FXML
    public Label serverNumber5;
    @FXML
    private Button Connect;
    @FXML
    private ImageView ImageConnect;
    private ChatCliente chatCliente;
    @FXML
    private TextField ServerNumber;
    @FXML
    public Button buttonRoom;
    @FXML
    private Label Response;
    private boolean con = false;


    public void initialize() throws IOException {
        Connect.setVisible(false);
        ImageConnect.setVisible(false);
        chatCliente = LoginController.chatCliente;
        chatCliente.clientSocket.msgSend("*changeR");

    }

    public void canConnect() {
        con = true;
        Connect.setVisible(true);
        ImageConnect.setVisible(true);
    }

    public void chooseRoom() {
        try {
            Integer new_sala = Integer.parseInt(ServerNumber.getText()) - 1;
            if ((new_sala >= 0) && (new_sala <= 4)) {
                chatCliente.clientSocket.msgSend(String.valueOf(new_sala));
                canConnect();
                Response.setText("Conectado");
            }
             else {
                Response.setText("Erro, digite uma sala válida.");
            }
        } catch (Exception e) {
            Response.setText("Deu merda.");
        }

    }

    // Ao invés de passar o numero do servidor
    // Passa uma lista para o listView
    // Unica coisa que tem que fazer é pegar os numeros dos servidores
    // e add na lista chamada listaNumerosServidor

    //Confirma um número

    // Metódo que muda a cena
    public void changeSceneToChat(ActionEvent event) throws Exception {
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
