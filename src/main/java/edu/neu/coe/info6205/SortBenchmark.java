
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
        File pinyinFile = new File(pinyinFileName);
        List<String> shuffledChinese = Utils.readFromFile("shuffledChinese.txt");
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
        pinyinMapping.forEach((key, value) -> System.out.println(key + ":" + value));
        shuffledChinesePinyin = Arrays.stream(tmp)
                .map(str -> str.split("=")[1])
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        preProcess();
        int curr = 0;

        //for(int N =250000 ; N < 8000000 ; N = 2*N) {

         //   System.out.println("**************Sort results for "+N+" elements:");
            // MSD sort
            System.out.println("MSD Sort");
            String[] MSDString = shuffledChinesePinyin.clone();
            MSDStringSort msdSort = new MSDStringSort();
            benchmark = new Benchmark_Timer("MSDSort", b -> msdSort.sort(MSDString));
            //Benchmark start
            double MSDSortRunTime = benchmark.run(MSDString, 1);
            //Benchmark end

            Utils.writeToFile(MSDString, "MSDString_Sorting_Result.txt");
            for (int i = 0; i < MSDString.length; i++) {
                LinkedList<String> tmp = pinyinMapping.get(MSDString[i]);
                if (curr == tmp.size()) curr = 0;
                MSDString[i] = tmp.get(curr);
                curr++;
            }
            System.out.println("MSDSortRunTime: " + MSDSortRunTime);
            System.out.println(Arrays.toString(MSDString));


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

            // Dual-Pivot Quick Sort
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
                System.out.println(Arrays.toString(DualPivotString));
            } catch (Exception e) {
                System.out.println(e);
            }
       // }
    }
    private static Ini ini = new Ini();
    private static Config config = new Config(ini);
    private static Benchmark<String[]> benchmark;
    private static Helper<String> helper;
    private static String[] shuffledChinesePinyin;
    private static Map<String, LinkedList<String>> pinyinMapping = new HashMap<>();

    private final static String pinyinFileName = "shuffledChineseToPinyinMapping.txt";
}
