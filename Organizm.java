package projekt.java;

import java.awt.*;
import java.util.Random;

public abstract class Organizm {
    public enum RodzajOrganizmu {
        CZLOWIEK,
        ANTYLOPA,
        LIS,
        OWCA,
        WILK,
        ZOLW,
        BARSZCZ_SOSNOWSKIEGO,
        GUARANA,
        MLECZ,
        TRAWA,
        WILCZE_JAGODY;
    }

    public enum Kierunek {
        GORA(2),
        DOL(3),
        PRAWO(1),
        LEWO(0),
        W_MIEJSCU(4);

        private final int zwrot;

        private Kierunek(int zwrot) {
            this.zwrot = zwrot;
        }

        public int getZwrot() { return zwrot; }

    }

    private int sila;
    private int inicjatywa;
    private int turaUrodzenia;
    private Color kolor;
    private boolean czyUmarl;
    private boolean[] kierunek;
    private boolean czyRozmnazalSie;
    private Swiat swiat;
    private Point pozycja;
    private RodzajOrganizmu rodzajOrganizmu;
    private double szansaRozmnazania;
    private static final int LICZBA_ROZNYCH_GATUNKOW = 11;


    public Organizm(RodzajOrganizmu rodzajOrganizmu, Swiat swiat, Point pozycja, int turaUrodzenia, int sila, int inicjatywa) {
        this.rodzajOrganizmu = rodzajOrganizmu;
        this.swiat = swiat;
        this.pozycja = pozycja;
        this.turaUrodzenia = turaUrodzenia;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        czyUmarl = false;
        kierunek = new boolean[]{true, true, true, true};
    }

    public void WykonajRuch(Point przyszlaPozycja) {
        int x = przyszlaPozycja.x;
        int y = przyszlaPozycja.y;
        swiat.getPlansza()[pozycja.y][pozycja.x] = null;
        swiat.getPlansza()[y][x] = this;
        pozycja.setLocation(x, y);
    }

    public Point LosujPoleDowolne(Point pozycja) {

        Random rand = new Random();
        int x = getPozycja().x;
        int y = getPozycja().y;
        int sizeX = getSwiat().getSizeX();
        int sizeY = getSwiat().getSizeY();
        int orientacjaXY = rand.nextInt(2);
        int kierunekX = rand.nextInt(2);
        int kierunekY = rand.nextInt(2);

        if (orientacjaXY == 0) {
            if (kierunekY == 0 && y - 1 >= 0) { //gora
                return new Point(x, y - 1);
            } else { // DOL
                if (y + 1 < sizeY) {
                    return new Point(x, y + 1);
                }
            }
        } else {
            if (kierunekX == 0 && x + 1 < sizeX) { //PRAWO
                return new Point(x + 1, y);
            } else { // LEWO
                if (x - 1 >= 0) {
                    return new Point(x - 1, y);
                }
            }
        }
        return new Point(x, y);
    }

    public Point LosujPoleNiezajete(Point pozycja) {

        Random rand = new Random();
        int x = getPozycja().x;
        int y = getPozycja().y;
        int sizeX = getSwiat().getSizeX();
        int sizeY = getSwiat().getSizeY();
        int orientacjaXY = rand.nextInt(2);
        int kierunekX = rand.nextInt(2);
        int kierunekY = rand.nextInt(2);

        if (orientacjaXY == 0) {
            if (kierunekY == 0 && y - 1 >= 0 && getSwiat().CoZnajdujeSieNaPolu(new Point(x, y - 1)) == null) { //gora
                return new Point(x, y - 1);
            }
            else { // DOL
                if (y + 1 < sizeY && getSwiat().CoZnajdujeSieNaPolu(new Point(x, y + 1)) == null) {
                    return new Point(x, y + 1);
                }
            }
        }
        else {
            if (kierunekX == 0 && x + 1 < sizeX && getSwiat().CoZnajdujeSieNaPolu(new Point(x + 1, y)) == null) { //PRAWO
                return new Point(x + 1, y);
            }
            else { // LEWO
                if (x - 1 >= 0 && getSwiat().CoZnajdujeSieNaPolu(new Point(x - 1, y)) == null) {
                    return new Point(x - 1, y);
                }
            }
        }
        return new Point(x, y);

    }


    static RodzajOrganizmu LosujTyp() {
        Random rand = new Random();
        int tmp = rand.nextInt(LICZBA_ROZNYCH_GATUNKOW - 1);
        if (tmp == 0) return RodzajOrganizmu.ANTYLOPA;
        if (tmp == 1) return RodzajOrganizmu.BARSZCZ_SOSNOWSKIEGO;
        if (tmp == 2) return RodzajOrganizmu.GUARANA;
        if (tmp == 3) return RodzajOrganizmu.LIS;
        if (tmp == 4) return RodzajOrganizmu.MLECZ;
        if (tmp == 5) return RodzajOrganizmu.OWCA;
        if (tmp == 6) return RodzajOrganizmu.TRAWA;
        if (tmp == 7) return RodzajOrganizmu.WILCZE_JAGODY;
        if (tmp == 8) return RodzajOrganizmu.WILK;
        else return RodzajOrganizmu.ZOLW;
    }


    public abstract String NazwaToString();

    public abstract void Akcja();

    public abstract void Kolizja(Organizm other);

    public abstract boolean CzyJestZwierzeciem();

    public boolean SpecyfikacjaAkcji(Organizm atakujacy, Organizm ofiara) {
        return false;
    }


    public int getSila() {
        return sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public int getTuraUrodzenia() {
        return turaUrodzenia;
    }

    public boolean getCzyUmarl() {
        return czyUmarl;
    }

    public Swiat getSwiat() {
        return swiat;
    }

    public Point getPozycja() {
        return pozycja;
    }

    public RodzajOrganizmu getRodzajOrganizmu() {
        return rodzajOrganizmu;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public void setTuraUrodzenia(int turaUrodzenia) {
        this.turaUrodzenia = turaUrodzenia;
    }

    public void setCzyUmarl(boolean czyUmarl) {
        this.czyUmarl = czyUmarl;
    }

    public void setCzyRozmnazalSie(boolean czyRozmnazalSie) {
        this.czyRozmnazalSie = czyRozmnazalSie;
    }

    public void setSwiat(Swiat swiat) {
        this.swiat = swiat;
    }

    public void setPozycja(Point pozycja) {
        this.pozycja = pozycja;
    }

    public void setRodzajOrganizmu(RodzajOrganizmu rodzajOrganizmu) {
        this.rodzajOrganizmu = rodzajOrganizmu;
    }

    public Color getKolor() {
        return kolor;
    }

    public void setKolor(Color kolor) {
        this.kolor = kolor;
    }

    public double getSzansaRozmnazania() {
        return szansaRozmnazania;
    }

    public void setSzansaRozmnazania(double szansaRozmnazania) {
        this.szansaRozmnazania = szansaRozmnazania;
    }



}