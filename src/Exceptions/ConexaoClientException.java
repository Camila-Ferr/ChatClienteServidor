package Exceptions;

public class ConexaoClientException extends NullPointerException {
    public ConexaoClientException(){
        super (" Falha na conexão com o servidor.");
    }
}
