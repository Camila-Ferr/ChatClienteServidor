package controllers;

import Cliente.ChatCliente;

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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;

public class ChatController {

    @FXML
    private Button button_send;
    @FXML
    private TextField textField_client;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane scrollPane_chat;
    @FXML
    private Label CliNickname;
    public ChatCliente chatCliente;

    public void initialize() {
        this.chatCliente = LoginController.chatCliente;
        CliNickname.setText(chatCliente.getNickname());

        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane_chat.setVvalue((Double) newValue);
            }
        });
        // Youtube 15:05
        // Método do servidor em que recebe a mensagem do usuário
        // e ele recebe vbox_messages como entrada (servidor.recebemensagem(vbox_messages))

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend = textField_client.getText();

                if (!messageToSend.isEmpty()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-color: rgba(239, 242, 255);" +
                                      "-fx-background-color: #12B886;"  +
                                      "-fx-background-radius: 20px");

                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hBox.getChildren().add(textFlow);
                    vbox_messages.getChildren().add(hBox);

                    // Youtube: 20:20
                    // A mesma coisa que em cima, porém passando dessa vez não
                    // o local da mensagem mas sim a mensagem em si
                    // servidor.recebemensagem(messageToSend)

                    textField_client.clear();
                }
            }
        });
    }

    // Metódo que muda a cena
    public void changeSceneButtonPushed(ActionEvent event) throws Exception {
        URL login = getClass().getResource("/views/SceneLogin.fxml");
        if (login == null) return;

        Parent chatParent = FXMLLoader.load(login);
        Scene chatScene = new Scene(chatParent);

        // Pega a informção da cena
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(chatScene);
        window.show();
    }

    public static void addLabel(String messageFromClient, VBox vbox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgba(233, 233, 235);" +
                "-fx-background-radius: 20px");

        textFlow.setPadding(new Insets(5,10, 5, 10));
        hBox.getChildren().add(textFlow);

        // Youtube: 24:50
        // Cria um Runnable aqui
    }
}