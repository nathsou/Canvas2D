package fr.nathsou;
import fr.nathsou.Canvas2D.Canvas2D;
import fr.nathsou.Canvas2D.PixelManipulation.CanvasFilters;
import fr.nathsou.Canvas2D.Pixmap;
import fr.nathsou.Canvas2D.Shapes.*;
import fr.nathsou.Canvas2D.Shapes.Polygon;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static int r = 0;
    public static ArrayList<Color> dp;

    public static void main(String[] args) throws IOException {

        Canvas2D canvas = new Canvas2D(new Pixmap(new File("/Users/nathansoufflet/Pictures/laloue.jpeg")));
        CanvasFrame frame = new CanvasFrame(canvas);
        frame.setVisible(true);

        dp = canvas.getPixels();


        go(frame);
    }

    public static void go(CanvasFrame cf){

        r++;

        System.out.println(r);
        CanvasFilters.pixelate(cf.getCanvas(), r);
        CanvasFilters.naiveTreshold(cf.getCanvas(), 10);
        CanvasFilters.replaceColor(cf.getCanvas(), Color.white, Color.orange, 1);
        cf.repaint();

        try{
            Thread.sleep(100);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        cf.getCanvas().setPixels(dp, cf.getCanvas().getWidth(), cf.getCanvas().getHeight());
        go(cf);
    }
}
