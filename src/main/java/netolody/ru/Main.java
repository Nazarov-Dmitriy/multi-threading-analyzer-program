package netolody.ru;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {

        BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> queue2 = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> queue3 = new ArrayBlockingQueue<>(100);

        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                for (int k = 0; k < 10_000; k++) {
                    String text = generateText("abc", 100_000);
                    try {
                        switch (finalI) {
                            case 0 -> queue1.put(text);
                            case 1 -> queue2.put(text);
                            case 2 -> queue3.put(text);
                            default -> {
                            }
                        }


                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }


        new Thread(() -> {
            var maxCount = 0;
            for (int i = 0; i < 10_000; i++) {
                try {
                    String text = queue1.take();
                    for (char letter : text.toCharArray()) {
                        if (letter == 'a') maxCount++;

                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("максимальное количество символов 'a' =" + maxCount);
        }).start();

        new Thread(() -> {
            var maxCount = 0;
            for (int i = 0; i < 10_000; i++) {
                try {
                    String text = queue2.take();
                    for (char letter : text.toCharArray()) {
                        if (letter == 'b') maxCount++;

                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("максимальное количество символов 'b' =" + maxCount);
        }).start();

        new Thread(() -> {
            var maxCount = 0;
            for (int i = 0; i < 10_000; i++) {
                try {
                    String text = queue3.take();
                    for (char letter : text.toCharArray()) {
                        if (letter == 'c') maxCount++;

                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("максимальное количество символов 'b' =" + maxCount);
        }).start();
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