package com.digipay.digitalwallet.service.impl;

import com.digipay.digitalwallet.mapper.WalletTransactionMapper;
import com.digipay.digitalwallet.model.TransactionType;
import com.digipay.digitalwallet.model.entity.DebitTransaction;
import com.digipay.digitalwallet.model.entity.WalletTransaction;
import com.digipay.digitalwallet.repository.DebitTransactionRepository;
import com.digipay.digitalwallet.service.DebitTransactionService;
import com.digipay.digitalwallet.service.WalletTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class DebitTransactionServiceImpl implements DebitTransactionService {

    DebitTransactionRepository debitTransactionRepository;
    WalletTransactionMapper walletTransactionMapper;
    WalletTransactionService walletTransactionService;

    public DebitTransactionServiceImpl(DebitTransactionRepository debitTransactionRepository,
                                       WalletTransactionMapper walletTransactionMapper,
                                       WalletTransactionService walletTransactionService) {
        this.debitTransactionRepository = debitTransactionRepository;
        this.walletTransactionMapper = walletTransactionMapper;
        this.walletTransactionService = walletTransactionService;
    }

    @Transactional
    @Override
    public void withdraw(WalletTransaction walletTransaction, String iban) {
        Long transactionAmount = walletTransaction.getTransactionAmount();
        debitTransactionRepository.save(new DebitTransaction(UUID.randomUUID().toString(), walletTransaction,
                transactionAmount, TransactionType.WITHDRAW, new Date(), iban));
    }

    @Transactional
    @Override
    public void deposit(WalletTransaction walletTransaction, String iban) {
        Long transactionAmount = walletTransaction.getTransactionAmount();
        debitTransactionRepository.save(new DebitTransaction(UUID.randomUUID().toString(), walletTransaction,
                transactionAmount, TransactionType.DEPOSIT, new Date(), iban));
    }
}
