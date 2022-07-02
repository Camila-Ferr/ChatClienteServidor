package controllers;

import cliente.ChatCliente;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ChatController {
    @FXML
    private Button button_send;
    @FXML
    private TextField textField_client;
    @FXML
    private VBox vbox_messages;

    @FXML
    private static VBox vbox_messages_server;
    @FXML
    private ScrollPane scrollPane_chat;
    @FXML
    private Label CliNickname;
    public ChatCliente chatCliente;
    private boolean ChangeScene;


    public void initialize() throws IOException {
        this.chatCliente = LoginController.chatCliente;
        CliNickname.setText(chatCliente.getNickname());

        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane_chat.setVvalue((Double) newValue);
            }
        });

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend = textField_client.getText();
                boolean check = sendMsg(messageToSend);
                if (check){
                    if (!messageToSend.isEmpty()) {
                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        hBox.setPadding(new Insets(5, 5, 5, 10));

                        Text text = new Text(messageToSend);
                        TextFlow textFlow = new TextFlow(text);

                        textFlow.setStyle("-fx-color: rgba(239, 242, 255);" +
                                "-fx-background-color: #12B886;" +
                                "-fx-background-radius: 20px");

                        textFlow.setPadding(new Insets(5, 10, 5, 10));
                        text.setFill(Color.color(0.934, 0.945, 0.996));
                        hBox.getChildren().add(textFlow);
                        vbox_messages.getChildren().add(hBox);
                    }


                    textField_client.clear();
                }
            }
        });
    }

    public boolean sendMsg(String messageToSend){
        boolean check;
        if (messageToSend.equals("--*")){
            messageToSend = textField_client.getText();
            check = chatCliente.clientSocket.msgSend("*".concat(messageToSend));
        }
        else {
            check = chatCliente.clientSocket.msgSend(messageToSend);
        }
        if (messageToSend.equals("*exit")){
            return false;
        }
        return check;
    }

    public static void addLabel(String messageFromServer) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgba(233, 233, 235);" +
                "-fx-background-radius: 20px");

        textFlow.setPadding(new Insets(5,10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox_messages_server.getChildren().add(hBox);
            }
        });
    }

    // Metódos que mudam a cena
    public void changeSceneToCrypto(ActionEvent event) throws Exception {
        if (this.ChangeScene) {
            URL crypto = getClass().getResource("/views/SceneCrypto.fxml");
            if (crypto == null) return;

            Parent chatParent = FXMLLoader.load(crypto);
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
