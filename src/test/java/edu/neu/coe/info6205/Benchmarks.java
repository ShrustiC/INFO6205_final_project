package edu.neu.coe.info6205;

import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.LazyLogger;
import org.junit.BeforeClass;

import java.io.IOException;

/**
 * Unit tests which are in fact benchmarks of the various sort methods.
 * Keep in mind that we are sorting objects here (Integers). not primitives.
 */
public class Benchmarks {

    @BeforeClass
    public static void setupClass() {
        try {
            config = Config.load(Benchmarks.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testQuickSortDualPivotSortBenchmark() {
//        String description = "Dual-pivot Quick sort";
//        final Helper<Integer> helper = new BaseHelper<>(description, N, config);
//        final GenericSort<Integer> sort = new QuickSort_DualPivot<>(helper);
//        runBenchmark(description, sort, helper);
//    }
//
//    public void runBenchmark(String description, GenericSort<Integer> sort, Helper<Integer> helper) {
//        helper.init(N);
//        Supplier<Integer[]> supplier = () -> helper.random(Integer.class, Random::nextInt);
//        final Benchmark<Integer[]> benchmark = new Benchmark_Timer<>(
//                description + " for " + N + " Integers",
//                (xs) -> Arrays.copyOf(xs, xs.length),
//                sort::mutatingSort,
//                null
//        );
//        logger.info(Utilities.formatDecimal3Places(benchmark.runFromSupplier(supplier, 100)) + " ms");
//    }

    final static LazyLogger logger = new LazyLogger(Benchmarks.class);

    public static final int N = 2000;

    private static Config config;

}
