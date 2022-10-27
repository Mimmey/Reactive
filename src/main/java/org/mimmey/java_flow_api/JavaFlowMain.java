package org.mimmey.java_flow_api;

import org.mimmey.util.generator.Generator;
import org.mimmey.util.Consts;
import org.mimmey.Task;
import org.mimmey.util.generator.TaskStreamGenerator;
import org.mimmey.java_flow_api.entity.JavaFlowSubscriber;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Stream;

public class JavaFlowMain {

    private static final Generator<Stream<Task>> taskStreamGenerator = new TaskStreamGenerator();

    public static void main(String[] args) {
        SubmissionPublisher<Task> publisher = new SubmissionPublisher<>();

        Flow.Subscriber<Task> subscriber1 = new JavaFlowSubscriber(1L, 1);
        Flow.Subscriber<Task> subscriber2 = new JavaFlowSubscriber(2L, 2);
        Flow.Subscriber<Task> subscriber3 = new JavaFlowSubscriber(3L, 5);

        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);
        publisher.subscribe(subscriber3);

        taskStreamGenerator.generate().forEach(publisher::submit);

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

