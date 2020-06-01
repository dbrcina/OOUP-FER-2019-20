package hr.fer.zemris.ooup.lab4.model;

import hr.fer.zemris.ooup.lab4.listener.DocumentModelListener;
import hr.fer.zemris.ooup.lab4.listener.GraphicalObjectListener;
import hr.fer.zemris.ooup.lab4.util.GeometryUtil;
import hr.fer.zemris.ooup.lab4.util.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentModel {

    public static final double SELECTION_PROXIMITY = 10;

    // Kolekcija svih grafičkih objekata:
    private final List<GraphicalObject> objects = new ArrayList<>();
    // Read-Only proxy oko kolekcije grafičkih objekata:
    private final List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);
    // Kolekcija prijavljenih promatrača:
    private final List<DocumentModelListener> listeners = new ArrayList<>();
    // Kolekcija selektiranih objekata:
    private final List<GraphicalObject> selectedObjects = new ArrayList<>();
    // Read-Only proxy oko kolekcije selektiranih objekata:
    private final List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);

    // Promatrač koji će biti registriran nad svim objektima crteža...
    private final GraphicalObjectListener goListener = new GraphicalObjectListener() {
        @Override
        public void graphicalObjectChanged(GraphicalObject go) {
            notifyListeners();
        }

        @Override
        public void graphicalObjectSelectionChanged(GraphicalObject go) {
            if (go.isSelected()) selectedObjects.add(go);
            else selectedObjects.remove(go);
            notifyListeners();
        }
    };

    // Brisanje svih objekata iz modela (pazite da se sve potrebno odregistrira)
    // i potom obavijeste svi promatrači modela
    public void clear() {
        objects.forEach(obj -> obj.removeGraphicalObjectListener(goListener));
        objects.clear();
        selectedObjects.clear();
        notifyListeners();
    }

    // Dodavanje objekta u dokument (pazite je li već selektiran; registrirajte model kao promatrača)
    public void addGraphicalObject(GraphicalObject obj) {
        objects.add(obj);
        if (obj.isSelected()) selectedObjects.add(obj);
        obj.addGraphicalObjectListener(goListener);
        notifyListeners();
    }

    // Uklanjanje objekta iz dokumenta (pazite je li već selektiran; odregistrirajte model kao promatrača)
    public void removeGraphicalObject(GraphicalObject obj) {
        objects.remove(obj);
        if (obj.isSelected()) selectedObjects.remove(obj);
        obj.removeGraphicalObjectListener(goListener);
        notifyListeners();
    }

    // Vrati nepromjenjivu listu postojećih objekata (izmjene smiju ići samo kroz metode modela)
    public List<GraphicalObject> list() {
        return roObjects;
    }

    // Prijava...
    public void addDocumentModelListener(DocumentModelListener l) {
        listeners.add(l);
    }

    // Odjava...
    public void removeDocumentModelListener(DocumentModelListener l) {
        listeners.remove(l);
    }

    // Obavještavanje...
    public void notifyListeners() {
        listeners.forEach(DocumentModelListener::documentChange);
    }

    // Vrati nepromjenjivu listu selektiranih objekata
    public List<GraphicalObject> getSelectedObjects() {
        return roSelectedObjects;
    }

    // Pomakni predani objekt u listi objekata na jedno mjesto kasnije...
    // Time će se on iscrtati kasnije (pa će time možda veći dio biti vidljiv)
    public void increaseZ(GraphicalObject go) {
        int objIndex = objects.indexOf(go);
        int nextIndex = objIndex + 1;
        if (nextIndex >= objects.size()) nextIndex = 0;
        Collections.swap(objects, objIndex, nextIndex);
    }

    // Pomakni predani objekt u listi objekata na jedno mjesto ranije...
    public void decreaseZ(GraphicalObject go) {
        int objIndex = objects.indexOf(go);
        int nextIndex = objIndex - 1;
        if (nextIndex < 0) nextIndex = objects.size() - 1;
        Collections.swap(objects, objIndex, nextIndex);
    }

    // Pronađi postoji li u modelu neki objekt koji klik na točku koja je
    // predana kao argument selektira i vrati ga ili vrati null. Točka selektira
    // objekt kojemu je najbliža uz uvjet da ta udaljenost nije veća od
    // SELECTION_PROXIMITY. Status selektiranosti objekta ova metoda NE dira.
    public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
        for (GraphicalObject obj : objects) {
            if (Math.abs(obj.selectionDistance(mousePoint)) <= SELECTION_PROXIMITY) {
                return obj;
            }
        }
        return null;
    }

    // Pronađi da li u predanom objektu predana točka miša selektira neki hot-point.
    // Točka miša selektira onaj hot-point objekta kojemu je najbliža uz uvjet da ta
    // udaljenost nije veća od SELECTION_PROXIMITY. Vraća se indeks hot-pointa
    // kojeg bi predana točka selektirala ili -1 ako takve nema. Status selekcije
    // se pri tome NE dira.
    public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
        for (int i = 0; i < object.getNumberOfHotPoints(); i++) {
            Point hotPoint = object.getHotPoint(i);
            if (GeometryUtil.distanceFromPoint(hotPoint, mousePoint) <= SELECTION_PROXIMITY) {
                return i;
            }
        }
        return -1;
    }

}
