package org.mimmey.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mimmey.Task;

import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskGenerator {
    public static Stream<Task> generateTaskStream() {
        return Stream.iterate(1, index -> index + 1)
                .map(index -> Task.of(Consts.TASK_NAME_PATTERN + index))
                .limit(10);
    }
}
