package hr.fer.zemris.ooup.lab4.model;

import hr.fer.zemris.ooup.lab4.renderer.Renderer;
import hr.fer.zemris.ooup.lab4.util.GeometryUtil;
import hr.fer.zemris.ooup.lab4.util.Point;
import hr.fer.zemris.ooup.lab4.util.Rectangle;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LineSegment extends AbstractGraphicalObject {

    public LineSegment(Point s, Point e) {
        super(new Point[]{s, e});
    }

    public LineSegment() {
        this(new Point(0, 0), new Point(10, 0));
    }

    @Override
    public Rectangle getBoundingBox() {
        Point s = getHotPoint(0);
        Point e = getHotPoint(1);
        int xs = s.getX();
        int ys = s.getY();
        int xe = e.getX();
        int ye = e.getY();
        int x = Math.min(xs, xe);
        int y = Math.min(ys, ye);
        int width = Math.abs(xe - xs);
        int height = Math.abs(ye - ys);
        return new Rectangle(x, y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return GeometryUtil.distanceFromLineSegment(getHotPoint(0), getHotPoint(1), mousePoint);
    }

    @Override
    public void render(Renderer r) {
        r.drawLine(getHotPoint(0), getHotPoint(1));
    }

    @Override
    public String getShapeName() {
        return "Linija";
    }

    @Override
    public GraphicalObject duplicate() {
        return new LineSegment(getHotPoint(0), getHotPoint(1));
    }

    @Override
    public String getShapeID() {
        return "@LINE";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        int[] points = Arrays.stream(data.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        stack.push(new LineSegment(new Point(points[0], points[1]), new Point(points[2], points[3])));
    }

    @Override
    public void save(List<String> rows) {
        Point s = getHotPoint(0);
        Point e = getHotPoint(1);
        rows.add(String.format("%s %d %d %d %d",
                getShapeID(), s.getX(), s.getY(), e.getX(), e.getY()));
    }

}
