package com.digipay.digitalwallet.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletRequest {

    @NotBlank
    private String name;
}
