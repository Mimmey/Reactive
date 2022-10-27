package org.mimmey;

import org.mimmey.util.Consts;
import org.mimmey.util.MessagePatterns;

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
            System.out.printf(MessagePatterns.SUBSCRIBER_STARTED_TASK.getValue(), aLong, name);
            trySleepForProcessTime();
            System.out.printf(MessagePatterns.SUBSCRIBER_FINISHED_TASK.getValue(), aLong, name);
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
