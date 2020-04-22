package hr.fer.zemris.ooup.lab2.zad6;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;

public class Sheet {

    private final Cell[][] cells;

    public Sheet(int rows, int columns) {
        cells = new Cell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column] = new Cell(this, row, column);
            }
        }
    }

    public Cell cell(String ref) {
        Entry<Integer, Integer> position = Cell.position(ref);
        return cells[position.getKey()][position.getValue()];
    }

    public void set(String ref, String exp) {
        Cell cell = cell(ref);
        Collection<Cell> refsBefore = getRefs(cell);
        if (refsBefore != null) refsBefore.forEach(r -> r.removeListener(cell));
        try {
            evaluateExp(cell, exp);
        } catch (Exception e) {
            if (refsBefore != null) refsBefore.forEach(r -> r.addListener(cell));
            throw new RuntimeException(e.getMessage());
        }
        cell.setExp(exp);
        evaluate(cell);
    }

    private void evaluateExp(Cell cell, String exp) {
        if (exp == null || exp.matches("\\d+")) return;
        String[] refs = extractExp(exp);
        for (String ref : refs) {
            Cell temp = cell(ref);
            if (temp.isListener(cell) || cell.getName().equals(temp.getName()))
                throw new RuntimeException("Cycles are not allowed.");
            try {
                evaluateExp(cell, temp.getExp());
                temp.addListener(cell);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Collection<Cell> getRefs(Cell cell) {
        String exp = cell.getExp();
        if (exp == null || exp.matches("\\d+")) return null;
        String[] expParts = extractExp(exp);
        Collection<Cell> refs = new LinkedList<>();
        for (String ref : expParts) refs.add(cell(ref));
        return refs;
    }

    public void evaluate(Cell cell) {
        String exp = cell.getExp();
        if (exp.matches("\\d+")) {
            cell.setValue(Integer.parseInt(exp));
            return;
        }
        Collection<Cell> refs = getRefs(cell);
        int value = 0;
        for (Cell ref : refs) value += ref.getValue();
        cell.setValue(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] rowCells : cells) {
            for (Cell cell : rowCells) {
                int value = cell.getValue();
                sb.append(String.format("%4d", value));
            }
            sb.append("\n");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private String[] extractExp(String exp) {
        return exp.split("[+]");
    }

}
