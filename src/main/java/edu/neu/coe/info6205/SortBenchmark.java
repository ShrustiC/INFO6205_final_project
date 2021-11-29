package edu.neu.coe.info6205;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.sort.counting.LSDStringSort;
import edu.neu.coe.info6205.sort.counting.MSDStringSort;
import edu.neu.coe.info6205.sort.huskySort.PureHuskySort;
import edu.neu.coe.info6205.sort.huskySort.utils.HuskyCoderFactory;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import edu.neu.coe.info6205.util.Benchmark;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.Utils;
import org.apache.log4j.BasicConfigurator;
import org.ini4j.Ini;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class SortBenchmark {
    /*
        Todo:
         1. Add Chinese words to Pinyin mapping
         2. Convert sorted Pinyin back to Chinese
         3. MSD, LSD and Dual-Pivot Quicksort should extends SortWithHelper (done)
     */

    public static void main(String[] args) {
        BasicConfigurator.configure();
        String pinyinFileName = "shuffledChinesePinyin.txt";
        String[] shuffledChinesePinyin;
        File pinyinFile = new File(pinyinFileName);

        List<String> shuffledChinese = Utils.readFromFile("shuffledChinese.txt");

        if (pinyinFile.exists()){
            System.out.println("File exists, reading from file...");
            shuffledChinesePinyin = Utils.readFromFile(pinyinFile.toString()).stream().toArray(String[]::new);
        }else{
            System.out.println("File is not exists, converting and writing to file...");
            shuffledChinesePinyin = Utils.wordToPinyin(shuffledChinese.stream().toArray(String[]::new));
            Utils.writeToFile(shuffledChinesePinyin, pinyinFile.toString());
        }

        // MSD sort
        System.out.println("MSD Sort");
        String[] MSDString = shuffledChinesePinyin.clone();
        helper = new BaseHelper<>("Sorting", MSDString.length, config);
        SortWithHelper<String> msdSort = new MSDStringSort(helper);
        //Benchmark start
        msdSort.sort(MSDString);
        //Benchmark end
        //Utils.writeToFile(MSDString, "MSDString_Sorting_Result.txt");

        // LSD sort
        System.out.println("LSD Sort");
        String[] LSDString = shuffledChinesePinyin.clone();
        helper = new BaseHelper<>("Sorting", LSDString.length, config);
        SortWithHelper<String> lsdSort = new LSDStringSort(helper);
        //Benchmark start
        lsdSort.sort(LSDString);
        //Benchmark end
        //Utils.writeToFile(LSDString, "LSDString_Sorting_Result.txt");

        // Husky sort
        System.out.println("Husky Sort");
        String[] HuskyString = shuffledChinesePinyin.clone();
        PureHuskySort huskySort = new PureHuskySort(HuskyCoderFactory.unicodeCoder, false, false);
        //Benchmark start
        huskySort.sort(HuskyString);
        //Benchmark end
        //Utils.writeToFile(HuskyString, "HuskySort_Sorting_Result.txt");

        // TimSort
        System.out.println("TimSort");
        String[] TimSortString = shuffledChinesePinyin.clone();
        helper = new BaseHelper<>("Sorting", TimSortString.length, config);
        SortWithHelper<String> timSort = new TimSort<>(helper);
        benchmark = new Benchmark_Timer<>("TimSort", b -> timSort.sort(b));
        //Benchmark start
        Arrays.sort(TimSortString);
        //Benchmark end
        //Utils.writeToFile(TimSortString, "TimSort_Sorting_Result.txt");

        // Dual-Pivot Quick Sort
        System.out.println("Dual-Pivot Quicksort");
        try {
            String[] DualPivotString = shuffledChinesePinyin.clone();
            Ini ini = new Ini();
            SortWithHelper<String> quickSort = new QuickSort_DualPivot<String>(DualPivotString.length, new Config(ini));
            //Benchmark start
            quickSort.sort(DualPivotString);
            //Benchmark end
            //Utils.writeToFile(DualPivotString, "DualPivot_Sorting_Result.txt");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    private static Ini ini = new Ini();
    private static Config config = new Config(ini);
    private static Benchmark<String[]> benchmark;
    private static Helper<String> helper;
}
