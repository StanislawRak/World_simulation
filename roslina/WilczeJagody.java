package projekt.java.roslina;

import projekt.java.*;

import java.awt.*;
import java.util.Random;

public class WilczeJagody extends Roslina {
    private static final int sila = 99;
    private static final int inicjatywa = 0;
    private static final Color kolor = new Color(125, 138, 255);

    public WilczeJagody(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.WILCZE_JAGODY, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setSzansaRozmnazania(0.05);
        setKolor(kolor);
   }

    @Override
    public void Akcja() {
        Random rand = new Random();
        int los = rand.nextInt(100);
        if (los < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
    }

    @Override
    public String NazwaToString() {
        return "Wilcze jagody";
    }

}
