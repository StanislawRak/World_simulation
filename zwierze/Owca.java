package projekt.java.zwierze;

import projekt.java.*;

import java.awt.*;

public class Owca extends Zwierze {
    private static final int sila = 4;
    private static final int inicjatywa = 4;
    private static final Color kolor = new Color(129, 1, 183);

    public Owca(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.OWCA, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setKolor(kolor);
    }

    @Override
    public String NazwaToString() {
        return "Owca";
    }
}

