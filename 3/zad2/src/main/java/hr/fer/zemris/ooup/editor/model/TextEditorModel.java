package hr.fer.zemris.ooup.editor.model;

import hr.fer.zemris.ooup.editor.observer.CursorObserver;

import java.util.*;
import java.util.function.Consumer;

public class TextEditorModel {

    private final TextEditor editor;
    private final List<String> lines = new ArrayList<>();
    private final LocationRange selectionRange = new LocationRange();
    private Location cursorLocation = new Location();
    private int cursorSize;
    private final List<CursorObserver> cursorObservers = new LinkedList<>();

    public TextEditorModel(TextEditor editor, String text) {
        this.editor = editor;
        lines.addAll(Arrays.asList(text.split("\\r?\\n")));
    }

    public Location getCursorLocation() {
        return cursorLocation.copy();
    }

    public void setCursorLocation(Location location) {
        cursorLocation = location.copy();
    }

    public int getCursorSize() {
        return cursorSize;
    }

    public void setCursorSize(int cursorSize) {
        this.cursorSize = cursorSize;
    }

    public void attach(CursorObserver observer) {
        cursorObservers.add(observer);
    }

    public void detach(CursorObserver observer) {
        cursorObservers.remove(observer);
    }

    public void moveCursorLeft() {
        int a = editor.getGraphics().getFontMetrics().charWidth('a');
        Location loc = cursorLocation.copy();
        if (loc.getX() - a < 0) {
            if (loc.getY() - cursorSize == 0) {
                return; // cursor is in the first row at the start
            }
        } else {
            loc.setX(loc.getX() - a);
            notifyObservers(obs -> obs.updateCursorLocation(loc));
        }
    }

    public void moveCursorRight() {
        int x = cursorLocation.getX() + 1;
        if (x < editor.getWidth()) {
            cursorLocation.setX(x);
            notifyObservers(obs -> obs.updateCursorLocation(cursorLocation.copy()));
        }
    }

    public void moveCursorUp() {

    }

    public void moveCursorDown() {

    }

    private int calculateLineNumber() {
        return cursorLocation.getY() / cursorSize - 1;
    }

    private void notifyObservers(Consumer<CursorObserver> action) {
        cursorObservers.forEach(action);
    }

    public Iterator<String> allLines() {
        return new MyIterator(lines);
    }

    public Iterator<String> linesRange(int from, int to) {
        return new MyIterator(lines, from, to);
    }

    private static class MyIterator implements Iterator<String> {
        private final List<String> lines;
        private final int to;
        private int currentIndex;

        private MyIterator(List<String> lines, int from, int to) {
            this.lines = lines;
            this.to = to;
            this.currentIndex = from;
        }

        private MyIterator(List<String> lines) {
            this(lines, 0, lines.size());
        }

        @Override
        public boolean hasNext() {
            return currentIndex != to;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return lines.get(currentIndex++);
        }

    }
}
