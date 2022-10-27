package org.mimmey;

import java.util.function.Function;

public class Task implements Function<Long, Runnable> {

    private final String name;
    private final int processTime;

    public static Task of(String name) {
        return new Task(name);
    }

    private Task(String name) {
        this.name = name;
        this.processTime = generateRandom();
    }

    private static int generateRandom() {
        return (int) (Math.random() * Consts.MAX_TASK_PROCESS_DURATION);
    }

    @Override
    public Runnable apply(Long aLong) {
        return () -> {
            System.out.printf("Subscriber #%d started %s\n", aLong, name);
            trySleepForProcessTime();
            System.out.printf("Subscriber #%d finished %s\n", aLong, name);
        };
    }

    private void trySleepForProcessTime() {
        try {
            Thread.sleep(this.processTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
