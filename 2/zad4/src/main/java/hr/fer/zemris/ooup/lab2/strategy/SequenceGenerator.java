package hr.fer.zemris.ooup.lab2.strategy;

import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of sequence generator. It generates integers from <i>lower_bound</i>(inclusive) to
 * <i>upper_bound</i>(inclusive) with provided <i>step</i>. For example:
 *
 * <pre>Seq(0, 5, 1) = [0, 1, 2, 3, 4, 5].</pre>
 */
public final class SequenceGenerator implements IntegerGenerator {

    private final int lowerBound;
    private final int upperBound;
    private final int step;

    public SequenceGenerator(int lowerBound, int upperBound, int step) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.step = step;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> integers = new LinkedList<>();
        for (int i = lowerBound; i <= upperBound; i += step) integers.add(i);
        return integers;
    }

}
