package hr.fer.zemris.ooup.lab4.state;

import hr.fer.zemris.ooup.lab4.model.GraphicalObject;
import hr.fer.zemris.ooup.lab4.renderer.Renderer;
import hr.fer.zemris.ooup.lab4.util.Point;

public interface State {

    // poziva se kad progam registrira da je pritisnuta lijeva tipka miša
    void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown);
    // poziva se kad progam registrira da je otpuštena lijeva tipka miša
    void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown);
    // poziva se kad progam registrira da korisnik pomiče miš dok je tipka pritisnuta
    void mouseDragged(Point mousePoint);
    // poziva se kad progam registrira da je korisnik pritisnuo tipku na tipkovnici
    void keyPressed(int keyCode);

    // Poziva se nakon što je platno nacrtalo grafički objekt predan kao argument
    void afterDraw(Renderer r, GraphicalObject go);
    // Poziva se nakon što je platno nacrtalo čitav crtež
    void afterDraw(Renderer r);

    // Poziva se kada program napušta ovo stanje kako bi prešlo u neko drugo
    void onLeaving();

}
