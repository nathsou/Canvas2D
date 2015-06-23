package fr.nathsou.Canvas2D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by nathansoufflet on 21/06/15.
 */
public abstract class PixelTools {

    public static Color averageColor(ArrayList<Color> pixels) {

        int r = 0;
        int g = 0;
        int b = 0;
        int s = (pixels.size() > 0) ? pixels.size() : 1;

        for (Color c : pixels) {
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
        }

        return new Color(r / s, g / s, b / s);
    }

    public static Color averageColor(Point p1, Point p2, Canvas2D cnv) {

        return averageColor(cnv.getRegion(p1, p2));
    }

    public static float colorDistance(Color a, Color b){

        int deltaR = Math.abs(a.getRed() - b.getRed());
        int deltaG = Math.abs(a.getGreen() - b.getGreen());
        int deltaB = Math.abs(a.getBlue() - b.getBlue());

        return (deltaR + deltaG + deltaB)/3;
    }


}
