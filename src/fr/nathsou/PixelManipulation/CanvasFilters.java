package fr.nathsou.PixelManipulation;


import fr.nathsou.Canvas.Canvas2D;

import java.awt.*;
import java.io.IOException;

/**
 * Created by nathansoufflet on 20/06/15.
 */
public abstract class CanvasFilters {

    public static Canvas2D negative(Canvas2D cnv) {

        int brightness;
        for (int x = 0; x < cnv.getWidth(); x++) {
            for (int y = 0; y < cnv.getHeight(); y++) {
                Color c = cnv.getRGB(x, y);
                cnv.setRGB(x, y, new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue()));
            }
        }

        return cnv;
    }

    public static Canvas2D grayScale(Canvas2D cnv) {

        int brightness;
        for (int x = 0; x < cnv.getWidth(); x++) {
            for (int y = 0; y < cnv.getHeight(); y++) {
                Color c = cnv.getRGB(x, y);
                brightness = (int) (0.21f * c.getRed() + 0.72f * c.getGreen() + 0.07f * c.getBlue()); //Luminosity scaling
                cnv.setRGB(x, y, new Color(brightness, brightness, brightness));
            }
        }

        return cnv;
    }

    public static Canvas2D naiveTreshold(Canvas2D cnv, int fluct) {
        int s = 0;
        cnv = grayScale(cnv);
        for (Color c : grayScale(cnv).getPixels()) {
            s += c.getRed();
        }
        float avgColor = s / cnv.getPixels().size();


        for (int x = 0; x < cnv.getWidth(); x++) {
            for (int y = 0; y < cnv.getHeight(); y++) {
                Color c = cnv.getRGB(x, y);
                int b = c.getRed();
                cnv.setRGB(x, y, (b > avgColor - fluct) ? Color.white : Color.black);
            }
        }

        return cnv;
    }

    public static Canvas2D cannyEdge(Canvas2D cnv) throws IOException {

        CannyEdgeDetector detector = new CannyEdgeDetector();
        detector.setSourceImage(cnv.toBufferedImage());
        detector.process();

        return new Canvas2D(detector.getEdgesImage());
    }

    public static Canvas2D treshold(Canvas2D cnv, int sq, float fluct) {
        cnv = grayScale(cnv);
        Point p1, p2;
        for (int y = sq; y <= cnv.getHeight() + sq; y += sq) {
            for (int x = 0; x <= cnv.getWidth(); x += sq) {
                p1 = new Point(x, y - sq);
                p2 = new Point(x + sq, y);

                Color avg = PixelTools.averageColor(p1, p2, cnv);
                //cnv.fillRegion(p1, p2, avg);
                for (int y2 = p1.y; y2 < p2.y; y2++) {
                    for (int x2 = p1.x; x2 < p2.x; x2++) {
                        cnv.setRGB(x2, y2, (PixelTools.colorDistance(cnv.getRGB(x2, y2), avg) < fluct)?Color.white:Color.black);
                    }
                }

            }
        }


        return cnv;
    }

    public static Canvas2D pixelize(Canvas2D cnv, int sq) {

        //int sq = (int) Math.sqrt(cnv.getPixels().size() / nbPixel);
        Point p1, p2;
        for (int y = sq; y <= cnv.getHeight() + sq; y += sq) {
            for (int x = 0; x <= cnv.getWidth(); x += sq) {
                p1 = new Point(x, y - sq);
                p2 = new Point(x + sq, y);

                Color avg = PixelTools.averageColor(p1, p2, cnv);
                cnv.fillRegion(p1, p2, avg);

                /*
                for (int y2 = p1.y; y2 < p2.y; y2++) {
                    for (int x2 = p1.x; x2 < p2.x; x2++) {
                        //cnv.setRGB(x2, y2, avg);
                        cnv.drawLine(p1, new Point(p2.x, p1.y));
                        cnv.drawLine(p1, new Point(p1.x, p2.y));
                        cnv.drawLine(new Point(p1.x, p2.y), p2);
                    }
                }
                */
            }
        }


        return cnv;
    }

    public static Canvas2D filterColor(Canvas2D cnv, Color c, float sigma){

        for (int i = 0; i < cnv.getPixels().size(); i++) {
            cnv.setNthPixel(i, (PixelTools.colorDistance(c, cnv.getPixels().get(i)) < sigma)?cnv.getPixels().get(i):Color.white);
        }

        return cnv;
    }

    public static Canvas2D filterColor(Canvas2D cnv, Color c, float sigma, Color replacementColor){

        for (int i = 0; i < cnv.getPixels().size(); i++) {
            cnv.setNthPixel(i, (PixelTools.colorDistance(c, cnv.getPixels().get(i)) < sigma)?cnv.getPixels().get(i):replacementColor);
        }

        return cnv;
    }

    public static Canvas2D replaceColor(Canvas2D cnv, Color toReplace, Color replacement, int sigma){

        for (int i = 0; i < cnv.getPixels().size(); i++) {
            cnv.setNthPixel(i, (PixelTools.colorDistance(toReplace, cnv.getPixels().get(i)) < sigma)?replacement:cnv.getPixels().get(i));
        }

        return cnv;
    }

}
