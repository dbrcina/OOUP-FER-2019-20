package hr.fer.zemris.ooup.editor.model;

import java.util.Objects;

public class LocationRange {

    private Location start;
    private Location end;

    public LocationRange(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    public LocationRange() {
        this(new Location(), new Location());
    }

    public Location getStart() {
        return start.copy();
    }

    public void setStart(Location start) {
        this.start = start.copy();
    }

    public Location getEnd() {
        return end.copy();
    }

    public void setEnd(Location end) {
        this.end = end.copy();
    }

    public LocationRange copy() {
        return new LocationRange(start, end);
    }

    @Override
    public String toString() {
        return String.format("Start:%s, End:%s", start, end);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof LocationRange)) return false;
        LocationRange that = (LocationRange) other;
        return start.equals(that.start) &&
                end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

}
