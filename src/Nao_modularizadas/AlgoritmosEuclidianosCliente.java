package Nao_modularizadas;

public class AlgoritmosEuclidianosCliente {
        static int gcd(int b, int thetaN){

            int TEMP = 0 ;
            int GCD = 0;
            int max = b>thetaN?b : thetaN;
            int min = b<thetaN?b : thetaN;
            while(min!=0){
                TEMP=(max%min);
                GCD = min ;
                min = TEMP;
            }
            return GCD;
        }

    }
