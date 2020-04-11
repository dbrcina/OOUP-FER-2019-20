package hr.fer.zemris.ooup.lab2.zad5.izvor.impl;

import hr.fer.zemris.ooup.lab2.zad5.izvor.IzvorBrojeva;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatotecniIzvor extends IzvorBrojeva {

    public DatotecniIzvor(String file) throws IOException {
        super(Files.newInputStream(Paths.get(file)));
    }

}
