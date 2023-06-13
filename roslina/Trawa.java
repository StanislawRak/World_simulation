package projekt.java.roslina;

import projekt.java.*;

import java.awt.*;

public class Trawa extends Roslina {
    private static final int sila = 0;
    private static final int inicjatywa = 0;
    private static final Color kolor = new Color(102, 255, 133);

    public Trawa(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.TRAWA, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setSzansaRozmnazania(0.25);
        setKolor(kolor);
    }

    @Override
    public String NazwaToString() {
        return "Trawa";
    }
}
