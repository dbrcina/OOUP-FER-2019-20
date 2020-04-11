package hr.fer.zemris.ooup.lab2.zad5.listener;

import hr.fer.zemris.ooup.lab2.zad5.SlijedBrojeva;

public abstract class SlijedBrojevaListener {

    protected final SlijedBrojeva slijedBrojeva;

    protected SlijedBrojevaListener(SlijedBrojeva slijedBrojeva) {
        this.slijedBrojeva = slijedBrojeva;
        slijedBrojeva.addListener(this);
    }

    public abstract void update();

    public void done() {
        slijedBrojeva.removeListener(this);
    }

}
