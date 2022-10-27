package org.mimmey.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessagePatterns {
    SUBSCRIBER_STARTED_TASK("Subscriber #%d started %s\n"),
    SUBSCRIBER_FINISHED_TASK("Subscriber #%d finished %s\n"),
    SUBSCRIBER_FINISHED_ALL_TASKS("Subscriber #%d successfully finished all tasks\n");

    private final String value;
}
