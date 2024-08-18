package com.example.test.best_travel.util;

import java.time.LocalDateTime;
import java.util.Random;

public class BestTravelUtil {

    private static final Random random = new Random();

    public static final LocalDateTime getRandomSoon() {
        int randomHours = random.nextInt(5 - 2) + 2;
        LocalDateTime now = LocalDateTime.now();

        return now.plusHours(randomHours);
    }

    public static final LocalDateTime getRandomLatter() {
        int randomHours = random.nextInt(12 - 6) + 6;
        LocalDateTime now = LocalDateTime.now();

        return now.plusHours(randomHours);
    }

}
