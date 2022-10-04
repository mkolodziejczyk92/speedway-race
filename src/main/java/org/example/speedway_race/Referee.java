package org.example.speedway_race;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Referee {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private Rider winner = null;
    private CountDownLatch latch;

    private Map<Rider, Double> recordTimeMap = new HashMap<>();

    public void recordTime(Rider rider, double timeOfLap, int numberOfLap) {
        if (recordTimeMap.containsKey(rider)) {
            double timeForRider = recordTimeMap.get(rider);
            double sumOfTime = timeForRider + timeOfLap;
            recordTimeMap.put(rider, sumOfTime);
            String messageForNextLaps = (String.format("RIDER [%d] / LAP[%d] / LAP TIME [%.2f]",
                    rider.getIdentifier(),
                    numberOfLap,
                    (timeOfLap / 1000)));
            System.out.println(messageForNextLaps);
            if (numberOfLap == 4) {
                double total = recordTimeMap.get(rider);
                String messageForFinish = String.format(ANSI_YELLOW + "FINISH LINE FOR RIDER [%d] / RACE TIME [%.2f]" + ANSI_RESET,
                        rider.getIdentifier(),
                        (total / 1000));
                System.out.println(messageForFinish);
                if (winner == null) {
                    winner = rider;
                }
                latch.countDown();
            }
        } else {
            recordTimeMap.put(rider, timeOfLap);
            System.out.printf("RIDER [%d] / LAP[1] / LAP TIME [%.2f]%n",
                    rider.getIdentifier(),
                    (timeOfLap / 1000));

        }
    }

    public Rider whoWonRace() {
        return winner;
    }

    public void startTheRace() {
        this.latch = new CountDownLatch(4);

    }

    public void await() throws InterruptedException {
        latch.await();
    }

    public void getWinner() {
        System.out.printf(ANSI_RED + "REFEREE: THE WINNER IS RIDER [%d]%n" + ANSI_RESET,
                whoWonRace().getIdentifier());
    }
}


