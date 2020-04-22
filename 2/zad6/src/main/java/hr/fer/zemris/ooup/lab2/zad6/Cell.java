package hr.fer.zemris.ooup.lab2.zad6;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class Cell implements CellListener {

    private final Sheet sheet;
    private final String name;
    private String exp;
    private int value;

    private final Collection<CellListener> listeners = new HashSet<>();

    public Cell(Sheet sheet, int row, int column) {
        this.sheet = sheet;
        name = Character.toString('A' + column) + (row + 1);
    }

    public String getName() {
        return name;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (this.value == value) return;
        this.value = value;
        notifyListeners(l -> l.cellUpdated(this));
    }

    public boolean isListener(Cell cell) {
        return cell.listeners.contains(this);
    }

    public void addListener(CellListener l) {
        listeners.add(l);
    }

    public void removeListener(CellListener l) {
        listeners.remove(l);
    }

    private void notifyListeners(Consumer<CellListener> action) {
        listeners.forEach(action);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void cellUpdated(Cell cell) {
        sheet.evaluate(this);
    }

    public static Entry<Integer, Integer> position(String ref) {
        String[] refParts = ref.trim().split("");
        int row = Integer.parseInt(refParts[1]) - 1;
        int column = refParts[0].toCharArray()[0] - 'A';
        return new AbstractMap.SimpleImmutableEntry<>(row, column);
    }

}
