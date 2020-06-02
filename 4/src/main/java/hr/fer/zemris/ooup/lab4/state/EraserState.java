package hr.fer.zemris.ooup.lab4.state;

import hr.fer.zemris.ooup.lab4.model.DocumentModel;
import hr.fer.zemris.ooup.lab4.model.GraphicalObject;
import hr.fer.zemris.ooup.lab4.renderer.Renderer;
import hr.fer.zemris.ooup.lab4.util.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EraserState extends IdleState {

    private final DocumentModel model;
    private final List<Point> points = new ArrayList<>();

    public EraserState(DocumentModel model) {
        this.model = model;
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        points.add(mousePoint);
        model.notifyListeners();
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        Set<GraphicalObject> objectsToBeRemoved = new HashSet<>();
        points.forEach(point -> {
            GraphicalObject object = model.findSelectedGraphicalObject(point);
            if (object != null) {
                objectsToBeRemoved.add(object);
            }
        });
        points.clear();
        objectsToBeRemoved.forEach(model::removeGraphicalObject);
    }

    @Override
    public void afterDraw(Renderer r) {
        for (int i = 0; i < points.size() - 1; i++) {
            r.drawLine(points.get(i), points.get(i + 1));
        }
    }

    @Override
    public void onLeaving() {
        model.notifyListeners();
    }
}
