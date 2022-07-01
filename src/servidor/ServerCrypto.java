package servidor;

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

    public String Encode(String msg){

        String msgCifrada = new BigInteger(msg.getBytes()).multiply(this.inicio_alfabeto).toString(16);
        return msgCifrada;

    }

    public String Desencode(String msgCifrada){
        return new String(new BigInteger(msgCifrada,16).divide(this.inicio_alfabeto).toByteArray());
    }

    public BigInteger getPublic_key() {
        return public_key;
    }

    public String confirma_(BigInteger public_client){
        int numero = random.nextInt(999);
        System.out.println("Numero enviado: " +numero);
        this.inicio_alfabeto = public_client.modPow(this.private_key,BigInteger.valueOf(23));
        System.out.println("inicio: " +inicio_alfabeto);
        return (Encode(String.valueOf(numero)));

    }

}
