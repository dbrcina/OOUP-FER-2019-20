package hr.fer.zemris.ooup.lab2.zad5.izvor;

import java.io.InputStream;

public abstract class IzvorBrojeva implements AutoCloseable {

    protected final InputStream is;

    protected IzvorBrojeva(InputStream is) {
        this.is = is;
    }

    public abstract int nextInt();

    @Override
    public void close() throws Exception {
        is.close();
        System.out.println("Izvor brojeva je zatvoren.");
    }

}
