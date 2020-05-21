package hr.fer.zemris.ooup.editor.singleton;

import hr.fer.zemris.ooup.editor.command.EditAction;

import java.util.Stack;

public class UndoManager {

    private static UndoManager manager;

    private final Stack<EditAction> undoStack = new Stack<>();
    private final Stack<EditAction> redoStack = new Stack<>();

    private UndoManager() {
    }

    public synchronized static UndoManager getInstance() {
        if (manager == null) {
            manager = new UndoManager();
        }
        return manager;
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            EditAction a = undoStack.pop();
            redoStack.push(a);
            a.executeUndo();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            EditAction a = redoStack.pop();
            undoStack.push(a);
            a.executeDo();
        }
    }

    public void push(EditAction a) {
        redoStack.clear();
        undoStack.push(a);
    }

}
