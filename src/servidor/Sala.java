package servidor;

import java.util.LinkedList;
import java.util.List;

public class Sala {
    Integer id;
    Integer clientes;
    private List<ServidorSocket> clients = new LinkedList<>();

    Sala(Integer id){
        this.id = id;
        this.clientes = 1;
    }

    public void setClientes() {
        this.clientes = this.clientes -1;
    }
    public void addClientes(ServidorSocket cliente){
        clients.add(cliente);
    }

    public Integer getClientes() {
        return clientes;
    }
    public Integer getId(){
        return id;
    }


    public void sendMessageToAll(ServidorSocket sender, String msg){
        for (ServidorSocket receptor: this.clients){
            if (!(receptor.getRemoteSocketAdress().equals(sender.getRemoteSocketAdress()))){
                receptor.sendMessage("from :".concat(sender.getClient_id()),'-');
                receptor.sendMessage(msg,'-');
            }
        }

    }
}
