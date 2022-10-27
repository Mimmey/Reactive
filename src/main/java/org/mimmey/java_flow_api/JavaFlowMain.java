package org.mimmey.java_flow_api;

import org.mimmey.java_flow_api.entity.JavaFlowSubscriber;
import org.mimmey.java_flow_api.util.Consts;

import java.util.List;
import java.util.concurrent.*;

public class JavaFlowMain {
    public static void main(String[] args) {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<String> subscriber1 = new JavaFlowSubscriber(1L, 5);
        Flow.Subscriber<String> subscriber2 = new JavaFlowSubscriber(2L, 5);
        Flow.Subscriber<String> subscriber3 = new JavaFlowSubscriber(3L, 5);

        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);
        publisher.subscribe(subscriber3);

        List.of("task1", "task2", "task3", "task4", "task5").forEach(publisher::submit);
        publisher.close();

        trySleep();
    }

    private static void trySleep() {
        try {
            Thread.sleep(Consts.FINAL_SLEEP_DURATION.getValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

