package hr.fer.zemris.ooup.lab2.zad5;

import hr.fer.zemris.ooup.lab2.zad5.izvor.IzvorBrojeva;
import hr.fer.zemris.ooup.lab2.zad5.izvor.impl.DatotecniIzvor;
import hr.fer.zemris.ooup.lab2.zad5.izvor.impl.TipkovnickiIzvor;
import hr.fer.zemris.ooup.lab2.zad5.listener.impl.DatumZapis;
import hr.fer.zemris.ooup.lab2.zad5.listener.impl.Medijan;
import hr.fer.zemris.ooup.lab2.zad5.listener.impl.Prosjek;
import hr.fer.zemris.ooup.lab2.zad5.listener.impl.Suma;

public class Demo {

    public static void main(String[] args) throws Exception {
        String file = "src/main/resources/integers.txt";
        IzvorBrojeva source = new DatotecniIzvor(file);
        //IzvorBrojeva source = new TipkovnickiIzvor();
        SlijedBrojeva slijedBrojeva = new SlijedBrojeva(source);
        new DatumZapis(slijedBrojeva);
        new Suma(slijedBrojeva);
        new Prosjek(slijedBrojeva);
        new Medijan(slijedBrojeva);
        slijedBrojeva.kreni();
    }

}
