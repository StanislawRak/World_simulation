package projekt.java;

import projekt.java.roslina.*;
import projekt.java.zwierze.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static projekt.java.Organizm.RodzajOrganizmu.CZLOWIEK;

public class Swiat {
    private int sizeX;
    private int sizeY;
    private int tura;
    private Organizm[][] plansza;
    private boolean czyCzlowiekZyje;
    private boolean pauza;
    private ArrayList<Organizm> organizmy;
    private static String tekst = "";
    private Czlowiek czlowiek;
    private Wizualizacja wizualizacja;
    private final int maxCzasTrwaniaUmiejetnosci = 5;
    private boolean czyMoznaAktywowacUmiejetnosc;
    private boolean czyUmiejetnoscJestAktywna;
    private int czasTrwaniaUmiejetnosci;
    private final int czasRegeneracjiUmiejetnosci = 5;
    private int RegeneracjaUmijetnosci;

    public Swiat(Wizualizacja wizualizacja) {
        this.sizeX = 0;
        this.sizeY = 0;
        tura = 0;
        czyCzlowiekZyje = true;
        pauza = true;
        RegeneracjaUmijetnosci = 0;
        czasTrwaniaUmiejetnosci = 0;
        czyUmiejetnoscJestAktywna = false;
        czyMoznaAktywowacUmiejetnosc = true;
        organizmy = new ArrayList<>();
        this.wizualizacja = wizualizacja;
    }

