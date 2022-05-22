package Exceptions;

import java.io.IOException;

public class ServidorErroException extends IOException {
    public ServidorErroException(){
        super("Erro ao criar o servidor.");
    }
    public ServidorErroException(int port){
        super("Erro ao criar o servidor " +port);
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

