package com.digipay.digitalwallet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashOutRequest{

    @NotNull
    private Long transactionAmount;

    @NotBlank
    private String destinationUserId;
}
