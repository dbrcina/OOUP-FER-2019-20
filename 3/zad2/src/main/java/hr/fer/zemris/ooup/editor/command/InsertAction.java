package hr.fer.zemris.ooup.editor.command;

import hr.fer.zemris.ooup.editor.model.Location;
import hr.fer.zemris.ooup.editor.model.LocationRange;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;

import java.util.ArrayList;
import java.util.List;

public class InsertAction implements EditAction {

    private final TextEditorModel model;
    private final String text;
    private final List<String> previousLines;
    private final Location previousCursorLocation;
    private final LocationRange previousSelectionRange;

    private List<String> deleted;

    public InsertAction(TextEditorModel model, String text) {
        this.model = model;
        this.text = text;
        previousLines = new ArrayList<>(model.getLines());
        previousCursorLocation = model.getCursorLocation();
        previousSelectionRange = model.getSelectionRange();
    }

    @Override
    public void executeDo() {
        deleted = model.insert(text.toCharArray());
    }

    @Override
    public void executeUndo() {
        model.setCursorLocation(previousCursorLocation);
        model.setSelectionRange(previousSelectionRange);
        model.setLines(previousLines);
    }

}
