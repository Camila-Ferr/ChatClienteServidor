package servidor;


import exceptions.ServidorErroException;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static servidor.Comandos.*;


public class Servidor {
    private static final int PORT_SERVIDOR = 3334;
    private ServerSocket serverSocket;
    private final List<ServidorSocket> clients = new LinkedList<>();
    private ArrayList<Sala> salas = new ArrayList<>();


    public void start() throws IOException {
        System.out.println("Servidor iniciado na porta " + PORT_SERVIDOR);

        for (int i = 0; i < 5; i++) {
            Sala sala = new Sala(i);
            salas.add(sala);
        }
        serverSocket = new ServerSocket(PORT_SERVIDOR);
        new Thread(() -> {
            try {
                clienteConnectionLoop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void changeR(int nova, int antiga, ServidorSocket cliente) {
        try {
            salas.get(nova).addClientes(cliente);
            cliente.setSala(salas.get(nova));
            salas.get(nova).sendMessageToAll(cliente, cliente.getClient_id().concat(" entrou na sala."));

            salas.get(antiga).setClientes(cliente);//Diminui 1

        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }

    private void clienteConnectionLoop() throws IOException {
        while (true) {

            ServidorSocket cliente = new ServidorSocket(serverSocket.accept());

            new Thread(() -> {
                try {
                    if (cliente.confirma_chaves()) {
                        cliente.sendMessage("true", '-');
                        cliente.setClient_id(cliente.keys.Desencode(cliente.getMessage()));
                        clients.add(cliente);

                        salas.get(0).addClientes(cliente);
                        cliente.setSala(salas.get(0));
                        salas.get(0).sendMessageToAll(cliente, cliente.getClient_id().concat(" entrou na sala."));

                    } else {
                        cliente.sendMessage("false", '-');
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    clientMessageLoop(cliente);
                } catch (ServidorErroException e) {
                    cliente.getSala().setClientes(cliente);

                    try {
                        cliente.closeS();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    clients.remove(cliente);
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private void clientMessageLoop(ServidorSocket socket) throws ServidorErroException {
        String message = null;
        String id = null;
        try {
            while (true) {
                message = socket.keys.Desencode(socket.getMessage());
                if ("*exit".equals(message)) {
                    socket.getSala().setClientes(socket);
                    socket.closeS();
                    clients.remove(socket);
                    return;
                } else if ("*private".equals(message)) {
                    try {
                        sendToOne(socket, socket.keys.Desencode(socket.getMessage()), "Private message from :", socket.keys.Desencode(socket.getMessage()), clients);
                    } catch (Exception e) {
                        socket.sendMessage("Cliente não encontrado", '-');
                    }

                } else if ("*changeR".equals(message)) {
                    System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                    System.out.println("Comando para mudar a sala.");
                    try {
                        int nova = Integer.parseInt(socket.keys.Desencode(socket.getMessage()));
                        System.out.println(nova);
                        changeR(nova, socket.getSala().getId(), socket);
                        socket.sendMessage("true", '-');
                    } catch (Exception e) {
                        // socket.sendMessage("Sala não encontrada", '-');
                        socket.sendMessage("false", '-');
                        System.out.println("Sala não encontrada");
                        System.out.println(e.getMessage());
                        for (Sala sala : salas) {
                            System.out.println(sala.getClientes());
                        }
                    }
                } else if ("*public".equals(message)) {
                    sendMessageToAll(socket, socket.keys.Desencode(socket.getMessage()), clients);
                } else if ("*help".equals(message)) {
                    help(socket);
                } else if ("*help --private".equals(message)) {
                    helpP(socket);
                } else if ("*help --public".equals(message)) {
                    helpPublic(socket);
                } else {
                    sendMessageToAll(socket, message, clients);
                }
                System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                System.out.printf("Mensagem: %s\n", message);
            }
        } catch (IOException | InterruptedException e) {
            socket.sendMessage("", '-');
            socket.closeS();
            socket.getSala().setClientes(socket);
            clients.remove(socket);
        }
    }

    public static void main(String[] args) throws ServidorErroException {
        //Serviço de escuta

        try {
            Servidor servidor = new Servidor();
            servidor.start();

        } catch (IOException e) {
            throw new ServidorErroException(PORT_SERVIDOR);
        }

    }
}
