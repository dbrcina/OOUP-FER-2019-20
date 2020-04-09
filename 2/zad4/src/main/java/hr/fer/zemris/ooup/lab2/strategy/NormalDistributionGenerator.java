package hr.fer.zemris.ooup.lab2.strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of normal distribution generator. It generates integers using normal distribution
 * with provided parameters; <i>mean</i>, <i>variance</i> and <i>generation_number</i>.
 */
public final class NormalDistributionGenerator implements IntegerGenerator {

    private final double mean;
    private final double variance;
    private final int generationNumber;
    private final Random rand = new Random();

    public NormalDistributionGenerator(double mean, double variance, int generationNumber) {
        this.mean = mean;
        this.variance = variance;
        this.generationNumber = generationNumber;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> integers = new LinkedList<>();
        for (int i = 0; i < generationNumber; i++)
            integers.add((int) (rand.nextGaussian() * variance + mean));
        return integers;
    }

}
