package hr.fer.zemris.ooup.lab4.util;

public class Rectangle {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle union(Rectangle r) {
        int tx1 = this.x;
        int ty1 = this.y;
        int tx2 = tx1 + this.width;
        int ty2 = ty1 + this.height;
        int rx1 = r.x;
        int ry1 = r.y;
        int rx2 = rx1 + r.width;
        int ry2 = ry1 + r.height;
        int x = Math.min(tx1, rx1);
        int y = Math.min(ty1, ry1);
        int width = Math.max(tx2, rx2);
        int height = Math.max(ty2, ry2);
        width -= x;
        height -= y;
        return new Rectangle(x, y, width, height);
    }

}
