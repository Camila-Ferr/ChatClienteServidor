package Exceptions;

import java.io.IOException;

public class ComandoNotFindException   extends IOException {
    public ComandoNotFindException(){
        super("O comando não foi encontrado.");
    }
}
