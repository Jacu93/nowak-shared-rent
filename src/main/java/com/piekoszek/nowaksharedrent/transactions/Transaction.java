package com.piekoszek.nowaksharedrent.transactions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;

@Builder
@Getter
public class Transaction {

    @Id
    private String id;
    private String apartmentId;
    private String title;
    private long createdAt;
    private TransactionType type;
    private String paidBy;
    @Min(value = 1, message = "Transaction value has to be greater than 0!")
    private int value;
}
