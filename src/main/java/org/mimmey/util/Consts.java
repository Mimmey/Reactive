package org.mimmey.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Consts {

    public static final long AWAIT_TERMINATION_PERIOD = 50L;
    public static final long FINAL_SLEEP_DURATION = 5000L;
    public static final long MAX_TASK_PROCESS_DURATION = 5000L;
    public static final int MAX_THREADS_NUMBER = 4;
    public static final String TASK_NAME_PATTERN = "task";
}
