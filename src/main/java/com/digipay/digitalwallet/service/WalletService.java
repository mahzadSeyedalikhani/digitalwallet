package com.digipay.digitalwallet.service;

import com.digipay.digitalwallet.exception.DuplicateWalletException;
import com.digipay.digitalwallet.exception.WalletAlreadyActiveException;
import com.digipay.digitalwallet.exception.WalletInactiveException;
import com.digipay.digitalwallet.exception.WalletNotFoundException;
import com.digipay.digitalwallet.model.TransactionType;
import com.digipay.digitalwallet.model.dto.WalletDto;
import com.digipay.digitalwallet.model.entity.Wallet;
import java.util.List;

public interface WalletService {

    Wallet createWallet(String userId, WalletDto walletDto) throws DuplicateWalletException;

    void disableWallet(String walletId) throws WalletInactiveException;

    void enableWallet(String walletId) throws WalletAlreadyActiveException;

    void updateBalance(String walletId, double transactionAmount, TransactionType transactionType);

    List<Wallet> displayUserWallets(String userId) throws WalletNotFoundException;
}

