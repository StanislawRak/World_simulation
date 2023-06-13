package projekt.java.roslina;

import projekt.java.*;

import java.awt.*;
import java.util.Random;

public class BarszczSosnowskiego extends Roslina {
    private static final int sila = 10;
    private static final int inicjatywa = 0;
    private static final Color kolor = new Color(212, 125, 255);

    public BarszczSosnowskiego(Swiat swiat, Point pozycja, int turaUrodzenia) {

        super(RodzajOrganizmu.BARSZCZ_SOSNOWSKIEGO, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setKolor(kolor);
        setSzansaRozmnazania(0.05);
    }

    @Override
    public void Akcja() {
        int pozX = getPozycja().x;
        int pozY = getPozycja().y;

        Organizm poleNaGorze = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX, pozY -1));
        Organizm poleNaDole = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX, pozY + 1));
        Organizm polePoPrawo = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX + 1, pozY));
        Organizm polePoLewo = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX - 1, pozY));

        if (poleNaGorze != null && poleNaGorze.CzyJestZwierzeciem()) {
            getSwiat().UsunOrganizm(poleNaGorze);
            Swiat.DodajKomentarz(NazwaToString() + " zabija " + poleNaGorze.NazwaToString());
        }
        if (poleNaDole != null && poleNaDole.CzyJestZwierzeciem()) {
            getSwiat().UsunOrganizm(poleNaDole);
            Swiat.DodajKomentarz(NazwaToString() + " zabija " + poleNaDole.NazwaToString());
        }
        if (polePoLewo != null && polePoLewo.CzyJestZwierzeciem()) {
            getSwiat().UsunOrganizm(polePoLewo);
            Swiat.DodajKomentarz(NazwaToString() + " zabija " + polePoLewo.NazwaToString());
        }
        if (polePoPrawo != null && polePoPrawo.CzyJestZwierzeciem()) {
            getSwiat().UsunOrganizm(polePoPrawo);
            Swiat.DodajKomentarz(NazwaToString() + " zabija " + polePoPrawo.NazwaToString());
        }

        Random rand = new Random();
        int los = rand.nextInt(100);
        if (los < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
    }

    @Override
    public String NazwaToString() {
        return "Barszcz Sosnowskiego";
    }

}