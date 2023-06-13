package projekt.java.zwierze;

import projekt.java.*;

import java.awt.*;

public class Czlowiek extends Zwierze {

    private static final int sila = 5;
    private static final int inicjatywa = 4;
    private static final Color kolor = Color.RED;
    private Kierunek kierunekRuchu;


    public Czlowiek(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.CZLOWIEK, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setKolor(kolor);

        kierunekRuchu = Kierunek.W_MIEJSCU;
    }

    private void Calopalenie() {
        int x = getPozycja().x;
        int y = getPozycja().y;
        Organizm PoleObokCzlowieka = getSwiat().CoZnajdujeSieNaPolu(new Point(x - 1, y - 1));
        UsunJesliCosSieZnajdujeNaPolu(PoleObokCzlowieka);
        PoleObokCzlowieka = getSwiat().CoZnajdujeSieNaPolu(new Point(x, y - 1));
        UsunJesliCosSieZnajdujeNaPolu(PoleObokCzlowieka);
        PoleObokCzlowieka = getSwiat().CoZnajdujeSieNaPolu(new Point(x + 1, y - 1));
        UsunJesliCosSieZnajdujeNaPolu(PoleObokCzlowieka);
        PoleObokCzlowieka = getSwiat().CoZnajdujeSieNaPolu(new Point(x + 1, y));
        UsunJesliCosSieZnajdujeNaPolu(PoleObokCzlowieka);
        PoleObokCzlowieka = getSwiat().CoZnajdujeSieNaPolu(new Point(x + 1, y + 1));
        UsunJesliCosSieZnajdujeNaPolu(PoleObokCzlowieka);
        PoleObokCzlowieka = getSwiat().CoZnajdujeSieNaPolu(new Point(x, y + 1));
        UsunJesliCosSieZnajdujeNaPolu(PoleObokCzlowieka);
        PoleObokCzlowieka = getSwiat().CoZnajdujeSieNaPolu(new Point(x - 1, y + 1));
        UsunJesliCosSieZnajdujeNaPolu(PoleObokCzlowieka);
        PoleObokCzlowieka = getSwiat().CoZnajdujeSieNaPolu(new Point(x - 1, y));
        UsunJesliCosSieZnajdujeNaPolu(PoleObokCzlowieka);
    }

    private void UsunJesliCosSieZnajdujeNaPolu(Organizm PoleObokCzlowieka){
        if(PoleObokCzlowieka != null) {
            Swiat.DodajKomentarz("W skutek całopalenia: ");
            getSwiat().UsunOrganizm(PoleObokCzlowieka);
        }
    }

    @Override
    protected Point ZaplanujRuch() {
        int x = getPozycja().x;
        int y = getPozycja().y;
        if (kierunekRuchu == Kierunek.W_MIEJSCU) return getPozycja();
        else {
            if (kierunekRuchu == Kierunek.DOL) return new Point(x, y + 1);
            if (kierunekRuchu == Kierunek.GORA) return new Point(x, y - 1);
            if (kierunekRuchu == Kierunek.LEWO) return new Point(x - 1, y);
            if (kierunekRuchu == Kierunek.PRAWO) return new Point(x + 1, y);
            else return new Point(x, y);
        }
    }

    @Override
    public void Akcja() {
        if (getSwiat().getCzyUmiejetnoscJestAktywna()) {
            Swiat.DodajKomentarz(NazwaToString() + " 'Calopalenie' jest aktywne(Pozostaly czas "
                    + getSwiat().getCzasTrwaniaUmiejetnosci() + " tur)");
            Calopalenie();
        }
        for (int i = 0; i < getZasiegRuchu(); i++) {
            Point przyszlaPozycja = ZaplanujRuch();

            if (getSwiat().CzyPoleJestZajete(przyszlaPozycja)
                    && getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) {
                Kolizja(getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja));
                break;
            } else if (getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) WykonajRuch(przyszlaPozycja);
            if (getSwiat().getCzyUmiejetnoscJestAktywna()) Calopalenie();
        }
        kierunekRuchu = Kierunek.W_MIEJSCU;
        getSwiat().SprawdzWarunki();
    }

    @Override
    public String NazwaToString() {
        return "Czlowiek";
    }


    public void setKierunekRuchu(Kierunek kierunekRuchu) {
        this.kierunekRuchu = kierunekRuchu;
    }



}
