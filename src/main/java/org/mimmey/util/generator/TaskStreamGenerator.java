package org.mimmey.util.generator;

import lombok.NoArgsConstructor;
import org.mimmey.Task;
import org.mimmey.util.Consts;

import java.util.stream.Stream;

@NoArgsConstructor
public final class TaskStreamGenerator implements StreamGenerator<Task> {

    @Override
    public Stream<Task> generate() {
        return Stream.iterate(1, index -> index + 1)
                .map(index -> Task.of(Consts.TASK_NAME_PATTERN + index))
                .limit(10);
    }
}
