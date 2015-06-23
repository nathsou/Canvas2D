package fr.nathsou;

import fr.nathsou.Canvas2D.Canvas2D;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nathansoufflet on 23/06/2015.
 */
public class PaintingPanel extends JPanel {

    private int frameRate = 60;
    private Canvas2D canvas;
    public PaintingPanel(Canvas2D canvas, int frameRate){

        this.canvas = canvas;
        this.frameRate = frameRate;
        repaint();
    }

    public PaintingPanel(Canvas2D canvas){

        this.canvas = canvas;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas.toBufferedImage(), null, null);
    }

    //Getters & Setters

    public Canvas2D getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas2D canvas) {
        this.canvas = canvas;
    }
}
