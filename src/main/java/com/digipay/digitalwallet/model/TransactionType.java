package com.digipay.digitalwallet.model;

public enum TransactionType {
    /*
    It means deposit from the Debit Card into the Wallet
     */
    CASH_IN,
    /*
    It means withdraw from the Wallet to transfer it into the Debit Card
     */
    CASH_OUT,
    /*
    Transfer money from one Wallet to another Wallet
     */
    WALLET_TRANSFER,
    /*
    put money into the Debit Card
     */
    DEPOSIT,
    /*
    take out money from the Debit Card
     */
    WITHDRAW;
}
