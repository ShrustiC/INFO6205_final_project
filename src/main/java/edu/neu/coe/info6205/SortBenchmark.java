
package edu.neu.coe.info6205;

import com.ibm.icu.util.ULocale;
import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.counting.CustomMSDStringSortCE1;
import edu.neu.coe.info6205.sort.counting.CustomMSDStringSortCE2;
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
    public static long preProcess(String size) {
        File pinyinFile = new File(TONELESS_PINYIN_FILE_NAME + size + ".txt");
        String[] tmp;

        long startTime = System.currentTimeMillis();
        if (pinyinFile.exists()){
            System.out.println("File exists, reading from file...");
            tmp = Utils.readFromFile(pinyinFile.toString()).stream().toArray(String[]::new);
        } else {
            System.out.println("File does not exists, converting and writing to file...");
            List<String> shuffledChinese = Utils.readFromFile(SHUFFLED_CHINESE_FILENAME + size + ".txt");
            Collections.shuffle(shuffledChinese);
            tmp = Utils.wordToPinyin(shuffledChinese.stream().toArray(String[]::new));
            Utils.writeToFile(tmp, pinyinFile.toString());
        }
        Arrays.stream(tmp).forEach(str -> {
            String ch = str.split("=")[0];
            String pinyIn = str.split("=")[1];
            LinkedList<String> val;
            if (pinyinMapping.containsKey(pinyIn)) {
                val = pinyinMapping.get(pinyIn);
                val.add(ch);
                Collections.sort(val);
            } else {
                val = new LinkedList<>();
                val.add(ch);
            }
            pinyinMapping.put(pinyIn, val);
        });
//        pinyinMapping.forEach((key, value) -> System.out.println(key + ":" + value));
        shuffledChinesePinyin = Arrays.stream(tmp)
                .map(str -> str.split("=")[1])
                .toArray(String[]::new);

        long endTime = System.currentTimeMillis();
        return Math.subtractExact(endTime,startTime);
    }

    public static long postProcess(String[] pinyinList) {
        int curr = 0;

        long startTime = System.currentTimeMillis();
        for(int i = 0; i < pinyinList.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(pinyinList[i]);
            if(curr == tmp.size()) curr = 0;
            pinyinList[i] = tmp.get(curr);
            curr++;
        }
        long endTime = System.currentTimeMillis();
        return Math.subtractExact(endTime,startTime);
    }

    public static void MSDSortBenchmark(String[] pinyinList) {
        MSDStringSort msdSort = new MSDStringSort();
        benchmark = new Benchmark_Timer("MSDDSort", b -> msdSort.sort(pinyinList));
        double MSDSortRunTime = benchmark.run(pinyinList, 3);
        double postprocessingTime = postProcess(pinyinList);

        System.out.println("MSDSortRunTime: " + MSDSortRunTime);
        MSDSortRunTime = preprocessingTime + MSDSortRunTime + postprocessingTime;
        System.out.println("Total MSDSortRunTime: " + MSDSortRunTime);
        if (writeToFile)
            Utils.writeToFile(pinyinList, String.valueOf(pinyinList.length) + "_MSDString_Sorting_Result.txt");
//        System.out.println(Arrays.toString(pinyinList));
    }

    public static void MSDSortCE1Benchmark(String[] pinyinList) {
        CustomMSDStringSortCE1 customMSDSortCE1 = new CustomMSDStringSortCE1();
        benchmark = new Benchmark_Timer("Custom_MSDSort_CE1", b -> customMSDSortCE1.sort(pinyinList));
        double customMSDSortCE1RunTime = benchmark.run(pinyinList, 3);
        double postprocessingTime = postProcess(pinyinList);

        System.out.println("customMSDSortCE1RunTime: " + customMSDSortCE1RunTime);
        customMSDSortCE1RunTime = preprocessingTime + customMSDSortCE1RunTime + postprocessingTime;
        System.out.println("Total customMSDSortCE1RunTime: " + customMSDSortCE1RunTime);
        if (writeToFile)
            Utils.writeToFile(pinyinList, String.valueOf(pinyinList.length) + "_MSDCE1String_Sorting_Result.txt");
//        System.out.println(Arrays.toString(pinyinList));
    }

    public static void MSDSortCE2Benchmark(String[] pinyinList) {
        CustomMSDStringSortCE2 customMSDSortCE2 = new CustomMSDStringSortCE2();
        benchmark = new Benchmark_Timer("Custom_MSDSort_CE1", b -> customMSDSortCE2.sort(pinyinList));
        double customMSDSortCE2RunTime = benchmark.run(pinyinList, 3);
        double postprocessingTime = postProcess(pinyinList);

        System.out.println("customMSDSortCE2RunTime: " + customMSDSortCE2RunTime);
        customMSDSortCE2RunTime = preprocessingTime + customMSDSortCE2RunTime + postprocessingTime;
        System.out.println("Total customMSDSortCE2RunTime: " + customMSDSortCE2RunTime);
        if (writeToFile)
            Utils.writeToFile(pinyinList, String.valueOf(pinyinList.length) + "_MSDCE2String_Sorting_Result.txt");
//        System.out.println(Arrays.toString(pinyinList));
    }

    public static void LSDSortBenchmark(String[] pinyinList) {
        LSDStringSort lsdSort = new LSDStringSort();
        benchmark = new Benchmark_Timer("LSDSort", b -> lsdSort.sort(pinyinList));
        double LSDSortRunTime = benchmark.run(pinyinList, 3);
        double postprocessingTime = postProcess(pinyinList);

        System.out.println("LSDSortRunTime: " + LSDSortRunTime);
        LSDSortRunTime = preprocessingTime + LSDSortRunTime + postprocessingTime;
        System.out.println("Total LSDSortRunTime: " + LSDSortRunTime);
        if (writeToFile)
            Utils.writeToFile(pinyinList, String.valueOf(pinyinList.length) + "_LSDString_Sorting_Result.txt");
//        System.out.println(Arrays.toString(pinyinList));
    }

    public static void HuskySortBenchmark(String[] pinyinList) {
        PureHuskySort huskySort = new PureHuskySort(HuskyCoderFactory.unicodeCoder, false, false);
        benchmark = new Benchmark_Timer("Husky Sort", b -> huskySort.sort(pinyinList));
        double HuskySortRunTime = benchmark.run(pinyinList, 3);
        double postprocessingTime = postProcess(pinyinList);

        System.out.println("HuskySortRunTime: " + HuskySortRunTime);
        HuskySortRunTime = preprocessingTime + HuskySortRunTime + postprocessingTime;
        System.out.println("Total HuskySortRunTime: " + HuskySortRunTime);
        if (writeToFile)
            Utils.writeToFile(pinyinList, String.valueOf(pinyinList.length) + "_HuskySort_Sorting_Result.txt");
//        System.out.println(Arrays.toString(pinyinList));
    }

    public static void TimsortWithICU4JBenchmark(String[] pinyinList) {
        helper = new BaseHelper<>("Timesort with ICU4J", pinyinList.length, config);
        com.ibm.icu.text.Collator icu4jCmp = com.ibm.icu.text.Collator.getInstance(ULocale.SIMPLIFIED_CHINESE);
        benchmark = new Benchmark_Timer<>("TimSort with ICU4J", b -> Arrays.sort(b,0, pinyinList.length, icu4jCmp));
        double timsortICU4JRunTIme = benchmark.run(pinyinList, 3);
        double postprocessingTime = postProcess(pinyinList);

        System.out.println("timsortICU4JRunTIme: " + timsortICU4JRunTIme);
        timsortICU4JRunTIme = preprocessingTime + timsortICU4JRunTIme + postprocessingTime;
        System.out.println("Total timsortICU4JRunTIme: " + timsortICU4JRunTIme);
        if (writeToFile)
            Utils.writeToFile(pinyinList, String.valueOf(pinyinList.length) + "_TimsortICU4J_Sorting_Result.txt");
//        System.out.println(Arrays.toString(pinyinList));
    }

    public static void TimsortBenchmark(String[] pinyinList) {
        helper = new BaseHelper<>("Timsort", pinyinList.length, new Config(ini));
        TimSort<String> timSort = new TimSort<>(helper);
        benchmark = new Benchmark_Timer<>("TimSort", b -> timSort.sort(b, 0, pinyinList.length));
        double timsortRunTime = benchmark.run(pinyinList, 3);
        double postprocessingTime = postProcess(pinyinList);

        System.out.println("timsortRunTime: " + timsortRunTime);
        timsortRunTime = preprocessingTime + timsortRunTime + postprocessingTime;
        System.out.println("Total timsortRunTime: " + timsortRunTime);
        if (writeToFile)
            Utils.writeToFile(pinyinList, String.valueOf(pinyinList.length) + "_Timsort_Sorting_Result.txt");
//        System.out.println(Arrays.toString(pinyinList));
    }

    public static void DualPivotQuickSortBenchmark(String[] pinyinList) {
        helper = new BaseHelper<>("Dual-Pivot quicksort", pinyinList.length, new Config(ini));
        QuickSort_DualPivot<String> quickSort = new QuickSort_DualPivot<>(helper);
        long start = System.currentTimeMillis();
        quickSort.sort(pinyinList, 0, pinyinList.length, 0);
     //   benchmark = new Benchmark_Timer<>("Dual-Pivot quicksort", b -> quickSort.sort(pinyinList, 0, pinyinList.length, 0));
        double dpQuickSortRunTime = System.currentTimeMillis() - start;
        double postprocessingTime = postProcess(pinyinList);

        System.out.println("dpQuickSortRunTime: " + dpQuickSortRunTime);
        dpQuickSortRunTime = preprocessingTime + dpQuickSortRunTime + postprocessingTime;
        System.out.println("dpQuickSortRunTime: " + dpQuickSortRunTime);
        if (writeToFile)
            Utils.writeToFile(pinyinList, String.valueOf(pinyinList.length) + "_dpQuckSort_Sorting_Result.txt");
//        System.out.println(Arrays.toString(pinyinList));
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        for(String size : sizes) {
            System.out.println("\n================ Running size " + size + " ================\n");
            pinyinMapping = new HashMap<>();
            preprocessingTime = preProcess(size);

            System.out.println("Preprocessing overhead: "+preprocessingTime);
            // MSD sort
            System.out.println("\n-----------------MSD Sort-------------------\n");
            String[] msdString = shuffledChinesePinyin.clone();
            MSDSortBenchmark(msdString);

            // MSD Sort - CE1
            System.out.println("\n-----------------Custom MSD Sort - CE1-------\n");
            String[] customMSDStringCE1 = shuffledChinesePinyin.clone();
            MSDSortCE1Benchmark(customMSDStringCE1);

            // MSD Sort - CE2
            System.out.println("\n-----------------Custom MSD Sort - CE2-------\n");
            String[] customMSDStringCE2 = shuffledChinesePinyin.clone();
            MSDSortCE2Benchmark(customMSDStringCE2);

            // LSD sort
            System.out.println("\n-----------------LSD Sort--------------------\n");
            String[] lsdString = shuffledChinesePinyin.clone();
            LSDSortBenchmark(lsdString);

            // Husky sort
            System.out.println("\n-----------------Husky Sort-------------------\n");
            String[] huskyString = shuffledChinesePinyin.clone();
            HuskySortBenchmark(huskyString);


            // TimSort with ICU4J Collator
            System.out.println("\n---------------TimSort with ICU4J Collator-----\n");
            String[] timsortICU4JString = shuffledChinesePinyin.clone();
            TimsortWithICU4JBenchmark(timsortICU4JString);

            // TimSort
            System.out.println("\n----------------TimSort----------------------\n");
            String[] timsortString = shuffledChinesePinyin.clone();
            TimsortBenchmark(timsortString);

            //Dual-Pivot Quick Sort
            System.out.println("\n----------------Dual-Pivot Quicksort----------\n");
            try {
                String[] dpQuickSortString = shuffledChinesePinyin.clone();
                DualPivotQuickSortBenchmark(dpQuickSortString);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    // for unit test
    public static String[] getShuffledChinesePinyin() {
        return shuffledChinesePinyin;
    }

    private static Ini ini = new Ini();
    private static Config config = new Config(ini);
    private static Benchmark<String[]> benchmark;
    private static Helper<String> helper;
    private static String[] shuffledChinesePinyin;
    private static Map<String, LinkedList<String>> pinyinMapping = new HashMap<>();
    private static String[] sizes = new String[]{"250k", "500k", "1M", "2M", "4M"};
    private static boolean writeToFile = false;
//    private static String[] sizes = new String[]{"Trimmed"};
    private static double preprocessingTime;
    private final static String SHUFFLED_CHINESE_FILENAME = "shuffledChinese";
    private final static String SHUFFLED_CHINESE_FILENAME_TRIMMED = SHUFFLED_CHINESE_FILENAME + "Trimmed.txt";
    private final static String PINYIN_FILE_NAME = SHUFFLED_CHINESE_FILENAME + "ToPinyinMapping";
    private final static String TONELESS_PINYIN_FILE_NAME = SHUFFLED_CHINESE_FILENAME + "ToTonelessPinyinMapping";


}
