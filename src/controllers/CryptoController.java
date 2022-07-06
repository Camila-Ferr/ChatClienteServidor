package controllers;

import cliente.ChatCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;

public class CryptoController {

    public Button roomButton;
    public Label CliNickname;
    public VBox vbox_messages;
    public ScrollPane scrollPane_chat;
    public Label normalChat;
    public Label chavePublica;
    public Label chavePublicaServidor;
    @FXML
    public Label cryptoChat;
    @FXML
    public Label cryptoChat1;
    @FXML
    public Label cryptoChat2;
    @FXML
    public Label cryptoChat3;
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
        criptoFunction();
    }

    public void send(){
        normalChat.setText(chatCliente.clientSocket.Encode(textField_client.getText()));
        textField_client.clear();
    }
    public void setText (){
        mensagens.add("Aqui, estamos usando a criptografia Diffie Helman.");
        mensagens.add(" Nessa criptografia, você e o servidor tem 2 chaves :");
        mensagens.add("a privada, que é gerada aleatoriamente, e a pública, que é gerada");
        mensagens.add("a partir da privada.");
        mensagens.add("Por motivos de segurança, a chave privada não foi disponibilizada.");                // 1 cena
        mensagens.add("A partir dessas 2 chaves, no início da conexão, antes de qualquer ");
        mensagens.add("comunicação ocorre uma troca de chaves públicas.");
        mensagens.add("");
        mensagens.add("Agora que o servidor já conhece sua chave pública e você conhece a dele,");                        // 2 cena
        mensagens.add("vocês combinarão uma chave decifradora.");
        mensagens.add("Essa chave só precisará da sua chave privada e da chave pública do servidor. ");
        mensagens.add(" Da mesma forma, o servidor calcula a mesma chave do outro lado.");
        mensagens.add("Agora que chegamos a um denominador comum, é possível cifrar");                    // 3 cena
        mensagens.add("e descifrar as mensagens como quiser.");
        mensagens.add("Para esse projeto, foi escolhido transformar as mensagens para um array");
        mensagens.add("de bytes e multiplicá-las pela chave codificadora.");
        mensagens.add("É a sua hora de testar, escreva uma mensagem abaixo");                             // 4 cena
        mensagens.add("e veja como ela foi criptografada :");
        mensagens.add("");
        mensagens.add("");
    }

    public void criptoFunction(){
        chavePublica.setText(String.valueOf(chatCliente.clientSocket.getPublic_key()));
        chavePublicaServidor.setText((String.valueOf(chatCliente.clientSocket.getServer_key())));
        cryptoChat.setText(mensagens.get(cripto));
        cryptoChat1.setText(mensagens.get(cripto +1));
        cryptoChat2.setText(mensagens.get(cripto +2));
        cryptoChat3.setText(mensagens.get(cripto +3));
    }

    public void prox(){
        cripto +=4;
        if (cripto == mensagens.size()){
            cripto = 0;
        }
        criptoFunction();
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
