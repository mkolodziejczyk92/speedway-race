package org.example.speedway_race;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Rider implements Runnable {

    private Referee referee;

    private final int identifier;
    private static final int LENGTH = 50;

    public int getIdentifier() {
        return identifier;
    }

    public Rider(Referee referee, int identifier) {
        this.referee = referee;
        this.identifier = identifier;
    }

    private double randomVelocity() {
        Random r = new Random();
        return r.nextDouble(8) + 17;
    }

    private double timeOfLap(double velocity) {
        return (LENGTH / velocity) * 1000;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            double velocity = randomVelocity();
            double timeOfLap = timeOfLap(velocity);
            int numberOfLap = i + 1;
            try {
                TimeUnit.MILLISECONDS.sleep((long) timeOfLap);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            referee.recordTime(this, timeOfLap, numberOfLap);
        }


    }
}
