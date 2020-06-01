package hr.fer.zemris.ooup.lab4.model;

import hr.fer.zemris.ooup.lab4.renderer.Renderer;
import hr.fer.zemris.ooup.lab4.util.GeometryUtil;
import hr.fer.zemris.ooup.lab4.util.Point;
import hr.fer.zemris.ooup.lab4.util.Rectangle;

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
        int x = width - rightHP.getX();
        int y = height - downHP.getY();
        return new Rectangle(x, y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        Point rightHP = getHotPoint(0);
        Point downHP = getHotPoint(1);
        int a = rightHP.getX() - downHP.getX();
        int b = downHP.getY() - rightHP.getY();
        int centerX = rightHP.getX();
        int centerY = downHP.getY();
        double result = Math.pow(mousePoint.getX() - centerX, 2) / (a * a)
                + Math.pow(mousePoint.getY() - centerY, 2) / (b * b);
        if (result <= 1.0) {
            return 0.0;
        }
        double distance = -1;
        for (Point p : points()) {
            distance = Math.min(distance, GeometryUtil.distanceFromPoint(mousePoint, p));
        }
        return distance;
    }

    private Point[] points() {
        Point rightHP = getHotPoint(0);
        Point downHP = getHotPoint(1);
        int a = rightHP.getX() - downHP.getX();
        int b = downHP.getY() - rightHP.getY();
        int centerX = rightHP.getX();
        int centerY = downHP.getY();
        Point[] points = new Point[NUM_OF_POINTS];
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            double t = 2 * Math.PI / NUM_OF_POINTS * i;
            double x = centerX + a * Math.cos(t);
            double y = centerY + b * Math.sin(t);
            points[i] = new Point((int) x, (int) y);
        }
        return points;
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
        return null;
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {

    }

    @Override
    public void save(List<String> rows) {

    }

}
