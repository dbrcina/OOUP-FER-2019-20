package hr.fer.zemris.ooup.lab4.renderer;

import hr.fer.zemris.ooup.lab4.util.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SVGRenderer implements Renderer {

    private final List<String> lines = new ArrayList<>();
    private final String fileName;

    // zapamti fileName; u lines dodaj zaglavlje SVG dokumenta:
    // <svg xmlns=... >
    // ..
    public SVGRenderer(String fileName) {
        this.fileName = fileName;
        lines.add("<svg xmlns=\"http://www.w3.org/2000/svg\" " +
                "xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n");
    }

    // u lines još dodaj završni tag SVG dokumenta: </svg>
    // sve retke u listi lines zapiši na disk u datoteku
    // ...
    public void close() throws IOException {
        lines.add("\n</svg>");
        Files.write(Paths.get(fileName), lines);
    }

    // Dodaj u lines redak koji definira linijski segment:
    // <line ... />
    @Override
    public void drawLine(Point s, Point e) {
        lines.add(String.format(
                "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#0000FF;\"/>",
                s.getX(), s.getY(), e.getX(), e.getY()));
    }

    // Dodaj u lines redak koji definira popunjeni poligon:
    // <polygon points="..." style="stroke: ...; fill: ...;" />
    @Override
    public void fillPolygon(Point[] points) {
        StringBuilder sb = new StringBuilder();
        for (Point point : points) {
            sb.append(point.getX()).append(",").append(point.getY()).append(" ");
        }
        sb.setLength(sb.length() - 1);
        lines.add("<polygon points=\"" + sb.toString() + "\" style=\"stroke:#FF0000; fill:#0000FF;\"/>");
    }

}
