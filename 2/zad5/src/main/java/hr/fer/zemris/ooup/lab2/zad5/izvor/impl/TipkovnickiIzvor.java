package hr.fer.zemris.ooup.lab2.zad5.izvor.impl;

import hr.fer.zemris.ooup.lab2.zad5.izvor.IzvorBrojeva;

import java.util.Scanner;

public class TipkovnickiIzvor extends IzvorBrojeva {

    private final Scanner sc = new Scanner(is);

    public TipkovnickiIzvor() {
        super(System.in);
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
