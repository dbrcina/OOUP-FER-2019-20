package hr.fer.zemris.ooup.editor.command;

import hr.fer.zemris.ooup.editor.model.Location;
import hr.fer.zemris.ooup.editor.model.LocationRange;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;

import java.util.ArrayList;
import java.util.List;

public class ClearDocumentAction implements EditAction {

    private final TextEditorModel model;
    private final List<String> lines;
    private final List<String> previousLines;
    private final Location previousCursorLocation;
    private final LocationRange previousSelectionRange;

    public ClearDocumentAction(TextEditorModel model, List<String> lines) {
        this.model = model;
        this.lines = lines;
        previousLines = new ArrayList<>(model.getLines());
        previousCursorLocation = model.getCursorLocation();
        previousSelectionRange = model.getSelectionRange();
    }

    @Override
    public void executeDo() {
        model.setLines(lines);
    }

    @Override
    public void executeUndo() {
        model.setCursorLocation(previousCursorLocation);
        model.setSelectionRange(previousSelectionRange);
        model.setLines(previousLines);
    }

}
