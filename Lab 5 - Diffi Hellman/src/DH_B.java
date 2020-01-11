import java.math.BigInteger;
import java.util.Random;

public class DH_B {

    public static int y;
    public static BigInteger Y;
    public static BigInteger k;
    public static BigInteger gg =  new BigInteger("" + DH.g);
    public static BigInteger nn =  new BigInteger("" + DH.n);


    public static void generate_y(Random random){

        y = DH.generate_primes(random, 100, 999);           // klucz prywatny

        BigInteger power = gg.pow(y);
        Y = power.mod(nn);

        System.out.println("Tajna liczba y (dla B): " + y );
        System.out.println("Obliczone Y (dla B): " + Y );
        System.out.println();

    }

    public static void calculate_k_B(){

        BigInteger power = DH_A.X.pow(y);
        k = power.mod(nn);

    }

}
