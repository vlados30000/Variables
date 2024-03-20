import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counterLetters3 = new AtomicInteger();
    public static AtomicInteger counterLetters4 = new AtomicInteger();
    public static AtomicInteger counterLetters5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread sameLettersThread = new Thread(() -> {
            for (String text : texts) {
                if (sameLettersWord(text)) {
                    counter(text.length());
                }
            }
        });
        sameLettersThread.start();

        Thread incrementalLettersThread = new Thread(() -> {
            for (String text : texts) {
                if (incrementalLettersWord(text) && !sameLettersWord(text)) {
                    counter(text.length());
                }
            }
        });
        incrementalLettersThread.start();

        Thread symmetryThread = new Thread(() -> {
            for (String text : texts) {
                if (symmetricalLettersWord(text) && !sameLettersWord(text)) {
                    counter(text.length());
                }
            }
        });
        symmetryThread.start();

        sameLettersThread.join();
        incrementalLettersThread.join();
        symmetryThread.join();

        System.out.println();
        System.out.println("Красивых слов с длиной 3: " + counterLetters3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterLetters4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterLetters5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean sameLettersWord(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) != text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean incrementalLettersWord(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean symmetricalLettersWord(String text) {
        if (text.equals(new StringBuilder(text).reverse().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public static void counter(int textLength) {
        if (textLength == 3) {
            counterLetters3.getAndIncrement();
        } else if ((textLength == 4)) {
            counterLetters4.getAndIncrement();
        } else if ((textLength == 5)) {
            counterLetters5.getAndIncrement();
        }
    }
}