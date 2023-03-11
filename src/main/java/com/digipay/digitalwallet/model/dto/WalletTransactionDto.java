package com.digipay.digitalwallet.model.dto;

import com.digipay.digitalwallet.model.TransactionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Getter
@Setter
@ToString
public class WalletTransactionDto {

    private Long transactionAmount;

    private TransactionType transactionType;

    private Date transactionDate;
}
