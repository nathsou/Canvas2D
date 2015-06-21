package fr.nathsou.Canvas.Shapes;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Created by nathansoufflet on 07/06/15.
 */
public class Circle extends Polygon{

    private double radius, radAngle;

    public Circle(Point center, double radius, double radAngle) {

        super(new ArrayList<Point>());

        this.radius = radius;
        this.radAngle = (radAngle > 2 * Math.PI) ? radAngle % (2 * Math.PI) : radAngle;


        setVertices(new Polygon(center, (radius < 39)?(int)(2*radius):78, radius).getVertices());

    }

    public Circle(Point center, double radius) {

        super(new ArrayList<Point>());

        this.radius = radius;
        this.radAngle = 2*Math.PI;
        final double step = Math.PI / (2 * radius);
        int n = (int)((2*Math.PI)/step);

        setVertices(new Polygon(center, n, radius).getVertices());

    }

    //Getters & Setters

    public double getRadius(){
        return radius;
    }

    public ShapeType getType(){
        return ShapeType.CIRCLE;
    }

    public double getPerimeter(){
        return radAngle * radius;
    }

    public double getArea(){
        return (radAngle/(2*Math.PI))*Math.PI * Math.pow(radius, 2);
    }
}
