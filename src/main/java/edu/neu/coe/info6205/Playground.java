package edu.neu.coe.info6205;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.sort.counting.MSDStringSort;
import edu.neu.coe.info6205.sort.counting.LSDStringSort;
import edu.neu.coe.info6205.sort.huskySort.PureHuskySort;
import edu.neu.coe.info6205.sort.huskySort.utils.HuskyCoderFactory;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import edu.neu.coe.info6205.util.Benchmark;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.Utils;
import org.apache.log4j.BasicConfigurator;
import org.ini4j.Ini;


public class Playground {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        // 台 tái    \u0074\u00e1\u0069
        // 南 nán    \u006e\u00e1\u006e
        // 美 měi    \u006d\u011b\u0069
        // 食 shí    \u0073\u0068\u00ed
        // 最 zuì    \u007a\u0075\u00ec
        // 棒 bàng   \u0062\u00e0\u006e\u0067
        // 了 le     \u006c\u0065
        // 好 hǎo    \u0068\u01ce\u006f
        // 想 xiǎng  \u0078\u0069\u01ce\u006e\u0067
        // 逛 guàng  \u0067\u0075\u00e0\u006e\u0067
        // 水 shuǐ   \u0073\u0068\u0075\u01d0
        // 族 zú     \u007a\u00fa
        // 館 guǎn   \u0067\u0075\u01ce\u006e
        originalStringPinyin = Utils.wordToPinyin(originalString.clone());

        // MSD sort
        String[] MSDString = originalStringPinyin.clone();
        MSDStringSort msdSort = new MSDStringSort();
        msdSort.sort(MSDString);
        System.out.println("MSD String sort result: ");
        for(String i: MSDString){
            System.out.print(i + " ");
        }
        System.out.println("");

        // LSD sort
        String[] LSDString = originalStringPinyin.clone();
        LSDStringSort lsdSort = new LSDStringSort();
        lsdSort.sort(LSDString);
        System.out.println("LSD String sort result: ");
        for(String i: LSDString){
            System.out.print(i + " ");
        }
        System.out.println("");

        // Husky sort
        String[] HuskyString = originalStringPinyin.clone();
        PureHuskySort huskySort = new PureHuskySort(HuskyCoderFactory.unicodeCoder, false, false);
        huskySort.sort(HuskyString);
        System.out.println("HuskySort String sort result: ");
        for(String i: HuskyString){
            System.out.print(i + " ");
        }
        System.out.println("");

        // TimSort
        String[] TimSortString = originalStringPinyin.clone();
        helper = new BaseHelper<>("Sorting", TimSortString.length, config);
        TimSort<String> timSort = new TimSort<>(helper);
        timSort.sort(TimSortString, 0, TimSortString.length);
        System.out.println("TimSort String sort result: ");
        for(String i: TimSortString){
            System.out.print(i + " ");
        }
        System.out.println("");

        try {
            String[] DualPivotString = originalStringPinyin.clone();
            Ini ini = new Ini();
            QuickSort_DualPivot<String> quickSort = new QuickSort_DualPivot<String>(DualPivotString.length, new Config(ini));
            quickSort.sort(DualPivotString, 0, DualPivotString.length, 0);
            System.out.println("Dual Pivot String sort result: ");
            for(String i: DualPivotString){
                System.out.print(i + " ");
            }
            System.out.println("");
        } catch (Exception e) {
            System.out.println(e);
        }


        String words = String.join("", originalString);
        System.out.println("");

        // Chinese to Pinyin
        String pinyin = Utils.wordToPinyin(words, true);
        System.out.println("Pinyin: " + pinyin);

        // Pinyin to Unicodes
        String unicode = Utils.pinyinToUnicode(pinyin);
        System.out.println("Unicode of Pinyin: " + unicode);

        String[] wordsArray = words.split("");
        Collator cmp = Collator.getInstance(Locale.CHINA);

        System.out.println("\nBefore sort: ");
        System.out.println("Words: " + Arrays.toString(wordsArray));
        System.out.println("CN Unicode: " + Utils.cnToUnicode(wordsArray));

        Arrays.sort(wordsArray, cmp);

        System.out.println("\nAfter sort: ");
        System.out.println("Words: " + Arrays.toString(wordsArray));
        System.out.println("CN Unicode: " + Utils.cnToUnicode(wordsArray));

//        List<String> list = edu.neu.coe.info6205.util.Utils.readFromFile("shuffledChinese.txt");
//        String[] array = list.stream().toArray(String[]::new);
//        huskySort.sort(array);
//        Arrays.asList(array).stream().forEach(s -> System.out.println(s));

    }

    private static String[] originalString = {"樊辉辉", "苏会敏", "高民政", "曹玉德", "袁继鹏", "舒冬梅", "杨腊香", "许凤山", "王广风", "黄锡鸿", "罗庆富", "顾芳芳", "苑彬", "郭健华", "郭建俊"};
    private static String[] originalStringPinyin;
    private static Ini ini = new Ini();
    private static Config config = new Config(ini);
    private static Helper<String> helper = new BaseHelper<String>("Sorting", originalString.length, config);
}
