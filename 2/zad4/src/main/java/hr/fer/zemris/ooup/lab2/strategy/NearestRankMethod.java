package hr.fer.zemris.ooup.lab2.strategy;

import java.util.List;
import java.util.stream.Collectors;

public final class NearestRankMethod implements PercentileCalc {

    @Override
    public int calculate(int p, List<Integer> integers) {
        List<Integer> sorted = integers.stream()
                .sorted()
                .collect(Collectors.toList());
        double n_p = (double) p * sorted.size() / 100;
        return sorted.get((int) Math.ceil(n_p) - 1);
    }

}
