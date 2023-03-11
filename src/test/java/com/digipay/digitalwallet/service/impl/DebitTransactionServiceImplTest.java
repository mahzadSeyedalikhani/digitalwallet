package com.digipay.digitalwallet.service.impl;

import com.digipay.digitalwallet.model.TransactionType;
import com.digipay.digitalwallet.model.entity.DebitTransaction;
import com.digipay.digitalwallet.model.entity.WalletTransaction;
import com.digipay.digitalwallet.repository.DebitTransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DebitTransactionServiceImplTest {

    @Mock
    WalletTransaction walletTransaction;

    @Mock
    DebitTransactionRepository debitTransactionRepository;

    @InjectMocks
    DebitTransactionServiceImpl debitTransactionService;

    @Test
    void withdraw() {
        String iban = "4566";
        Long transactionAmount = 100L;
        given(walletTransaction.getTransactionAmount()).willReturn(transactionAmount);

        debitTransactionService.withdraw(walletTransaction, iban);

        ArgumentCaptor<DebitTransaction> debitTransactionArgumentCaptor = ArgumentCaptor.forClass(DebitTransaction.class);
        verify(debitTransactionRepository).save(debitTransactionArgumentCaptor.capture());
        DebitTransaction capturedDebitTransaction = debitTransactionArgumentCaptor.getValue();
        assertThat(capturedDebitTransaction.getTransactionAmount()).isEqualTo(transactionAmount);
        assertThat(capturedDebitTransaction.getIban()).isEqualTo(iban);
        assertThat(capturedDebitTransaction.getTransactionType()).isEqualTo(TransactionType.WITHDRAW);
        assertThat(capturedDebitTransaction.getWalletTransaction()).isEqualTo(walletTransaction);
        assertThat(capturedDebitTransaction.getDebitTransactionId()).isNotNull();
        assertThat(capturedDebitTransaction.getTransactionDate()).isNotNull();
    }

    @Test
    void deposit() {
        String iban = "4566";
        Long transactionAmount = 10L;
        given(walletTransaction.getTransactionAmount()).willReturn(transactionAmount);

        debitTransactionService.deposit(walletTransaction, iban);

        ArgumentCaptor<DebitTransaction> debitTransactionArgumentCaptor = ArgumentCaptor.forClass(DebitTransaction.class);
        verify(debitTransactionRepository).save(debitTransactionArgumentCaptor.capture());
        DebitTransaction capturedDebitTransaction = debitTransactionArgumentCaptor.getValue();
        assertThat(capturedDebitTransaction.getTransactionAmount()).isEqualTo(transactionAmount);
        assertThat(capturedDebitTransaction.getIban()).isEqualTo(iban);
        assertThat(capturedDebitTransaction.getTransactionType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(capturedDebitTransaction.getWalletTransaction()).isEqualTo(walletTransaction);
        assertThat(capturedDebitTransaction.getDebitTransactionId()).isNotNull();
        assertThat(capturedDebitTransaction.getTransactionDate()).isNotNull();
    }

}