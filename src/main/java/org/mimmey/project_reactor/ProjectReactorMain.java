package org.mimmey.project_reactor;

import org.mimmey.Consts;
import org.mimmey.Task;
import org.mimmey.project_reactor.entity.ProjectReactorSubscriber;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ProjectReactorMain {

    public static void main(String[] args) {
        Subscriber<Task> subscriber1 = new ProjectReactorSubscriber(1L, 2);
        Subscriber<Task> subscriber2 = new ProjectReactorSubscriber(2L, 5);
        Subscriber<Task> subscriber3 = new ProjectReactorSubscriber(3L, 10);

        Flux<Task> taskFlux = Flux.generate(
                () -> 1,
                (state, sink) -> {
                    sink.next(Task.of(Consts.TASK_NAME_PATTERN + state));
                    if (state == 10) {
                        sink.complete();
                    }
                    return state + 1;
                }
        );

        ConnectableFlux<Task> connectableTaskFlux = taskFlux.publish();

        connectableTaskFlux.subscribe(subscriber1);
        connectableTaskFlux.subscribe(subscriber2);
        connectableTaskFlux.subscribe(subscriber3);

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
