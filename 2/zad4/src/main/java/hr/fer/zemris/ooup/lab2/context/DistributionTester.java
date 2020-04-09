package hr.fer.zemris.ooup.lab2.context;

import hr.fer.zemris.ooup.lab2.strategy.IntegerGenerator;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public final class DistributionTester {

    private final PrintWriter wr;
    private final IntegerGenerator generator;

    public DistributionTester(OutputStream os, IntegerGenerator generator) {
        this.wr = new PrintWriter(os);
        this.generator = generator;
    }

    public void startTester() {
        List<Integer> integers = generator.generate();
        integers.forEach(wr::println);
        wr.flush();
    }

}
