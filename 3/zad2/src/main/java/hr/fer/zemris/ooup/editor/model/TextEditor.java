package hr.fer.zemris.ooup.editor.model;

import hr.fer.zemris.ooup.editor.command.ClearDocumentAction;
import hr.fer.zemris.ooup.editor.command.EditAction;
import hr.fer.zemris.ooup.editor.observer.*;
import hr.fer.zemris.ooup.editor.singleton.UndoManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static java.awt.event.KeyEvent.*;

public class TextEditor extends JComponent implements CursorObserver, TextObserver {

    private static final long serialVersionUID = 3669353336390971206L;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int LEFT_MARGIN = 3;
    private static final Font defaultFont = new Font("Monospaced", Font.PLAIN, 20);
    private static final List<Integer> cursorKeyCodes = List.of(VK_LEFT, VK_RIGHT, VK_UP, VK_DOWN);
    private static final List<Integer> deletionKeyCodes = List.of(VK_DELETE, VK_BACK_SPACE);

    private final TextEditorModel model;
    private boolean initialRun = true;
    private boolean blinkCursor = true;
    private boolean showCursorWithoutBlinking = false;
    private boolean shiftPressed = false;
    private final LocationRange selectionRange = new LocationRange();
    private final ClipboardStack<String> clipboard = new ClipboardStack<>();
    private final List<TextEditorObserver> editorObservers = new ArrayList<>();

    public TextEditor() {
        this("Ovo je tekst\nkoji se nalazi\nu više r.");
    }

    public TextEditor(String text) {
        this.model = new TextEditorModel(text);
        setPreferredSize(new Dimension(WIDTH - LEFT_MARGIN, HEIGHT - LEFT_MARGIN));
        setBorder(BorderFactory.createEmptyBorder(0, LEFT_MARGIN, 0, 0));
        setFocusable(true);
        setFont(defaultFont);
        model.attachCursorObserver(this);
        model.attachTextObserver(this);
        initKeyListener();
    }

    public void copy() {
        copyCut(false);
    }

    public void cut() {
        copyCut(true);
    }

    private void copyCut(boolean remove) {
        if (selectionRange.equals(new LocationRange())) return;
        LocationRange r = selectionRange.copy();
        selectionRange.setStart(new Location());
        selectionRange.setEnd(new Location());
        List<String> selected = model.selectedText(r, remove);
        StringBuilder sb = new StringBuilder();
        selected.forEach(s -> sb.append(s).append("\n"));
        sb.setLength(sb.length() - 1);
        clipboard.push(sb.toString());
    }

    public void paste() {
        pastePasteAndTake(false);
    }

    public void pasteAndTake() {
        pastePasteAndTake(true);
    }

    private void pastePasteAndTake(boolean remove) {
        if (clipboard.isEmpty()) return;
        model.insert(remove ? clipboard.pop() : clipboard.peek());
    }

    public void deleteSelection() {
        LocationRange r = selectionRange.copy();
        selectionRange.setStart(new Location());
        selectionRange.setEnd(new Location());
        model.deleteRange(r);
    }

