package hr.fer.zemris.ooup.lab2.zad5.izvor.impl;

import hr.fer.zemris.ooup.lab2.zad5.izvor.IzvorBrojeva;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class DatotecniIzvor extends IzvorBrojeva {

    private final Scanner sc = new Scanner(is);

    public DatotecniIzvor(String file) throws IOException {
        super(Files.newInputStream(Paths.get(file)));
    }

    @Override
    public int nextInt() {
        try {
            return sc.nextInt();
        } catch (Exception e) {
            return -1;
        }
    }

}
