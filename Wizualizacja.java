package projekt.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Wizualizacja implements ActionListener, KeyListener {

    private Toolkit toolkit;
    private Dimension dimension;
    private JFrame jFrame;
    private PlanszaGraphics planszaGraphics = null;
    private KomentatorGraphics komentatorGraphics = null;
    private Oznaczenia oznaczenia = null;
    private JMenuItem nowaGra, wczytaj, zapisz, wyjscie;
    private JPanel Panel;
    private Swiat swiat;


    public Wizualizacja(String title) {
        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();

        jFrame = new JFrame(title);
        jFrame.setBounds((dimension.width - 600) / 2, (dimension.height - 600) / 2, 600, 600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        nowaGra = new JMenuItem("Nowa Gra");
        menuBar.add(nowaGra);
        nowaGra.addActionListener(this);

        wczytaj = new JMenuItem("Wczytaj");
        menuBar.add(wczytaj);
        wczytaj.addActionListener(this);

        zapisz = new JMenuItem("Zapisz");
        menuBar.add(zapisz);
        zapisz.addActionListener(this);

        wyjscie = new JMenuItem("Wyjście z gry");
        menuBar.add(wyjscie);
        wyjscie.addActionListener(this);

        jFrame.setJMenuBar(menuBar);
        jFrame.setLayout(new CardLayout());

        Panel = new JPanel();
        Panel.setLayout(null);


        jFrame.addKeyListener(this);
        jFrame.add(Panel);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nowaGra) {
            Swiat.WyczyscKomentarzy();
            swiat = new Swiat(20, 20, this);
            swiat.GenerujSwiat(0.2);
            if (planszaGraphics != null)
                Panel.remove(planszaGraphics);
            if (komentatorGraphics != null)
                Panel.remove(komentatorGraphics);
            if (oznaczenia != null)
                Panel.remove(oznaczenia);
            startGame();
        }
        if (e.getSource() == wczytaj) {
            Swiat.WyczyscKomentarzy();
            String nameOfFile = JOptionPane.showInputDialog(jFrame, "Podaj nazwe zapisu", "Zapis");
            swiat = Swiat.OdtworzSwiat(nameOfFile);
            swiat.setWizualizacja(this);
            planszaGraphics = new PlanszaGraphics(swiat);
            komentatorGraphics = new KomentatorGraphics();
            oznaczenia = new Oznaczenia();
            if (planszaGraphics != null)
                Panel.remove(planszaGraphics);
            if (komentatorGraphics != null)
                Panel.remove(komentatorGraphics);
            if (oznaczenia != null)
                Panel.remove(oznaczenia);
            startGame();
        }
        if (e.getSource() == zapisz) {
            String nameOfFile = JOptionPane.showInputDialog(jFrame, "Podaj nazwe zapisu", "Zapis");
            swiat.ZapiszSwiat(nameOfFile);
            Swiat.DodajKomentarz("Swiat zostal zapisany pod nazwą " + nameOfFile);
            komentatorGraphics.odswiezKomentarzy();
        }
        if (e.getSource() == wyjscie) {
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (swiat != null && swiat.isPauza()) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_SPACE) {

            } else if (swiat.getCzyCzlowiekZyje()) {
                if (keyCode == KeyEvent.VK_UP) {
                    if(swiat.getCzlowiek().getPozycja().y - 1 >= 0)
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.GORA);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    if(swiat.getCzlowiek().getPozycja().y + 1 < swiat.getSizeY())
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.DOL);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    if(swiat.getCzlowiek().getPozycja().x - 1 >= 0)
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.LEWO);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    if(swiat.getCzlowiek().getPozycja().x + 1 < swiat.getSizeX())
                    swiat.getCzlowiek().setKierunekRuchu(Organizm.Kierunek.PRAWO);
                } else if (keyCode == KeyEvent.VK_C) {
                    if (swiat.getCzyMoznaAktywowacUmiejetnosc()) {
                        swiat.Aktywuj();
                        Swiat.DodajKomentarz("Umiejetnosc Calopalenie aktywowana jeszcze przez:" + swiat.getCzasTrwaniaUmiejetnosci() + " tur)");
                    }
                } else {
                    Swiat.DodajKomentarz("! Brak opcji związanej z klawiszem, sprobuj ponownie !");
                    komentatorGraphics.odswiezKomentarzy();
                    return;
                }
            } else if (!swiat.getCzyCzlowiekZyje() && (keyCode == KeyEvent.VK_UP ||
                    keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_P)) {
                komentatorGraphics.odswiezKomentarzy();
                return;
            } else {
                Swiat.DodajKomentarz("! Brak opcji związanej z klawiszem, sprobuj ponownie !");
                komentatorGraphics.odswiezKomentarzy();
                return;
            }
            Swiat.WyczyscKomentarzy();
            swiat.setPauza(false);
            swiat.WykonajTure();
            odswiezSwiat();
            swiat.setPauza(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private class PlanszaGraphics extends JPanel {
        private final int sizeX;
        private final int sizeY;
        private PolePlanszy[][] polaPlanszy;
        private Swiat SWIAT;

        public PlanszaGraphics(Swiat swiat) {
            super();
            setBounds(Panel.getX(), Panel.getY(),
                    Panel.getHeight() * 2 / 3, Panel.getHeight() * 2 / 3);

            SWIAT = swiat;
            this.sizeX = swiat.getSizeX();
            this.sizeY = swiat.getSizeY();
            setBackground(Color.WHITE);

            polaPlanszy = new PolePlanszy[sizeY][sizeX];
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    polaPlanszy[i][j] = new PolePlanszy();
                }
            }

            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    this.add(polaPlanszy[i][j]);
                }
            }
            this.setLayout(new GridLayout(sizeY, sizeX, 5, 5));
        }

        private class PolePlanszy extends JButton {
            private boolean isEmpty;
            private Color kolor;



            public PolePlanszy() {
                super();
                kolor = Color.WHITE;
                setBackground(kolor);
                isEmpty = true;
            }

            public void setEmpty(boolean empty) {
                isEmpty = empty;
            }

            public Color getKolor() {
                return kolor;
            }

            public void setKolor(Color kolor) {
                this.kolor = kolor;
                setBackground(kolor);
            }

        }

        public void odswiezPlansze() {
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    Organizm tmpOrganizm = swiat.getPlansza()[i][j];
                    if (tmpOrganizm != null) {
                        polaPlanszy[i][j].setEmpty(false);
                        polaPlanszy[i][j].setEnabled(false);
                        polaPlanszy[i][j].setKolor(tmpOrganizm.getKolor());
                    } else {
                        polaPlanszy[i][j].setEmpty(true);
                        polaPlanszy[i][j].setEnabled(false);
                        polaPlanszy[i][j].setKolor(Color.WHITE);
                    }
                }
            }
        }

    }

    private class KomentatorGraphics extends JPanel {
        private String tekst;
        private final String instriction = "Autor: Stanisław Rak 188754\n" +
                "Spacja - Nowa tura bez ruchu człowieka\n" +
                "Strzalki - kierowanie Czlowiekiem\n" +
                "C - aktywacja całopalenia \n";
        private JTextArea textArea;

        public KomentatorGraphics() {
            super();
            setBounds(Panel.getX(), Panel.getHeight() * 2 / 3,
                    Panel.getWidth(),Panel.getHeight() * 1 / 3);

            tekst = Swiat.getTekst();
            textArea = new JTextArea(tekst);
            textArea.setEditable(false);
            setLayout(new CardLayout());

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }

        public void odswiezKomentarzy() {
            tekst = instriction + Swiat.getTekst();
            textArea.setText(tekst);
        }
    }

    private class Oznaczenia extends JPanel {
        private final int liczbaOrganizmow = 11;

        public Oznaczenia() {
            super();

            setBounds(planszaGraphics.getX() + planszaGraphics.getWidth(),
                    Panel.getY(),
                    Panel.getWidth() - planszaGraphics.getWidth(),
                    Panel.getHeight() * 2 / 3);

            setBackground(Color.WHITE);
            setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel[] newLine = new JLabel[liczbaOrganizmow];
            for(int i = 0; i < liczbaOrganizmow; i++) {
                newLine[i] = new JLabel();
                newLine[i].setPreferredSize(new Dimension(Panel.getWidth() - planszaGraphics.getWidth(), 1));
                newLine[i].setOpaque(false);
            }

            JLabel labelCzlowiek = new JLabel("Człowiek");
            labelCzlowiek.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekCzlowieka = new JButton();
            klocekCzlowieka.setBackground(Color.RED);
            klocekCzlowieka.setEnabled(false);
            add(klocekCzlowieka);
            add(labelCzlowiek);
            add(newLine[0]);

            JLabel labelBarszcz = new JLabel("Barszcz Sosnowskiego\n");
            labelBarszcz.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekBarszczu = new JButton();
            klocekBarszczu.setBackground(new Color(212, 125, 255));
            klocekBarszczu.setEnabled(false);
            add(klocekBarszczu);
            add(labelBarszcz);
            add(newLine[1]);

            JLabel labelGuarana = new JLabel("Guarana");
            labelGuarana.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekGuarana = new JButton();
            klocekGuarana.setBackground(new Color(255, 146, 146));
            klocekGuarana.setEnabled(false);
            add(klocekGuarana);
            add(labelGuarana);
            add(newLine[2]);

            JLabel labelTrawa = new JLabel("Trawa\n");
            labelTrawa.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekTrawa = new JButton();
            klocekTrawa.setBackground(new Color(102, 255, 133));
            klocekTrawa.setEnabled(false);
            add(klocekTrawa);
            add(labelTrawa);
            add(newLine[3]);


            JLabel labelMlecz = new JLabel("Mlecz\n");
            labelMlecz.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekMlecz = new JButton();
            klocekMlecz.setBackground(new Color(255, 255, 102));
            klocekMlecz.setEnabled(false);
            add(klocekMlecz);
            add(labelMlecz);
            add(newLine[4]);


            JLabel labelJagody = new JLabel("Wilcze Jagody\n");
            labelJagody.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekJagody = new JButton();
            klocekJagody.setBackground(new Color(125, 138, 255));
            klocekJagody.setEnabled(false);
            add(klocekJagody);
            add(labelJagody);
            add(newLine[5]);

            JLabel labelAntylopa = new JLabel("Antylopa\n");
            labelAntylopa.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekAntylopa = new JButton();
            klocekAntylopa.setBackground(new Color(130, 90, 0));
            klocekAntylopa.setEnabled(false);
            add(klocekAntylopa);
            add(labelAntylopa);
            add(newLine[6]);

            JLabel labelLis = new JLabel("Lis\n");
            labelLis.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekLis = new JButton();
            klocekLis.setBackground(Color.ORANGE);
            klocekLis.setEnabled(false);
            add(klocekLis);
            add(labelLis);
            add(newLine[7]);

            JLabel labelWilk = new JLabel("Wilk\n");
            labelWilk.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekWilk = new JButton();
            klocekWilk.setBackground(new Color(95, 73, 73));
            klocekWilk.setEnabled(false);
            add(klocekWilk);
            add(labelWilk);
            add(newLine[8]);

            JLabel labelOwca = new JLabel("Owca\n");
            labelOwca.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekOwca = new JButton();
            klocekOwca.setBackground(new Color(129, 1, 183));
            klocekOwca.setEnabled(false);
            add(klocekOwca);
            add(labelOwca);
            add(newLine[9]);

            JLabel labelZolw = new JLabel("Żółw\n");
            labelZolw.setFont(new Font("Serif", Font.BOLD, 15));
            JButton klocekZolw = new JButton();
            klocekZolw.setBackground( new Color(0, 110, 0));
            klocekZolw.setEnabled(false);
            add(klocekZolw);
            add(labelZolw);
        }
    }

    private void startGame() {
        planszaGraphics = new PlanszaGraphics(swiat);
        Panel.add(planszaGraphics);

        komentatorGraphics = new KomentatorGraphics();
        Panel.add(komentatorGraphics);

        oznaczenia = new Oznaczenia();
        Panel.add(oznaczenia);

        odswiezSwiat();
    }

    public void odswiezSwiat() {
        planszaGraphics.odswiezPlansze();
        komentatorGraphics.odswiezKomentarzy();
        SwingUtilities.updateComponentTreeUI(jFrame);
        jFrame.requestFocusInWindow();
    }

    public Swiat getSwiat() {
        return swiat;
    }

}

