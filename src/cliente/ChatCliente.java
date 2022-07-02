package cliente;
import controllers.ChatController;
import exceptions.ClienteErroException;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente  {
    private final String SERVER_ADRESS;
    private static final int PORT_SERVIDOR = 3334;
    public ClientSocket clientSocket;
    private Scanner scanner;
    private String nickname;

    public ChatCliente() {
        this.SERVER_ADRESS = "127.0.0.1";
        scanner = new Scanner(System.in);
    }

    public String getNickname() {
        return nickname;
    }

    public void start() throws IOException {

    }

    public String geraNumero() throws IOException {
        clientSocket = new ClientSocket(new Socket(SERVER_ADRESS, PORT_SERVIDOR));
        return (clientSocket.confirma());
    }

    public boolean confirma(String numero, String apelido) throws IOException {
        clientSocket.msgSend(numero);
        try {
            String confirmação = clientSocket.getMessage(1);
            if (confirmação.equals("true")) {
                clientSocket.msgSend(apelido);
                this.nickname = apelido;
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    private void mensage_loop() throws NullPointerException{
        String message;
        Boolean verifica = true;
        do{
            message = scanner.nextLine();
            if (message.equals("--*")){
                message = scanner.nextLine();
                verifica = clientSocket.msgSend("*".concat(message));
            }
            else {
                verifica = clientSocket.msgSend(message);
            }
        } while ((verifica) || (!message.equals("*exit")));
    }
}
//    public static void main (String[]args) throws ClienteErroException {
//        ChatCliente cliente = new ChatCliente();
//        try {
//            cliente.start();
//        } catch (IOException ex){
//            cliente.clientSocket.closeC();
//            throw new ClienteErroException();
//
//        }
//
//    }
//}
