package Servidor;


import Exceptions.ServidorErroException;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static Servidor.Comandos.*;


public class Servidor {
    private static final int PORT_SERVIDOR = 3334;
    private  ServerSocket serverSocket;
    private final List<ServidorSocket> clients = new LinkedList<>();
    private ArrayList<Sala> salas = new ArrayList<>();

    public void start() throws IOException {
        System.out.println("Servidor iniciado na porta " +PORT_SERVIDOR );
        Sala sala = new Sala(0);
        salas.add(sala);
        serverSocket = new ServerSocket(PORT_SERVIDOR);
        new Thread(() -> {
                try {
                    clienteConnectionLoop();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }).start();
    }
    private void verificaR(Sala sala){
        if ((sala.getClientes() == 0) && (sala.getId()!= 0)){
            salas.remove(sala);
        }
    }
    private void clienteConnectionLoop() throws IOException {
        while (true) {

            ServidorSocket cliente = new ServidorSocket(serverSocket.accept());

                new Thread(() -> {
                    try {
                        if (cliente.confirma_chaves()) {
                            cliente.sendMessage("Digite seu apelido: ", '-');
                            cliente.setClient_id(cliente.keys.Desencode(cliente.getMessage()));
                            clients.add(cliente);
                            salas.get(0).setClientes();
                            cliente.setSala(salas.get(0));
                            sendMessageToAll(cliente,cliente.getClient_id().concat(" entrou na sala."),clients);
                        }
                        else {
                            cliente.sendMessage("",'-');
                        }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    try {
                        clientMessageLoop(cliente);
                    } catch (ServidorErroException e) {
                        cliente.getSala().setClientes();
                        verificaR(cliente.getSala());
                        try {
                            cliente.closeS();
                        }
                        catch (Exception ex){
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
                    socket.getSala().setClientes();
                    socket.closeS();
                    verificaR(socket.getSala());
                    clients.remove(socket);
                    return;
                }
                else if ("*private".equals(message)){
                    mostraOnline(socket,clients);
                    try {
                        id = socket.keys.Desencode(socket.getMessage());
                        sendToOne(socket, Integer.parseInt(id),"*Private message from ",socket.keys.Desencode(socket.getMessage()),clients);
                    }
                    catch (Exception e){
                        socket.sendMessage("Cliente não encontrado",'-');
                    }

                }
                else if ("*room".equals(message)){
                    Sala sala = new Sala(salas.size());
                    salas.add(sala);
                    socket.setSala(salas.get(salas.size()-1));
                    sendMessageToAll(socket,socket.getClient_id().concat(" entrou na sala."),clients);


                    mostraOnline(socket,clients);
                    message ="*";
                    while ("*".equals(message)) {
                        try {
                            id = socket.keys.Desencode(socket.getMessage());
                            sendToOne(socket, Integer.parseInt(id), "*Change your room from : ", " Número da sala : ".concat (String.valueOf(socket.getSala().getId())),clients);
                            message = socket.keys.Desencode(socket.getMessage());
                        }
                        catch (Exception e){
                            socket.sendMessage("Erro ao convidar",'-');
                        }

                    };
                    sendMessageToAll(socket,message,clients);
                }
                else if ("*changeR".equals(message)){
                    System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                    System.out.println("Comando para mudar a sala.");
                    try {
                        socket.setSala(salas.get(Integer.parseInt(socket.keys.Desencode(socket.getMessage()))));
                        sendMessageToAll(socket,socket.getClient_id().concat(" entrou na sala."),clients);
                    }
                    catch (Exception e){
                        socket.sendMessage("Sala não encontrada",'-');
                    }
                }
                else if ("*activeR".equals(message)){
                    activeRooms(socket,salas);
                }
                else if ("*help".equals(message)){
                    //TODO
                }
                else {
                    sendMessageToAll(socket,message,clients);
                    }
                System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                System.out.printf("Mensagem: %s\n", message);
            }
        } catch (IOException e) {
            socket.sendMessage("",'-');
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
