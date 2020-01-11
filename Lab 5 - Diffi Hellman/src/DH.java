import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


public class DH {

    public static int n;
    public static int g;

    public static boolean isPrimeNumber(int liczba) {
        if(liczba < 2) { return false; }
        for(int i = 2; i <= liczba/2; i++) { if(liczba % i == 0) { return false; } }
        return true;
    }

    public static int generate_primes(Random random, int min, int max){

        int num;

        while(true){
            num = random.nextInt((max - min) + 1) + min;
            if (isPrimeNumber(num)) break;
        }

        return num;
    }

    public static int gcd(int a, int b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }

    static int countPrimitiveRoots(int p) {
        int result = 1;
        for (int i = 2; i < p; i++)
            if (gcd(i, p) == 1)
                result++;

        return result;
    }

    public static void generate_n_and_g(Random random){

        n = generate_primes(random, 100000, 999999);

        // a musi być pierwiastkiem pierwotnym modulo m, czyli dla dowolnego b takiego,
        // że NWD(b, m) = 1 istnieje liczba całkowita k taka, że ak mod m = b

        while(true){
            g = countPrimitiveRoots(n - 1);
            if(g > 1 && g < n) break;
        }
    }


    public static void main(String[] args) throws IOException {

        SecureRandom rand = new SecureRandom();                                  // silny random - dla kryptografii
        rand.nextInt();

        System.out.println();
        generate_n_and_g(rand);
        System.out.println("Obliczona liczba n: " + n + ", oraz g: " + g);
        System.out.println();

        DH_A.generate_x(rand);
        DH_B.generate_y(rand);

        DH_A.calculate_k_A();
        DH_B.calculate_k_B();

        System.out.println("Klucz sesji k obliczony przez A: " + DH_A.k);
        System.out.println("Klucz sesji k obliczony przez B: " + DH_B.k);

    }


}
