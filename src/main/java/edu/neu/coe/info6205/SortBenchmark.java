
package edu.neu.coe.info6205;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
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
import java.util.*;

public class SortBenchmark {
    /*
        Todo:
         1. Add Chinese words to Pinyin mapping (done)
         2. Convert sorted Pinyin back to Chinese (done)
         3. MSD, LSD and Dual-Pivot Quicksort should extends SortWithHelper (done)
         4. Unit test with Chinese terms
     */
    public static void preProcess() {
        File pinyinFile = new File(tonelessPinyinFileName);
        List<String> shuffledChinese = Utils.readFromFile("shuffledChinese.txt");
        String[] tmp;

        if (pinyinFile.exists()){
            System.out.println("File exists, reading from file...");
            tmp = Utils.readFromFile(pinyinFile.toString()).stream().toArray(String[]::new);
        }else{
            System.out.println("File is not exists, converting and writing to file...");
            tmp = Utils.wordToPinyin(shuffledChinese.stream().toArray(String[]::new));
            Utils.writeToFile(tmp, pinyinFile.toString());
        }
        Arrays.stream(tmp).forEach(str -> {
            String ch = str.split("=")[0];
            String pinyIn = str.split("=")[1];
            LinkedList<String> val;
            if(pinyinMapping.containsKey(pinyIn)) {
                val = pinyinMapping.get(pinyIn);
                val.add(ch);
                Collections.sort(val);
            } else {
                val = new LinkedList<>();
                val.add(ch);
            }
            pinyinMapping.put(pinyIn, val);
        });
        pinyinMapping.forEach((key, value) -> System.out.println(key + ":" + value));
        shuffledChinesePinyin = Arrays.stream(tmp)
                .map(str -> str.split("=")[1])
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        preProcess();
        int curr = 0;

        // MSD sort
        System.out.println("MSD Sort");
        String[] MSDString = shuffledChinesePinyin.clone();
        MSDStringSort msdSort = new MSDStringSort();
        //Benchmark start
        msdSort.sort(MSDString);
        //Benchmark end

        for(int i = 0; i < MSDString.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(MSDString[i]);
            if(curr == tmp.size()) curr = 0;
            MSDString[i] = tmp.get(curr);
            curr++;
        }
        Utils.writeToFile(MSDString, "MSDString_Sorting_Result.txt");
//        System.out.println(Arrays.toString(MSDString));


        // LSD sort
        System.out.println("LSD Sort");
        String[] LSDString = shuffledChinesePinyin.clone();
        LSDStringSort lsdSort = new LSDStringSort();
        //Benchmark start
        lsdSort.sort(LSDString);
        //Benchmark end

        for(int i = 0; i < LSDString.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(LSDString[i]);
            if(curr == tmp.size()) curr = 0;
            LSDString[i] = tmp.get(curr);
            curr++;
        }
        Utils.writeToFile(LSDString, "LSDString_Sorting_Result.txt");
//        System.out.println(Arrays.toString(LSDString));

        // Husky sort
        System.out.println("Husky Sort");
        String[] HuskyString = shuffledChinesePinyin.clone();
        PureHuskySort huskySort = new PureHuskySort(HuskyCoderFactory.unicodeCoder, false, false);
        //Benchmark start
        huskySort.sort(HuskyString);
        //Benchmark end

        for(int i = 0; i < HuskyString.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(HuskyString[i]);
            if(curr == tmp.size()) curr = 0;
            HuskyString[i] = tmp.get(curr);
            curr++;
        }
        Utils.writeToFile(HuskyString, "HuskySort_Sorting_Result.txt");
//        System.out.println(Arrays.toString(HuskyString));

        // TimSort
        System.out.println("TimSort");
        String[] TimSortString = shuffledChinesePinyin.clone();
        helper = new BaseHelper<>("Sorting", TimSortString.length, config);
        TimSort<String> timSort = new TimSort<>(helper);
        benchmark = new Benchmark_Timer<>("TimSort", b -> timSort.sort(b,0, TimSortString.length));
        benchmark.run(TimSortString, 5);
        //Benchmark start
        //timSort.sort(TimSortString, 0, TimSortString.length);
        //Benchmark end

        for(int i = 0; i < TimSortString.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(TimSortString[i]);
            if(curr == tmp.size()) curr = 0;
            TimSortString[i] = tmp.get(curr);
            curr++;
        }
        Utils.writeToFile(TimSortString, "TimSort_Sorting_Result.txt");
//        System.out.println(Arrays.toString(TimSortString));

        // Dual-Pivot Quick Sort
        System.out.println("Dual-Pivot Quicksort");
        try {
            String[] DualPivotString = shuffledChinesePinyin.clone();
            Ini ini = new Ini();
            QuickSort_DualPivot<String> quickSort = new QuickSort_DualPivot<String>(DualPivotString.length, new Config(ini));
            //Benchmark start
            quickSort.sort(DualPivotString, 0, DualPivotString.length, 0);
            //Benchmark end

            for(int i = 0; i < DualPivotString.length; i++) {
                LinkedList<String> tmp = pinyinMapping.get(DualPivotString[i]);
                if(curr == tmp.size()) curr = 0;
                DualPivotString[i] = tmp.get(curr);
                curr++;
            }
            Utils.writeToFile(DualPivotString, "DualPivot_Sorting_Result.txt");
//            System.out.println(Arrays.toString(DualPivotString));
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    private static Ini ini = new Ini();
    private static Config config = new Config(ini);
    private static Benchmark<String[]> benchmark;
    private static Helper<String> helper;
    private static String[] shuffledChinesePinyin;
    private static Map<String, LinkedList<String>> pinyinMapping = new HashMap<>();

    private final static String pinyinFileName = "shuffledChineseToPinyinMapping.txt";
    private final static String tonelessPinyinFileName = "shuffledChineseToTonelessPinyinMapping.txt";
}
