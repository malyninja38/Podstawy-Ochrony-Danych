import java.nio.charset.Charset;
import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedOutputStream;
import java.io.FileReader;
import java.io.File;
import java.io.InputStream;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.BitSet;


public class Lab6 {

    public static BufferedImage image;
    public static File outputFile;
    public static File outputimage;
    private static ArrayList<String> tab = new ArrayList<>();                                 // przechowuje binarne wartości ARGB
    public static int p, r, g, b, a;
    public static int x, y;
    private static int[] values = new int[4];                                                 // przechowuje dziesiętne wartości ARGB
    private static StringBuilder decrypted_binary = new StringBuilder("00000000");            // binarna wartosc hex
    private static int decrypted_decimal;                                                     // dziesietna wartosc hex



    public static BufferedImage pobierz_obrazek() throws Exception{

        File f = new File("C:\\Users\\pauli\\Desktop\\Semestr_5\\POD - Podstawy Ochrony Danych\\Laboratoria\\Lab 6 - Steganografia\\src\\koteczek.png");
        BufferedImage obrazek = ImageIO.read(f);
        return obrazek;
    }

    public static File pobierz_plik() throws Exception{

        File f = new File("C:\\Users\\pauli\\Desktop\\Semestr_5\\POD - Podstawy Ochrony Danych\\Laboratoria\\Lab 6 - Steganografia\\src\\zaszyfrowany.png");
        return f;
    }

    public static void get_pixels( BufferedImage image, int x, int y) throws Exception{

        if (x < image.getWidth() && y < image.getHeight()){
            p = image.getRGB(x,y);
            a = (p>>24) & 0xff;
            r = (p>>16) & 0xff;
            g = (p>>8) & 0xff;
            b = p & 0xff;

            System.out.println("Dla x = " + x + ", y = " + y + " -> a: " + a + ", r: " + r + ", g: " + g + ", b: " + b);
        }
        else{ System.out.println("Wartości wykraczają poza wymiar obrazka"); }
    }

    private static void to_binary(int x){                                               // zamienia dziesiętne na binarne

        if(Integer.toBinaryString(x).length() < 8) {                                    // poszerza, żeby zamiast 1111 mieć 00001111

            StringBuilder poszerz = new StringBuilder(Integer.toBinaryString(x));

            while (poszerz.length() < 8) {
                poszerz.insert(0, '0');                                       // dodaje zera na przód
            }

            tab.add(poszerz.toString());
        }
        else tab.add(Integer.toBinaryString(x));

        System.out.println("Binarna wartość " + x + " to: " + Integer.toBinaryString(x));
    }

    private static void encrypt(StringBuilder hex){

        int licznik = 0;
        StringBuilder sb;

        for(int i = 0; i < tab.size()-1; i++){

            sb = new StringBuilder(tab.get(i+1));                    // w sb umieszczasz wartość z tablicy (i+1 bo pomijamy alfę)

            for(int j = 5; j < 8; j++){                              // na trzech ostatnich znakach (j to indeks w wartości binarnej)

                if (licznik < 6){                                    // licznik dotyczy binernej wartości hex - pierwsze 6 idzie na R i G, tylko dwa idą na B
                    sb.setCharAt(j, hex.charAt(licznik));            // wpisuje na 5, 6, 7 index kolejny znak z hex
                    licznik++;
                }
                else{
                    if(licznik < 8) {
                        sb.setCharAt(j + 1, hex.charAt(licznik));        // j+1 żeby zapisało na dwóch ostatnich
                        licznik++;
                    }
                    else break;
                }
            }

            tab.set(i + 1, sb.toString());                            // i+1 bo znowu pomijamy alfę, zapisujemy nowy string w miejsce starego
        }
    }

    private static void decrypt(){

        int licznik = 0;

        for(int i = 1; i < tab.size(); i++){

            StringBuilder reader = new StringBuilder(tab.get(i));                   // binarna wartość r, g, b

            for(int j = 5; j < 8; j++){

                if(licznik < 6){
                    decrypted_binary.setCharAt(licznik, reader.charAt(j));               // ustawiamy znak o indeksie "licznik" na wartość z readera
                    licznik++;
                }
                else if(licznik < 8){
                    decrypted_binary.setCharAt(licznik, reader.charAt(j+1));
                    licznik++;
                }

            }
        }

        decrypted_decimal = Integer.parseInt(decrypted_binary.toString(), 2);
        System.out.println("Odszyfrowana wiadomość to " + decrypted_binary + ", czyli " + decrypted_decimal);
    }



    public static void main(String [] args) throws Exception {

        image = pobierz_obrazek();

        x = 156;
        y = 126;

        System.out.println();
        get_pixels(image, x, y);

        System.out.println();
        to_binary(a);
        to_binary(r);
        to_binary(g);
        to_binary(b);


        int hex = 17;                                                                        // wartość hex danej litery
        StringBuilder hex_sb = new StringBuilder(Integer.toBinaryString(hex));

        while (hex_sb.length() < 8){                                                         // dopełnianie zerami żeby miało 8 bitów
            hex_sb.insert(0, '0');
        }

        System.out.println();
        System.out.println("Hex -> Dziesiętnie: " + hex + ", binarnie: " + hex_sb);

        encrypt(hex_sb);


        for(int i = 0; i < tab.size(); i++) {
            values[i] = Integer.parseInt(tab.get(i), 2);                              // zamienia binarne wartości zaszyfrowanych kolorów na dziesiętne
        }

        System.out.println();
        System.out.println("Bity po zaszyfrowaniu: ");
        for(int i = 0; i < tab.size(); i++){
            System.out.println("Binarna wartość " + values[i] + " to: " + tab.get(i));
        }

        outputFile = pobierz_plik();                                                       // pobieram informacje o pliku, który będę szyfrować
        BufferedImage out_image = ImageIO.read(outputFile);
        outputimage = pobierz_plik();                                                      // plik, który będę szyfrować (masło maślane ale inaczej nie działą)

        int img;                                                                           // wpisujemy wartości poszczególnych kolorów (a,r,g,b)
        img = values[0] << 24 | values[1] << 16 | values[2] << 8 | values[3];

        out_image.setRGB(x, y, img);                                                // ustawiamy zmienione wartości na pikselu w zaszyfrowanym obrazie

        System.out.println();
        System.out.println("Wartosc zaszyfrowanego piksela w obrazie: ");
        get_pixels(out_image, x, y);

        System.out.println();
        decrypt();

        ImageIO.write(out_image, "png", outputimage);                         // zapisujemy zmieniony obrazek

    }

}
