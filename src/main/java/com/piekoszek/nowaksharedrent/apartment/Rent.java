package com.piekoszek.nowaksharedrent.apartment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Rent {
    private long borderDate;
    private int value;
}
