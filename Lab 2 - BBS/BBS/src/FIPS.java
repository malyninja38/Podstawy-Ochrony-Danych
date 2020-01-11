import java.util.ArrayList;

import static java.lang.StrictMath.pow;

public class FIPS {

    public static Boolean testPojedynczychBitow(){

        int count = 0;

        for(int i = 0; i < Main.tab.size(); i++){

            if(Main.tab.get(i) == 1){ count++; }
        }

        if(count > 9725 && count < 10275) return true;
        else return false;
    }

    public static Boolean testDlugiejSerii(){

        int count = 0;
        int temp = Main.tab.get(0);

        for(int i = 1; i < Main.tab.size(); i++){

            if(Main.tab.get(i) == temp){
                count++;
            }
            else count = 0;

            if(count >= 26) {return false;}

            temp = Main.tab.get(i);
        }

        return true;
    }

    public static Boolean testSerii(){

        int count = 0;
        int jeden = 0, dwa = 0, trzy = 0, cztery = 0, piec = 0, szesc = 0;           // dlugosci serii

        int temp = Main.tab.get(0);

        for(int i = 1; i < Main.tab.size(); i++){

            if(Main.tab.get(i) == temp){
                count++;
            }
            else{
                if(count == 1) { jeden++; }
                else if(count == 2) { dwa++; }
                else if(count == 3) { trzy++; }
                else if(count == 4) { cztery++; }
                else if(count == 5) { piec++; }
                else if(count >= 6) { szesc++; }

                count = 0;
            }

            temp = Main.tab.get(i);
        }

        if(jeden >= 2315 && jeden <= 2685 && dwa >= 1114 && dwa <= 1286 &&
                trzy >= 527 && trzy <= 723 && cztery >= 240 && cztery <= 384 &&
                piec >= 103 && piec <= 209 && szesc >= 103 && szesc <= 209){ return true; }
        else return false;
    }

    public static Boolean testPokerowy(){

        int _0000 = 0, _0001 = 0, _0010 = 0, _0011 = 0, _0100 = 0, _0101 = 0,
                _0110 = 0, _0111 = 0, _1000 = 0, _1001 = 0, _1010 = 0, _1011 = 0,
                _1100 = 0, _1101 = 0, _1110 = 0, _1111 = 0;

        String segment = "";
        int n = 0;

        for(int i = 0; i < 5000; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                segment = segment.concat(String.valueOf(Main.tab.get(n)));       // dopisuje wartość na końcu stringa
                n++;
            }

            switch(segment){
                case "0000": _0000++; break;
                case "0001": _0001++; break;
                case "0010": _0010++; break;
                case "0011": _0011++; break;
                case "0100": _0100++; break;
                case "0101": _0101++; break;
                case "0110": _0110++; break;
                case "0111": _0111++; break;
                case "1000": _1000++; break;
                case "1001": _1001++; break;
                case "1010": _1010++; break;
                case "1011": _1011++; break;
                case "1100": _1100++; break;
                case "1101": _1101++; break;
                case "1110": _1110++; break;
                case "1111": _1111++; break;
            }

            segment = "";
        }

        double suma = pow(_0000,2) + pow(_0001,2) + pow(_0010,2) + pow(_0011,2) +
                pow(_0100,2) + pow(_0101,2) + pow(_0110,2) + pow(_0111,2) + pow(_1000,2) +
                pow(_1001,2) + pow(_1010,2) + pow(_1011,2) + pow(_1100,2) + pow(_1101,2) +
                pow(_1110,2) + pow(_1111,2);

        double x = ((16.0/5000.0) * suma) - 5000;

        if (x > 2.16 && x < 46.17) return true;
        else return false;
    }

}
