package com.piekoszek.nowaksharedrent.payment;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.Calendar;

@Builder
@Getter
public class Payment {

    @Id
    private String id;
    private String apartmentId;
    private String title;
    private Calendar createdAt;
    private String type;
    private String paidBy;
    private int value;
}
