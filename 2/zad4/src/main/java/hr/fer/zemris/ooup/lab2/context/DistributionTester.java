package hr.fer.zemris.ooup.lab2.context;

import hr.fer.zemris.ooup.lab2.strategy.IntegerGenerator;
import hr.fer.zemris.ooup.lab2.strategy.PercentileCalc;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringJoiner;

public final class DistributionTester {

    private final PrintWriter wr;
    private final IntegerGenerator generator;
    private final PercentileCalc percentileCalc;

    public DistributionTester(
            OutputStream os, IntegerGenerator generator, PercentileCalc percentileCalc) {
        this.wr = new PrintWriter(os);
        this.generator = generator;
        this.percentileCalc = percentileCalc;
    }

    public void startTester() {
        List<Integer> integers = generator.generate();
        StringJoiner sj = new StringJoiner(", ");
        integers.forEach(i -> sj.add(i.toString()));
        wr.println("Integers: " + sj.toString());

        for (int i = 10; i <= 90; i += 10) {
            wr.println(i + ". percentile: " + percentileCalc.calculate(i, integers));
        }

        wr.flush();
    }

}
