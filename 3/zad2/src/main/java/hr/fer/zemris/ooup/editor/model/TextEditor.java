package hr.fer.zemris.ooup.editor.model;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class TextEditor extends JComponent {

    private static final long serialVersionUID = 3669353336390971206L;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int LEFT_MARGIN = 2;
    private static final float FONT_SIZE = 20.0f;

    private final TextEditorModel model = new TextEditorModel(
            "Ovo je tekst\nkoji se nalazi\nu više r.");

    private volatile boolean showCursor = true;

    public TextEditor() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        // clear background
        g.clearRect(0, 0, getWidth(), getHeight());

        // make font size bigger
        g.setFont(g.getFont().deriveFont(FONT_SIZE));
        FontMetrics metrics = g.getFontMetrics();

        // display text on the screen
        displayText(g, metrics);

        // display cursor on the screen
        displayCursor(g, metrics);
    }

    public void toggleCursor() {
        showCursor = !showCursor;
    }

    private void displayText(Graphics g, FontMetrics metrics) {
        Iterator<String> allLinesIterator = model.allLines();
        int y = metrics.getHeight();
        while (allLinesIterator.hasNext()) {
            String line = allLinesIterator.next();
            g.drawString(line, LEFT_MARGIN, y);
            int lineWidth = metrics.stringWidth(line);
            model.setCursorLocation(LEFT_MARGIN + lineWidth, y + metrics.getDescent());
            y += metrics.getHeight();
        }
    }

    private void displayCursor(Graphics g, FontMetrics metrics) {
        if (!showCursor) return;
        Location cursorLocation = model.getCursorLocation();
        g.drawLine(cursorLocation.getX(), cursorLocation.getY() - metrics.getHeight(),
                cursorLocation.getX(), cursorLocation.getY());
    }

}
