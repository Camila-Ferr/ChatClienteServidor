package Cliente;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
        return !out.checkError();
    }
}

