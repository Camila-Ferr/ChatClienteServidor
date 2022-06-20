package Servidor;

public class Sala {
    Integer id;
    Integer clientes;

    Sala(Integer id){
        this.id = id;
        this.clientes = 1;
    }

    public void setClientes() {
        this.clientes = this.clientes -1;
    }

    public Integer getClientes() {
        return clientes;
    }
    public Integer getId(){
        return id;
    }
}
