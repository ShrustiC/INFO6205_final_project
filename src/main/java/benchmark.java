import edu.neu.coe.info6205.sort.counting.LSDStringSort;
import edu.neu.coe.info6205.sort.counting.MSDStringSort;
import edu.neu.coe.info6205.sort.huskySort.PureHuskySort;
import edu.neu.coe.info6205.sort.huskySort.utils.HuskyCoderFactory;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.util.Config;
import org.apache.log4j.BasicConfigurator;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class benchmark {

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
        String[] MSDString = shuffledChinesePinyin.clone();
        //Benchmark start
        MSDStringSort.sort(MSDString);
        //Benchmark end
        Utils.writeToFile(MSDString, "MSDString_Sorting_Result.txt");

        // LSD sort
        String[] LSDString = shuffledChinesePinyin.clone();
        LSDStringSort lsdSort = new LSDStringSort();
        //Benchmark start
        lsdSort.sort(LSDString);
        //Benchmark end
        Utils.writeToFile(LSDString, "LSDString_Sorting_Result.txt");

        // Husky sort
        String[] HuskyString = shuffledChinesePinyin.clone();
        PureHuskySort huskySort = new PureHuskySort(HuskyCoderFactory.unicodeCoder, false, false);
        //Benchmark start
        huskySort.sort(HuskyString);
        //Benchmark end
        Utils.writeToFile(HuskyString, "HuskySort_Sorting_Result.txt");

        // TimSort
        String[] TimSortString = shuffledChinesePinyin.clone();
        //Benchmark start
        Arrays.sort(TimSortString);
        //Benchmark end
        Utils.writeToFile(TimSortString, "TimSort_Sorting_Result.txt");

        try {
            String[] DualPivotString = shuffledChinesePinyin.clone();
            Ini ini = new Ini();
            QuickSort_DualPivot<String> quickSort = new QuickSort_DualPivot<String>(DualPivotString.length, new Config(ini));
            //Benchmark start
            DualPivotString = quickSort.sort(DualPivotString, true);
            //Benchmark end
            Utils.writeToFile(DualPivotString, "DualPivot_Sorting_Result.txt");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
