package projekt.java;

import java.awt.*;
import java.util.Random;

public abstract class Roslina extends Organizm {
    protected Roslina(RodzajOrganizmu rodzajOrganizmu, Swiat swiat, Point pozycja, int turaUrodzenia, int sila, int inicjatywa) {
        super(rodzajOrganizmu, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setSzansaRozmnazania(0.3);
    }
    @Override
    public void Akcja() {
        Random rand = new Random();
        int granica = 100;
        int los = rand.nextInt(granica);
        if (los < getSzansaRozmnazania() * 100) Rozprzestrzenianie();
    }

    protected void Rozprzestrzenianie() {
        Random rand = new Random();
        int x = getPozycja().x;
        int y = getPozycja().y;
        Point nowyPunkt = new Point(x, y);
        int sizeX = getSwiat().getSizeX();
        int sizeY = getSwiat().getSizeY();
        int nastapilRuch = 0;
        int orientacjaXY = rand.nextInt(2);
        int kierunekX = rand.nextInt(2);
        int kierunekY = rand.nextInt(2);

        if (orientacjaXY == 0) {
            if (kierunekY == 0 && y - 1 >= 0 && getSwiat().CoZnajdujeSieNaPolu(new Point(x, y - 1)) == null) { //GORA
                nowyPunkt.setLocation(x, y - 1);
                nastapilRuch++;
            }
            else { // DOL
                if (y + 1 < sizeY && getSwiat().CoZnajdujeSieNaPolu(new Point(x, y + 1)) == null) {
                    nowyPunkt.setLocation(x, y + 1);
                    nastapilRuch++;
                }
            }
        }
        else {
            if (kierunekX == 0 && x + 1 < sizeX && getSwiat().CoZnajdujeSieNaPolu(new Point(x + 1, y)) == null) { //PRAWO
                nowyPunkt.setLocation(x + 1, y);
                nastapilRuch++;
            }
            else { // LEWO
                if (x - 1 >= 0 && getSwiat().CoZnajdujeSieNaPolu(new Point(x - 1, y)) == null) {
                    nowyPunkt.setLocation(x - 1, y);
                    nastapilRuch++;
                }
            }
        }
        if (nastapilRuch != 0) {
            Organizm nowozasiany = Swiat.StworzNowyOrganizm(getRodzajOrganizmu(), this.getSwiat(), nowyPunkt);
            Swiat.DodajKomentarz(nowozasiany.NazwaToString() + ": rozrasta się i zajmuje teraz rowniez pole (" + Integer.toString(x) +";"+ Integer.toString(y) + ")");
            getSwiat().DodajOrganizm(nowozasiany);
        }

    }

    @Override
    public boolean CzyJestZwierzeciem() {
        return false;
    }

    @Override
    public void Kolizja(Organizm other) {
    }

}
