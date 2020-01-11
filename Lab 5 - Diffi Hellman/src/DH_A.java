import java.math.BigInteger;
import java.util.Random;

public class DH_A {

    public static int x;
    public static BigInteger X;
    public static BigInteger k;
    public static BigInteger gg =  new BigInteger("" + DH.g);
    public static BigInteger nn =  new BigInteger("" + DH.n);


    public static void generate_x(Random random){

        x = DH.generate_primes(random, 100, 999);           // klucz prywatny

        BigInteger power = gg.pow(x);
        X = power.mod(nn);

        System.out.println("Tajna liczba x (dla A): " + x );
        System.out.println("Obliczone X (dla A): " + X );
        System.out.println();

    }

    public static void calculate_k_A(){

        BigInteger power = DH_B.Y.pow(x);
        k = power.mod(nn);
    }

}
