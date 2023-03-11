package com.digipay.digitalwallet.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private String username;
    private String nationalCode;
    private String iban;

    public UserDto(String username, String nationalCode, String iban) {
        this.username = username;
        this.nationalCode = nationalCode;
        this.iban = iban;
    }
}
