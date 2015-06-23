package fr.nathsou;

import fr.nathsou.Canvas2D.Canvas2D;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nathansoufflet on 23/06/2015.
 */
public class CanvasFrame extends JFrame {

    private Canvas2D canvas;
    private PaintingPanel panel;

    public CanvasFrame(Canvas2D canvas){

        panel = new PaintingPanel(canvas);

        setTitle("Canvas2D");
        setSize(canvas.getWidth(), canvas.getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setLocationRelativeTo(null);

        this.canvas = canvas;

    }

    public void update(){
        panel.repaint();
    }

    //Getters & Setters


    public Canvas2D getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas2D canvas) {
        this.canvas = canvas;
    }

}
