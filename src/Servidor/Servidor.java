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
            cliente.confirma_chaves(cliente.getMessage());
            new Thread(() -> {
                try {
                    clientMessageLoop(cliente);
                } catch (ServidorErroException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
    public void clientMessageLoop(ServidorSocket socket) throws ServidorErroException {
        String message;
        try {
            while ((message = socket.getMessage()) != null) {
                if (!"exit".equalsIgnoreCase(message)) {
                    return;
                }
                System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                System.out.printf("Mensagem: %s\n", message);
            }
        } finally {
            socket.closeS();
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
