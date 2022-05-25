package Cliente;
import Exceptions.ClienteErroException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente {
    private final String SERVER_ADRESS = "127.0.0.1";
    private static final int PORT_SERVIDOR = 3334;
    private ClientSocket clientSocket;
    private Scanner scanner;
    

    public ChatCliente(){
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        clientSocket = new ClientSocket(new Socket(SERVER_ADRESS, PORT_SERVIDOR));

        mensage_loop();
    }
    private void mensage_loop() throws IOException{
        String message;
        do{
            message = scanner.nextLine();
            clientSocket.msgSend(message);
        } while (!message.equals(" "));
    }

    public static void main (String[]args) throws ClienteErroException {
        try {
            ChatCliente cliente = new ChatCliente();
            cliente.start();
        } catch (IOException ex){
            throw new ClienteErroException();

        }

    }
}
