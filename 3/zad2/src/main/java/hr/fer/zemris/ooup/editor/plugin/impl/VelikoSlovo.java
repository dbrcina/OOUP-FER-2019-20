package hr.fer.zemris.ooup.editor.plugin.impl;

import hr.fer.zemris.ooup.editor.command.EditAction;
import hr.fer.zemris.ooup.editor.command.ToUpperAction;
import hr.fer.zemris.ooup.editor.model.ClipboardStack;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.editor.plugin.Plugin;
import hr.fer.zemris.ooup.editor.singleton.UndoManager;

public class VelikoSlovo implements Plugin {

    private final String name = "Upper case";

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
        EditAction a = new ToUpperAction(model);
        a.executeDo();
        undoManager.push(a);
    }

}
