package Cliente;

import Exceptions.ClienteErroException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocket {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final BigInteger private_key;
    private final BigInteger public_key;

    private BigInteger server_key;
    private BigInteger inicio_alfabeto;

    private final BigInteger _base = BigInteger.valueOf(5);

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        BigInteger[] chaves = generateRandomKeys(BigInteger.valueOf(23));
        this.private_key = chaves[0];
        this.public_key = chaves[1];
    }

    private BigInteger[] generateRandomKeys(BigInteger modulus) {
        //Cliente tem um par
        //Servidor tem um par
        BigInteger privateKey;

        privateKey = BigInteger.valueOf(System.currentTimeMillis());
        System.out.println(privateKey + "\n");
        return new BigInteger[]{privateKey, generatePublicKey(privateKey, modulus)};
    }

    private BigInteger generatePublicKey(BigInteger privateKey, BigInteger modulus) {
        return _base.modPow(privateKey, modulus);
    }

    private ArrayList<Integer> Encode(String msg){
        int codigo;
        System.out.println(msg);
        ArrayList<Integer> msgs = new ArrayList<Integer>();

        for (int i=0; i<msg.length(); i++) {
            codigo = (int)msg.charAt(i);
            codigo = codigo + Integer.parseInt(String.valueOf(inicio_alfabeto));
            msgs.add(codigo);
        }
        return msgs;
    }
    private String Desencode(ArrayList<Integer> descripta) {
        int codigo;
        String msg = "";
        char letra;
        System.out.println(inicio_alfabeto);
        for (Integer i : descripta) {
            codigo = i-Integer.parseInt(String.valueOf(inicio_alfabeto));
            letra = (char) codigo;
            msg = msg.concat(String.valueOf(letra));
        }
        return msg;
    }

    public boolean msgSend(ArrayList<Integer> msg) {
        for (int i : msg) {
            out.println(msg);
        }
        return !out.checkError();
    }

    public boolean msgSend(String msg) {
        ArrayList<Integer> criptografada;
        criptografada = Encode(msg);
        msgSend(msg);
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

    public void getMessage(int cod) throws IOException {
        this.server_key = BigInteger.valueOf(Long.parseLong(in.readLine()));
        System.out.println(server_key);
        this.inicio_alfabeto = this.server_key.modPow(this.private_key, BigInteger.valueOf(23));
        System.out.println(inicio_alfabeto);
    }

    public ArrayList<Integer> getMessage() throws IOException {

        ArrayList<Integer> msg = new ArrayList<>();
        while (true) {
            String s = in.readLine();
            System.out.println(msg);
            if (s.equals("-")){
                getMessage(1);
                break;
            }
            else {
                msg.add(Integer.valueOf(s));
            }
        }
        System.out.println("Digite o número:" + Desencode(msg));
        return msg;
    }
}
