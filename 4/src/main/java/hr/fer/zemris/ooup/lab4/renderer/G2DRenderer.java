package hr.fer.zemris.ooup.lab4.renderer;

import hr.fer.zemris.ooup.lab4.util.Point;

import java.awt.*;

public class G2DRenderer implements Renderer {

    private final Graphics2D g2d;

    public G2DRenderer(Graphics2D g2d) {
        this.g2d = g2d;
    }

    // Postavi boju na plavu
    // Nacrtaj linijski segment od S do E
    // (sve to uporabom g2d dobivenog u konstruktoru)
    @Override
    public void drawLine(Point s, Point e) {
        Color initialColor = g2d.getColor();
        g2d.setColor(Color.BLUE);
        g2d.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
        g2d.setColor(initialColor);
    }

    // Postavi boju na plavu
    // Popuni poligon definiran danim točkama
    // Postavi boju na crvenu
    // Nacrtaj rub poligona definiranog danim točkama
    // (sve to uporabom g2d dobivenog u konstruktoru)
    @Override
    public void fillPolygon(Point[] points) {
        Color initialColor = g2d.getColor();
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        int nPoints = points.length;
        for (int i = 0; i < nPoints; i++) {
            xPoints[i] = points[i].getX();
            yPoints[i] = points[i].getY();
        }
        g2d.setColor(Color.BLUE);
        g2d.fillPolygon(xPoints, yPoints, nPoints);
        g2d.setColor(Color.RED);
        g2d.drawPolygon(xPoints, yPoints, nPoints);
        g2d.setColor(initialColor);
    }

}
