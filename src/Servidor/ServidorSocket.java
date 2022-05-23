package Servidor;


import Exceptions.ServidorErroException;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ServidorSocket {
    private final Socket socket;
    private final BufferedReader in;

    public ServidorSocket(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Cliente " + socket.getRemoteSocketAddress());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String getMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public SocketAddress getRemoteSocketAdress() {
        return socket.getRemoteSocketAddress();
    }

    public void closeS() throws ServidorErroException {
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            throw new ServidorErroException(1);
        }
    }
}
