package Servidor;
import Exceptions.ComandoNotFindException;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class ServerCrypto {
    static Random random = new Random();
    private final BigInteger _base;
    private static final long __server = Math.abs(random.nextLong());

    private final BigInteger private_key;
    private final BigInteger public_key;

    private BigInteger inicio_alfabeto;


    public ServerCrypto(BigInteger base, BigInteger modulus) {
        _base = base;

        BigInteger[] chaves = generateRandomKeys(modulus);
        this.private_key = chaves[0];
        this.public_key = chaves[1];


    }

    private BigInteger[] generateRandomKeys(BigInteger modulus) {
        BigInteger privateKey = BigInteger.valueOf(System.currentTimeMillis() + __server);
        return new BigInteger[]{privateKey, generatePublicKey(privateKey, modulus)};
    }

    private BigInteger generatePublicKey(BigInteger privateKey, BigInteger modulus) {
        return _base.modPow(privateKey, modulus);
    }

    private ArrayList<Integer> Encode(String msg){
        int codigo;
        int inicio = Integer.parseInt(String.valueOf(this.inicio_alfabeto));
      //  System.out.println(msg);
        ArrayList<Integer> msgs = new ArrayList<Integer>();

        for (int i=0; i<msg.length(); i++) {
            codigo = (int)msg.charAt(i);
            codigo = codigo + inicio;
            msgs.add(codigo);
        }
        return msgs;
    }

    public String Desencode(ArrayList<Integer> msgs){
        int codigo;
        int inicio = Integer.parseInt(String.valueOf(this.inicio_alfabeto));
        String msg = "";
        char letra;

        for (Integer i : msgs) {
            codigo = i - inicio;
            letra = (char)codigo;
            msg = msg.concat(String.valueOf(letra));
        }
        return msg;
    }

    public BigInteger getPublic_key() {
        return public_key;
    }

    public ArrayList<Integer> confirma_(BigInteger public_client){
        int numero = random.nextInt(999);
        System.out.println("Numero enviado: " +numero);
        this.inicio_alfabeto = public_client.modPow(this.private_key,BigInteger.valueOf(23));
        System.out.println("inicio: " +inicio_alfabeto);
        return (Encode(String.valueOf(numero)));

    }
    public ArrayList<Integer> reconhece_comandos(String comando_servidor) {
        if (comando_servidor.equals("find")){

        }

        return null;
    }

}
