package fr.nathsou;

import fr.nathsou.Canvas.Canvas2D;
import fr.nathsou.PixelManipulation.CanvasFilters;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Canvas2D canvas = new Canvas2D(new File("/Users/nathansoufflet/Documents/Pictures/larc-de-triomphe-paris-eugene-galien-laloue.jpg"));

        CanvasFilters.naiveTreshold(canvas, 10);
        CanvasFilters.replaceColor(canvas, Color.black, Color.pink, 50);


        BufferedImage img = canvas.toBufferedImage();
        JFrame frame = new JFrame("Canvas2D");
        frame.setSize(img.getWidth(), img.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        ImageIO.write(img, "png", new File("canvas.png"));
        frame.pack();
        frame.setVisible(true);

    }
}
