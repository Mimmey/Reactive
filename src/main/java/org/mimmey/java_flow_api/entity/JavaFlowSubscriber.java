package org.mimmey.java_flow_api.entity;

import lombok.RequiredArgsConstructor;
import org.mimmey.Task;
import org.mimmey.Consts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public final class JavaFlowSubscriber implements Flow.Subscriber<Task> {

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
    public void onNext(Task item) {
        taskProcessExecutor.submit(item.apply(id));
        subscription.request(taskPortionSize);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        taskProcessExecutor.shutdown();
        tryAwaitTerminationUntilFinish(taskProcessExecutor);

        System.out.printf("Subscriber#%d finished all tasks\n", id);
    }

    private static void tryAwaitTerminationUntilFinish(ExecutorService executorService) {
        try {
            while (true) {
                if (executorService.awaitTermination(
                        Consts.AWAIT_TERMINATION_PERIOD,
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