    public Swiat(int sizeX, int sizeY, Wizualizacja wizualizacja) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        tura = 0;
        RegeneracjaUmijetnosci = 0;
        czasTrwaniaUmiejetnosci = 0;
        czyUmiejetnoscJestAktywna = false;
        czyMoznaAktywowacUmiejetnosc = true;
        czyCzlowiekZyje = true;
        pauza = true;
        plansza = new Organizm[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                plansza[i][j] = null;
            }
        }
        organizmy = new ArrayList<>();
        this.wizualizacja = wizualizacja;
    }

    public static Organizm StworzNowyOrganizm(Organizm.RodzajOrganizmu rodzajOrganizmu, Swiat swiat, Point pozycja) {
        switch (rodzajOrganizmu) {
            case WILK:
                return new Wilk(swiat, pozycja, swiat.getNumerTury());
            case OWCA:
                return new Owca(swiat, pozycja, swiat.getNumerTury());
            case LIS:
                return new Lis(swiat, pozycja, swiat.getNumerTury());
            case ZOLW:
                return new Zolw(swiat, pozycja, swiat.getNumerTury());
            case ANTYLOPA:
                return new Antylopa(swiat, pozycja, swiat.getNumerTury());
            case CZLOWIEK:
                return new Czlowiek(swiat, pozycja, swiat.getNumerTury());
            case TRAWA:
                return new Trawa(swiat, pozycja, swiat.getNumerTury());
            case MLECZ:
                return new Mlecz(swiat, pozycja, swiat.getNumerTury());
            case GUARANA:
                return new Guarana(swiat, pozycja, swiat.getNumerTury());
            case WILCZE_JAGODY:
                return new WilczeJagody(swiat, pozycja, swiat.getNumerTury());
            case BARSZCZ_SOSNOWSKIEGO:
                return new BarszczSosnowskiego(swiat, pozycja, swiat.getNumerTury());
            default:
                return null;
        }
    }

    public void ZapiszSwiat(String nameOfFile) {
        try {
            nameOfFile += ".txt";
            File file = new File(nameOfFile);
            file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            pw.print(sizeX + " ");
            pw.print(sizeY + " ");
            pw.print(tura + " ");
            pw.print(czyCzlowiekZyje + " ");
            pw.print(maxCzasTrwaniaUmiejetnosci + " ");
            pw.print(RegeneracjaUmijetnosci + " ");
            pw.print(czyUmiejetnoscJestAktywna + " ");
            pw.print(czyMoznaAktywowacUmiejetnosc + "\n");
            for (int i = 0; i < organizmy.size(); i++) {
                pw.print(organizmy.get(i).getRodzajOrganizmu() + " ");
                pw.print(organizmy.get(i).getPozycja().x + " ");
                pw.print(organizmy.get(i).getPozycja().y + " ");
                pw.print(organizmy.get(i).getSila() + " ");
                pw.print(organizmy.get(i).getTuraUrodzenia() + " ");
                pw.print(organizmy.get(i).getCzyUmarl());
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static Swiat OdtworzSwiat(String nameOfFile) {
        try {
            nameOfFile += ".txt";
            File file = new File(nameOfFile);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            int sizeX = Integer.parseInt(properties[0]);
            int sizeY = Integer.parseInt(properties[1]);
            Swiat tmpSwiat = new Swiat(sizeX, sizeY, null);
            int numerTury = Integer.parseInt(properties[2]);
            tmpSwiat.tura = numerTury;
            boolean czyCzlowiekZyje = Boolean.parseBoolean(properties[3]);
            tmpSwiat.czyCzlowiekZyje = czyCzlowiekZyje;
            tmpSwiat.czlowiek = null;
            int czasTrwania = Integer.parseInt(properties[4]);
            tmpSwiat.setCzasTrwaniaUmiejetnosci(czasTrwania);
            int cooldown = Integer.parseInt(properties[5]);
            tmpSwiat.setRegeneracjaUmijetnosci(cooldown);
            boolean czyJestAktywna = Boolean.parseBoolean(properties[6]);
            tmpSwiat.setCzyUmiejetnoscJestAktywna(czyJestAktywna);
            boolean czyMoznaAktywowac = Boolean.parseBoolean(properties[7]);
            tmpSwiat.setCzyMoznaAktywowacUmiejetnosc(czyMoznaAktywowac);
 
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                Organizm.RodzajOrganizmu typOrganizmu = Organizm.RodzajOrganizmu.valueOf(properties[0]);
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);

                Organizm tmpOrganizm = StworzNowyOrganizm(typOrganizmu, tmpSwiat, new Point(x, y));
                int sila = Integer.parseInt(properties[3]);
                tmpOrganizm.setSila(sila);
                int turaUrodzenia = Integer.parseInt(properties[4]);
                tmpOrganizm.setTuraUrodzenia(turaUrodzenia);
                boolean czyUmarl = Boolean.parseBoolean(properties[5]);
                tmpOrganizm.setCzyUmarl(czyUmarl);

                if (typOrganizmu == Organizm.RodzajOrganizmu.CZLOWIEK) {
                    tmpSwiat.czlowiek = (Czlowiek) tmpOrganizm;
                }
                tmpSwiat.DodajOrganizm(tmpOrganizm);
            }
            scanner.close();
            return tmpSwiat;
        } catch (
                IOException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

        public void GenerujSwiat(double zapelnienie) {
            int liczbaOrganizmow = (int) Math.floor(sizeX * sizeY * zapelnienie);
            Point pozycja = WylosujWolnePole();
            Organizm tmpOrganizm = StworzNowyOrganizm(CZLOWIEK, this, pozycja);
            DodajOrganizm(tmpOrganizm);

            czlowiek = (Czlowiek) tmpOrganizm;
            for (int i = 0; i < liczbaOrganizmow - 1; i++) {
                pozycja = WylosujWolnePole();
                if (pozycja != new Point(-1, -1)) {
                    DodajOrganizm(StworzNowyOrganizm(Organizm.LosujTyp(), this, pozycja));
                } else return;
            }
        }

    public void WykonajTure() {

        tura++;
        DodajKomentarz("Tura: " + tura + "\n");
        SortujOrganizmy();
        for (int i = 0; i < organizmy.size(); i++) {
            if (organizmy.get(i).getTuraUrodzenia() != tura
                    && organizmy.get(i).getCzyUmarl() == false) {
                organizmy.get(i).Akcja();
            }
        }
        for (int i = 0; i < organizmy.size(); i++) {
            if (organizmy.get(i).getCzyUmarl() == true) {
                organizmy.remove(i);
                i--;
            }
        }
        for (int i = 0; i < organizmy.size(); i++) {
            organizmy.get(i).setCzyRozmnazalSie(false);
        }
    }

    private void SortujOrganizmy() {
        Collections.sort(organizmy, new Comparator<Organizm>() {
            @Override
            public int compare(Organizm o1, Organizm o2) {
                if (o1.getInicjatywa() != o2.getInicjatywa())
                    return Integer.valueOf(o2.getInicjatywa()).compareTo(o1.getInicjatywa());
                else
                    return Integer.valueOf(o1.getTuraUrodzenia()).compareTo(o2.getTuraUrodzenia());
            }
        });
    }

    public Point WylosujWolnePole() {
        Random rand = new Random();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (plansza[i][j] == null) {
                    while (true) {
                        int x = rand.nextInt(sizeX);
                        int y = rand.nextInt(sizeY);
                        if (plansza[y][x] == null) return new Point(x, y);
                    }
                }
            }
        }
        return new Point(-1, -1);
    }

    public void DodajOrganizm(Organizm organizm) {
        organizmy.add(organizm);
        plansza[organizm.getPozycja().y][organizm.getPozycja().x] = organizm;
    }

    public void UsunOrganizm(Organizm organizm) {
        plansza[organizm.getPozycja().y][organizm.getPozycja().x] = null;
        organizm.setCzyUmarl(true);
        DodajKomentarz(organizm.NazwaToString() +": śmierć organizmu");
        if (organizm.getRodzajOrganizmu() == CZLOWIEK) {
            czyCzlowiekZyje = false;
            czlowiek = null;
        }
    }

    public boolean CzyPoleJestZajete(Point pole) {
        if ((pole.x >= 0) && (pole.x < sizeX) && (pole.y >= 0) && (pole.y < sizeY)) {
            if (plansza[pole.y][pole.x] == null) return false;
        } else return true;
        return true;
    }


    public Organizm CoZnajdujeSieNaPolu(Point pole) {
        if ((pole.x >= 0) && (pole.x < sizeX) && (pole.y >= 0) && (pole.y < sizeY))
            return plansza[pole.y][pole.x];
        return null;
    }

    public void SprawdzWarunki() {
        if (RegeneracjaUmijetnosci > 0) RegeneracjaUmijetnosci--;
        if (czasTrwaniaUmiejetnosci > 0) czasTrwaniaUmiejetnosci--;
        if (czasTrwaniaUmiejetnosci == 0) Dezaktywuj();
        if (RegeneracjaUmijetnosci == 0) czyMoznaAktywowacUmiejetnosc = true;
    }

    public void Aktywuj() {
        if (RegeneracjaUmijetnosci == 0) {
            czyUmiejetnoscJestAktywna = true;
            czyMoznaAktywowacUmiejetnosc = false;
            RegeneracjaUmijetnosci = czasRegeneracjiUmiejetnosci;
            czasTrwaniaUmiejetnosci = maxCzasTrwaniaUmiejetnosci;
        }
    }

    public void Dezaktywuj() {
        czyUmiejetnoscJestAktywna = false;
    }

    public boolean getCzyMoznaAktywowacUmiejetnosc() {
        return czyMoznaAktywowacUmiejetnosc;
    }

    public void setCzyMoznaAktywowacUmiejetnosc(boolean czyMoznaAktywowacUmiejetnosc) {
        this.czyMoznaAktywowacUmiejetnosc = czyMoznaAktywowacUmiejetnosc;
    }

    public boolean getCzyUmiejetnoscJestAktywna() {
        return czyUmiejetnoscJestAktywna;
    }

    public void setCzyUmiejetnoscJestAktywna(boolean czyUmiejetnoscJestAktywna) {
        this.czyUmiejetnoscJestAktywna = czyUmiejetnoscJestAktywna;
    }

    public int getCzasTrwaniaUmiejetnosci() {
        return czasTrwaniaUmiejetnosci;
    }

    public void setCzasTrwaniaUmiejetnosci(int czasTrwaniaUmiejetnosci) {
        this.czasTrwaniaUmiejetnosci = czasTrwaniaUmiejetnosci;
    }

    public int getRegeneracjaUmijetnosci() {
        return RegeneracjaUmijetnosci;
    }

    public void setRegeneracjaUmijetnosci(int RegeneracjaUmijetnosci) {
        this.RegeneracjaUmijetnosci = RegeneracjaUmijetnosci;
    }


    public static void WyczyscKomentarzy() { tekst = ""; }

    public static void DodajKomentarz(String komentarz) {
        tekst += komentarz + "\n";
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getNumerTury() {
        return tura;
    }

    public Organizm[][] getPlansza() {
        return plansza;
    }

    public static String getTekst() {
        return tekst;
    }

    public boolean getCzyCzlowiekZyje() {
        return czyCzlowiekZyje;
    }

    public ArrayList<Organizm> getOrganizmy() {
        return organizmy;
    }

    public Czlowiek getCzlowiek() {
        return czlowiek;
    }

    public boolean isPauza() {
        return pauza;
    }

    public void setPauza(boolean pauza) {
        this.pauza = pauza;
    }

    public void setWizualizacja(Wizualizacja wizualizacja) {
        this.wizualizacja = wizualizacja;
    }

}