import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

public class playground {

    public static void main(String[] args){
        String pinyin = "táiběi";

        String unicode = Utils.pinyinToUnicode(pinyin);
        System.out.println(unicode);

        String words = "台北";
        String[] wordsArray = words.split("");
        Collator cmp = Collator.getInstance(Locale.CHINA);

        System.out.println("Before sort: ");
        System.out.println("Words: " + Arrays.toString(wordsArray));
        System.out.println("CN Unicode: " + Utils.cnToUnicode(wordsArray));

        Arrays.sort(wordsArray, cmp);

        System.out.println("\nAfter sort: ");
        System.out.println("Words: " + Arrays.toString(wordsArray));
        System.out.println("CN Unicode: " + Utils.cnToUnicode(wordsArray));
    }

}
