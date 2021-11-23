import java.nio.charset.StandardCharsets;

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
}
