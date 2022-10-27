package org.mimmey.project_reactor.entity;

import lombok.RequiredArgsConstructor;
import org.mimmey.Task;
import org.mimmey.util.Consts;
import org.mimmey.util.MessagePatterns;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public final class ProjectReactorSubscriber implements Subscriber<Task> {

    private final long id;
    private final int taskPortionSize;
    private Subscription subscription;
    private final ExecutorService taskProcessExecutor;

    public ProjectReactorSubscriber(long id, int taskPortionSize) {
        this.id = id;
        this.taskPortionSize = taskPortionSize;
        this.taskProcessExecutor = Executors.newFixedThreadPool(taskPortionSize);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
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
        tryAwaitTerminationByFinish(taskProcessExecutor);

        System.out.printf(MessagePatterns.SUBSCRIBER_FINISHED_ALL_TASKS.getValue(), id);
    }

    private static void tryAwaitTerminationByFinish(ExecutorService executorService) {
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
