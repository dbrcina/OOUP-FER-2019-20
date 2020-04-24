package hr.fer.zemris.ooup.lab2.strategy;

import java.util.List;
import java.util.stream.Collectors;

public final class LinearInterpolation implements PercentileCalc {
    @Override
    public int calculate(int p, List<Integer> integers) {
        List<Integer> sorted = integers.stream()
                .sorted()
                .collect(Collectors.toList());
        int N = sorted.size();

        for (int i = 0; i < N; i++) {
            double pVi = 100 * ((i + 1) - 0.5) / N;
            if (i == 0 && p < pVi) return sorted.get(0);
            if (i == N - 1 && p > pVi) return sorted.get(N - 1);
            double pVi1 = 100 * ((i + 1 + 1) - 0.5) / N;
            if (p >= pVi && p <= pVi1) {
                double inter = sorted.get(i) + N * (p - pVi) * (sorted.get(i + 1) - sorted.get(i)) / 100;
                return (int) inter;
            }
        }
        return 0;
    }

}
