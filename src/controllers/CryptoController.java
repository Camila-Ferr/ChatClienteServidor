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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;

public class CryptoController {

    public Button roomButton;
    public Button helpButton;
    public Label CliNickname;
    public VBox vbox_messages;
    public ScrollPane scrollPane_chat;
    public Label normalChat;
    public Label chavePublica;
    @FXML
    public Label cryptoChat;
    @FXML
    public Label titleCripto;
    public TextField textField_client;
    public Button button_send;
    private ChatCliente chatCliente;
    private int cripto = 0;
    private ArrayList<String> mensagens = new ArrayList<>();
    public void initialize() {
        chatCliente = LoginController.chatCliente;
        setText();
        cripto();

    }
    public void send(){
        normalChat.setText(chatCliente.clientSocket.Encode(textField_client.getText()));
        textField_client.clear();
    }
    public void setText (){
        mensagens.add("Aqui, estamos usando a criptografia Diffie Helman. Nessa criptografia, você e o servidor tem 2 chaves : a privada que é gerada aleatoriamente, " +
                "e a pública, que é gerada a partir da privada e um número constante para os dois lados." +
                "Por  motivos de segurança, a chave privada não foi disponibilizada.");
        mensagens.add("A partir dessas 2 chaves, no início da conexão, ocorre uma troca de chaves públicas. Como a chave pública foi gerada com o auxílio da privada, " +
                "apenas com a chave pública do servidor e a sua chave privada, você consegue calcular uma chave decifradora. Da mesma forma, o servidor calcula a mesma chave " +
                "do outro lado.");
        mensagens.add("Agora que chegamos a um denominador comum, é possível cifrar e descifrar as mensagens como quiser.");
        mensagens.add("Para esse projeto, foi escolhido transformar as mensagens para um array de bytes e multiplicá-las pela chave codificadora. É a sua hora de testar," +
                "escreva uma mensagem abaixo e veja como ela foi criptografada :");
    }
    public void cripto(){
        chavePublica.setText("Sua chave pública: " +String.valueOf(chatCliente.clientSocket.getPublic_key()).concat("/n")
                .concat("Chave pública do servidor : " + String.valueOf(chatCliente.clientSocket.getPublic_key())));
        titleCripto.setText("Como meus textos estão sendo criptografados ?");
        cryptoChat.setText(mensagens.get(cripto));
    }
    public void prox(){
        cripto +=1;
        if (cripto == mensagens.size()){
            cripto = 0;
        }
        cripto();
    }


    // Metódos que mudam a cena
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

    public void changeSceneToRooms(ActionEvent event) throws Exception {
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
