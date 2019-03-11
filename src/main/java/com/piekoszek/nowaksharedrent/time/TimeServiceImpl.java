package com.piekoszek.nowaksharedrent.time;

import org.springframework.stereotype.Service;

@Service
class TimeServiceImpl implements TimeService {

    @Override
    public long millisSinceEpoch() {
        return System.currentTimeMillis();
    }
}
