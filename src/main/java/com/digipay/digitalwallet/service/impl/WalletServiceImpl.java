package com.digipay.digitalwallet.service.impl;

import com.digipay.digitalwallet.exception.DuplicateWalletException;
import com.digipay.digitalwallet.exception.WalletAlreadyActiveException;
import com.digipay.digitalwallet.exception.WalletInactiveException;
import com.digipay.digitalwallet.exception.WalletNotFoundException;
import com.digipay.digitalwallet.mapper.WalletMapper;
import com.digipay.digitalwallet.model.TransactionType;
import com.digipay.digitalwallet.model.WalletStatus;
import com.digipay.digitalwallet.model.dto.WalletDto;
import com.digipay.digitalwallet.model.entity.User;
import com.digipay.digitalwallet.model.entity.Wallet;
import com.digipay.digitalwallet.repository.UserRepository;
import com.digipay.digitalwallet.repository.WalletRepository;
import com.digipay.digitalwallet.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final UserRepository userRepository;


    public WalletServiceImpl(WalletRepository walletRepository, WalletMapper walletMapper,
                             UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Wallet createWallet(String userId, WalletDto walletDto) throws DuplicateWalletException {
        User user = userRepository.findUserByUserId(userId);
        List<Wallet> userWallets = walletRepository.findWalletsByUser(user);
        Stream<Wallet> stream = userWallets.stream();
        if (stream.anyMatch(s -> s.getName().matches(walletDto.getName()))) {
            throw new DuplicateWalletException(walletDto.getName() + " wallet is duplicate and has been created before");
        } else {
            Wallet wallet = walletMapper.walletDtoToWallet(walletDto);
            wallet.setWalletId(UUID.randomUUID().toString());
            wallet.setStatus(WalletStatus.ACTIVE);
            double initialBalance = 0.0;
            wallet.setBalance(initialBalance);
            wallet.setUser(user);
            walletRepository.save(wallet);
            return wallet;
        }
    }

    @Transactional
    @Override
    public void disableWallet(String walletId) throws WalletInactiveException {
        Wallet wallet = walletRepository.findWalletByWalletId(walletId);
        if (wallet.getStatus().equals(WalletStatus.ACTIVE)) {
            wallet.setStatus(WalletStatus.INACTIVE);
            walletRepository.save(wallet);
        } else
            throw new WalletInactiveException(wallet.getName() + " wallet is already inactive!!!");
    }

    @Transactional
    @Override
    public void enableWallet(String walletId) throws WalletAlreadyActiveException {
        Wallet wallet = walletRepository.findWalletByWalletId(walletId);
        if (wallet.getStatus().equals(WalletStatus.INACTIVE)) {
            wallet.setStatus(WalletStatus.ACTIVE);
            walletRepository.save(wallet);
        } else
            throw new WalletAlreadyActiveException(wallet.getName() + " wallet is already active!!!");
    }

    @Transactional
    @Override
    public void updateBalance(String walletId, double transactionAmount, TransactionType transactionType) {
        Wallet wallet = walletRepository.findWalletByWalletId(walletId);
        double previousBalance = wallet.getBalance();
        if (transactionType.equals(TransactionType.CASH_IN) ||
                transactionType.equals(TransactionType.WALLET_TRANSFER)) {
            wallet.setBalance(previousBalance + transactionAmount);
        } else if (transactionType.equals(TransactionType.CASH_OUT)) {
            wallet.setBalance(previousBalance - transactionAmount);
        }
        walletRepository.save(wallet);
    }


    @Override
    public List<Wallet> displayUserWallets(String userId) throws WalletNotFoundException {
        User user = userRepository.findUserByUserId(userId);
        List<Wallet> userWallets = walletRepository.findWalletsByUser(user);
        if (userWallets.size() > 0) {
            return userWallets;
        } else
            throw new WalletNotFoundException("No wallet has been created yet " + user.getUsername() +
                    " for creating one, use the \"create wallet\" api plz :)");
    }
}