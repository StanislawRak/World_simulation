package projekt.java.zwierze;

import projekt.java.*;

import java.util.Random;
import java.awt.*;

public class Lis extends Zwierze {
    private static final int sila = 3;
    private static final int inicjatywa = 7;
    private static final Color kolor = Color.ORANGE;

    public Lis(Swiat swiat, Point pozycja, int turaUrodzenia) {
        super(RodzajOrganizmu.LIS, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setKolor(kolor);
    }

    @Override
    public String NazwaToString() {
        return "Lis";
    }

    @Override
    public Point LosujPoleDowolne(Point pozycja) {
        int xx = pozycja.x;
        int yy = pozycja.y;
        int sizeX = getSwiat().getSizeX();
        int sizeY = getSwiat().getSizeY();

        Random rand = new Random();
        int los = rand.nextInt(100);
        //RUCH W LEWO
        if (los < 25 && (xx - 1) > 0 && getSwiat().CoZnajdujeSieNaPolu(new Point(xx - 1, yy)) != null)
        {
            if(getSwiat().CoZnajdujeSieNaPolu(new Point(xx - 1, yy)).getSila() <= getSila())
                return new Point(xx - 1, yy);

        } else if(los < 25 && (xx - 1) > 0) return new Point(xx - 1, yy);

            //RUCH W PRAWO
        else if (los >= 25 && los < 50 && (xx + 1) < sizeX && getSwiat().CoZnajdujeSieNaPolu(new Point(xx + 1, yy)) != null){
            if(getSwiat().CoZnajdujeSieNaPolu(new Point(xx + 1, yy)).getSila() <= getSila())
                return new Point(xx + 1, yy);
        } else if(los >= 25 && los < 50 && (xx + 1) < sizeX ) return new Point(xx + 1, yy);
            //RUCH W GORE
        else if (los >= 50 && los < 75  && (yy - 1) > 0 && getSwiat().CoZnajdujeSieNaPolu(new Point(xx, yy - 1)) != null){
            if(getSwiat().CoZnajdujeSieNaPolu(new Point(xx, yy - 1)).getSila() <= getSila())
                return new Point(xx, yy - 1);
        } else if(los >= 50 && los < 75  && (yy - 1) > 0 ) return new Point(xx, yy - 1);
            //RUCH W DOL
        else if (los >= 75  && (yy + 1) < sizeY && getSwiat().CoZnajdujeSieNaPolu(new Point(xx, yy + 1)) != null){
            if(getSwiat().CoZnajdujeSieNaPolu(new Point(xx, yy + 1)).getSila() <= getSila())
                return new Point(xx, yy + 1);
        } else if (los >= 75  && (yy + 1) < sizeY ) return new Point(xx, yy + 1);

        return new Point(xx, yy);
    }
}
