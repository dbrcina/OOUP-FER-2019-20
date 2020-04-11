package hr.fer.zemris.ooup.lab2.zad5.listener.impl;

import hr.fer.zemris.ooup.lab2.zad5.SlijedBrojeva;
import hr.fer.zemris.ooup.lab2.zad5.listener.SlijedBrojevaListener;

import java.util.List;
import java.util.stream.Collectors;

public class Medijan extends SlijedBrojevaListener {

    public Medijan(SlijedBrojeva slijedBrojeva) {
        super(slijedBrojeva);
    }

    @Override
    public void update() {
        List<Integer> sorted = slijedBrojeva.getIntegers().stream()
                .sorted()
                .collect(Collectors.toList());
        int size = sorted.size();
        double median = sorted.get(size / 2);
        if (size % 2 == 0) {
            median += sorted.get(size / 2 - 1);
            median *= 0.5;
        }
        System.out.println("Medijan: " + median);
    }

}
