package hr.fer.zemris.ooup.lab3.zad1.factory;

import hr.fer.zemris.ooup.lab3.zad1.model.Animal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AnimalFactory {

    public static Animal newInstance(String animalKind, String name)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Class<Animal> clazz = (Class<Animal>) Class.forName(
                "hr.fer.zemris.ooup.lab3.zad1.model.plugins." + animalKind);
        Constructor<Animal> constructor = clazz.getConstructor(String.class);
        return constructor.newInstance(name);
    }

}
