package org.mimmey.java_flow_api;

import org.mimmey.Consts;
import org.mimmey.Task;
import org.mimmey.java_flow_api.entity.JavaFlowSubscriber;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaFlowMain {
    public static void main(String[] args) {
        SubmissionPublisher<Task> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<Task> subscriber1 = new JavaFlowSubscriber(1L, 2);
        Flow.Subscriber<Task> subscriber2 = new JavaFlowSubscriber(2L, 5);
        Flow.Subscriber<Task> subscriber3 = new JavaFlowSubscriber(3L, 10);

        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);
        publisher.subscribe(subscriber3);

        Stream.iterate(1, index -> index + 1)
                .map(index -> Task.of(Consts.TASK_NAME_PATTERN + index))
                .limit(10)
                .collect(Collectors.toList())
                .forEach(publisher::submit);

        publisher.close();

        trySleep();
    }

    private static void trySleep() {
        try {
            Thread.sleep(Consts.FINAL_SLEEP_DURATION);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

