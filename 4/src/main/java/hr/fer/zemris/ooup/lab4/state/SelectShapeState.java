package hr.fer.zemris.ooup.lab4.state;

import hr.fer.zemris.ooup.lab4.model.CompositeShape;
import hr.fer.zemris.ooup.lab4.model.DocumentModel;
import hr.fer.zemris.ooup.lab4.model.GraphicalObject;
import hr.fer.zemris.ooup.lab4.renderer.Renderer;
import hr.fer.zemris.ooup.lab4.util.Point;
import hr.fer.zemris.ooup.lab4.util.Rectangle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SelectShapeState extends IdleState {

    private static final int HOT_POINT_PIXEL = 6;

    private final DocumentModel model;
    private GraphicalObject selectedObj;
    private int indexOfSelectedHotPoint = -1;
    private Point previousPoint;

    public SelectShapeState(DocumentModel model) {
        this.model = model;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        if (!ctrlDown) {
            model.deselectAll();
        }
        selectedObj = model.findSelectedGraphicalObject(mousePoint);
        if (selectedObj != null) {
            previousPoint = mousePoint;
            selectedObj.setSelected(!selectedObj.isSelected());
            indexOfSelectedHotPoint = model.findSelectedHotPoint(selectedObj, mousePoint);
            if (indexOfSelectedHotPoint >= 0) {
                if (!selectedObj.isHotPointSelected(indexOfSelectedHotPoint)) {
                    selectedObj.setHotPointSelected(indexOfSelectedHotPoint, true);
                }
            }
        }
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        if (indexOfSelectedHotPoint >= 0) {
            selectedObj.setHotPointSelected(indexOfSelectedHotPoint, false);
            indexOfSelectedHotPoint = -1;
            previousPoint = null;
        }
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (selectedObj == null) return;
        if (indexOfSelectedHotPoint >= 0) {
            selectedObj.setHotPoint(indexOfSelectedHotPoint, mousePoint);
        } else {
            selectedObj.translate(mousePoint.difference(previousPoint));
            previousPoint = mousePoint;
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        List<GraphicalObject> selectedObjects = model.getSelectedObjects();
        if (selectedObjects.isEmpty()) return;
        switch (keyCode) {
            case KeyEvent.VK_UP:
                selectedObjects.forEach(obj -> obj.translate(new Point(0, -1)));
                break;
            case KeyEvent.VK_DOWN:
                selectedObjects.forEach(obj -> obj.translate(new Point(0, 1)));
                break;
            case KeyEvent.VK_LEFT:
                selectedObjects.forEach(obj -> obj.translate(new Point(-1, 0)));
                break;
            case KeyEvent.VK_RIGHT:
                selectedObjects.forEach(obj -> obj.translate(new Point(1, 0)));
                break;
            case KeyEvent.VK_PLUS:
            case KeyEvent.VK_ADD:
                if (selectedObjects.size() == 1) {
                    model.increaseZ(selectedObjects.get(0));
                }
                break;
            case KeyEvent.VK_MINUS:
            case KeyEvent.VK_SUBTRACT:
                if (selectedObjects.size() == 1) {
                    model.decreaseZ(selectedObjects.get(0));
                }
                break;
            case KeyEvent.VK_G:
                List<GraphicalObject> objs = new ArrayList<>(selectedObjects);
                GraphicalObject composite = new CompositeShape(objs, true);
                objs.forEach(model::removeGraphicalObject);
                model.addGraphicalObject(composite);
                break;
            case KeyEvent.VK_U:
                if (selectedObjects.size() == 1) {
                    GraphicalObject object = selectedObjects.get(0);
                    if (object instanceof CompositeShape) {
                        CompositeShape comp = (CompositeShape) object;
                        List<GraphicalObject> objects = comp.getObjects();
                        objects.forEach(obj -> obj.setSelected(true));
                        model.removeGraphicalObject(comp);
                        objects.forEach(model::addGraphicalObject);
                    }
                }
        }
    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        if (!go.isSelected()) return;
        Rectangle bbox = go.getBoundingBox();
        int x = bbox.getX();
        int y = bbox.getY();
        int width = bbox.getWidth();
        int height = bbox.getHeight();
        Point topLeft = new Point(x, y);
        Point topRight = new Point(x + width, y);
        Point bottomRight = new Point(x + width, y + height);
        Point bottomLeft = new Point(x, y + height);
        r.drawLine(topLeft, topRight);
        r.drawLine(topRight, bottomRight);
        r.drawLine(bottomRight, bottomLeft);
        r.drawLine(bottomLeft, topLeft);
        if (go instanceof CompositeShape) return;
        if (model.getSelectedObjects().size() == 1) {
            int halfPixel = HOT_POINT_PIXEL / 2;
            for (int i = 0; i < go.getNumberOfHotPoints(); i++) {
                Point p = go.getHotPoint(i);
                Point topLeftHP = new Point(p.getX() - halfPixel, p.getY() - halfPixel);
                Point topRightHP = new Point(topLeftHP.getX() + HOT_POINT_PIXEL, topLeftHP.getY());
                Point bottomRightHP = new Point(topRightHP.getX(), topRightHP.getY() + HOT_POINT_PIXEL);
                Point bottomLeftHP = new Point(topLeftHP.getX(), bottomRightHP.getY());
                r.drawLine(topLeftHP, topRightHP);
                r.drawLine(topRightHP, bottomRightHP);
                r.drawLine(bottomRightHP, bottomLeftHP);
                r.drawLine(bottomLeftHP, topLeftHP);
            }
        }
    }

    @Override
    public void onLeaving() {
        model.deselectAll();
    }

}
