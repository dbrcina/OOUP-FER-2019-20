package hr.fer.zemris.ooup.lab4.model;

import hr.fer.zemris.ooup.lab4.renderer.Renderer;
import hr.fer.zemris.ooup.lab4.util.Point;
import hr.fer.zemris.ooup.lab4.util.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CompositeShape extends AbstractGraphicalObject {

    private final List<GraphicalObject> objects;

    public CompositeShape(List<GraphicalObject> objects, boolean selected) {
        super(new Point[0]);
        this.objects = new ArrayList<>(objects);
        setSelected(selected);
    }

    public List<GraphicalObject> getObjects() {
        return objects;
    }

    @Override
    public Rectangle getBoundingBox() {
        Rectangle bb = null;
        for (GraphicalObject object : objects) {
            if (bb == null) {
                bb = object.getBoundingBox();
            } else {
                bb = bb.union(object.getBoundingBox());
            }
        }
        return bb;
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return objects.stream()
                .mapToDouble(obj -> obj.selectionDistance(mousePoint))
                .min().getAsDouble();
    }

    @Override
    public void translate(Point delta) {
        objects.forEach(obj -> obj.translate(delta));
        notifyListeners();
    }

    @Override
    public void render(Renderer r) {
        objects.forEach(obj -> obj.render(r));
    }

    @Override
    public String getShapeName() {
        return null;
    }

    @Override
    public GraphicalObject duplicate() {
        return null;
    }

    @Override
    public String getShapeID() {
        return "@COMP";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        int num = Integer.parseInt(data);
        List<GraphicalObject> objects = new ArrayList<>();
        while (num > 0) {
            objects.add(stack.pop());
            num--;
        }
        stack.push(new CompositeShape(objects, false));
    }

    @Override
    public void save(List<String> rows) {
        objects.forEach(obj -> obj.save(rows));
        rows.add(String.format("%s %d", getShapeID(), objects.size()));
    }

}
