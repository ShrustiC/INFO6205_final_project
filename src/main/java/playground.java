import java.text.Collator;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class playground {

    public static void main(String[] args) {
        String words = "台北";

        String pinyin = Utils.wordToPinyin(words, true);
        System.out.println("Pinyin: " + pinyin);

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

        List<String> list = Utils.readFromFile("shuffledChinese.txt");
        list.stream().forEach(s -> System.out.println(s));

    }
}
