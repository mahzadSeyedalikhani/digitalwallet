package com.digipay.digitalwallet.exception;

public class WalletAlreadyActiveException extends Exception{
    public WalletAlreadyActiveException(String message) {
        super(message);
    }
}
