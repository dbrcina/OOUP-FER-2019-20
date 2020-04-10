package hr.fer.zemris.ooup.lab2.context;

import hr.fer.zemris.ooup.lab2.strategy.IntegerGenerator;
import hr.fer.zemris.ooup.lab2.strategy.PercentileCalc;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

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
        StringBuilder sb = new StringBuilder();
        sb.append("Integers: ");
        integers.forEach(i -> sb.append(i).append(", "));
        sb.setLength(sb.length() - 2);
        wr.println(sb.toString());

        for (int i = 10; i <= 90; i += 10) {
            wr.println(i + ". percentile: " + percentileCalc.calculate(i, integers));
        }

        wr.flush();
    }

}
