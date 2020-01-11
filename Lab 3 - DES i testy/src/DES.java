import java.io.BufferedReader;
import java.io.FileReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.KeyGenerator;


public class DES {

    private static String type = "DES/ECB/PKCS5Padding";
    public static String data = "";
    public static SecretKey key = null;

    static SecureRandom rnd = new SecureRandom();
    static IvParameterSpec iv = new IvParameterSpec(rnd.generateSeed(8));


    public static void setType(String type) { DES.type = type; }

    public static String encrypt(String strToEncrypt, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance(type);

            if(type.equals("DES/ECB/PKCS5Padding")){ cipher.init(Cipher.ENCRYPT_MODE, secret); }
            else { cipher.init(Cipher.ENCRYPT_MODE, secret, iv); }

            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e) { System.out.println("Error while encrypting: " + e.toString()); }
        return null;
    }

    public static String decrypt(String strToDecrypt, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance(type);

            if(type.equals("DES/ECB/PKCS5Padding")){ cipher.init(Cipher.DECRYPT_MODE, secret); }
            else { cipher.init(Cipher.DECRYPT_MODE, secret, iv); }

            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) { System.out.println("Error while decrypting: " + e.toString()); }
        return null;
    }

    public static void call(){

        long czasRozpoczecia = System.currentTimeMillis();

        String out = DES.encrypt(data,key);
        //System.out.println("Zakodowany: " + out);

        long czasZakonczenia = System.currentTimeMillis();
        long czasTrwania = czasZakonczenia - czasRozpoczecia;
        System.out.println("Czas trwania szyfrowania: " + czasTrwania + "ms");

        czasRozpoczecia = System.currentTimeMillis();

        data = DES.decrypt(out,key);
        //System.out.println("Odkodowany: " + data);

        czasZakonczenia = System.currentTimeMillis();
        czasTrwania = czasZakonczenia - czasRozpoczecia;
        System.out.println("Czas trwania odszyfrowania: " + czasTrwania + "ms");
        System.out.println();
    }                                                                      // Dla ECB - bez parametru iv

    public static void zad1(){

        System.out.println();

        System.out.println("----------------------- Tryb pracy: ECB -----------------------");
        DES.setType("DES/ECB/PKCS5Padding");

        System.out.println("Plik 10 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("maly.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 30 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("sredni.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 300 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("duzy.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println(e.getMessage());}
        call();

        //-------------------------------------------------------------------------

        System.out.println("----------------------- Tryb pracy: CBC -----------------------");
        DES.setType("DES/CBC/PKCS5Padding");

        System.out.println("Plik 10 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("maly.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 30 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("sredni.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 300 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("duzy.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        //-------------------------------------------------------------------------

        System.out.println("----------------------- Tryb pracy: CTR -----------------------");
        DES.setType("DES/CTR/PKCS5Padding");

        System.out.println("Plik 10 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("maly.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 30 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("sredni.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 300 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("duzy.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        //-------------------------------------------------------------------------

        System.out.println("----------------------- Tryb pracy: OFB -----------------------");
        DES.setType("DES/OFB/PKCS5Padding");

        System.out.println("Plik 10 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("maly.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 30 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("sredni.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 300 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("duzy.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        //-------------------------------------------------------------------------

        System.out.println("----------------------- Tryb pracy: CFB -----------------------");
        DES.setType("DES/CFB/PKCS5Padding");

        System.out.println("Plik 10 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("maly.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 30 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("sredni.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        System.out.println("Plik 300 MB");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("duzy.txt"));
            data = reader.readLine();
        }catch(Throwable e){System.out.println("Nie działa");}
        call();

        //-------------------------------------------------------------------------
    }

    //-----------------------------------------------

    public static void call2(){

        data = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";

        String out = DES.encrypt(data,key);
        System.out.println("Zakodowany: " + out);

        StringBuilder new_out = new StringBuilder(out);
        new_out.setCharAt(4, 'x');

        System.out.println("Zakodowany: " + new_out);

        data = DES.decrypt(new_out.toString(),key);
        System.out.println("Odkodowany: " + data);

        System.out.println();
    }

    public static void zad2(){

        System.out.println();

        System.out.println("----------------------- Tryb pracy: ECB -----------------------");
        DES.setType("DES/ECB/PKCS5Padding");

        System.out.println();
        call2();
        System.out.println();

        //-------------------------------------------------------------------------

        System.out.println("----------------------- Tryb pracy: CBC -----------------------");
        DES.setType("DES/CBC/PKCS5Padding");

        System.out.println();
        call2();
        System.out.println();

        //-------------------------------------------------------------------------

        System.out.println("----------------------- Tryb pracy: CTR -----------------------");
        DES.setType("DES/CTR/PKCS5Padding");

        System.out.println();
        call2();
        System.out.println();

        //-------------------------------------------------------------------------

        System.out.println("----------------------- Tryb pracy: OFB -----------------------");
        DES.setType("DES/OFB/PKCS5Padding");

        System.out.println();
        call2();
        System.out.println();

        //-------------------------------------------------------------------------

        System.out.println("----------------------- Tryb pracy: CFB -----------------------");
        DES.setType("DES/CFB/PKCS5Padding");

        System.out.println();
        call2();
        System.out.println();

        //-------------------------------------------------------------------------
    }

    //-----------------------------------------------

    public static String cbc_encrypt(String strToEncrypt, SecretKey secret){

        ArrayList<String> blocks = new ArrayList<String>();

        int count = 0;
        int length = 0;
        StringBuilder temp = new StringBuilder();
        char temp_char;


        for(int i = 0; i < strToEncrypt.length(); i++){
            temp_char = strToEncrypt.charAt(i);
            temp.append(temp_char);
            count ++;
            length ++;

            if(count == 8){                                // dzieli na bloki (kazdy po 8 znaków)
                blocks.add(temp.toString());
                count = 0;
                temp_char = 0;
                temp = new StringBuilder();
            }

            if((count < 8) && (count > 0) && (length == strToEncrypt.length())){          // jeśli zostaje tekst, który nie zapełnia całego bloku, uzupełnia go zerami

                for(int j = temp.length(); j < 8; j++){
                    temp.append(0);
                }

                blocks.add(temp.toString());
            }
        }                   // dzieli na bloki 8 bajtowe

        for(int i = 0; i < blocks.size(); i++) {
            System.out.println("Blok " + i + " = " + blocks.get(i));
        }                            // wyświetla bloki

        try{
            Cipher cipher = Cipher.getInstance(type);
            cipher.init(Cipher.ENCRYPT_MODE, secret);

            byte[] wynik = new byte[8];
            byte[] xorApplied = iv.getIV();
            ArrayList<Byte> all = new ArrayList<>();

            for(int i = 0; i < blocks.size(); i++) {

                for(int j = 0; j < blocks.get(i).length(); j++){
                    wynik[j] = (byte)(blocks.get(i).charAt(j) ^ xorApplied[j]);     // xor wejścia z poprzednim blokiem
                }

                byte[] result = cipher.doFinal(wynik);                              // szyfruje pierwszy blok
                xorApplied = result;                                                // przepisujemy nowy blok do xora

                for(byte byt : result){ all.add(byt); }                                // dodajemy do tablicy bajtów wynik

            }

            byte[] toret = new byte[all.size()];                                      // tablica bajtów

            for(int i = 0; i < all.size(); i++){ toret[i] = all.get(i); }                 // przepisujemy z array do bajtów

            return Base64.getEncoder().encodeToString(toret);

        }
        catch (Exception e) { System.out.println("Error while encrypting: " + e.toString()); }

        return null;

    }

    public static String cbc_decrypt(String strToDecrypt, SecretKey secret){

        StringBuilder temp;
        ArrayList<byte[]> blocks = new ArrayList<byte[]>();
        byte[] tab =  new byte[8];
        byte[] all = Base64.getDecoder().decode(strToDecrypt);       // dekodujemy do tablicy ze stringa na bajty

        for(int i = 0; i <= all.length; i++) {

            if(i%8 == 0 && i != 0){                                // dzielimy na bloczki po 8 bajtów
                byte[] tmptab = new byte[8];
                for(int j = 0; j < 8; j++){
                    tmptab[j] = tab[j];
                }
                blocks.add(tmptab);
            }

            if(i < all.length) { tab[i % 8] = all[i]; }             // zapisujemy ostatni blok
        }


        try{

            Cipher cipher = Cipher.getInstance(type);
            cipher.init(Cipher.DECRYPT_MODE, secret);

            byte[] xorApplied = iv.getIV();                                  // pierwszy xor to iv
            temp = new StringBuilder();


            for(int i = 0; i < blocks.size(); i++) {

                byte[] wynik = cipher.doFinal(blocks.get(i));                                // deszyfrowanie
                for (int j = 0; j < blocks.get(i).length; j++) {
                    wynik[j] = (byte) (wynik[j] ^ xorApplied[j]);                           // xor wynik z poprzednim
                }

                xorApplied = blocks.get(i);                                 // przypisujemy kolejny xor
                temp.append(new String(wynik));                                   // dodajemy do wyjścia
            }
            return temp.toString();

        }catch (Exception e) { System.out.println("Error while decrypting: " + e.toString()); }

        return null;
    }

    public static void zad3(){

        DES.setType("DES/ECB/NoPadding");
        data = "koteczkikoteczkikoteczkikoteczkikot";

        String result = cbc_encrypt(data, key);
        System.out.println();
        System.out.println("Zakodowany: " + result);
        String result2 = cbc_decrypt(result, key);
        result2 = result2.replaceAll("0","");
        System.out.println("Odkodowany: " + result2);
    }

    //-----------------------------------------------

    public static void main(String[] args) {

        try { key = KeyGenerator.getInstance("DES").generateKey(); }
        catch(Throwable e){ }

        //zad1();
        //zad2();
        //zad3();


    }
}

// DES dzieli blok danych na dwie połówki, AES przedstawia dane za pomocą macierzy
// DES jest wolniejszy, ma krótszy klucz (56 bitów), AES ma 128, 192 lub 255

/*
Źródła:
https://techdifferences.com/difference-between-des-and-aes.html
http://www.crypto-it.net/pl/teoria/tryby-szyfrow-blokowych.html
 */
