package Cliente;

import Exceptions.ClienteErroException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    private final Socket socket;
    private final PrintWriter out;

    public ClientSocket(Socket socket) throws IOException{
            this.socket = socket;
            this.out = new PrintWriter(socket.getOutputStream(),true);
    }
    public boolean msgSend(String msg){
        out.println(msg);
        return !out.checkError();
    }
    public void closeC() throws ClienteErroException {
        try {
            out.close();
            socket.close();
        }
        catch (IOException e){
            throw new ClienteErroException(1);
        }
    }
}

