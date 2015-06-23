package fr.nathsou.Canvas2D.Shapes;

import java.awt.*;

/**
 * Created by nathansoufflet on 07/06/15.
 */
public class Rectangle extends Polygon {

    private Point centroid;
    private int width, height;

    public Rectangle(Point center, int halfW, int halfH) {

        super(new Point[]{
                new Point(center.x - halfW, center.y - halfH),
                new Point(center.x + halfW, center.y - halfH),
                new Point(center.x + halfW, center.y + halfH),
                new Point(center.x - halfW, center.y + halfH)
        });

        width = 2*halfW;
        height = 2*halfH;
        centroid = center;
    }


    //Setters & Getters

    public ShapeType getType(){
        return (isRegular())?ShapeType.SQUARE:ShapeType.RECTANGLE;
}

    public boolean isSquare() {
        return isRegular();
    }

    public double getArea(){return width*height;}

    public Point getCentroid(){return centroid;}
}
