package fr.nathsou.Canvas2D.Shapes;

import fr.nathsou.Canvas2D.Canvas2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nathansoufflet on 07/06/15.
 */
public class Polygon {

    private ArrayList<Point> vertices;

    public Polygon(Point[] vertices) {
        this.vertices = new ArrayList<Point>(Arrays.asList(vertices));
    }

    public Polygon(ArrayList<Point> vertices) {
        this.vertices = vertices;
    }

    public Polygon(Point center, int numberOfVertices, double sideLength) { //Regular polygon

        //Point[] vertices = new Point[numberOfVertices];
        ArrayList<Point> vertices = new ArrayList<Point>();
        double TwoPi = 2 * Math.PI;
        double radStep = TwoPi / numberOfVertices;
        double angle = 0;

        for (int i = 0; i < numberOfVertices; i++) {
            vertices.add(new Point((int) (center.x + sideLength * Math.cos(angle)), (int) (center.y + sideLength * Math.sin(angle))));
            angle += radStep;

        }

        this.vertices = vertices;
    }

    public void draw(Canvas2D canvas) {

        for (int i = 0; i < vertices.size() - 1; i++) {
            canvas.drawLine(vertices.get(i), vertices.get(i + 1));
        }

        canvas.drawLine(vertices.get(0), vertices.get(vertices.size() - 1));
    }

    public void addVertice(Point vertice) {
        vertices.add(vertice);
    }

    public String toString() {
        return "Type : " + ((isRegular() && vertices.size() <= ShapeType.values().length - 4) ? "REGULAR " : "") + getType() + ", Perimeter : " + getPerimeter() + ", Area : " + getArea() + " , Centroid : (" + getCentroid().x + " , " + getCentroid().y + ")";
    }

    public Polygon fill() {

        return this;
    }

    public void rotate(double radAngle) {

        Point c = getCentroid();
        int i = 0;
        int m = 0;
        Point e = new Point();

        for (Point p : vertices) {
            int r = (int) c.distance(p);
            if (r > m) {
                m = r;
                e = p;
            }
        }

        for (Point p : vertices) {
            double r = c.distance(p);
            double a = Math.acos(((e.x * p.x) + (e.y * p.y)) / (Math.sqrt(Math.pow(e.x, 2) + Math.pow(p.y, 2)) * Math.sqrt(Math.pow(e.x, 2) + Math.pow(p.y, 2))));
            vertices.set(i, new Point(c.x + (int) (r * Math.cos(radAngle + a)), c.y - (int) (r * Math.sin(radAngle + a))));
            i++;
        }


    }

    public void translate(int x, int y){
        for(Point p : vertices){
            p.x += x;
            p.y += y;
        }
    }

    //Getters & Setters

    public void setVertices(Point[] vertices) {
        this.vertices = new ArrayList<Point>(Arrays.asList(vertices));
    }

    public Point[] getVertices() {
        return vertices.toArray(new Point[vertices.size()]);
    }

    public void setVertices(ArrayList<Point> vertices) {
        this.vertices = vertices;
    }

    public int getVerticesNumber() {
        return vertices.size();
    }

    public ShapeType getType() {

        if (vertices.size() > 30) {
            return ShapeType.CIRCLE;
        }

        if (vertices.size() > ShapeType.values().length - 4) {
            return ShapeType.POLYGON;
        }
        return ShapeType.values()[vertices.size() - 1];
    }

    public Point getNthVertice(int n) {
        return vertices.get(n);
    }

    public boolean isRegular() {
        double refDist = vertices.get(0).distance(vertices.get(1));

        for (int i = 2; i < vertices.size(); i++) {
            if (Math.abs(vertices.get(i - 1).distance(vertices.get(i)) - refDist) > 1)
                return false;
        }

        return true;
    }

    public double getPerimeter() {
        double p = 0;

        for (int i = 1; i < vertices.size(); i++) {
            p += vertices.get(i - 1).distance(vertices.get(i));
        }

        p += vertices.get(0).distance(vertices.get(vertices.size() - 1));

        return p;
    }

    public double getArea() {

        double area = 0;
        int j = vertices.size() - 1;  // The last vertex is the 'previous' one to the first

        for (int i = 0; i < vertices.size(); i++) {
            area += (vertices.get(j).x + vertices.get(i).x) * (vertices.get(j).y - vertices.get(i).y);
            j = i;  //j is previous vertex to i
        }
        return Math.abs(area / 2.0);
    }

    public Point getCentroid() {
        double centroidX = 0, centroidY = 0;

        for (Point p : vertices) {
            centroidX += p.getX();
            centroidY += p.getY();
        }
        return new Point((int) centroidX / vertices.size(), (int) centroidY / vertices.size());
    }

}
