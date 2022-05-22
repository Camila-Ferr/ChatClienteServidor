package Servidor;

import Exceptions.ServidorErroException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

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
            new Thread(cliente::getMessage).start();

            //TIRA ISSO
            System.out.println("Mensagem recebida do cliente " +cliente.getRemoteSocketAddress()  +":" +message);
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
