package Criptografado;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;


//Classe vai ser compartilhada pelo cliente e servidor, pensar num jeito de separar o que é do cliente e o que é do server
public class DiffieHelman {
        BigInteger modulus;
        public  Keys keys_1;
        public Keys keys_2;

        private BigInteger inicio_alfabeto;

    DiffieHelman(BigInteger modulus) throws IOException, NoSuchAlgorithmException {
        this.modulus = modulus;
         this.keys_1 = new Keys(BigInteger.valueOf(5),modulus,1);
        this.keys_2 = new Keys(BigInteger.valueOf(5),modulus,2);
        this.inicio_alfabeto = (keys_1.getPublic_key().modPow(keys_2.getPrivate_key(),BigInteger.valueOf(23)));

        System.out.println(inicio_alfabeto);
        System.out.println("Prova real " +(keys_2.getPublic_key().modPow(keys_1.getPrivate_key(),BigInteger.valueOf(23))));

    }

    //tirar esse método daqui
    private ArrayList<Integer> Encode(String msg){
        int codigo;
        int inicio = Integer.parseInt(String.valueOf(this.inicio_alfabeto));
        ArrayList<Integer> msgs = new ArrayList<Integer>();

        for (int i=0; i<msg.length(); i++) {
            codigo = (int)msg.charAt(i);
            codigo = codigo + inicio;
            msgs.add(codigo);
        }
        System.out.println(msgs);
        return msgs;
    }

    private ArrayList<Integer> Desencode(ArrayList<Integer> msgs){
        int codigo;
        int inicio = Integer.parseInt(String.valueOf(this.inicio_alfabeto));
        String msg = "";
        char letra;

        for (Integer i : msgs) {
            codigo = i - inicio;
            letra = (char)codigo;
            msg = msg.concat(String.valueOf(letra));
        }
        return msgs;
    }

    
    
    
    public static void main (String[] args) throws IOException, NoSuchAlgorithmException {
        DiffieHelman diffieHelman = new DiffieHelman(BigInteger.valueOf(23));
        diffieHelman.Desencode(diffieHelman.Encode("comi hj"));


    }
}
