import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static ArrayList<Integer> tab = new ArrayList<Integer>();

    private static BigInteger m;
    private static BigInteger x;
    private static BigInteger seed;

    private static final BigInteger one = BigInteger.valueOf(1L);           // L - zwiększa do Long
    private static final BigInteger three = BigInteger.valueOf(3L);
    private static final BigInteger four = BigInteger.valueOf(4L);


    public static BigInteger generate(Random random, int bits){

        BigInteger num;

        while(true){
            num = new BigInteger(bits, 100, random);        // Konstruuje losowo generowaną dodatnią wartość BigInteger, która z prawdopodobieństwem 100 jest liczbą pierwszą, o określonej długości bitowej.
            if (num.mod(four).equals(three)) break;
        }

        return num;
    }

    public static BigInteger getM (Random random, int bits){
        BigInteger p =  generate(random, bits);                  // bits/2 żeby była wartość 256
        BigInteger q = generate(random, bits);

        while(p.equals(q)) { p =  generate(random, bits); }           // jeśli p i q są takie same, losuje p jeszcze raz

        return p.multiply(q);
    }

    public static BigInteger generateSeed(Random random, int bits){

        System.out.println();
        System.out.println("Generating seed...");
        BigInteger s;

        while(true){
            s = new BigInteger(bits, 100, random);
            if (one.compareTo(s) == -1 && (m.subtract(one)).compareTo(s) == 1 && (s.gcd(m)).equals(one)) break;
        }
        return s;
    }

    public static int next() {

        int result = 0;
        x = seed.pow(2).mod(m);

        if(x.testBit(0)) result = 1;            // sprawdza, czy ostatni bit jest ustawiony
        else result = 0;

        seed = x;

        return result;
    }


    public static void main(String[] args) throws IOException {

        SecureRandom rand = new SecureRandom();                             // silny random - dla kryptografii
        rand.nextInt();

        int bitsize = 256;

        m = getM(rand, bitsize);
        seed = generateSeed(rand, bitsize);


        System.out.println();
        System.out.println("Generating 20.000 bytes");
        for (int i = 0; i < 20000; ++i) {
            int d = next();
            tab.add(d);
            System.out.print(tab.get(i));
        }

        System.out.println();
        System.out.println();

        Boolean tds = FIPS.testDlugiejSerii();
        if(tds) {System.out.println("Test długiej serii zakończył się pomyślnie");}
        else {System.out.println("Test długiej serii zakończył się niepomyślnie");}

        Boolean tpb = FIPS.testPojedynczychBitow();
        if(tpb) {System.out.println("Test pojedynczych bitów zakończył się pomyślnie");}
        else {System.out.println("Test pojedynczych bitów zakończył się niepomyślnie");}

        Boolean ts = FIPS.testSerii();
        if(ts) {System.out.println("Test serii zakończył się pomyślnie");}
        else {System.out.println("Test serii zakończył się niepomyślnie");}

        Boolean tp = FIPS.testPokerowy();
        if(tp) {System.out.println("Test pokerowy zakończył się pomyślnie");}
        else {System.out.println("Test pokerowy zakończył się niepomyślnie");}

    }
}


// generowanie rośnie liniowo, zwiększając np. co 10 tysięcy jest stały wzrost czasu podczas generowania
// w sumie zależy od komputera, kompilatora, próby i wszystkiego innego
