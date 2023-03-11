package com.digipay.digitalwallet.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletTransactionRequest {

    @NotNull
    private Long transactionAmount;
}
