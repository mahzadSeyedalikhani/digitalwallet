package com.digipay.digitalwallet.service.impl;

import com.digipay.digitalwallet.exception.DuplicateWalletException;
import com.digipay.digitalwallet.exception.WalletAlreadyActiveException;
import com.digipay.digitalwallet.exception.WalletInactiveException;
import com.digipay.digitalwallet.mapper.WalletMapper;
import com.digipay.digitalwallet.model.TransactionType;
import com.digipay.digitalwallet.model.WalletStatus;
import com.digipay.digitalwallet.model.dto.WalletDto;
import com.digipay.digitalwallet.model.entity.User;
import com.digipay.digitalwallet.model.entity.Wallet;
import com.digipay.digitalwallet.repository.UserRepository;
import com.digipay.digitalwallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    private WalletDto walletDto;
    @Mock
    private WalletRepository walletRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    WalletMapper walletMapper;

    @InjectMocks
    private WalletServiceImpl walletService;

    private String userId;

    private String walletId;


    @BeforeEach
    void setUp() {
        walletDto = new WalletDto("internet");
    }

    @Test
    void createWallet() throws DuplicateWalletException {
        User user = new User(userId, "Mahzad", "1020", "46545");
        given(userRepository.findUserByUserId(Mockito.any())).willReturn(user);
        List<Wallet> wallets = new ArrayList<>();
        Wallet myWallet = new Wallet("12", "credit", WalletStatus.ACTIVE, 200);
        wallets.add(myWallet);
        given(walletRepository.findWalletsByUser(Mockito.any())).willReturn(wallets);
        given(walletMapper.walletDtoToWallet(Mockito.any())).willReturn(myWallet);

        Wallet createdWallet = walletService.createWallet(userId, walletDto);

        assertThat(createdWallet).isNotNull();
    }

    @Test
    void disableWallet() throws WalletInactiveException {
        Wallet wallet = new Wallet(walletId, "toll", WalletStatus.ACTIVE, 200);
        given(walletRepository.findWalletByWalletId(Mockito.any())).willReturn(wallet);

        given(walletRepository.save(Mockito.any())).willReturn(wallet);
        walletService.disableWallet(walletId);

        assertThat(wallet.getStatus()).isEqualTo(WalletStatus.INACTIVE);
    }

    @Test
    void enableWallet() throws WalletAlreadyActiveException {
        Wallet wallet = new Wallet(walletId, "toll", WalletStatus.INACTIVE, 200);
        given(walletRepository.findWalletByWalletId(Mockito.any())).willReturn(wallet);
        given(walletRepository.save(Mockito.any())).willReturn(wallet);

        walletService.enableWallet(walletId);

        assertThat(wallet.getStatus()).isEqualTo(WalletStatus.ACTIVE);
    }

    @Test
    void updateBalanceWhenTransactionType_is_cashIn() {
        Wallet wallet = new Wallet(walletId, "internet", WalletStatus.ACTIVE, 200);
        given(walletRepository.findWalletByWalletId(Mockito.any())).willReturn(wallet);
        double transactionAmount = 100;
        TransactionType cashIn = TransactionType.CASH_IN;
        given(walletRepository.save(Mockito.any())).willReturn(wallet);

        walletService.updateBalance(walletId, transactionAmount, cashIn);

        assertThat(wallet.getBalance()).isEqualTo(300);
    }

    @Test
    void updateBalanceWhenTransactionType_is_walletTransfer() {
        Wallet wallet = new Wallet(walletId, "internet", WalletStatus.ACTIVE, 200);
        given(walletRepository.findWalletByWalletId(Mockito.any())).willReturn(wallet);
        double transactionAmount = 100;
        TransactionType walletTransfer = TransactionType.WALLET_TRANSFER;
        given(walletRepository.save(Mockito.any())).willReturn(wallet);

        walletService.updateBalance(walletId, transactionAmount, walletTransfer);

        assertThat(wallet.getBalance()).isEqualTo(300);
    }

    @Test
    void updateBalanceWhenTransactionType_is_cashOut() {
        Wallet wallet = new Wallet(walletId, "internet", WalletStatus.ACTIVE, 200);
        given(walletRepository.findWalletByWalletId(Mockito.any())).willReturn(wallet);
        double transactionAmount = 100;
        TransactionType cashOut = TransactionType.CASH_OUT;
        given(walletRepository.save(Mockito.any())).willReturn(wallet);

        walletService.updateBalance(walletId, transactionAmount, cashOut);

        assertThat(wallet.getBalance()).isEqualTo(100);
    }
}