    private void initKeyListener() {
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == VK_SHIFT) {
                    selectionRange.setStart(model.getCursorLocation());
                    shiftPressed = true;
                } else if (cursorKeyCodes.contains(code)) {
                    showCursorWithoutBlinking = true;
                    if (code == VK_LEFT) model.moveCursorLeft();
                    else if (code == VK_RIGHT) model.moveCursorRight();
                    else if (code == VK_UP) model.moveCursorUp();
                    else model.moveCursorDown();
                } else if (deletionKeyCodes.contains(code)) {
                    if (!model.getSelectionRange().equals(new LocationRange())) {
                        LocationRange r = selectionRange.copy();
                        selectionRange.setStart(new Location());
                        selectionRange.setEnd(new Location());
                        model.deleteRange(r);
                    } else if (code == VK_DELETE) model.deleteAfter();
                    else model.deleteBefore();
                } else if (code == VK_ENTER) {
                    showCursorWithoutBlinking = true;
                } else if (String.valueOf(e.getKeyChar()).matches("\\w+")) {
                    model.setSelectionRange(selectionRange);
                    selectionRange.setStart(new Location());
                    selectionRange.setEnd(new Location());
                    model.insert(e.getKeyChar());
                }
            }

            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == VK_SHIFT) shiftPressed = false;
                else if (cursorKeyCodes.contains(code) || code == VK_ENTER)
                    showCursorWithoutBlinking = false;
            }
        });
    }

    @Override
    public void updateCursorLocation(Location location) {
        model.setCursorLocation(location);
        if (shiftPressed) {
            selectionRange.setEnd(model.getCursorLocation());
            model.setSelectionRange(selectionRange);
        } else {
            model.setSelectionRange(new LocationRange());
        }
        SwingUtilities.invokeLater(this::repaint);
    }

    @Override
    public void updateText() {
        if (selectionRange.equals(new LocationRange())) {
            model.setSelectionRange(new LocationRange());
        }
        SwingUtilities.invokeLater(this::repaint);
    }

    public void toggleBlinkCursor() {
        blinkCursor = !blinkCursor;
    }

    public void reset(List<String> lines) {
        blinkCursor = true;
        showCursorWithoutBlinking = false;
        shiftPressed = false;
        selectionRange.setStart(new Location());
        selectionRange.setEnd(new Location());
        EditAction a = new ClearDocumentAction(model, lines);
        a.executeDo();
        UndoManager.getInstance().push(a);
        int row = lines.isEmpty() ? 0 : lines.size() - 1;
        int column = lines.isEmpty() ? -1 : lines.get(row).length() - 1;
        model.setCursorLocation(new Location(row, column));
        clipboard.clear();
    }

    public void save(Path file) throws IOException {
        List<String> lines = model.getLines();
        Files.write(file, lines);
    }

    public void attach(TextEditorObserver observer) {
        editorObservers.add(observer);
    }

    private void notifyObservers(Consumer<TextEditorObserver> action) {
        editorObservers.forEach(action);
    }

    public void attachCursorObs(CursorObserver observer) {
        model.attachCursorObserver(observer);
    }

    public void attachSelectionObs(SelectionObserver observer) {
        model.attachSelectionObserver(observer);
    }

    public void attachClipboardObs(ClipboardObserver observer) {
        clipboard.attach(observer);
    }

    public void pageStart() {
        model.moveCursorStart();
    }

    public void pageEnd() {
        model.moveCursorEnd();
    }

    /* ############################################ */

    /* Next block of code is used for panting the component */
    @Override
    protected void paintComponent(Graphics g) {
        // clear background
        g.clearRect(0, 0, getWidth() + LEFT_MARGIN, getHeight() + LEFT_MARGIN);
        // transform graphics
        g = g.create(LEFT_MARGIN, 0, getWidth(), getHeight());
        FontMetrics metrics = g.getFontMetrics();
        // display text on the screen
        displayText(g, metrics);
        // display cursor on the screen
        displayCursor(g, metrics);
    }

    private void displayText(Graphics g, FontMetrics metrics) {
        if (model.getLines().isEmpty()) return;
        Iterator<String> allLinesIterator = model.allLines();
        int y = 0;
        int row = 0;
        LocationRange selectionRange = model.getSelectionRange();
        Location start = selectionRange.getStart();
        Location end = selectionRange.getEnd();
        while (allLinesIterator.hasNext()) {
            String line = allLinesIterator.next();
            if (!selectionRange.equals(new LocationRange()) && !line.isEmpty()) {
                drawSelection(g, metrics, line, start, end, row, y + metrics.getDescent());
            }
            y += metrics.getHeight();
            g.drawString(line, 0, y);
            row++;
        }
        if (initialRun) {
            initialRun = false;
            row -= 1;
            int column = model.getLines().get(row).length() - 1;
            model.setCursorLocation(new Location(row, column));
            notifyObservers(obs -> obs.componentShown(model.getCursorLocation()));
        }
    }

    private void drawSelection(
            Graphics g, FontMetrics metrics, String line, Location start, Location end, int row, int y) {
        int columnStart = 0;
        int columnEnd = 0;
        if (start.getRow() == row && end.getRow() == row) {
            columnStart = start.getColumn();
            columnEnd = end.getColumn();
        } else if (start.getRow() == row) {
            columnStart = start.getColumn();
            if (row > end.getRow()) {
                columnEnd = -1;
            } else {
                columnEnd = line.length() - 1;
            }
        } else if (end.getRow() == row) {
            columnStart = end.getColumn();
            if (row > start.getRow()) {
                columnEnd = -1;
            } else {
                columnEnd = line.length() - 1;
            }
        } else if (row > start.getRow() && row < end.getRow()
                || row < start.getRow() && row > end.getRow()) {
            columnStart = -1;
            columnEnd = line.length() - 1;
        }
        if (columnStart > columnEnd) {
            int temp = columnStart;
            columnStart = columnEnd;
            columnEnd = temp;
        }
        int x = metrics.stringWidth(line.substring(0, columnStart + 1));
        int width = metrics.stringWidth(line.substring(columnStart + 1, columnEnd + 1));
        int height = metrics.getHeight();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
    }

    private void displayCursor(Graphics g, FontMetrics metrics) {
        if (!showCursorWithoutBlinking) {
            if (!blinkCursor) return;
        }
        List<String> lines = model.getLines();
        model.setCursorSize(metrics.getHeight());
        Location cursorLocation = model.getCursorLocation();
        // map rows and columns to the screen coordinates
        int y1 = (cursorLocation.getRow() + 1) * metrics.getHeight() + metrics.getDescent();
        int y2 = y1 - model.getCursorSize();
        if (lines.isEmpty()) {
            g.drawLine(0, y1, 0, y2);
        } else {
            int row = cursorLocation.getRow();
            int x1 = metrics.stringWidth(lines.get(row).substring(0, cursorLocation.getColumn() + 1));
            g.drawLine(x1, y1, x1, y2);
        }
    }

}
