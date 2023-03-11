package com.digipay.digitalwallet.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WalletTransferDto{

    private Long transactionAmount;
    private String destinationWalletName;
    private String destinationUserId;
}
