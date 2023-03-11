package com.digipay.digitalwallet.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

    @NotBlank
    private String username;

    @NotBlank

    private String nationalCode;

    @NotBlank
    private String iban;
}
