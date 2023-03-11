package com.digipay.digitalwallet.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CashOutDto{

    private Long transactionAmount;
    private String destinationUserId;
}
