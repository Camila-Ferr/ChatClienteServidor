package servidor;

import java.io.IOException;
import java.util.List;

public class Comandos {

    public static void sendMessageToAll(ServidorSocket sender, String msg, List<ServidorSocket> clients){
        for (ServidorSocket receptor: clients){
            if ((!receptor.getRemoteSocketAdress().equals(sender.getRemoteSocketAdress())) && (receptor.getSala() == sender.getSala())) {
                System.out.println("aq");
                receptor.sendMessage("from : " + sender.getClient_id() + "\n" + msg, '-');
            }
        }
    }
    public static void sendMessageToAll(ServidorSocket sender, String msg, List<ServidorSocket> clients, int cod){
        for (ServidorSocket receptor: clients){
            if ((!receptor.getRemoteSocketAdress().equals(sender.getRemoteSocketAdress()))) {
                receptor.sendMessage("from : " + sender.getClient_id() + "\n" + msg, '-');
            }
        }
    }
    public static void sendToOne(ServidorSocket sender, String receptor, String motivo, String msg,List<ServidorSocket> clients){
        for (ServidorSocket procura: clients){
            if (procura.getClient_id().equals(receptor)){
                procura.sendMessage(motivo.concat(sender.getClient_id()) +"\n" +msg,'-');
                break;
            }
        }
    }
    public static void help(ServidorSocket sender) throws InterruptedException, IOException {
        sender.sendMessage("Comandos : " +"\n" +"private - envia uma mensagem privada" +"\n" +"*public - envia uma mensagem publica .", '-');
    }
    public static void helpP(ServidorSocket sender) throws IOException {
        sender.sendMessage("Para o comando funcionar, envie 2 mensagens:" +"\n" +"Primeiro o apelido." +"\n" +"Logo após a mensagem.",'-');


    }
    public static void helpPublic(ServidorSocket sender) throws IOException {
        sender.sendMessage("Para o comando funcionar, digite somente a mensagem após o comando.",'-');

    }

}
