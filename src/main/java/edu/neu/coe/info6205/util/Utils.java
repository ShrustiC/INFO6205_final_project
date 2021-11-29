package edu.neu.coe.info6205.util;

import com.ibm.icu.text.Transliterator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Utils {

    //Method to convert chinese words to pinyin; using the icu4j library
    public static String wordToPinyin(String words, Boolean withAccent) {
        Transliterator chineseToLatinTrans;

        if (withAccent) {
            chineseToLatinTrans = Transliterator.getInstance("Han-Latin");
        } else {
            chineseToLatinTrans = Transliterator.getInstance("Han-Latin; nfd; [:nonspacing mark:] remove; nfc");
        }

        return chineseToLatinTrans.transliterate(words);
    }

    //Method to convert to pinyin to the equivalent hexadecimal unicodes
    public static String pinyinToUnicode(String pinyin) {
        char pinyinArray[] = pinyin.toCharArray();
        String unicode = "";

        //UTF16
        for (int i = 0; i < pinyinArray.length; i++) {
            unicode += ("\\u" + Integer.toHexString(pinyinArray[i] | 0x10000).substring(1));
        }
        return unicode;
    }

    public static String[] wordToPinyin(String[] words) {
        return Arrays.stream(words).map(word -> {
            return wordToPinyin(word, false);
        }).toArray(String[]::new);
    }

    public static String cnToUnicode(String cn) {
        char[] cnArray = cn.toCharArray();
        String unicode = "";

        for (int i = 0; i < cnArray.length; i++) {
            unicode += "\\u" + Integer.toString(cnArray[i], 16) + " ";
        }

        return unicode;
    }

    public static String cnToUnicode(String[] cnArray) {
        String unicode = "";

        for (int i = 0; i < cnArray.length; i++) {
            unicode += "\\u" + Integer.toString(cnArray[i].toCharArray()[0], 16) + " ";
        }

        return unicode;
    }

    public static List<String> readFromFile(String fileName) {
        List<String> list = new ArrayList<>();
        try (
                FileReader fReader = new FileReader(fileName);
                BufferedReader bReader = new BufferedReader(fReader)
        ) {
            while (true) {
                String line = bReader.readLine();
                if (line != null) list.add(line);
                else break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeToFile(String[] data, String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File: " + file.getName() + " already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            for (String s: data) {
                fileWriter.write(s + System.getProperty( "line.separator" ));
            }
            fileWriter.close();
            System.out.println("Successfully wrote to the file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
