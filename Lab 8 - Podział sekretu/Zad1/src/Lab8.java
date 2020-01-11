import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;


public class Lab8 {

    public static int n = 3;
    public static int k = 999;
    public static int s;
    public static int sn;
    public static ArrayList<Integer> vector = new ArrayList<>(n-1);


    public static void generate_vector(){

        Random generator = new Random();

        for(int i = 0; i < n-1; i++) {
            vector.add(generator.nextInt(k-1));
            //System.out.println(vector.get(i));
        }
    }

    public static int n_ty_udzial(){

        int sum = vector.get(0);

        for(int i = 1; i < n-1; i++) {
            sum = sum - vector.get(i);
        }

        int sn = sum % k;

        return sn;
    }

    public static void secret(){

        int sum = 0;

        for(int i = 0; i < n-1; i++) {
            sum = sum + vector.get(i);
        }

        sum = sum + sn;

        s = sum % k;

        System.out.println("Sekret to " + s);
    }

    public static void zad1(){
        generate_vector();
        sn = n_ty_udzial();
        secret();
    }

    public static void main(String[] args) {

        zad1();
    }
}
