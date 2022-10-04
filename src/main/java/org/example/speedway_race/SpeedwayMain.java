package org.example.speedway_race;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpeedwayMain {
    public static void main(String[] args) throws InterruptedException {

        Referee referee = new Referee();

        Runnable r1 = new Rider(referee, 1);
        Runnable r2 = new Rider(referee, 2);
        Runnable r3 = new Rider(referee, 3);
        Runnable r4 = new Rider(referee, 4);

        referee.startTheRace();

        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);
        executor.submit(r4);
        executor.shutdown();

        referee.await();
        referee.getWinner();

    }
}
