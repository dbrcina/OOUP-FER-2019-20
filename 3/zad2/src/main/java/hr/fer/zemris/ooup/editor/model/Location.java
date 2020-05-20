package hr.fer.zemris.ooup.editor.model;

import java.util.Objects;

public class Location {

    private int row;
    private int column;

    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Location() {
        this(0, 0);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Location copy() {
        return new Location(row, column);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", row, column);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Location)) return false;
        Location location = (Location) other;
        return row == location.row &&
                column == location.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

}
