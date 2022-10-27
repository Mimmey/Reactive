package org.mimmey.project_reactor;

import org.mimmey.util.generator.Generator;
import org.mimmey.util.Consts;
import org.mimmey.Task;
import org.mimmey.util.generator.TaskStreamGenerator;
import org.mimmey.project_reactor.entity.ProjectReactorSubscriber;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public class ProjectReactorMain {

    private static final Generator<Stream<Task>> taskStreamGenerator = new TaskStreamGenerator();

    public static void main(String[] args) {
        Subscriber<Task> subscriber1 = new ProjectReactorSubscriber(1L, 1);
        Subscriber<Task> subscriber2 = new ProjectReactorSubscriber(2L, 2);
        Subscriber<Task> subscriber3 = new ProjectReactorSubscriber(3L, 4);
        Subscriber<Task> subscriber4 = new ProjectReactorSubscriber(4L, 10);

        Flux<Task> taskFlux = Flux.fromStream(taskStreamGenerator.generate());

        ConnectableFlux<Task> connectableTaskFlux = taskFlux.publish();

        connectableTaskFlux.subscribe(subscriber1);
        connectableTaskFlux.subscribe(subscriber2);
        connectableTaskFlux.subscribe(subscriber3);
        connectableTaskFlux.subscribe(subscriber4);

        connectableTaskFlux.connect();

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
