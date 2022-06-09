package Cliente;

import Exceptions.ClienteErroException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSocket {
    private final Socket socket;
    private final BufferedReader in;
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
        //Cliente tem um par
        //Servidor tem um par
        BigInteger privateKey;

        privateKey = BigInteger.valueOf(System.currentTimeMillis());
        return new BigInteger[]{privateKey, generatePublicKey(privateKey, modulus)};
    }

    private BigInteger generatePublicKey(BigInteger privateKey, BigInteger modulus) {
        return _base.modPow(privateKey, modulus);
    }

    private ArrayList<Integer> Encode(String msg){
        int codigo;
        ArrayList<Integer> msgs = new ArrayList<Integer>();

        for (int i=0; i<msg.length(); i++) {
            codigo = (int)msg.charAt(i);
            codigo = codigo + Integer.parseInt(String.valueOf(this.inicio_alfabeto));
            msgs.add(codigo);
        }
        return msgs;
    }
    private String Desencode(ArrayList<Integer> descripta) {
        int codigo;
        String msg = "";
        char letra;
        for (Integer i : descripta) {
            codigo = i-Integer.parseInt(String.valueOf(inicio_alfabeto));
            letra = (char) codigo;
            msg = msg.concat(String.valueOf(letra));
        }
        return msg;
    }




    public boolean msgSend(String msg) {
        ArrayList<Integer> criptografada;
        criptografada = Encode(msg);

        for (int i : criptografada) {
            out.println(i);
       }
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

    public ArrayList<Integer> getMessage() throws IOException {

        ArrayList<Integer> msg = new ArrayList<>();
        ArrayList<Integer> chave = new ArrayList<Integer>();
        while (true) {
            String s = in.readLine();
            if (s.equals("*")){
                Long k = Long.parseLong(in.readLine());
                server_key = BigInteger.valueOf(k);
                break;
            }
            else if (s.equals("-")){
                break;
            }
            else {
                msg.add(Integer.valueOf(s));
            }
        }
        return msg;
    }
    public String getMessage(int cod) throws IOException {
        return Desencode(getMessage());
    }
    public void confirma() throws IOException {
        out.println(getPublic_key());
        System.out.println("Public key :"+getPublic_key());
        ArrayList<Integer> msg = getMessage();
        inicio_alfabeto =  this.server_key.modPow(this.private_key, BigInteger.valueOf(23));
        System.out.println("Digite o número:" + Desencode(msg));
        msgSend(scanner.nextLine());
    }
}
