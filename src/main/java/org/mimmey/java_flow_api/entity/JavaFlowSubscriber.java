package org.mimmey.java_flow_api.entity;

import lombok.RequiredArgsConstructor;
import org.mimmey.java_flow_api.util.Consts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class JavaFlowSubscriber implements Flow.Subscriber<String> {

    private final long id;
    private final int taskPortionSize;
    private Flow.Subscription subscription;
    private final ExecutorService taskProcessExecutor;

    public JavaFlowSubscriber(long id, int taskPortionSize) {
        this.id = id;
        this.taskPortionSize = taskPortionSize;
        this.taskProcessExecutor = Executors.newFixedThreadPool(taskPortionSize);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(taskPortionSize);
    }

    @Override
    public void onNext(String item) {
        taskProcessExecutor.submit(() -> {
            System.out.printf("Subscriber #%d started %s\n", id, item);
            trySleepForRandomTime();
            System.out.printf("Subscriber #%d finished %s\n", id, item);
        });

        subscription.request(taskPortionSize);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        taskProcessExecutor.shutdown();
        tryAwaitTerminationByFinish(taskProcessExecutor);

        System.out.printf("Subscriber#%d finished all tasks\n", id);
    }

    private static void trySleepForRandomTime() {
        try {
            Thread.sleep(getRandom());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static long getRandom() {
        return (long) (Math.random() * Consts.RANDOM_TIME_MAX.getValue());
    }

    private static void tryAwaitTerminationByFinish(ExecutorService executorService) {
        try {
            while (true) {
                if (executorService.awaitTermination(
                        Consts.AWAIT_TERMINATION_PERIOD.getValue(),
                        TimeUnit.MILLISECONDS
                )) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
