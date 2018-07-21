package com.github.ko_noguchi.books;

import java.time.LocalDateTime;

public class ClockStub implements Clock {
    private final LocalDateTime now_returnValue;

    ClockStub(LocalDateTime now) {
        this.now_returnValue = now;
    }

    @Override
    public LocalDateTime now() {
        return now_returnValue;
    }
}
