package fr.nathsou.Canvas2D.PixelManipulation;


import fr.nathsou.Canvas2D.Canvas2D;
import fr.nathsou.Canvas2D.PixelTools;
import fr.nathsou.Canvas2D.Pixmap;

import java.awt.*;
import java.io.IOException;

/**
 * Created by nathansoufflet on 20/06/15.
 */
public abstract class CanvasFilters {

    public static Canvas2D negative(Canvas2D cnv) {
        Canvas2D copy = cnv;

        int brightness;
        for (int x = 0; x < copy.getWidth(); x++) {
            for (int y = 0; y < copy.getHeight(); y++) {
                Color c = copy.getRGB(x, y);
                copy.setRGB(x, y, new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue()));
            }
        }

        return copy;
    }

    public static Canvas2D grayScale(Canvas2D cnv) {

        Canvas2D copy = cnv;
        int brightness;
        for (int x = 0; x < copy.getWidth(); x++) {
            for (int y = 0; y < copy.getHeight(); y++) {
                Color c = copy.getRGB(x, y);
                brightness = (int) (0.21f * c.getRed() + 0.72f * c.getGreen() + 0.07f * c.getBlue()); //Luminosity scaling
                copy.setRGB(x, y, new Color(brightness, brightness, brightness));
            }
        }

        return copy;
    }

    public static Canvas2D naiveTreshold(Canvas2D cnv, int fluct) {
        Canvas2D copy = cnv;
        int s = 0;
        copy = grayScale(copy);
        for (Color c : grayScale(copy).getPixels()) {
            s += c.getRed();
        }
        float avgColor = s / copy.getPixels().size();


        for (int x = 0; x < copy.getWidth(); x++) {
            for (int y = 0; y < copy.getHeight(); y++) {
                Color c = copy.getRGB(x, y);
                int b = c.getRed();
                copy.setRGB(x, y, (b > avgColor - fluct) ? Color.white : Color.black);
            }
        }

        return copy;
    }

    public static Canvas2D cannyEdge(Canvas2D cnv) throws IOException {

        CannyEdgeDetector detector = new CannyEdgeDetector();
        detector.setSourceImage(cnv.toBufferedImage());
        detector.process();

        return new Canvas2D(new Pixmap(detector.getEdgesImage()));
    }


    public static Canvas2D treshold(Canvas2D cnv, int tresh, int maxValue) {
        Canvas2D copy = cnv;
        copy = grayScale(copy);
        Color m = new Color(maxValue, maxValue, maxValue);

        for(int i = 0; i < copy.getPixels().size(); i++){
            copy.setNthPixel(i, (copy.getPixels().get(i).getRed() > tresh)?m:Color.black);
        }

        return copy;
    }

    public static Canvas2D treshold(Canvas2D cnv, int tresh) {

        return treshold(cnv, tresh, 255);
    }



    public static Canvas2D pixelate(Canvas2D cnv, int sq, boolean grid) {

        Canvas2D copy = cnv;
        //int sq = (int) Math.sqrt(cnv.getPixels().size() / nbPixel);
        Point p1, p2;
        for (int y = sq; y <= copy.getHeight() + sq; y += sq) {
            for (int x = 0; x <= copy.getWidth(); x += sq) {
                p1 = new Point(x, y - sq);
                p2 = new Point(x + sq, y);

                Color avg = PixelTools.averageColor(p1, p2, copy);
                copy.fillRegion(p1, p2, avg);

                if (grid) {
                    for (int y2 = p1.y; y2 < p2.y; y2++) {
                        for (int x2 = p1.x; x2 < p2.x; x2++) {
                            //cnv.setRGB(x2, y2, avg);
                            copy.drawLine(p1, new Point(p2.x, p1.y));
                            copy.drawLine(p1, new Point(p1.x, p2.y));
                            copy.drawLine(new Point(p1.x, p2.y), p2);
                        }
                    }
                }

            }
        }


        return copy;
    }

    public static Canvas2D pixelate(Canvas2D cnv, int sq) {

        return pixelate(cnv, sq, false);
    }

    public static Canvas2D filterColor(Canvas2D cnv, Color c, float sigma){

        Canvas2D copy = cnv;

        for (int i = 0; i < copy.getPixels().size(); i++) {
            copy.setNthPixel(i, (PixelTools.colorDistance(c, copy.getPixels().get(i)) < sigma)?copy.getPixels().get(i):Color.white);
        }

        return copy;
    }

    public static Canvas2D filterColor(Canvas2D cnv, Color c, Color replacementColor, float sigma) {

        Canvas2D copy = cnv;

        for (int i = 0; i < copy.getPixels().size(); i++) {
            copy.setNthPixel(i, (PixelTools.colorDistance(c, copy.getPixels().get(i)) < sigma)?copy.getPixels().get(i):replacementColor);
        }

        return copy;
    }

    public static Canvas2D replaceColor(Canvas2D cnv, Color toReplace, Color replacement, int sigma){

        Canvas2D copy = cnv;

        for (int i = 0; i < copy.getPixels().size(); i++) {
            copy.setNthPixel(i, (PixelTools.colorDistance(toReplace, cnv.getPixels().get(i)) < sigma)?replacement:cnv.getPixels().get(i));
        }

        return cnv;
    }

}
