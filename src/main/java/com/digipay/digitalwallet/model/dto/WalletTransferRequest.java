package com.digipay.digitalwallet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletTransferRequest{

    @NotNull
    private Long transactionAmount;

    @NotBlank
    private String destinationWalletName;

    @NotBlank
    private String destinationUserId;

}
