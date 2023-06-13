package projekt.java.zwierze;

import projekt.java.*;

import java.awt.*;

public class Wilk extends Zwierze {
    private static final int sila = 9;
    private static final int inicjatywa = 5;
    private static final Color kolor = new Color(95, 73, 73);

    public Wilk(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.WILK, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setKolor(kolor);
    }

    @Override
    public String NazwaToString() {
        return "Wilk";
    }
}
