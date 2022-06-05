package Servidor;
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


    public ServerCrypto(BigInteger base, BigInteger modulus) {
        _base = base;

        BigInteger[] chaves = generateRandomKeys(modulus);
        this.private_key = chaves[0];
        this.public_key = chaves[1];
        System.out.println(this.public_key + "\n");


    }

    private BigInteger[] generateRandomKeys(BigInteger modulus) {
        BigInteger privateKey = BigInteger.valueOf(System.currentTimeMillis() + __server);
        System.out.println(privateKey + "\n");
        return new BigInteger[]{privateKey, generatePublicKey(privateKey, modulus)};
    }

    private BigInteger generatePublicKey(BigInteger privateKey, BigInteger modulus) {
        return _base.modPow(privateKey, modulus);
    }

    private ArrayList<Integer> Encode(String msg, BigInteger inicio_alfabeto){
        int codigo;
        int inicio = Integer.parseInt(String.valueOf(inicio_alfabeto));
        System.out.println(msg);
        ArrayList<Integer> msgs = new ArrayList<Integer>();

        for (int i=0; i<msg.length(); i++) {
            codigo = (int)msg.charAt(i);
            codigo = codigo + inicio;
            msgs.add(codigo);
        }
        return msgs;
    }

    private ArrayList<Integer> Desencode(ArrayList<Integer> msgs, BigInteger inicio_alfabeto){
        int codigo;
        int inicio = Integer.parseInt(String.valueOf(inicio_alfabeto));
        String msg = "";
        char letra;

        for (Integer i : msgs) {
            codigo = i - inicio;
            letra = (char)codigo;
            msg = msg.concat(String.valueOf(letra));
        }
        return msgs;
    }

    public BigInteger getPublic_key() {
        return public_key;
    }

    public ArrayList<Integer> confirma_(String msg){
        int numero = random.nextInt();
        BigInteger public_key = new BigInteger(msg);
        return (Encode(String.valueOf(numero),public_key.modPow(this.private_key,BigInteger.valueOf(23))));

    }

}
