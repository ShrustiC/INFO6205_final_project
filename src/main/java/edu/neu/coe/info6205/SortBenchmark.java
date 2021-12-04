
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
    public static void preProcess() {
        File pinyinFile = new File(pinyinFileName);
        List<String> shuffledChinese = Utils.readFromFile(shuffledChineseFilename + ".txt");

        String[] tmp;

        if (pinyinFile.exists()){
            System.out.println("File exists, reading from file...");
            tmp = Utils.readFromFile(pinyinFile.toString()).stream().toArray(String[]::new);
        }else{
            System.out.println("File does not exists, converting and writing to file...");
            tmp = Utils.wordToPinyin(shuffledChinese.stream().toArray(String[]::new));
            int size = tmp.length;
            System.out.println("************Pinyin array size : "+size);
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
//        pinyinMapping.forEach((key, value) -> System.out.println(key + ":" + value));
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

        // MSD Sort - CE1
        System.out.println("Custom MSD Sort - CE1");
        String[] customMSDStringCE1 = shuffledChinesePinyin.clone();
        CustomMSDStringSortCE1 customMSDSortCE1 = new CustomMSDStringSortCE1();
        benchmark = new Benchmark_Timer("Custom_MSDSort_CE1", b -> customMSDSortCE1.sort(customMSDStringCE1));
        //Benchmark start
        double customMSDSortCE1RunTime = benchmark.run(customMSDStringCE1, 1);
        //Benchmark end

        Utils.writeToFile(customMSDStringCE1, "customMSDStringCE1_Sorting_Result.txt");
        for (int i = 0; i < customMSDStringCE1.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(customMSDStringCE1[i]);
            if (curr == tmp.size()) curr = 0;
            customMSDStringCE1[i] = tmp.get(curr);
            curr++;
        }
        System.out.println("customMSDSortCE1RunTime: " + customMSDSortCE1RunTime);
        System.out.println(Arrays.toString(customMSDStringCE1));

        // MSD Sort - CE2
        System.out.println("Custom MSD Sort - CE2");
        String[] customMSDStringCE2 = shuffledChinesePinyin.clone();
        CustomMSDStringSortCE2 customMSDSortCE2 = new CustomMSDStringSortCE2();
        benchmark = new Benchmark_Timer("Custom_MSDSort_CE2", b -> customMSDSortCE2.sort(customMSDStringCE2));
        //Benchmark start
        double customMSDSortCE2RunTime = benchmark.run(customMSDStringCE2, 1);
        //Benchmark end

        Utils.writeToFile(customMSDStringCE2, "customMSDStringCE2_Sorting_Result.txt");
        for (int i = 0; i < customMSDStringCE2.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(customMSDStringCE2[i]);
            if (curr == tmp.size()) curr = 0;
            customMSDStringCE2[i] = tmp.get(curr);
            curr++;
        }
        System.out.println("customMSDSortCE2RunTime: " + customMSDSortCE2RunTime);
        System.out.println(Arrays.toString(customMSDStringCE2));

        // LSD sort
        System.out.println("LSD Sort");
        String[] LSDString = shuffledChinesePinyin.clone();
        LSDStringSort lsdSort = new LSDStringSort();
        benchmark = new Benchmark_Timer("LSDSort", b -> msdSort.sort(LSDString));
        //Benchmark start
        double LSDSortRunTime = benchmark.run(LSDString, 1);
        //Benchmark end

        Utils.writeToFile(LSDString, "LSDString_Sorting_Result.txt");
        for (int i = 0; i < LSDString.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(LSDString[i]);
            if (curr == tmp.size()) curr = 0;
            LSDString[i] = tmp.get(curr);
            curr++;
        }
        System.out.println("LSDSortRunTime: " + LSDSortRunTime);
        System.out.println(Arrays.toString(LSDString));

        // Husky sort
        System.out.println("Husky Sort");
        String[] HuskyString = shuffledChinesePinyin.clone();
        PureHuskySort huskySort = new PureHuskySort(HuskyCoderFactory.unicodeCoder, false, false);
        benchmark = new Benchmark_Timer("Husky Sort", b -> huskySort.sort(HuskyString));
        //Benchmark start
        double HuskySortRunTime = benchmark.run(HuskyString, 1);
        //Benchmark end

        Utils.writeToFile(HuskyString, "HuskySort_Sorting_Result.txt");
        for (int i = 0; i < HuskyString.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(HuskyString[i]);
            if (curr == tmp.size()) curr = 0;
            HuskyString[i] = tmp.get(curr);
            curr++;
        }
        System.out.println("HuskySortRunTime: " + HuskySortRunTime);
        System.out.println(Arrays.toString(HuskyString));


        // TimSort with ICU4J Collator
        System.out.println("TimSort with ICU4J Collator");
        String[] TimSortICU4JString = shuffledChinesePinyin.clone();
        helper = new BaseHelper<>("Timesort with ICU4J", TimSortICU4JString.length, config);
        com.ibm.icu.text.Collator icu4jCmp = com.ibm.icu.text.Collator.getInstance(ULocale.SIMPLIFIED_CHINESE);
        benchmark = new Benchmark_Timer<>("TimSort with ICU4J", b -> Arrays.sort(b,0, TimSortICU4JString.length, icu4jCmp));
        benchmark.run(TimSortICU4JString, 5);
        //Benchmark start
        //timSort.sort(TimSortString, 0, TimSortString.length);
        //Benchmark end

        for(int i = 0; i < TimSortICU4JString.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(TimSortICU4JString[i]);
            if(curr == tmp.size()) curr = 0;
            TimSortICU4JString[i] = tmp.get(curr);
            curr++;
        }
        Utils.writeToFile(TimSortICU4JString, "TimSort_with_ICU4J_Sorting_Result.txt");
//        System.out.println(Arrays.toString(TimSortICU4JString));

        // TimSort
        System.out.println("TimSort");
        String[] TimSortString = shuffledChinesePinyin.clone();
        helper = new BaseHelper<>("Sorting", TimSortString.length, new Config(ini));
        TimSort<String> timSort = new TimSort<>(helper);
        benchmark = new Benchmark_Timer<>("TimSort", b -> timSort.sort(b, 0, TimSortString.length));
        //Benchmark start
        double TimSortRunTime = benchmark.run(TimSortString, 1);
        //Benchmark end
        Utils.writeToFile(TimSortString, "TimSort_Sorting_Result.txt");
        for (int i = 0; i < TimSortString.length; i++) {
            LinkedList<String> tmp = pinyinMapping.get(TimSortString[i]);
            if (curr == tmp.size()) curr = 0;
            TimSortString[i] = tmp.get(curr);
            curr++;
        }
        System.out.println("TimSortRunTime: " + TimSortRunTime);
        System.out.println(Arrays.toString(TimSortString));

        //             Dual-Pivot Quick Sort
        System.out.println("Dual-Pivot Quicksort");
        try {
            String[] DualPivotString = shuffledChinesePinyin.clone();
            helper = new BaseHelper<>("Dual-Pivot quicksort", DualPivotString.length, new Config(ini));

            QuickSort_DualPivot<String> quickSort = new QuickSort_DualPivot<>(helper);
            benchmark = new Benchmark_Timer<>("Dual-Pivot quicksort", b -> quickSort.sort(DualPivotString, 0, DualPivotString.length, 0));
            //Benchmark start
            double DPQuickSortRunTime = benchmark.run(DualPivotString, 1);
            //Benchmark end
            Utils.writeToFile(DualPivotString, "DualPivot_Sorting_Result.txt");
            for (int i = 0; i < DualPivotString.length; i++) {
                LinkedList<String> tmp = pinyinMapping.get(DualPivotString[i]);
                if (curr == tmp.size()) curr = 0;
                DualPivotString[i] = tmp.get(curr);
                curr++;
            }
            System.out.println("DPQuickSortRunTime: " + DPQuickSortRunTime);
//                System.out.println(Arrays.toString(DualPivotString));
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

    private final static String shuffledChineseFilename = "shuffledChinese";
    private final static String pinyinFileName = shuffledChineseFilename + "ToPinyinMapping.txt";
    private final static String tonelessPinyinFileName = "shuffledChineseToTonelessPinyinMapping.txt";
}
