package hr.fer.zemris.ooup.editor.command;

import hr.fer.zemris.ooup.editor.model.Location;
import hr.fer.zemris.ooup.editor.model.LocationRange;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteRangeAction implements EditAction {

    private final TextEditorModel model;
    private final LocationRange selection;
    private final List<String> previousLines;
    private final Location previousCursorLocation;
    private final LocationRange previousSelectionRange;

    public DeleteRangeAction(TextEditorModel model, LocationRange selection) {
        this.model = model;
        this.selection = selection;
        previousLines = new ArrayList<>(model.getLines());
        previousCursorLocation = model.getCursorLocation();
        previousSelectionRange = model.getSelectionRange();
    }

    @Override
    public void executeDo() {
        model.selectedText(selection, true);
    }

    @Override
    public void executeUndo() {
        model.setCursorLocation(previousCursorLocation);
        model.setSelectionRange(previousSelectionRange);
        model.setLines(previousLines);
    }

}
