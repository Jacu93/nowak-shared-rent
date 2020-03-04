package com.piekoszek.nowaksharedrent.time;

import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
class TimeServiceImpl implements TimeService {

    @Override
    public long millisSinceEpoch() {
        return System.currentTimeMillis();
    }

    @Override
    public Calendar currentDateAndTime() {
        return Calendar.getInstance();
    }
}
