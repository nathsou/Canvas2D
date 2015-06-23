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

        Canvas2D canvas = new Canvas2D(new Pixmap(new File("/Users/nathansoufflet/Pictures/laloue.jpeg")));

        CanvasFrame frame = new CanvasFrame(canvas);
        frame.setVisible(true);
    }

}
