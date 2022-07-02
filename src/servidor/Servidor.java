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
        salas.get(nova).addClientes(cliente);
        cliente.setSala(salas.get(nova));
        salas.get(nova).sendMessageToAll(cliente, cliente.getClient_id().concat(" entrou na sala."));

        salas.get(antiga).setClientes(cliente);//Diminui 1

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
                    mostraOnline(socket, clients);
                    try {
                        id = socket.keys.Desencode(socket.getMessage());
                        sendToOne(socket, Integer.parseInt(id), "*Private message from ", socket.keys.Desencode(socket.getMessage()), clients);
                    } catch (Exception e) {
                        socket.sendMessage("Cliente não encontrado", '-');
                    }

                } else if ("*changeR".equals(message)) {
                    System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                    System.out.println("Comando para mudar a sala.");
                    try {
                        int antiga = socket.getSala().getId();
                        changeR(salas.get(Integer.parseInt(socket.keys.Desencode(socket.getMessage()))).getId(), antiga, socket);
                    } catch (Exception e) {
                        socket.sendMessage("Sala não encontrada", '-');
                    }
                } else if ("*activeR".equals(message)) {
                    System.out.println("Aq");
                    activeRooms(socket, salas);
                } else if ("*help".equals(message)) {
                    //TODO
                } else {
                    sendMessageToAll(socket, message, clients);
                }
                System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                System.out.printf("Mensagem: %s\n", message);
                socket.sendMessage("RECEBA  !!", '-');
            }
        } catch (IOException e) {
            socket.sendMessage("", '-');
            throw new RuntimeException(e);
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
