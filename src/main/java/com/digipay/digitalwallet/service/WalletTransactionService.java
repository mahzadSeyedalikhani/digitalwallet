package com.digipay.digitalwallet.service;

import com.digipay.digitalwallet.exception.*;
import com.digipay.digitalwallet.model.dto.CashInDto;
import com.digipay.digitalwallet.model.dto.CashOutDto;
import com.digipay.digitalwallet.model.dto.WalletTransferDto;
import com.digipay.digitalwallet.model.entity.WalletTransaction;

import java.util.List;

public interface WalletTransactionService {


    void cashIn(String walletId, CashInDto cashInDto) throws WalletInactiveException;

    void cashOut(String walletId, CashOutDto cashOutDto) throws UserNotExistException, WalletInactiveException, InsufficientBalanceException;

    void walletTransfer(String walletId, WalletTransferDto walletTransferDto) throws WalletInactiveException, WalletNotFoundException, UserNotExistException, InsufficientBalanceException;

    List<WalletTransaction> getAllWalletTransactions(String walletId) throws WalletTransactionNotFoundException;

}
