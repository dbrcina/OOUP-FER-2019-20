package hr.fer.zemris.ooup.lab2;

import hr.fer.zemris.ooup.lab2.context.DistributionTester;
import hr.fer.zemris.ooup.lab2.strategy.*;

import java.io.IOException;
import java.io.OutputStream;

public class Client {

    public static void main(String[] args) {
        try (OutputStream os = System.out) {
            //IntegerGenerator generator = new SequenceGenerator(0, 5, 2);
            IntegerGenerator generator = new NormalDistributionGenerator(0, 2.5, 10);
            //IntegerGenerator generator = new FibonacciGenerator(10);
            //PercentileCalc percentileCalc = new NearestRankMethod();
            PercentileCalc percentileCalc = new LinearInterpolation();
            DistributionTester tester = new DistributionTester(os, generator, percentileCalc);
            tester.startTester();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
