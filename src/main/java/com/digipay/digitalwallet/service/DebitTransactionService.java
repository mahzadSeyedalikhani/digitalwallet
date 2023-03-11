package com.digipay.digitalwallet.service;

import com.digipay.digitalwallet.model.entity.WalletTransaction;

public interface DebitTransactionService {

    void withdraw(WalletTransaction walletTransaction, String iban);

    void deposit(WalletTransaction walletTransaction, String iban);
}
