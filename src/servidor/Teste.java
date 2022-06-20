package servidor;

import java.util.ArrayList;

public class Teste {
    public static void main (String[] args) {
        ArrayList<Integer> array = new ArrayList<>();
        array.add(1);
        array.add(2);


        for (int i: array){
            String cod = Long.toHexString(966);
            System.out.println(cod);

            System.out.println(Long.parseLong(cod,16));
        }

    }
}
