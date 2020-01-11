import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static java.lang.StrictMath.pow;


public class Main {

    public static int n;
    public static int p;
    public static int t;
    public static int s;

    public static ArrayList<Integer> vector_a = new ArrayList<>();                     // a0,a1,a2 itp
    public static ArrayList<Integer> vector_s = new ArrayList<>();                     // s0,s1,s2 itp
    public static ArrayList<int[]> udzial = new ArrayList<>();


    public static boolean isPrimeNumber(int liczba) {
        if(liczba < 2) { return false; }
        for(int i = 2; i <= liczba/2; i++) { if(liczba % i == 0) { return false; } }
        return true;
    }

    public static int generate_p(){

        int num;
        Random random = new Random();

        while(true){
            num = random.nextInt();
            if (isPrimeNumber(num) && num > s && num > n) break;
        }

        System.out.println("p: " + num);
        return num;
    }

    public static void generate_vector(){

        Random generator = new Random();

        for(int i = 0; i <= t-1; i++) {
            vector_a.add(generator.nextInt(1000));
            //System.out.println(vector_a.get(i));
        }
    }

    public static void sum(){

        for(int i = 1; i <= n; i++) {                              // Dla każdego si, a jest ich n

            int sigma = 0;

            for (int j = 1; j <= t-1; j++) {                            // sigma, a sigma jest od 1 to t-1
                sigma = sigma + (vector_a.get(j) * (int)pow(i,j));
            }

            int temp = (s + sigma) % p;
            vector_s.add(temp);
            System.out.println("s" + i + ": " + vector_s.get(i-1));
        }
    }

    public static void udzial(){

        int[] tab;

        for(int i = 1; i <= n; i++) {                              // udziałów jest n

            tab = new int[2];
            tab[0] = i-1;
            tab[1] = vector_s.get(i-1);

            udzial.add(tab);
        }
    }

    public static void sekret(){

        double sigma = 0;

         for(int i = 1; i <= t; i++){

             double multiplication = 1;

             for(int j = 1; j <= t; j++) {

                 if (i != j) {
                     multiplication = (multiplication * ( -j / (double)(i - j)));
                 }
             }

             sigma = sigma + ((udzial.get(i-1)[1] * (multiplication)) % p);
         }

        System.out.println();
        System.out.println("Sekret (954): " + sigma);
}


    public static void zad2(){

        System.out.println();

        System.out.println("Podaj sekret (s): ");
        Scanner scanner = new Scanner(System.in);
        String s_string = scanner.nextLine();
        s = Integer.parseInt(s_string);

        System.out.println("Podaj całkowitą liczbę udziałowców (n): ");
        String n_string = scanner.nextLine();
        n = Integer.parseInt(n_string);

        System.out.println("Podaj wymaganą liczbę udziałowców (t): ");
        String t_string = scanner.nextLine();
        t = Integer.parseInt(t_string);

        System.out.println();
        p = generate_p();
        System.out.println();

        generate_vector();

        sum();
        udzial();
        sekret();

    }


    public static void main(String[] args) {
        zad2();
    }
}



// działa dla 8 wymaganych udziałowców, potem przekręca liczniki