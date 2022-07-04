package servidor;

import java.util.ArrayList;
import java.util.List;

public class Comandos {

    public static void sendMessageToAll(ServidorSocket sender, String msg, List<ServidorSocket> clients){
        for (ServidorSocket receptor: clients){
            if (!(receptor.getRemoteSocketAdress().equals(sender.getRemoteSocketAdress()))){
                receptor.sendMessage("from :".concat(sender.getClient_id()),'-');
                receptor.sendMessage(msg,'-');
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
    public static void help(ServidorSocket sender) throws InterruptedException {
        sender.sendMessage("Comandos :",'-');
        sender.sendMessage("*private - envia uma mensagem privada",'-');
        sender.sendMessage("*public - envia uma mensagem publica .",'-');


    }
    public static void helpP(ServidorSocket sender){
        sender.sendMessage("Para o comando funcionar, envie 2 mensagens:",'-');
        sender.sendMessage("Primeiro o apelido.",'-');
        sender.sendMessage("Logo após a mensagem.",'-');

    }
    public static void helpPublic(ServidorSocket sender){
        sender.sendMessage("Para o comando funcionar, digite somente a mensagem após o comando.",'-');

    }

}
