package Servidor;


import Exceptions.ServidorErroException;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class ServidorSocket {
    private final Socket socket;
    private BufferedReader in;

    private final PrintWriter out;

    final ServerCrypto keys;

    public ServidorSocket(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Cliente " + socket.getRemoteSocketAddress());
        this.out = new PrintWriter(socket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        keys = new ServerCrypto(BigInteger.valueOf(5), BigInteger.valueOf(23));
    }
    public boolean confirma_chaves() throws IOException {

        Long msg = Long.parseLong(in.readLine());
        System.out.println(msg);
        ArrayList<Integer> msgs = keys.confirma_(BigInteger.valueOf(msg));
        sendMessage(msgs,'*');
        out.println(keys.getPublic_key());
        String verifica = keys.Desencode(getMessage());
        String original = keys.Desencode(msgs);
        return original.equals(verifica);
    }
    public boolean sendMessage(ArrayList<Integer> msg, char ultimo_caracter) {

        for (int i : msg) {
            out.println(i);
            System.out.println(i);
        }
        out.println(ultimo_caracter);
        return !out.checkError();
    }
    public ArrayList<Integer> getMessage() throws IOException {

        ArrayList<Integer> msg = new ArrayList<>();
        while (true) {
            String s = in.readLine();
            System.out.println(msg);
            if (s.equals("-")){
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                break;
            }
            else {
                msg.add(Integer.valueOf(s));
            }
        }
        System.out.println(msg);
        return msg;
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
