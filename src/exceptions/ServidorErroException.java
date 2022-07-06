package exceptions;

import java.io.IOException;

public class ServidorErroException extends IOException {
    public ServidorErroException(){
        super("Erro ao criar o servidor.");
    }
    public ServidorErroException(int close){
        super("Erro ao fechar o servidor ");
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

