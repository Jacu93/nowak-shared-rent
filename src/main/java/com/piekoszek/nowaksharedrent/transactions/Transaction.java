package com.piekoszek.nowaksharedrent.transactions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
public class Transaction {

    @Id
    private String id;
    private String apartmentId;
    private String title;
    private long createdAt;
    private String type;
    private String paidBy;
    private int value;
}
