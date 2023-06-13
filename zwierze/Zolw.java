package projekt.java.zwierze;

import projekt.java.*;

import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze {
    private static final int sila = 2;
    private static final int inicjatywa = 1;
    private static final Color kolor = new Color(0, 110, 0);

    public Zolw(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.ZOLW, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setKolor(kolor);
    }

    @Override
    public String NazwaToString() {
        return "Zolw";
    }

    @Override
    protected Point ZaplanujRuch() {
        Random rand = new Random();
        int upperbound = 100;
        int los = rand.nextInt(upperbound);
        if (los >= 75) return getPozycja();
        else
        return LosujPoleDowolne(getPozycja());
    }

    @Override
    public boolean SpecyfikacjaAkcji(Organizm atakujacy, Organizm ofiara) {
        if (this == ofiara) {
            if (atakujacy.getSila() < 5 && atakujacy.CzyJestZwierzeciem()) {
                Swiat.DodajKomentarz(NazwaToString() + " odpiera atak " + atakujacy.NazwaToString());
                return true;
            } else return false;
        } else
            return false;
    }
}

