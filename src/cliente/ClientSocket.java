package cliente;

import exceptions.ClienteErroException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientSocket {
    private final Socket socket;
    private BufferedReader in;
    private final PrintWriter out;
    private final BigInteger private_key;
    private final BigInteger public_key;
    private BigInteger server_key;
    private BigInteger inicio_alfabeto;

    private final BigInteger _base = BigInteger.valueOf(5);

    Scanner scanner = new Scanner(System.in);

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        BigInteger[] chaves = generateRandomKeys(BigInteger.valueOf(23));
        this.private_key = chaves[0];
        this.public_key = chaves[1];
    }

    private BigInteger[] generateRandomKeys(BigInteger modulus) {
        BigInteger privateKey;

        privateKey = BigInteger.valueOf(System.currentTimeMillis());
        return new BigInteger[]{privateKey, generatePublicKey(privateKey, modulus)};
    }

    private BigInteger generatePublicKey(BigInteger privateKey, BigInteger modulus) {
        return _base.modPow(privateKey, modulus);
    }

    private String Encode(String msg){
        String msgCifrada = new BigInteger(msg.getBytes()).multiply(this.inicio_alfabeto).toString(16);

        return msgCifrada;
    }
    private String Desencode(String msgCifrada) {
        return new String(new BigInteger(msgCifrada,16).divide(this.inicio_alfabeto).toByteArray());
    }

    public boolean msgSend(String msg) {

        String criptografada = Encode(msg);

        out.println(criptografada);
        out.println("-");
        return !out.checkError();
    }

    public void closeC() throws ClienteErroException {
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new ClienteErroException(1);
        }
    }

    public BigInteger getPublic_key() {
        return public_key;
    }

    public String getMessage() throws IOException {
        String msg = "";
        while (true) {
            String s = in.readLine();

            if (s.equals("*")) {
                Long k = Long.parseLong(in.readLine());
                server_key = BigInteger.valueOf(k);
                break;
            } else if (s.equals("-")) {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                break;
            } else {
                msg = s;
            }
        }
        return msg;
    }

    public String getMessage(int cod) throws IOException {
        return Desencode(getMessage());
    }
    public String confirma() throws IOException {
        out.println(getPublic_key());
        String msg = getMessage();
        inicio_alfabeto =  server_key.modPow(this.private_key, BigInteger.valueOf(23));
        return Desencode(msg);
    }
}
