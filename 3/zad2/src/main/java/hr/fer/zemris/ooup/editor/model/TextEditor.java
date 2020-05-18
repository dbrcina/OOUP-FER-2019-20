package hr.fer.zemris.ooup.editor.model;

import hr.fer.zemris.ooup.editor.observer.CursorObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class TextEditor extends JComponent implements CursorObserver {

    private static final long serialVersionUID = 3669353336390971206L;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int LEFT_MARGIN = 3;
    private static final float FONT_SIZE = 20.0f;

    private final TextEditorModel model = new TextEditorModel(this,
            "Ovo je tekst\nkoji se nalazi\nu više r.");
    private boolean showCursor = true;
    private boolean initialRun = true;

    public TextEditor() {
        setPreferredSize(new Dimension(WIDTH - LEFT_MARGIN, HEIGHT - LEFT_MARGIN));
        setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
        setFocusable(true);
        model.attach(this);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_LEFT) model.moveCursorLeft();
                else if (code == KeyEvent.VK_RIGHT) model.moveCursorRight();
                else if (code == KeyEvent.VK_UP) model.moveCursorUp();
                else if (code == KeyEvent.VK_DOWN) model.moveCursorDown();
            }
        });
    }

    @Override
    public void updateCursorLocation(Location loc) {
        model.setCursorLocation(loc);
        SwingUtilities.invokeLater(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // clear background
        g.clearRect(0, 0, getWidth() + LEFT_MARGIN, getHeight() + LEFT_MARGIN);
        // transform graphics
        g = g.create(LEFT_MARGIN, 0, getWidth(), getHeight());
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
            g.drawString(line, 0, y);
            if (initialRun) {
                int lineWidth = metrics.stringWidth(line);
                model.setCursorLocation(new Location(lineWidth, y + metrics.getDescent()));
            }
            y += metrics.getHeight();
        }
        if (initialRun) initialRun = false;
    }

    private void displayCursor(Graphics g, FontMetrics metrics) {
        if (!showCursor) return;
        model.setCursorSize(metrics.getHeight());
        Location cursorLocation = model.getCursorLocation();
        g.drawLine(cursorLocation.getX(), cursorLocation.getY() - model.getCursorSize(),
                cursorLocation.getX(), cursorLocation.getY());
    }

}
