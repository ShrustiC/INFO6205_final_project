package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.Utils;
import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.sort.counting.LSDStringSort;
import edu.neu.coe.info6205.sort.counting.MSDStringSort;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.sort.huskySort.PureHuskySort;
import edu.neu.coe.info6205.sort.huskySort.utils.HuskyCoderFactory;
import edu.neu.coe.info6205.sort.huskySort.utils.HuskySortHelper;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;
import org.ini4j.Ini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;


public class SortBenchmark {

    public static int N = 250000;
    static Ini ini = new Ini();
    static Config config = new Config(ini);

    public static void main(String[] args) {
        System.out.println("Program for benchmarking Insertion Sort");
        String[] sortingAlgorithms = new String[]{"TimSort", "QuickSort_dualPivot", "MSDStringSort", "LSDStringSort","HuskySort"};

        for (String s : sortingAlgorithms) {
            for (int i = 0; i < 5; i++) {

                String[] originalString = {"樊辉辉", "苏会敏", "高民政", "曹玉德", "袁继鹏", "舒冬梅", "杨腊香", "许凤山", "王广风", "黄锡鸿", "罗庆富", "顾芳芳", "苑彬", "郭健华", "郭建俊"};
                String[] originalStringPinyin = Utils.wordToPinyin(originalString.clone());


                Helper<String> helper = new BaseHelper<>("Sorting", N, config);

                Benchmark<String[]> benchmark= null;

                if (s.equalsIgnoreCase("TimSort")) {
                    SortWithHelper<String> arraySorter = new TimSort<>(helper);
                    benchmark = new Benchmark_Timer<>("TimSort", b -> arraySorter.sort(b));
                } else if (s.equalsIgnoreCase("QuickSort_dualPivot")) {
                    SortWithHelper<String> arraySorter = new QuickSort_DualPivot<>(helper);
                    benchmark = new Benchmark_Timer<>("QuickSort_DualPivot", b -> arraySorter.sort(b));
                }else if (s.equalsIgnoreCase("HuskySort")) {
                    PureHuskySort arraySorter = new PureHuskySort(HuskyCoderFactory.unicodeCoder, false, false);
                    benchmark = new Benchmark_Timer<>("MSDStringSort", b -> arraySorter.sort(b));
                }else if (s.equalsIgnoreCase("LSDStringSort")) {
                    LSDStringSort arraySorter = new LSDStringSort();
                    benchmark = new Benchmark_Timer<>("LSDStringSort", b -> arraySorter.sort(b));
                }else if (s.equalsIgnoreCase("MSDStringSort")) {
                    MSDStringSort arraySorter = new MSDStringSort();
                    benchmark = new Benchmark_Timer<>("Variois Sorts", b -> arraySorter.sort(b));
                }
                double lapTime = benchmark.run(originalStringPinyin, N);
                System.out.println("Laptime for "+s+" array when n is " + N + " is: " + lapTime);
                System.out.println("-------------------------------------------------------------");
                N = N * 2;
            }
            System.out.println("***************************************************************************");
            N = 100;
        }



    }

}

