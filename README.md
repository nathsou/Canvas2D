# Canvas2D

Canvas2D allows you to easily manipulate pixel arrays

```java
//Creating the canvas
Canvas2D canvas = new Canvas2D(800, 600, Color.white);
canvas.setStrokeColor(Color.black);
```

## Simple shapes

#### Points and Lines

```java
//Drawing a point
canvas.setRGB(10, 10, Color.red);

//Drawing lines
canvas.drawLine(new Point(40, 35), new Point(40, 125));
canvas.drawLine(new Point(160, 35), new Point(160, 125));
canvas.drawLine(new Point(30, 175), new Point(30, 150));
canvas.drawLine(new Point(30, 150), new Point(170, 175));
canvas.drawLine(new Point(30, 175), new Point(170, 175));
```

![Lines](http://nathsou.fr/iup/u/f0bc-canvas.png)

#### Polygons

```java
Polygon triangle = new Polygon(new Point(175, 175), 3, 150);
canvas.draw(triangle);
        
Polygon heptagon = new Polygon(new Point(500, 175), 7, 150);
canvas.setStrokeColor(Color.red);
canvas.draw(heptagon);
        
canvas.fillRegion(new Point(0, 350), new Point(700, 700), Color.black);

Polygon square = new Rectangle(new Point(175, 525), 150, 150);
canvas.setStrokeColor(Color.white);
canvas.draw(square);
        
//A circle is considered as a Polygon
Polygon circle = new Circle(new Point(500, 525), 150);
canvas.setStrokeColor(Color.green);
canvas.draw(circle);
```

![Polygons](http://nathsou.fr/iup/u/78e9-canvas.png)


You can also draw non regular polygons:
```java
Polygon weirdPolygon = new Polygon(new Point[]{
         new Point(100, 200),
         new Point(150, 80),
         new Point(227, 169),
         new Point(10, 260),
         new Point(10, 10),
 });

        canvas.draw(strangePolygon);
```

![Polygons](http://nathsou.fr/iup/u/7303-canvas.png)

####Manipulating images


To demonstrate how Canvas2D handles image manipulation, we will create a canvas using the following image :

![Painting from Eugène Galien-Laloue](http://images.fineartamerica.com/images-medium-large-5/larc-de-triomphe-paris-eugene-galien-laloue.jpg)


The CanvasFilters abstract class offers some image filters such as:

####Gray scaling

```java
//To manipulate images, Canvas2D uses its own Pixmap class
Canvas2D canvas = new Canvas2D(new Pixmap("Pictures/larc-de-triomphe-paris-eugene-galien-laloue.jpg")));
CanvasFilters.grayScale(canvas);
```
![GrayScaling](http://nathsou.fr/iup/u/f576-canvas.png)

####Negative/Inverse
```java
CanvasFilters.negative(canvas);
```
![Negative](http://nathsou.fr/iup/u/91a4-canvas.png)

#####Threshold
```java
CanvasFilters.naiveTreshold(canvas, 10);
```
![Threshold](http://nathsou.fr/iup/u/bdc3-canvas.png)

####Canny Edge Detection
```java
CanvasFilters.cannyEdge(canvas);
```
![CannyEdgeDetection](http://nathsou.fr/iup/u/9125-canvas.png)

####Pixelization
```java
CanvasFilters.pixelate(canvas, 5);
```
![Pixelisation](http://nathsou.fr/iup/u/6488-canvas.png)

####Color filtering
```java
CanvasFilters.filterColor(canvas, new Color(154, 139, 130), 25);
```
![ColorFiltering](http://nathsou.fr/iup/u/6139-canvas.png)

##Combining filters
```java
CanvasFilters.pixelate(canvas, 2);
CanvasFilters.naiveTreshold(canvas, 10);
CanvasFilters.replaceColor(canvas, Color.white, Color.orange, 1);
```
![Combining](http://nathsou.fr/iup/u/fbaa-canvas.png)
