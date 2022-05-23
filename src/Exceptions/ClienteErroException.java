package Exceptions;

import java.io.IOException;

public class ClienteErroException extends IOException {
    public ClienteErroException(){
        super("Erro ao criar o cliente.");
    }
    public ClienteErroException(Integer erro){
        super("Erro ao fechar o cliente.");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
