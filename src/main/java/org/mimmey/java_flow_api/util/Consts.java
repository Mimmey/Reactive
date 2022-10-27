package org.mimmey.java_flow_api.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Consts {
    RANDOM_TIME_MAX(5000),
    AWAIT_TERMINATION_PERIOD(50),

    FINAL_SLEEP_DURATION(10000);

    private final int value;
}
