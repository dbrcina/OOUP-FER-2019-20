package hr.fer.zemris.ooup.editor.model;

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
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public LocationRange copy() {
        return new LocationRange(start, end);
    }

}
