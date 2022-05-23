package Cliente;
import Exceptions.ClienteErroException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente {
    private final String SERVER_ADRESS = "127.0.0.1";
    private static final int PORT_SERVIDOR = 3334;
    private Socket clientSocket;
    private Scanner scanner;
    private PrintWriter out;

    public ChatCliente(){
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        clientSocket = new Socket(SERVER_ADRESS, PORT_SERVIDOR);

        mensage_loop();
    }
    private void mensage_loop() throws IOException{
        String message;
        do{
            this.out = new PrintWriter(clientSocket.getOutputStream(),true);
            message = scanner.nextLine();
            out.write(message);
            //Confirma o envio
            out.flush();
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
