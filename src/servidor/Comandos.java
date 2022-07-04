package servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Comandos {

    public static void sendMessageToAll(ServidorSocket sender, String msg, List<ServidorSocket> clients){
        for (ServidorSocket receptor: clients){
            if (!(receptor.getRemoteSocketAdress().equals(sender.getRemoteSocketAdress()))) {
                receptor.sendMessage("from :".concat(sender.getClient_id()), '-');
                receptor.sendMessage(msg, '-');
            }
            }
        }
    public static void sendToOne(ServidorSocket sender, String receptor, String motivo, String msg,List<ServidorSocket> clients){
        for (ServidorSocket procura: clients){
            if (procura.getClient_id().equals(receptor)){
                procura.sendMessage(motivo.concat(sender.getClient_id()),'-');
                procura.sendMessage(msg, '-');
                break;
            }
        }
    }
    public static void help(ServidorSocket sender) throws InterruptedException, IOException {
        sender.sendMessage("Comandos : ", '-');
        try {
            sender.keys.Desencode(sender.getMessage());
        } catch (Exception e) {
            sender.sendMessage("Comandos : ", '-');
        }
        sender.sendMessage("*private - envia uma mensagem privada", '-');
        try {
            sender.keys.Desencode(sender.getMessage());
        } catch (Exception e) {
            sender.sendMessage("*private - envia uma mensagem privada", '-');
        }
        sender.sendMessage("*public - envia uma mensagem publica .", '-');
        try {
            sender.keys.Desencode(sender.getMessage());
        } catch (Exception e) {
            sender.sendMessage("*public - envia uma mensagem publica .", '-');
        }
    }
    public static void helpP(ServidorSocket sender) throws IOException {
        sender.sendMessage("Para o comando funcionar, envie 2 mensagens:",'-');
        try {
            sender.keys.Desencode(sender.getMessage());
        }
        catch (Exception e){
            helpP(sender);
        }
        sender.sendMessage("Primeiro o apelido.",'-');
        try {
            sender.keys.Desencode(sender.getMessage());
        }
        catch (Exception e){
            sender.sendMessage("Primeiro o apelido.",'-');
        }
        sender.sendMessage("Logo após a mensagem.",'-');
        try {
            sender.keys.Desencode(sender.getMessage());
        }
        catch (Exception e){
            sender.sendMessage("Logo após a mensagem.",'-');
        }


    }
    public static void helpPublic(ServidorSocket sender) throws IOException {
        sender.sendMessage("Para o comando funcionar, digite somente a mensagem após o comando.",'-');
        try {
            sender.keys.Desencode(sender.getMessage());
        }
        catch (Exception e){
            sender.sendMessage("Para o comando funcionar, digite somente a mensagem após o comando.",'-');
        }

    }

}
