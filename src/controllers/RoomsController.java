package controllers;

import cliente.ChatCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RoomsController {
    public ListView<Integer> serverNumberList;
    @FXML
    private Button Connect;
    @FXML
    private ImageView ImageConnect;
    private ChatCliente chatCliente;


    public void initialize() throws IOException {
        Connect.setVisible(false);
        ImageConnect.setVisible(false);
        chatCliente = LoginController.chatCliente;
        chatCliente.clientSocket.msgSend("*activeR");
       int active_rooms = Integer.parseInt(chatCliente.clientSocket.getOneMessage());
        chatCliente.clientSocket.getMessage(1);
//        for (int i = 0; i<active_rooms; i++){
//            serverNumberList.getItems().add(Integer.parseInt(chatCliente.clientSocket.getMessage(1)));
//        }
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
