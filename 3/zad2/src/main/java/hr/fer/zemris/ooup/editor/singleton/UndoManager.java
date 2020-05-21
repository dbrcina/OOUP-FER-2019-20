package hr.fer.zemris.ooup.editor.singleton;

import hr.fer.zemris.ooup.editor.command.EditAction;
import hr.fer.zemris.ooup.editor.observer.UndoManagerObserver;
import hr.fer.zemris.ooup.editor.observer.UndoManagerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class UndoManager {

    private static UndoManager manager;

    private final Stack<EditAction> undoStack = new Stack<>();
    private final Stack<EditAction> redoStack = new Stack<>();
    private final List<UndoManagerObserver> undoStackObservers = new ArrayList<>();
    private final List<UndoManagerObserver> redoStackObservers = new ArrayList<>();

    private UndoManager() {
    }

    public static UndoManager getInstance() {
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
            notifyRedoStackObserver(obs -> obs.updatedUndoManager(UndoManagerState.STACK_NOT_EMPTY));
            if (undoStack.isEmpty()) {
                notifyUndoStackObserver(obs -> obs.updatedUndoManager(UndoManagerState.STACK_EMPTY));
            }
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            EditAction a = redoStack.pop();
            undoStack.push(a);
            a.executeDo();
            notifyUndoStackObserver(obs -> obs.updatedUndoManager(UndoManagerState.STACK_NOT_EMPTY));
            if (redoStack.isEmpty()) {
                notifyRedoStackObserver(obs -> obs.updatedUndoManager(UndoManagerState.STACK_EMPTY));
            }
        }
    }

    public void push(EditAction a) {
        redoStack.clear();
        undoStack.push(a);
        notifyRedoStackObserver(obs -> obs.updatedUndoManager(UndoManagerState.STACK_EMPTY));
        notifyUndoStackObserver(obs -> obs.updatedUndoManager(UndoManagerState.STACK_NOT_EMPTY));
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
        notifyUndoStackObserver(obs -> obs.updatedUndoManager(UndoManagerState.STACK_EMPTY));
        notifyRedoStackObserver(obs -> obs.updatedUndoManager(UndoManagerState.STACK_EMPTY));
    }

    public void attachUndoStackObserver(UndoManagerObserver observer) {
        undoStackObservers.add(observer);
    }

    public void detachUndoStackObserver(UndoManagerObserver observer) {
        undoStackObservers.remove(observer);
    }

    private void notifyUndoStackObserver(Consumer<UndoManagerObserver> action) {
        undoStackObservers.forEach(action);
    }

    public void attachRedoStackObserver(UndoManagerObserver observer) {
        redoStackObservers.add(observer);
    }

    public void detachRedoStackObserver(UndoManagerObserver observer) {
        redoStackObservers.remove(observer);
    }

    private void notifyRedoStackObserver(Consumer<UndoManagerObserver> action) {
        redoStackObservers.forEach(action);
    }

}
