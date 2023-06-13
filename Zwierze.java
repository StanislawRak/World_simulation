package projekt.java;

import java.awt.*;
import java.util.Random;

public abstract class Zwierze extends Organizm {
    private int zasiegRuchu = 1;

    public Zwierze(RodzajOrganizmu typOrganizmu, Swiat swiat,
                   Point pozycja, int turaUrodzenia, int sila, int inicjatywa) {
        super(typOrganizmu, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setCzyRozmnazalSie(false);
        setSzansaRozmnazania(1);
    }

    @Override
    public void Akcja() {
        for (int i = 0; i < zasiegRuchu; i++) {
            Point przyszlaPozycja = ZaplanujRuch();

            if (getSwiat().CzyPoleJestZajete(przyszlaPozycja)
                    && getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) {
                Kolizja(getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja));
                break;
            } else if (getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) WykonajRuch(przyszlaPozycja);
        }
    }

    @Override
    public void Kolizja(Organizm other) {
        Swiat.DodajKomentarz(NazwaToString() + " wszedł w konfrontacje z " + other.NazwaToString());
        if (getRodzajOrganizmu() == other.getRodzajOrganizmu()) {
            Random rand = new Random();
            int los = rand.nextInt(100);
            if (los < getSzansaRozmnazania() * 100) Rozmnazanie(other);
        } else {
            if (other.SpecyfikacjaAkcji(this, other)) return;
            if (SpecyfikacjaAkcji(this, other)) return;

            if (getSila() >= other.getSila()) {
                getSwiat().UsunOrganizm(other);
                WykonajRuch(other.getPozycja());
            } else {
                getSwiat().UsunOrganizm(this);
            }
        }
    }

    @Override
    public boolean CzyJestZwierzeciem() {
        return true;
    }

    protected Point ZaplanujRuch() { return LosujPoleDowolne(getPozycja()); }

    private void Rozmnazanie(Organizm other) {
        if (SzukajMiejscaDlaPotomka(this) == -1)
            SzukajMiejscaDlaPotomka(other);
    }

    private int SzukajMiejscaDlaPotomka(Organizm org){
        int pozX = org.getPozycja().x;
        int pozY = org.getPozycja().y;
        Organizm poleNaGorze = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX, pozY -1));
        Organizm poleNaDole = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX, pozY + 1));
        Organizm polePoPrawo = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX + 1, pozY));
        Organizm polePoLewo = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX - 1, pozY));
        if (pozY - 1 >= 0 && poleNaGorze == null) {
            Swiat.StworzNowyOrganizm(getRodzajOrganizmu(), this.getSwiat(), new Point(pozX , pozY - 1));
        }
        else if (pozY + 1 < getSwiat().getSizeY() && poleNaDole == null) {
            Swiat.StworzNowyOrganizm(getRodzajOrganizmu(), this.getSwiat(), new Point(pozX , pozY + 1));

        }
        else if (pozX + 1 < getSwiat().getSizeX() && polePoPrawo == null) {
            Swiat.StworzNowyOrganizm(getRodzajOrganizmu(), this.getSwiat(), new Point(pozX + 1, pozY));

        }
        else if (pozX - 1 >= 0 && polePoLewo == null) {
            Swiat.StworzNowyOrganizm(getRodzajOrganizmu(), this.getSwiat(), new Point(pozX - 1, pozY));

        }
        else return -1;
        return 0;
    }

    public int getZasiegRuchu() {
        return zasiegRuchu;
    }

    public void setZasiegRuchu(int zasiegRuchu) {
        this.zasiegRuchu = zasiegRuchu;
    }
}
