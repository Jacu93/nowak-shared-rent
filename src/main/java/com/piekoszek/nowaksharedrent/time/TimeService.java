package com.piekoszek.nowaksharedrent.time;

import java.util.Calendar;

public interface TimeService {

    long millisSinceEpoch();
    Calendar currentDateAndTime();
}
