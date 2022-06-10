package Cliente;
import Exceptions.ClienteErroException;
import Exceptions.ConexaoClientException;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente implements Runnable{
    private final String SERVER_ADRESS = "127.0.0.1";
    private static final int PORT_SERVIDOR = 3334;
    private ClientSocket clientSocket;
    private Scanner scanner;


    public ChatCliente(){
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        clientSocket = new ClientSocket(new Socket(SERVER_ADRESS, PORT_SERVIDOR));
        confirma(clientSocket);
        new Thread(this).start();
        mensage_loop();
    }
    @Override
    public void run(){
        try {
            while (true) {
                String msg =clientSocket.getMessage(1);
                System.out.println(msg);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void confirma(ClientSocket clientSocket) throws IOException {
        clientSocket.confirma();
        String confirmação = clientSocket.getMessage(1);
        System.out.println(confirmação);

        if (confirmação.isEmpty()){
            throw new ConexaoClientException();
        }
        clientSocket.msgSend(scanner.nextLine());
    }
    private void mensage_loop() throws NullPointerException{
        String message;
        do{
            message = scanner.nextLine();
            if (message.equals("--*")){
                message = scanner.nextLine();
                clientSocket.msgSend("*".concat(message));
            }
            else {
                clientSocket.msgSend(message);
            }
        } while (!message.equalsIgnoreCase("exit"));
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
