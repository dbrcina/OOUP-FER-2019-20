package hr.fer.zemris.ooup.editor.model;

import hr.fer.zemris.ooup.editor.observer.ClipboardObserver;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class ClipboardStack<T> {

    private final Stack<T> texts = new Stack<>();
    private final List<ClipboardObserver> observers = new LinkedList<>();

    public void push(T text) {
        texts.push(text);
        notifyObservers(ClipboardObserver::updateClipboard);
    }

    public T pop() {
        T text = texts.pop();
        notifyObservers(ClipboardObserver::updateClipboard);
        return text;
    }

    public T peek() {
        return texts.peek();
    }

    public boolean isEmpty() {
        return texts.isEmpty();
    }

    public void clear() {
        texts.clear();
        notifyObservers(ClipboardObserver::updateClipboard);
    }

    public void attach(ClipboardObserver observer) {
        observers.add(observer);
    }

    public void detach(ClipboardObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Consumer<ClipboardObserver> action) {
        observers.forEach(action);
    }

}
