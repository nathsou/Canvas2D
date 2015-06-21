package fr.nathsou.Canvas;

import fr.nathsou.Canvas.Shapes.Polygon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nathansoufflet on 17/05/15.
 */
public class Canvas2D {

    private ArrayList<Color> pixels = new ArrayList<Color>();
    private int width, height;
    private Color strokeColor = Color.black;
    private Point camera = new Point(width / 2, height / 2);

    public Canvas2D(int width, int height, Color color) {

        this.width = width;
        this.height = height;

        for (int i = 0; i < width * (height + 1); i++) {
            pixels.add(color);
        }
    }

    public Canvas2D(ArrayList<Color> pixels, int width, int height) {
        if (pixels.size() == width * height) {
            this.pixels = pixels;
            this.width = width;
            this.height = height;
        } else {
            throw new NumberFormatException("Wrong width or/and height parameters");
        }
    }

    public Canvas2D(File image) throws IOException {

        BufferedImage img = ImageIO.read(image);
        width = img.getWidth();
        height = img.getHeight();

        for (int i = 0; i < width * height; i++) {
            pixels.add(new Color(img.getRGB(i % width, (int) Math.floor(i / width))));
        }

    }

    public Canvas2D(BufferedImage img) {

        width = img.getWidth();
        height = img.getHeight();

        for (int i = 0; i < width * height; i++) {
            pixels.add(new Color(img.getRGB(i % width, (int) Math.floor(i / width))));
        }

    }

    public void clear(Color color) {

        for (int i = 1; i < pixels.size(); i++) {
            pixels.set(i, color);
        }
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


    public BufferedImage toBufferedImage() {

        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                image.setRGB(x, y, this.getRGB(x, y).getRGB());
            }
        }

        return image;
    }

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

    public ArrayList<Color> getRegionPixels(Point p1, Point p2) {

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

    public BufferedImage getRegionBufferedImage(Point p1, Point p2) {

        int h = p2.y - p1.y;
        int w = p2.x - p1.x;

        BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = p1.y; y < p2.y; y++) {
            for (int x = p1.x; x < p2.x; x++) {
                if (!(x < 0 || x > width || y < 0 || y > height)) {
                    buf.setRGB(x - p1.x, y - p1.y, getRGB(x, y).getRGB());
                }
            }
        }


        return buf;
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

    public void setNthPixel(int n, Color c){
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


    public ArrayList<Color> getPixels() {
        return pixels;
    }

    public void setStrokeColor(Color color) {
        strokeColor = color;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }
}
