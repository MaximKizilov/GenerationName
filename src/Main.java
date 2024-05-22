import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger count3 = new AtomicInteger(0);
    public static AtomicInteger count4 = new AtomicInteger(0);
    public static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
//        Set<String> uniqueTexts = new HashSet<>();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
//            uniqueTexts.add(texts[i]);
        }

        Thread palindrome = new Thread(() -> {
            for (String s : texts) {
                if (isPalindrome(s)) {
                    beautifulCount(s.length());
                }
            }
        });
        palindrome.start();

        Thread oneChar = new Thread(() -> {
            for (String s : texts) {
                if (isOneChar(s)) {
                    beautifulCount(s.length());
                }
            }
        });
        oneChar.start();

        Thread alphabet = new Thread(() -> {
            for (String s : texts) {
                if (isAlphabetOrder(s)) {
                    beautifulCount(s.length());
                }
            }
        });
        alphabet.start();

        palindrome.join();
        oneChar.join();
        alphabet.join();

        System.out.printf("Красивых слов с длиной %d: %d шт \n", 3, count3.intValue());
        System.out.printf("Красивых слов с длиной %d: %d шт\n", 4, count4.intValue());
        System.out.printf("Красивых слов с длиной %d:  %d шт\n", 5, count5.intValue());
    }

    private static boolean isPalindrome(String word) {
        return word.contentEquals(new StringBuilder(word).reverse());
    }

    private static boolean isOneChar(String word) {
        return word.chars().distinct().count() == 1;
    }

    private static boolean isAlphabetOrder(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) > word.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static void beautifulCount(int textLength) {
        if (textLength == 3) {
            count3.incrementAndGet();
        } else if (textLength == 4) {
            count4.incrementAndGet();
        } else if (textLength == 5) {
            count5.incrementAndGet();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}