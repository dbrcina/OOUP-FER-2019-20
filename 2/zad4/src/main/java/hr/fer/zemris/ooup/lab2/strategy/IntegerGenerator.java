package hr.fer.zemris.ooup.lab2.strategy;

import java.util.List;

/**
 * Interface used for generating integers.
 */
public interface IntegerGenerator {

    /**
     * @return a list of generated integers.
     */
    List<Integer> generate();

}
