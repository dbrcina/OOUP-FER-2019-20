package hr.fer.zemris.ooup.editor.plugin;

import hr.fer.zemris.ooup.editor.model.ClipboardStack;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.editor.singleton.UndoManager;

public class VelikoSlovo implements Plugin {

    private final String name;

    public VelikoSlovo(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Prvo slovo svake rijeci pretvara u veliko.";
    }

    @Override
    public void execute(
            TextEditorModel model, UndoManager undoManager, ClipboardStack<String> clipboardStack) {

    }
}
