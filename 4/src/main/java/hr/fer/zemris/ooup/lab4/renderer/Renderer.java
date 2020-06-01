package hr.fer.zemris.ooup.lab4.renderer;

import hr.fer.zemris.ooup.lab4.util.Point;

public interface Renderer {

    void drawLine(Point s, Point e);

    void fillPolygon(Point[] points);

}
