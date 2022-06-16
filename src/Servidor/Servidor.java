package Servidor;


import Exceptions.ServidorErroException;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Servidor {
    private static final int PORT_SERVIDOR = 3334;
    private  ServerSocket serverSocket;
    private final List<ServidorSocket> clients = new LinkedList<>();
    private ArrayList<Sala> salas = new ArrayList<>();

    public void start() throws IOException {
        System.out.println("Servidor iniciado na porta " +PORT_SERVIDOR );
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
        if (sala.getClientes() == 0){
            salas.remove(sala);
        }
    }
    private void clienteConnectionLoop() throws IOException {
        while (true) {

            ServidorSocket cliente = new ServidorSocket(serverSocket.accept());

            if (cliente.confirma_chaves()) {
                cliente.sendMessage("Digite seu apelido: ", '-');
                cliente.setClient_id(cliente.keys.Desencode(cliente.getMessage()));
                clients.add(cliente);
                new Thread(() -> {
                    try {
                        clientMessageLoop(cliente);
                    } catch (ServidorErroException e) {
                        cliente.getSala().setClientes();
                        verificaR(cliente.getSala());
                        clients.remove(cliente);
                        throw new RuntimeException(e);
                    }
                }).start();
            }
            else {
                cliente.sendMessage("",'-');
            }
        }
    }
    private void sendMessageToAll(ServidorSocket sender, String msg){
        for (ServidorSocket receptor: clients){
            if ((receptor.getSala() == sender.getSala()) && (!(receptor.getRemoteSocketAdress().equals(sender.getRemoteSocketAdress())))){
                receptor.sendMessage("from :".concat(sender.getClient_id()),'-');
                receptor.sendMessage(msg,'-');
            }
        }

    }
    private void mostraOnline(ServidorSocket requisitor){
        int id = 1;
        for (ServidorSocket percorre: clients){
            requisitor.sendMessage(percorre.getClient_id().concat(" , ").concat(String.valueOf((id))), '-');
            id +=1;
        }
    }
    private void sendToOne(ServidorSocket sender, int receptor, String motivo, String msg){
        System.out.println(clients.get(receptor-1).getClient_id());
        clients.get(receptor-1).sendMessage(motivo.concat(sender.getClient_id()),'-');
        clients.get(receptor-1).sendMessage(msg, '-');
    }
    private void activeRooms(ServidorSocket socket){
        for (Sala percorre : salas){
            socket.sendMessage(String.valueOf(salas.get(percorre.getId())),'-');
        }
    }
    private void help(ServidorSocket socket){
        //TODO
    }

    private void clientMessageLoop(ServidorSocket socket) throws ServidorErroException {
        String message = null;
        String id = null;
        try {
            while (true) {
                message = socket.keys.Desencode(socket.getMessage());
                if ("*exit".equals(message)) {
                    socket.getSala().setClientes();
                    verificaR(socket.getSala());
                    clients.remove(socket);
                    return;
                }
                else if ("*private".equals(message)){
                    mostraOnline(socket);
                    id = socket.keys.Desencode(socket.getMessage());
                    sendToOne(socket, Integer.parseInt(id),"*Private message from ",socket.keys.Desencode(socket.getMessage()));
                }
                else if ("*room".equals(message)){
                    Sala sala = new Sala(salas.size());
                    salas.add(sala);
                    socket.setSala(salas.get(salas.size()-1));
                    System.out.println(sala.getId());

                    mostraOnline(socket);
                    message ="*";
                    while ("*".equals(message)) {
                        id = socket.keys.Desencode(socket.getMessage());
                        sendToOne(socket, Integer.parseInt(id), "*Change your room from : ", " Número da sala : ".concat (String.valueOf(socket.getSala().getId())));
                        message = socket.keys.Desencode(socket.getMessage());
                    }
                    System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                    System.out.printf("Mensagem: %s\n", message);
                    sendMessageToAll(socket,message);
                }
                else if ("*changeR".equals(message)){
                    socket.setSala(salas.get(Integer.parseInt(socket.keys.Desencode(socket.getMessage()))));
                }
                else if ("*activeR".equals(message)){
                    activeRooms(socket);
                }
                else if ("*help".equals(message)){
                    //TODO
                }
                else {
                    System.out.printf("Cliente: %s\n", socket.getRemoteSocketAdress());
                    System.out.printf("Mensagem: %s\n", message);
                    sendMessageToAll(socket,message);
                    }
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
