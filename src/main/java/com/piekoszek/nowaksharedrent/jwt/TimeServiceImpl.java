package com.piekoszek.nowaksharedrent.jwt;

class TimeServiceImpl implements TimeService {

    @Override
    public long millisSinceEpoch() {
        return System.currentTimeMillis();
    }
}
