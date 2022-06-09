package Servidor;

import Exceptions.ServidorErroException;

import java.io.*;
import java.net.ServerSocket;


public class Servidor {
    private static final int PORT_SERVIDOR = 3334;
    private  ServerSocket serverSocket;

    public void start() throws IOException {
        System.out.println("Servidor iniciado na porta " +PORT_SERVIDOR );
        serverSocket = new ServerSocket(PORT_SERVIDOR);
        clienteConnectionLoop();
    }

    private void clienteConnectionLoop() throws IOException{
        while (true){
            ServidorSocket cliente = new ServidorSocket (serverSocket.accept());
            if (cliente.confirma_chaves()) {
                cliente.sendMessage("Digite seu apelido: ",'-');
                cliente.setClient_id(cliente.keys.Desencode(cliente.getMessage()));
                new Thread(() -> {
                    try {
                        clientMessageLoop(cliente);
                    } catch (ServidorErroException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
            else {
                cliente.sendMessage("",'-');
            }
        }
    }
    public void clientMessageLoop(ServidorSocket socket) throws ServidorErroException {
        String message = null;
        try {
            while (true) {
                message = null;
                message = socket.keys.Desencode(socket.getMessage());
                if ("exit".equals(message)) {
                    return;
                }
                System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                System.out.printf("Mensagem: %s\n", message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main (String[] args) throws ServidorErroException {
        //Serviço de escuta

        try {
            Servidor servidor = new Servidor();
            servidor.start();

        } catch (IOException e) {
            throw new ServidorErroException(PORT_SERVIDOR);
        }

    }
}
