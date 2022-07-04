package cliente;

import java.io.IOException;
import java.net.Socket;


public class ChatCliente {
    private final String SERVER_ADRESS;
    private static final int PORT_SERVIDOR = 3334;
    public ClientSocket clientSocket;

    private String nickname;

    public ChatCliente() {
        this.SERVER_ADRESS = "127.0.0.1";
    }

    public String getNickname() {
        return nickname;
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
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}