package Criptografado;

import java.math.BigInteger;
import java.util.Random;

public final class Keys {
    static Random random = new Random();
    private static final long __cliente = System.currentTimeMillis();
    private static final long __server = __cliente+ Math.abs(random.nextLong());

    private final BigInteger _base;
    private final BigInteger private_key;
    private final BigInteger public_key;

    public Keys(BigInteger base, BigInteger modulus, int id) {
        _base = base;

        BigInteger[] chaves = generateRandomKeys(modulus, id);
        this.private_key = chaves[0];
        this.public_key = chaves[1];
        System.out.println(this.public_key + "\n");


    }

    public BigInteger[] generateRandomKeys(BigInteger modulus, int id) {
        //Cliente tem um par
        //Servidor tem um par
        BigInteger privateKey;
        if (id == 1) {
             privateKey = BigInteger.valueOf(System.currentTimeMillis() + __cliente);
        }
        else {
            privateKey = BigInteger.valueOf(System.currentTimeMillis() + __server);
        }
        System.out.println(privateKey + "\n");
        return new BigInteger[]{privateKey, generatePublicKey(privateKey, modulus)};
    }

    public BigInteger generatePublicKey(BigInteger privateKey, BigInteger modulus) {
        return _base.modPow(privateKey, modulus);
    }

    public BigInteger getPublic_key() {
        return public_key;
    }
    public BigInteger getPrivate_key(){
        return private_key;
    }
}