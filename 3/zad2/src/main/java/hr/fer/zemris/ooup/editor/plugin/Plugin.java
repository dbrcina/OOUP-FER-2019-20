package hr.fer.zemris.ooup.editor.plugin;

import hr.fer.zemris.ooup.editor.model.ClipboardStack;
import hr.fer.zemris.ooup.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.editor.singleton.UndoManager;

public interface Plugin {

    String getName();

    String getDescription();

    void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack<String> clipboardStack);

}
