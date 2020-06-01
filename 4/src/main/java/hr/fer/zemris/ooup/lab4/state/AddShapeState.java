package hr.fer.zemris.ooup.lab4.state;

import hr.fer.zemris.ooup.lab4.model.DocumentModel;
import hr.fer.zemris.ooup.lab4.model.GraphicalObject;
import hr.fer.zemris.ooup.lab4.util.Point;

public class AddShapeState extends IdleState {

    private final DocumentModel model;
    private final GraphicalObject prototype;

    public AddShapeState(DocumentModel model, GraphicalObject prototype) {
        this.model = model;
        this.prototype = prototype;
    }

    // dupliciraj zapamćeni prototip, pomakni ga na poziciju miša i dodaj u model
    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject object = prototype.duplicate();
        object.translate(mousePoint);
        model.addGraphicalObject(object);
    }

}
