package hr.fer.zemris.ooup.lab2.zad5.izvor;

import java.io.InputStream;
import java.util.Scanner;

public abstract class IzvorBrojeva implements AutoCloseable {

    private final Scanner sc;

    public IzvorBrojeva(InputStream is) {
        this.sc = new Scanner(is);
    }

    public int nextInt() {
        try {
            return sc.nextInt();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void close() throws Exception {
        sc.close();
        System.out.println("Izvor brojeva je zatvoren.");
    }

}
