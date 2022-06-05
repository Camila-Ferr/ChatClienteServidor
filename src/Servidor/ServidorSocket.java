package Servidor;


import Exceptions.ServidorErroException;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class ServidorSocket {
    private final Socket socket;
    private final BufferedReader in;

    private final PrintWriter out;

    private final ServerCrypto keys;

    public ServidorSocket(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Cliente " + socket.getRemoteSocketAddress());
        this.out = new PrintWriter(socket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        keys = new ServerCrypto(BigInteger.valueOf(5), BigInteger.valueOf(23));
    }
    public boolean confirma_chaves(String msg){
        ArrayList<Integer> msgs = keys.confirma_(msg);
        for (int i: msgs){
            out.println(i);
            System.out.println(i);
        }
        out.println("-");
        out.println(this.keys.getPublic_key());
        return !out.checkError();
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
