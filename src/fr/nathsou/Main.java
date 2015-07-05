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


    public static void main(String[] args) throws IOException {

        Canvas2D canvas = new Canvas2D(400, 400, Color.white);


        canvas.drawLine(new Point(100, 200), new Point(300, 200));

        ArrayList<Integer> chain = CanvasFilters.findContour(canvas, new Point(100, 200));

        System.out.println(chain.size());

        canvas.saveAsImage(new File("/Users/nathan/Documents/Pictures/borders.jpg"));

    }

}
