package hr.fer.zemris.ooup.lab3.zad1.model.plugins;

import hr.fer.zemris.ooup.lab3.zad1.model.Animal;

public class Parrot extends Animal {

    private final String name;

    public Parrot(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String greet() {
        return "grr!";
    }

    @Override
    public String menu() {
        return "sjemenke";
    }

}
