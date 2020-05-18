package hr.fer.zemris.ooup.editor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TextEditorModel {

    private static final String LINE_SEPARATOR = "\\r?\\n";

    private final List<String> lines = new ArrayList<>();
    private final LocationRange selectionRange = new LocationRange();
    private final Location cursorLocation = new Location();

    public TextEditorModel(String text) {
        lines.addAll(Arrays.asList(text.split(LINE_SEPARATOR)));
    }

    public Location getCursorLocation() {
        return cursorLocation.copy();
    }

    public void setCursorLocation(int x, int y) {
        cursorLocation.setX(x);
        cursorLocation.setY(y);
    }

    public Iterator<String> allLines() {
        return lines.iterator();
    }


}
