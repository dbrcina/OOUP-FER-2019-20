package hr.fer.zemris.ooup.lab4.util;

public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // vraća NOVU točku translatiranu za argument tj. THIS+DP...
    public Point translate(Point dp) {
        return new Point(x + dp.x, y + dp.y);
    }

    // vraća NOVU točku koja predstavlja razliku THIS-P...
    public Point difference(Point p) {
        return new Point(x - p.x, y - p.y);
    }

}
