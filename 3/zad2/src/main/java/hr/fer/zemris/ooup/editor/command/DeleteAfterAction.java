package hr.fer.zemris.ooup.editor.command;

import hr.fer.zemris.ooup.editor.model.Location;
import hr.fer.zemris.ooup.editor.model.LocationRange;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteAfterAction implements EditAction {

    private final TextEditorModel model;
    private final List<String> previousLines;
    private final Location previousCursorLocation;
    private final LocationRange previousSelectionRange;

    public DeleteAfterAction(TextEditorModel model) {
        this.model = model;
        previousLines = new ArrayList<>(model.getLines());
        previousCursorLocation = model.getCursorLocation();
        previousSelectionRange = model.getSelectionRange();
    }

    @Override
    public void executeDo() {
        Location cursorLocation = model.getCursorLocation();
        List<String> lines = model.getLines();
        int row = cursorLocation.getRow();
        int column = cursorLocation.getColumn();
        String line = lines.get(row);
        if (row == lines.size() - 1 && column == line.length() - 1) return;
        if (column == line.length() - 1) {
            lines.set(row, line + lines.remove(row + 1));
        } else {
            lines.set(row, line.substring(0, column + 1) + line.substring(column + 2));
        }
        model.setCursorLocation(cursorLocation);
    }

    @Override
    public void executeUndo() {
        model.setCursorLocation(previousCursorLocation);
        model.setSelectionRange(previousSelectionRange);
        model.setLines(previousLines);
    }

}
