package hr.fer.zemris.ooup.lab4.util;

public class GeometryUtil {

    // izračunaj euklidsku udaljenost između dvije točke ...
    public static double distanceFromPoint(Point point1, Point point2) {
        Point difference = point1.difference(point2);
        int xDiff = difference.getX();
        int yDiff = difference.getY();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    // Izračunaj koliko je točka P udaljena od linijskog segmenta određenog
    // početnom točkom S i završnom točkom E. Uočite: ako je točka P iznad/ispod
    // tog segmenta, ova udaljenost je udaljenost okomice spuštene iz P na S-E.
    // Ako je točka P "prije" točke S ili "iza" točke E, udaljenost odgovara
    // udaljenosti od P do početne/konačne točke segmenta.
    public static double distanceFromLineSegment(Point s, Point e, Point p) {
        double distance;
        int xMin = Math.min(s.getX(), e.getX());
        int xMax = Math.max(s.getX(), e.getX());
        if (p.getX() >= xMin && p.getX() <= xMax) {
            int a = e.getY() - s.getY();
            int b = e.getX() - s.getX();
            int c = e.getX() * s.getY() - e.getY() * s.getX();
            distance = Math.abs(a * p.getX() - b * p.getY() + c) / Math.sqrt(a * a + b * b);
        } else {
            distance = Math.min(distanceFromPoint(s, p), distanceFromPoint(e, p));
        }
        return distance;
    }

}
