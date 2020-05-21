package hr.fer.zemris.ooup.editor.command;

import hr.fer.zemris.ooup.editor.model.Location;
import hr.fer.zemris.ooup.editor.model.LocationRange;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteBeforeAction implements EditAction {

    private final TextEditorModel model;
    private final List<String> previousLines;
    private final Location previousCursorLocation;
    private final LocationRange previousSelectionRange;

    public DeleteBeforeAction(TextEditorModel model) {
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
        model.setCursorLocation(cursorLocation);
    }

    @Override
    public void executeUndo() {
        model.setCursorLocation(previousCursorLocation);
        model.setSelectionRange(previousSelectionRange);
        model.setLines(previousLines);
    }

}
