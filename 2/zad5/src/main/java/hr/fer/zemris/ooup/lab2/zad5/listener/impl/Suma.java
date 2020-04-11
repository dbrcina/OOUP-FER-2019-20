package hr.fer.zemris.ooup.lab2.zad5.listener.impl;

import hr.fer.zemris.ooup.lab2.zad5.SlijedBrojeva;
import hr.fer.zemris.ooup.lab2.zad5.listener.SlijedBrojevaListener;

public class Suma extends SlijedBrojevaListener {

    public Suma(SlijedBrojeva slijedBrojeva) {
        super(slijedBrojeva);
    }

    @Override
    public void update() {
        int sum = 0;
        for (int i : slijedBrojeva.getIntegers()) sum += i;
        System.out.println("Suma: " + sum);
    }

}
