package com.example.greet.business;

import java.util.Random;

public class WaitUtil {

    private static final Random RANDOM = new Random();

    public static void waitInMilliseconds(final long max) {
        try {
            Thread.sleep(RANDOM.nextLong(max));
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
