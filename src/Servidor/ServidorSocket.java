package Servidor;

import java.io.*;
import java.net.Socket;

public class ServidorSocket{
    private final Socket socket;
    private final BufferedReader in;

    public ServidorSocket(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Cliente " +socket.getRemoteSocketAddress());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String getMessage(){
        try {
            return in.readLine();
        }
        catch (IOException e){
            return null;
        }
    }
}
