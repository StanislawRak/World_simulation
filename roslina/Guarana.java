package projekt.java.roslina;

import projekt.java.*;

import java.awt.*;

public class Guarana extends Roslina {
    private static final int sila = 0;
    private static final int inicjatywa = 0;
    private static final Color kolor = new Color(255, 146, 146);

    public Guarana(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.GUARANA, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setKolor(kolor);
        setSzansaRozmnazania(0.15);
    }

    @Override
    public String NazwaToString() {
        return "Guarana";
    }

    @Override
    public boolean SpecyfikacjaAkcji(Organizm atakujacy, Organizm ofiara) {
        Point Pozycja = this.getPozycja();
        getSwiat().UsunOrganizm(this);
        atakujacy.WykonajRuch(Pozycja);
        Swiat.DodajKomentarz(atakujacy.NazwaToString() + " wzmocnił siłę o 3 pkt");
        atakujacy.setSila(atakujacy.getSila() + 3);
        return true;
    }
}