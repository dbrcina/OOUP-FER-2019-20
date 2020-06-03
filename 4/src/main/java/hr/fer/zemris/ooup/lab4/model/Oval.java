package hr.fer.zemris.ooup.lab4.model;

import hr.fer.zemris.ooup.lab4.renderer.Renderer;
import hr.fer.zemris.ooup.lab4.util.GeometryUtil;
import hr.fer.zemris.ooup.lab4.util.Point;
import hr.fer.zemris.ooup.lab4.util.Rectangle;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Oval extends AbstractGraphicalObject {

    private static final int NUM_OF_POINTS = 100;

    public Oval(Point rightHP, Point downHP) {
        super(new Point[]{rightHP, downHP});
    }

    public Oval() {
        this(new Point(10, 0), new Point(0, 10));
    }

    @Override
    public Rectangle getBoundingBox() {
        Point rightHP = getHotPoint(0);
        Point downHP = getHotPoint(1);
        int width = 2 * (rightHP.getX() - downHP.getX());
        int height = 2 * (downHP.getY() - rightHP.getY());
        int x = rightHP.getX() - width;
        int y = downHP.getY() - height;
        return new Rectangle(x, y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        int a = longHalfAxis();
        int b = shortHalfAxis();
        Point center = center();
        double result = Math.pow(mousePoint.getX() - center.getX(), 2) / (a * a)
                + Math.pow(mousePoint.getY() - center.getY(), 2) / (b * b);
        if (result <= 1.0) {
            return 0.0;
        }
        double distance = Double.MAX_VALUE;
        for (Point p : points()) {
            distance = Math.min(distance, GeometryUtil.distanceFromPoint(mousePoint, p));
        }
        return distance;
    }

    private Point[] points() {
        int a = longHalfAxis();
        int b = shortHalfAxis();
        Point center = center();
        Point[] points = new Point[NUM_OF_POINTS];
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            double t = 2 * Math.PI / NUM_OF_POINTS * i;
            double x = center.getX() + a * Math.cos(t);
            double y = center.getY() + b * Math.sin(t);
            points[i] = new Point((int) x, (int) y);
        }
        return points;
    }

    private int longHalfAxis() {
        Point rightHP = getHotPoint(0);
        Point downHP = getHotPoint(1);
        return rightHP.getX() - downHP.getX();
    }

    private int shortHalfAxis() {
        Point rightHP = getHotPoint(0);
        Point downHP = getHotPoint(1);
        return downHP.getY() - rightHP.getY();
    }

    private Point center() {
        Point rightHP = getHotPoint(0);
        Point downHP = getHotPoint(1);
        return new Point(downHP.getX(), rightHP.getY());
    }

    @Override
    public void render(Renderer r) {
        r.fillPolygon(points());
    }

    @Override
    public String getShapeName() {
        return "Oval";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval(getHotPoint(0), getHotPoint(1));
    }

    @Override
    public String getShapeID() {
        return "@OVAL";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        int[] points = Arrays.stream(data.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        stack.push(new Oval(new Point(points[0], points[1]), new Point(points[2], points[3])));
    }

    @Override
    public void save(List<String> rows) {
        Point rightHP = getHotPoint(0);
        Point downHP = getHotPoint(1);
        rows.add(String.format("%s %d %d %d %d",
                getShapeID(), rightHP.getX(), rightHP.getY(), downHP.getX(), downHP.getY()));
    }

}
