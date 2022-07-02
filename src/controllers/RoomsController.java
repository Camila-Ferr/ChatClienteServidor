package controllers;

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

import java.net.URL;

public class RoomsController {
    public ListView<Integer> serverNumberList;
    @FXML
    private Button Connect;
    @FXML
    private ImageView ImageConnect;


    public void initialize() {
        Connect.setVisible(false);
        ImageConnect.setVisible(false);
        serverNumberList.getItems().add(1);
        serverNumberList.getItems().add(2);
        serverNumberList.getItems().add(3);
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
