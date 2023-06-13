package projekt.java.roslina;

import projekt.java.*;

import java.awt.*;
import java.util.Random;

public class Mlecz extends Roslina {
    private static final int sila = 0;
    private static final int inicjatywa = 0;
    private static final Color kolor = new Color(255, 255, 102);
    private static final int ilosc_aktywnosci = 3;

    public Mlecz(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.MLECZ, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setSzansaRozmnazania(0.3);
        setKolor(kolor);
    }

    @Override
    public void Akcja() {
        Random rand = new Random();
        for (int i = 0; i < ilosc_aktywnosci; i++) {
            int los = rand.nextInt(100);
            if (los < getSzansaRozmnazania()) Rozprzestrzenianie();
        }
    }

    @Override
    public String NazwaToString() {
        return "Mlecz";
    }
}
