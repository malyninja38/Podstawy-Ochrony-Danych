import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.math.*;

public class Main {

    private static int p;
    private static int q;
    private static int n;
    public static int phi;
    public static int e;
    public static int d;
    public static BigInteger c;
    public static int m;
    public static ArrayList<BigInteger> list;

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

    static private int modInv(int e, int phi) {           // Odwrotność modulo
        var a = new int[1024];
        var q = new int[1024];
        var x = new int[1024];

        a[0] = phi; a[1] = e;
        int i = 0;
        while (a[i] % a[i + 1] != 0)
        {
            a[i + 2] = a[i] % a[i + 1];
            q[i + 1] = (int)Math.floor((double)a[i] / a[i + 1]);
            i++;
        }
        int n = i + 1;
        x[n] = 0;
        x[n - 1] = 1;
        for (int j = n - 2; j >= 0; j--) {
            x[j] = x[j + 1] * q[j + 1] + x[j + 2];
        }
        if (a[n] != 1) return -1;
        if ((x[1] * a[0]) > (x[0] * a[1])) return phi - x[0];
        return x[0];
    }                              // jest gotowiec w Javie, ale na BigInt

    public static int gcd(int a, int b) {
        if (b==0) return a;
        return gcd(b,a%b);
    }

    public static void generate_key(Random random){

        p = generate_primes(random, 10, 99);
        q = generate_primes(random, 10, 99);

        n = p * q;

        phi = (p-1)*(q-1);

        e = 7;

        if(gcd(e,phi) != 1) generate_key(random);             // jak się nie zgadza nwd(e,phi) to jeszcze raz

       while(true){
            d = modInv(e, phi);
            if(d >= 0) break;
       }                                  // e * d -1 mod phi = 0
    }

    public static BigInteger pow_big(BigInteger base, BigInteger power) {
        BigInteger res = BigInteger.ONE;
        while (power.compareTo(BigInteger.ZERO) > 0) {
            BigInteger i = power.and(BigInteger.ONE);
            if (!i.equals(BigInteger.ZERO))
                res = res.multiply(base);
            base = base.multiply(base);
            power = power.shiftRight(1);
        }
        return res;
    }                    // algorytm szybkiego mnożenia

    public static ArrayList encrypt(String message){

        list = new ArrayList<>();
        char letter;
        BigInteger pow;

        for(int i = 0; i < message.length(); i++){

            letter = message.charAt(i);
            m = Character.getNumericValue(letter);            // do m przypisuje wartość ASCII pojedyńczej litery z wiadomości

            BigInteger mm =  new BigInteger("" + m);
            BigInteger nn = new BigInteger("" + n);
            BigInteger ee =  new BigInteger("" + e);              // zmiana na Big Integer

            pow = pow_big(mm,ee);                                       // m do potęgi e
            BigInteger pow2 = new BigInteger("" + pow);            // zmiana na Big Integer
            c = pow2.mod(nn);                                          // potęga modulo n
            list.add(c);                                               // wartość wpisuje do tablicy
        }
        return list;
    }

    public static String decrypt(ArrayList<BigInteger> encrypted){

        list = new ArrayList<>();
        char letter;
        String text = "";
        BigInteger pow2;

        for(int i = 0; i < encrypted.size(); i++){

            c = encrypted.get(i);                                         // pobiera wartość z tablicy

            BigInteger nn = new BigInteger("" + n);
            BigInteger dd = new BigInteger("" + d);

            pow2 = pow_big(c,dd);                                         // c do potęgi d
            BigInteger pow3 = new BigInteger("" + pow2);

            BigInteger mm = pow3.mod(nn);                                 // potęga modulo n
            m = mm.intValue();                                            // Big Integer na zwykły int

            letter = Character.forDigit(m, 36);                     // m na char
            text = text + letter;                                         // dołącza do tekstu wyjściowego
        }
        return text;
   }

    public static void show(ArrayList<BigInteger> list){
        for(BigInteger x : list){
            System.out.print(x);
        }
   }


    public static void main(String[] args) throws IOException {

        SecureRandom rand = new SecureRandom();                                  // silny random - dla kryptografii
        rand.nextInt();

        generate_key(rand);
        System.out.println();
        System.out.println("p: " + p + ", q: " + q + ", n: " + n + ", phi: " + phi + ", e: " + e + ", d: " + d);
        System.out.println();

        String message = "bardzo lubie male koteczki puchate pieski i kuoki";


        ArrayList<BigInteger> encrypted = encrypt(message);
        System.out.println("Zaszyfrowana wiadomość: " );
        show(encrypted);

        System.out.println();
        System.out.println();

        String decrypted = decrypt(encrypted);
        System.out.println("Odszyfrowana wiadomość: " + decrypted);

    }
}



