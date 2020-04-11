package hr.fer.zemris.ooup.lab2.zad5.listener.impl;

import hr.fer.zemris.ooup.lab2.zad5.SlijedBrojeva;
import hr.fer.zemris.ooup.lab2.zad5.listener.SlijedBrojevaListener;

import java.util.Collection;

public class Prosjek extends SlijedBrojevaListener {

    public Prosjek(SlijedBrojeva slijedBrojeva) {
        super(slijedBrojeva);
    }

    @Override
    public void update() {
        Collection<Integer> integers = slijedBrojeva.getIntegers();
        int sum = 0;
        for (int i : integers) sum += i;
        System.out.println("Prosjek: " + (double) sum / integers.size());
    }
}
