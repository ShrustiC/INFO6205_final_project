import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public final class Utils {

    public static String pinyinToUnicode(String pinyin){
        char pinyinArray[] = pinyin.toCharArray();
        String unicode = "";

        //UTF16
        for (int i=0;i<pinyinArray.length;i++){
            unicode += ("\\u" + Integer.toHexString( pinyinArray[i] | 0x10000).substring(1));
        }

        return unicode;
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
}
