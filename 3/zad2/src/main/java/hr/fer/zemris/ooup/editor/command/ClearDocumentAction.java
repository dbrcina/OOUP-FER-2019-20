package hr.fer.zemris.ooup.editor.command;

import hr.fer.zemris.ooup.editor.model.Location;
import hr.fer.zemris.ooup.editor.model.LocationRange;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.editor.observer.TextObserver;

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
        int row = lines.isEmpty() ? 0 : lines.size() - 1;
        int column = lines.isEmpty() ? -1 : lines.get(row).length() - 1;
        model.setLines(lines);
        model.notifyCursorObservers(obs -> obs.updateCursorLocation(new Location(row, column)));
        model.notifyTextObservers(TextObserver::updateText);
    }

    @Override
    public void executeUndo() {
        model.setCursorLocation(previousCursorLocation);
        model.setSelectionRange(previousSelectionRange);
        model.setLines(previousLines);
        model.notifyCursorObservers(obs -> obs.updateCursorLocation(previousCursorLocation));
    }

}
