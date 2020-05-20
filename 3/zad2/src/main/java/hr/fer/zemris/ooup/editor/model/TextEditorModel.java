package hr.fer.zemris.ooup.editor.model;

import hr.fer.zemris.ooup.editor.observer.CursorObserver;
import hr.fer.zemris.ooup.editor.observer.TextObserver;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class TextEditorModel {

    /* List of lines of text */
    private final List<String> lines = new CopyOnWriteArrayList<>();
    /* --------------------- */

    /* Cursors data */
    private final Location cursorLocation = new Location();
    private int cursorSize;
    private final LocationRange selectionRange = new LocationRange();
    /* ------------ */

    /* List of observers */
    private final List<CursorObserver> cursorObservers = new LinkedList<>();
    private final List<TextObserver> textObservers = new LinkedList<>();
    /* ----------------- */

    /* ################################################ */

    /* Constructor */
    public TextEditorModel(String text) {
        lines.addAll(Arrays.asList(text.split("\\r?\\n")));
    }
    /* ----------- */

    /* ################################################ */

    /* Methods used for cursor manipulation */
    public Location getCursorLocation() {
        return cursorLocation.copy();
    }

    public void setCursorLocation(Location location) {
        cursorLocation.setRow(location.getRow());
        cursorLocation.setColumn(location.getColumn());
    }

    public int getCursorSize() {
        return cursorSize;
    }

    public void setCursorSize(int cursorSize) {
        this.cursorSize = cursorSize;
    }

    public void moveCursorLeft() {
        int row = cursorLocation.getRow();
        int column = cursorLocation.getColumn();
        if (row == 0 && column < 0) return;
        if (column >= 0) {
            column--;
        } else {
            row--;
            column = lines.get(row).length() - 1;
        }
        Location location = new Location(row, column);
        notifyCursorObservers(obs -> obs.updateCursorLocation(location));
    }

    public void moveCursorRight() {
        int row = cursorLocation.getRow();
        int column = cursorLocation.getColumn();
        int size = lines.get(row).length() - 1;
        if (row == lines.size() - 1 && column == size) return;
        if (column < size) {
            column++;
        } else {
            row++;
            column = -1;
        }
        Location location = new Location(row, column);
        notifyCursorObservers(obs -> obs.updateCursorLocation(location));
    }

    public void moveCursorUp() {
        int row = cursorLocation.getRow();
        int column = cursorLocation.getColumn();
        if (row == 0) return;
        row--;
        int size = lines.get(row).length() - 1;
        if (column >= size) column = size;
        Location location = new Location(row, column);
        notifyCursorObservers(obs -> obs.updateCursorLocation(location));
    }

    public void moveCursorDown() {
        int row = cursorLocation.getRow();
        int column = cursorLocation.getColumn();
        if (row == lines.size() - 1) return;
        row++;
        int size = lines.get(row).length() - 1;
        if (column >= size) column = size;
        Location location = new Location(row, column);
        notifyCursorObservers(obs -> obs.updateCursorLocation(location));
    }
    /* ------------------------------------ */

    /* ################################################ */

    /* Methods used for text manipulation */
    public void deleteAfter() {
        int row = cursorLocation.getRow();
        int column = cursorLocation.getColumn();
        String line = lines.get(row);
        if (row == lines.size() - 1 && column == line.length() - 1) return;
        if (column == line.length() - 1) {
            lines.set(row, line + lines.remove(row + 1));
        } else {
            lines.set(row, line.substring(0, column + 1) + line.substring(column + 2));
        }
        notifyTextObservers(TextObserver::updateText);
    }

    /* ---------------------------------- */
    public void deleteBefore() {
        int row = cursorLocation.getRow();
        int column = cursorLocation.getColumn();
        if (row == 0 && column == -1) return;
        String line = lines.get(row);
        if (column == -1) {
            String lineBefore = lines.get(row - 1);
            lines.set(row - 1, lineBefore + lines.remove(row));
            cursorLocation.setRow(row - 1);
            cursorLocation.setColumn(lineBefore.length() - 1);
        } else {
            lines.set(row, line.substring(0, column) + line.substring(column + 1));
            cursorLocation.setColumn(column - 1);
        }
        notifyTextObservers(TextObserver::updateText);
    }

    public void deleteRange(LocationRange range) {
        Location start = range.getStart();
        Location end = range.getEnd();
        int rowStart = start.getRow();
        int rowEnd = end.getRow();
        if (rowStart > rowEnd) {
            int temp = rowStart;
            rowStart = rowEnd;
            rowEnd = temp;
        }
        int columnStart = start.getColumn();
        int columnEnd = end.getColumn();
        if (columnStart > columnEnd) {
            int temp = columnStart;
            columnStart = columnEnd;
            columnEnd = temp;
        }
        if (rowStart == rowEnd) {
            String line = lines.get(rowStart);
            lines.set(rowStart, line.substring(0, columnStart + 1) + line.substring(columnEnd + 1));
        } else {
            List<Integer> rows = new ArrayList<>();
            for (int row = rowStart; row <= rowEnd; row++) {
                String line = lines.get(row);
                if (row == rowStart) {
                    lines.set(row, line.substring(0, columnStart + 1));
                } else if (row == rowEnd) {
                    lines.set(row, line.substring(columnEnd + 1));
                    if (lines.get(row).isEmpty()) rows.add(row);
                } else {
                    rows.add(row);
                }
            }
            for (int i = rows.size() - 1; i >= 0; i--) {
                lines.remove(rows.get(i).intValue());
            }
        }
        cursorLocation.setRow(rowStart);
        cursorLocation.setColumn(columnStart);
        notifyTextObservers(TextObserver::updateText);
    }

    public LocationRange getSelectionRange() {
        return selectionRange.copy();
    }

    public void setSelectionRange(LocationRange range) {
        selectionRange.setStart(range.getStart());
        selectionRange.setEnd(range.getEnd());
    }
    /* ################################################ */

    /* Methods for observer manipulation */
    public void attachCursorObserver(CursorObserver observer) {
        cursorObservers.add(observer);
    }

    public void detachCursorObserver(CursorObserver observer) {
        cursorObservers.remove(observer);
    }

    private void notifyCursorObservers(Consumer<CursorObserver> action) {
        cursorObservers.forEach(action);
    }

    public void attachTextObserver(TextObserver observer) {
        textObservers.add(observer);
    }

    public void detachTextObserver(TextObserver observer) {
        textObservers.remove(observer);
    }

    private void notifyTextObservers(Consumer<TextObserver> action) {
        textObservers.forEach(action);
    }
    /* ---------------------------------- */

    /* ################################################ */

    /* Methods for iterating over list of lines */
    public List<String> getLines() {
        return lines;
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
    /* --------------------------------------------- */

}
