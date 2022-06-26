package cliente;
import exceptions.ClienteErroException;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente implements Runnable{
    private final String SERVER_ADRESS = "127.0.0.1";
    private static final int PORT_SERVIDOR = 3334;
    public ClientSocket clientSocket;
    private Scanner scanner;
    private String nickname;


    public ChatCliente(){
        scanner = new Scanner(System.in);
    }

    public String getNickname() {
        return nickname;
    }

    public void start() throws IOException {

//        mensage_loop();
//        clientSocket.closeC();
    }
    @Override
    public void run(){
        try {
            while (true) {
                String msg =clientSocket.getMessage(1);
                System.out.println(msg);
            }
        }catch (Exception e) {
            System.out.println("Conexão fechada.");
        }
    }
    public String geraNumero() throws IOException {
        clientSocket = new ClientSocket(new Socket(SERVER_ADRESS, PORT_SERVIDOR));
        return(clientSocket.confirma());
    }

    public boolean confirma(String numero, String apelido) throws IOException {
        clientSocket.msgSend(numero);
        String confirmação = clientSocket.getMessage(1);

        if (confirmação.isEmpty()){
            return false;
        }
        else {
            clientSocket.msgSend(apelido);
            this.nickname = apelido;
        }
        return true;
   }
//    private void mensage_loop() throws NullPointerException{
//        String message;
//        Boolean verifica = true;
//        do{
//            message = scanner.nextLine();
//            if (message.equals("--*")){
//                message = scanner.nextLine();
//                verifica = clientSocket.msgSend("*".concat(message));
//            }
//            else {
//                verifica = clientSocket.msgSend(message);
//            }
//        } while ((verifica) || (!message.equals("*exit")));
//    }

    public static void main (String[]args) throws ClienteErroException {
        ChatCliente cliente = new ChatCliente();
        try {
            cliente.start();
        } catch (IOException ex){
            cliente.clientSocket.closeC();
            throw new ClienteErroException();

        }

    }
}
