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
    public static void mostraOnline(ServidorSocket requisitor, List<ServidorSocket> clients){
        int id = 1;
        for (ServidorSocket percorre: clients){
            requisitor.sendMessage(percorre.getClient_id().concat(" , ").concat(String.valueOf((id))), '-');
            id +=1;
        }
    }
    public static void sendToOne(ServidorSocket sender, int receptor, String motivo, String msg,List<ServidorSocket> clients){
        clients.get(receptor-1).sendMessage(motivo.concat(sender.getClient_id()),'-');
        clients.get(receptor-1).sendMessage(msg, '-');
    }
    public static void activeRooms(ServidorSocket socket, ArrayList<Sala> salas){
        int size =salas.size();
        socket.sendOneMessage(String.valueOf(size));
        //for (Sala percorre : salas){
            //socket.sendOneMessage(String.valueOf(salas.get(percorre.getId()).getId()));
        //}
    }
    public static void help(ServidorSocket socket){
        //TODO
    }
}
