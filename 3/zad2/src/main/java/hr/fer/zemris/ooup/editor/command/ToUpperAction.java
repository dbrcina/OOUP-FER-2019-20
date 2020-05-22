package hr.fer.zemris.ooup.editor.command;

import hr.fer.zemris.ooup.editor.model.Location;
import hr.fer.zemris.ooup.editor.model.LocationRange;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.editor.observer.TextObserver;

import java.util.ArrayList;
import java.util.List;

public class ToUpperAction implements EditAction {

    private final TextEditorModel model;
    private final List<String> previousLines;
    private final Location previousCursorLocation;
    private final LocationRange previousSelectionRange;

    public ToUpperAction(TextEditorModel model) {
        this.model = model;
        previousLines = new ArrayList<>(model.getLines());
        previousCursorLocation = model.getCursorLocation();
        previousSelectionRange = model.getSelectionRange();
    }

    @Override
    public void executeDo() {
        List<String> lines = model.getLines();
        List<String> newLines = new ArrayList<>();
        String sep = " ,.-;";
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            boolean toCap = true;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (toCap)
                    sb.append(Character.toUpperCase(c));
                else
                    sb.append(Character.toLowerCase(c));

                toCap = sep.indexOf(c) >= 0;
            }
            newLines.add(sb.toString());
            sb.setLength(0);
        }
        model.setLines(newLines);
    }

    @Override
    public void executeUndo() {
        model.setCursorLocation(previousCursorLocation);
        model.setSelectionRange(previousSelectionRange);
        model.setLines(previousLines);
    }

}
