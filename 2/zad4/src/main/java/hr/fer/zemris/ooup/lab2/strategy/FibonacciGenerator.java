package hr.fer.zemris.ooup.lab2.strategy;

import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of Fibonacci number generator.
 */
public final class FibonacciGenerator implements IntegerGenerator {

    private final int n;

    public FibonacciGenerator(int n) {
        this.n = n;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> integers = new LinkedList<>();
        for (int i = 0; i <= n; i++) integers.add(fibonacciRecursion(i));
        return integers;
    }

    private int fibonacciRecursion(int n) {
        if (n <= 1) return n;
        return fibonacciRecursion(n - 1) + fibonacciRecursion(n - 2);
    }

}
