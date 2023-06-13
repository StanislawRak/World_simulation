package projekt.java.zwierze;

import projekt.java.*;

import java.awt.*;
import java.util.Random;

public class Antylopa extends Zwierze {
    private static final int sila = 4;
    private static final int inicjatywa = 4;
    private static final Color kolor = new Color(130, 90, 0);
    private static final int zasieg_ruchu = 2;

    public Antylopa(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.ANTYLOPA, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        this.setZasiegRuchu(zasieg_ruchu);
        setKolor(kolor);
    }

    @Override
    public String NazwaToString() {
        return "Antylopa";
    }

    @Override
    public boolean SpecyfikacjaAkcji(Organizm atakujacy, Organizm ofiara) {
        Random rand = new Random();
        int los = rand.nextInt(100);
        if (los < 50) {
            Swiat.DodajKomentarz(NazwaToString() + ": ucieczka od walki");
            if (this == atakujacy) {
                Point Pozycja = LosujPoleNiezajete(ofiara.getPozycja());
                if (!Pozycja.equals(ofiara.getPozycja()))
                    WykonajRuch(Pozycja);
            } else if (this == ofiara) {
                Point Pozycja = this.getPozycja();
                WykonajRuch(LosujPoleNiezajete(this.getPozycja()));
                if (getPozycja().equals(Pozycja)) {
                    Swiat.DodajKomentarz("Ucieczka nie powiodła się");
                    getSwiat().UsunOrganizm(this);
                }
                atakujacy.WykonajRuch(Pozycja);
            }
            return true;
        } else return false;
    }
}
