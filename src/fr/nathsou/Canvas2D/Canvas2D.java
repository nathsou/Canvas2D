package fr.nathsou.Canvas2D;

import fr.nathsou.Canvas2D.Shapes.Polygon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nathansoufflet on 17/05/15.
 */
public class Canvas2D {

    private ArrayList<Pixmap> layouts = new ArrayList<Pixmap>();
    private ArrayList<Point> layoutsPositions = new ArrayList<Point>();
    private ArrayList<Color> pixels = new ArrayList<Color>();
    private int width, height;
    private Color strokeColor = Color.black;
    private Point camera = new Point(width / 2, height / 2);

    public Canvas2D(int width, int height, Color color) {

        if (pixels.size() > 0) {
            pixels.clear();
        }

        this.width = width;
        this.height = height;

        for (int i = 0; i < width * (height + 1); i++) {
            pixels.add(color);
        }

        layouts.add(new Pixmap(pixels, width, height));
        layoutsPositions.add(new Point(0, 0));
    }

    public Canvas2D(Pixmap img) {

        if (pixels.size() > 0) {
            pixels.clear();
        }

        width = img.getWidth();
        height = img.getHeight();

        pixels = img.getPixels();
        layouts.add(new Pixmap(pixels, width, height));
        layoutsPositions.add(new Point(0, 0));

    }

    public void fill(Color color) {

        for (int i = 1; i < pixels.size(); i++) {
            pixels.set(i, color);
        }
    }

    public void clear(){
        pixels = layouts.get(0).getPixels();
    }


    //Draw methods

    public void drawLine(Point p1, Point p2) {

        int d = 0;
        int x1 = p1.x;
        int y1 = p1.y;
        int x2 = p2.x;
        int y2 = p2.y;

        int dy = Math.abs(y2 - y1);
        int dx = Math.abs(x2 - x1);

        int dy2 = (dy << 1); // slope scaling factors to avoid floating
        int dx2 = (dx << 1); // point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        if (dy <= dx) {
            for (; ; ) {
                setRGB(x1, y1, strokeColor);
                if (x1 == x2)
                    break;
                x1 += ix;
                d += dy2;
                if (d > dx) {
                    y1 += iy;
                    d -= dx2;
                }
            }
        } else {
            for (; ; ) {
                setRGB(x1, y1, strokeColor);
                if (y1 == y2)
                    break;
                y1 += iy;
                d += dx2;
                if (d > dy) {
                    x1 += ix;
                    d -= dy2;
                }
            }
        }
    }


    //LAYOUTS

    public void addLayout(Pixmap pixmap, Point position) {

        layouts.add(pixmap);
        layoutsPositions.add(position);
    }

    private void renderLayouts() {

        for (int i = 0; i < layouts.size(); i++) {
            if (layouts.get(i).isVisible()) {
                for (int y = layoutsPositions.get(i).y; y < layoutsPositions.get(i).y + layouts.get(i).getHeight(); y++) {
                    for (int x = layoutsPositions.get(i).x; x < layoutsPositions.get(i).x + layouts.get(i).getWidth(); x++) {
                        setRGB(x, y, layouts.get(i).getPixels().get((y - layoutsPositions.get(i).y) * layouts.get(i).getWidth() + (x - layoutsPositions.get(i).x)));
                    }
                }
            }
        }
    }

    public void removeLayout(Pixmap layout) {

        if (layouts.contains(layout)) {
            layoutsPositions.remove(layouts.indexOf(layout));
            layouts.remove(layout);

            renderLayouts();
        }
    }

    public void removeLayout(int layoutIndex) {

        removeLayout(layouts.get(layoutIndex));
    }

    public void showLayout(int layoutIndex) {
        layouts.get(layoutIndex).setVisible(true);
    }

    public void showLayout(Pixmap layout) {
        layouts.get(layouts.indexOf(layout)).setVisible(true);
    }

    public void hideLayout(int layoutIndex) {
        layouts.get(layoutIndex).setVisible(false);
    }

    public void hideLayout(Pixmap layout) {
        layouts.get(layouts.indexOf(layout)).setVisible(false);
    }

    //END LAYOUTS


    public Point getCameraPosition() {
        return camera;
    }

    public void setCameraPosition(Point pos) {
        camera = pos;
    }

    public void draw(Polygon shape) {
        shape.draw(this);
    }


    //Getters & Setters

    public ArrayList<Color> getRegion(Point p1, Point p2) {

        ArrayList<Color> region = new ArrayList<Color>();

        for (int y = p1.y; y < p2.y; y++) {
            for (int x = p1.x; x < p2.x; x++) {
                if (!(x < 0 || x > width - 1 || y < 0 || y > height - 1)) {
                    region.add(getRGB(x, y));
                }
            }
        }

        return region;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getRGB(int x, int y) {
        return pixels.get(y * width + x);
    }

    public void setRGB(int x, int y, Color color) {

        if (x > 0 && x < width && y > 0 && y < height)
            pixels.set(y * width + x, color);
    }

    public void setRGB(Point p, Color color) {

        setRGB(p.x, p.y, color);
    }

    public void setRGB(int x, int y) {

        setRGB(x, y, strokeColor);
    }

    public void setRGB(Point p) {

        setRGB(p.x, p.y, strokeColor);
    }

    public void setNthPixel(int n, Color c) {
        pixels.set(n, c);
    }

    public void setPixels(ArrayList<Color> pixels, int width, int height) {
        if (pixels.size() == width * height) {
            this.pixels = pixels;
            this.width = width;
            this.height = height;
        } else {
            throw new NumberFormatException("Wrong width or/and height parameters");
        }
    }

    public void fillRegion(Point p1, Point p2, Color c) {

        for (int y = p1.y; y < p2.y; y++) {
            for (int x = p1.x; x < p2.x; x++) {
                setRGB(x, y, c);
            }
        }

    }

    /*
        public Canvas2D getResizedCanvas(float sq){

            ArrayList<Color> pxls = new ArrayList<Color>();
                //int sq = (int) Math.sqrt(cnv.getPixels().size() / nbPixel);
                Point p1, p2;
                for (float y = sq; y <= this.getHeight() + sq; y += sq) {
                    for (float x = 0; x <= this.getWidth(); x += sq) {
                        p1 = new Point((int)x, (int)(y - sq));
                        p2 = new Point((int)(x + sq), (int)y);
                        Color avg = PixelTools.averageColor(p1, p2, this);
                        if(x >= 0 && x < width && y >= 0 && y < height) {
                            pxls.add(avg);
                        }

                    }
                    System.out.println((width/sq)*(height/sq) + " " + pxls.size());
                }
            return new Canvas2D(pxls, (int)(width/sq), (int)(height/sq));
        }
    */


    public ArrayList<Color> getPixels() {
        return pixels;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color color) {
        strokeColor = color;
    }


    //CONVERSIONS

    public Pixmap toPixmap() {

        return new Pixmap(pixels, width, height);
    }

    public BufferedImage toBufferedImage() {

        renderLayouts();

        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                image.setRGB(x, y, this.getRGB(x, y).getRGB());
            }
        }

        return image;
    }

    public void saveAsImage(File file) throws IOException{
        ImageIO.write(toBufferedImage(), "png", file);
    }
}
