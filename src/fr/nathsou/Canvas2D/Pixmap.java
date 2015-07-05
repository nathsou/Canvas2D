package fr.nathsou.Canvas2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nathansoufflet on 22/06/15.
 */
public class Pixmap { //Basically an image

    private ArrayList<Color> pixels = new ArrayList<Color>();
    private int width, height;
    private boolean visible = true;


    public Pixmap(BufferedImage texture) {

        if (pixels.size() > 0) {
            pixels.clear();
        }

        width = texture.getWidth();
        height = texture.getHeight();

        for (int i = 0; i < width * height; i++) {
            pixels.add(new Color(texture.getRGB(i % width, (int) Math.floor(i / width))));
        }

    }

    public Pixmap(ArrayList<Color> pxls, int width, int height) {

        if (pixels.size() > 0) {
            pixels.clear();
        }

        this.width = width;
        this.height = height;

        pixels = pxls;

    }

    public Pixmap(File image) throws IOException {

        if (pixels.size() > 0) {
            pixels.clear();
        }

        BufferedImage img = ImageIO.read(image);
        width = img.getWidth();
        height = img.getHeight();

        for (int i = 0; i < width * height; i++) {
            pixels.add(new Color(img.getRGB(i % width, (int) Math.floor(i / width))));
        }
    }

    public Pixmap(String path) throws IOException {

        if (pixels.size() > 0) {
            pixels.clear();
        }

        BufferedImage img = ImageIO.read(new File(path));
        width = img.getWidth();
        height = img.getHeight();

        for (int i = 0; i < width * height; i++) {
            pixels.add(new Color(img.getRGB(i % width, (int) Math.floor(i / width))));
        }
    }

    //Getters && Setters

    public ArrayList<Color> getPixels() {
        return pixels;
    }

    public void setPixels(ArrayList<Color> pxls, int width, int height) {
        this.width = width;
        this.height = height;
        pixels = pxls;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public ArrayList<Color> getRegion(Point p1, Point p2) {

        ArrayList<Color> region = new ArrayList<Color>();

        for (int y = p1.y; y < p2.y; y++) {
            for (int x = p1.x; x < p2.x; x++) {
                if (!(x < 0 || x > width - 1 || y < 0 || y > height - 1)) {
                    region.add(pixels.get(y * width + x));
                }
            }
        }

        return region;
    }
}
