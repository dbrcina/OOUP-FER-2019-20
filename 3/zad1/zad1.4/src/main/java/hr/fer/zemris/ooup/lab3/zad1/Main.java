package hr.fer.zemris.ooup.lab3.zad1;

import hr.fer.zemris.ooup.lab3.zad1.factory.AnimalFactory;
import hr.fer.zemris.ooup.lab3.zad1.model.Animal;

public class Main {

    public static void main(String[] args) throws Exception {
        Animal t = AnimalFactory.newInstance("Tiger", "Modrobradi");
        Animal p = AnimalFactory.newInstance("Parrot", "Svjetlobradi");

        t.animalPrintGreeting();
        p.animalPrintGreeting();

        t.animalPrintMenu();
        p.animalPrintMenu();
    }

}
