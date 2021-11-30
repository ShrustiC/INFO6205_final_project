package edu.neu.coe.info6205.util;

import com.ibm.icu.text.Transliterator;

import java.io.*;
import java.util.*;

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

    public static String pinyinToCustomEncoding(String pinyin) {
        char pinyinArray[] = pinyin.toCharArray();
        HashMap<String, String> customEncodingMapping = new HashMap<String, String>(){{
            put("ā", "0"); put("á", "1"); put("ǎ", "2"); put("à", "3"); put("a", "4");
            put("b", "5"); put("c", "6"); put("d", "7"); put("ē", "8"); put("é", "9");
            put("ě", "a"); put("è", "b"); put("e", "c"); put("f", "d"); put("g", "e");
            put("h", "f"); put("ī", "10"); put("í", "11"); put("ǐ", "12"); put("ì", "13");
            put("i", "14"); put("j", "15"); put("k", "16"); put("l", "17"); put("m", "18");
            put("n", "19"); put("ō", "1a"); put("ó", "1b"); put("ǒ", "1c"); put("ò", "1d");
            put("o", "1e"); put("p", "1f"); put("q", "20"); put("r", "21"); put("s", "22");
            put("t", "23"); put("ū", "24"); put("ú", "25"); put("ǔ", "26"); put("ù", "27");
            put("u", "28"); put("v", "29"); put("w", "2a"); put("x", "2b"); put("y", "2c");
            put("z", "2d");

        }};
        String encoding = "";

        //UTF16
        for (int i = 0; i < pinyinArray.length; i++) {
            encoding += customEncodingMapping.get(String.valueOf(pinyinArray[i]));
        }
        return encoding;
    }

    public static String[] wordToPinyin(String[] words) {
        return Arrays.stream(words).map(word -> {
            return word+ "=" + wordToPinyin(word, false);
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
