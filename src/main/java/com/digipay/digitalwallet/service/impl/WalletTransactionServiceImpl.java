package com.digipay.digitalwallet.service.impl;

import com.digipay.digitalwallet.exception.*;
import com.digipay.digitalwallet.mapper.CashInMapper;
import com.digipay.digitalwallet.mapper.CashOutMapper;
import com.digipay.digitalwallet.mapper.WalletTransferMapper;
import com.digipay.digitalwallet.model.TransactionType;
import com.digipay.digitalwallet.model.WalletStatus;
import com.digipay.digitalwallet.model.dto.CashInDto;
import com.digipay.digitalwallet.model.dto.CashOutDto;
import com.digipay.digitalwallet.model.dto.WalletTransferDto;
import com.digipay.digitalwallet.model.entity.User;
import com.digipay.digitalwallet.model.entity.Wallet;
import com.digipay.digitalwallet.model.entity.WalletTransaction;
import com.digipay.digitalwallet.repository.UserRepository;
import com.digipay.digitalwallet.repository.WalletRepository;
import com.digipay.digitalwallet.repository.WalletTransactionRepository;
import com.digipay.digitalwallet.service.DebitTransactionService;
import com.digipay.digitalwallet.service.WalletService;
import com.digipay.digitalwallet.service.WalletTransactionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final DebitTransactionService debitTransactionService;
    private final WalletService walletService;
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletRepository walletRepository;
    private final CashInMapper cashInMapper;
    private final UserRepository userRepository;
    private final CashOutMapper cashOutMapper;
    private final WalletTransferMapper walletTransferMapper;


    public WalletTransactionServiceImpl(@Lazy DebitTransactionService debitTransactionService,
                                        WalletService walletService,
                                        WalletTransactionRepository walletTransactionRepository,
                                        WalletRepository walletRepository, UserRepository userRepository,
                                        CashInMapper cashInMapper,
                                        CashOutMapper cashOutMapper,
                                        WalletTransferMapper walletTransferMapper) {
        this.debitTransactionService = debitTransactionService;
        this.walletService = walletService;
        this.walletTransactionRepository = walletTransactionRepository;
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.cashInMapper = cashInMapper;
        this.cashOutMapper = cashOutMapper;
        this.walletTransferMapper = walletTransferMapper;
    }


    @Transactional
    public void createWalletTransaction(WalletTransaction walletTransaction, String walletId){
        Wallet sourceWallet = walletRepository.findWalletByWalletId(walletId);
        walletTransaction.setSourceWallet(sourceWallet);
        walletTransaction.setWalletTransactionId(UUID.randomUUID().toString());
        walletTransaction.setTransactionDate(new Date());
    }


    @Transactional
    @Override
    public void cashIn(String walletId, CashInDto cashInDto) throws WalletInactiveException {
        Wallet sourceWallet = walletRepository.findWalletByWalletId(walletId);
        if (sourceWallet.getStatus().equals(WalletStatus.ACTIVE)) {
            WalletTransaction walletTransaction = cashInMapper.CashInDtoToWalletTransaction(cashInDto);
            createWalletTransaction(walletTransaction, walletId);
            walletTransaction.setTransactionType(TransactionType.CASH_IN);
            walletTransaction.setTransactionAmount(cashInDto.getTransactionAmount());
            String iban = sourceWallet.getUser().getIban();
            debitTransactionService.withdraw(walletTransaction, iban);
            walletTransactionRepository.save(walletTransaction);
            walletService.updateBalance(walletId, (double) walletTransaction.getTransactionAmount(),
                    walletTransaction.getTransactionType());
        }else
            throw new WalletInactiveException(sourceWallet.getName() + " is inactive so you cannot do any transaction." +
                    " to activate it use the \"enable wallet\" api :>");

    }

    @Transactional
    @Override
    public void cashOut(String walletId, CashOutDto cashOutDto) throws UserNotExistException,
            WalletInactiveException, InsufficientBalanceException {

        Wallet sourceWallet = walletRepository.findWalletByWalletId(walletId);
        if (sourceWallet.getStatus().equals(WalletStatus.ACTIVE)) {
            if (cashOutDto.getTransactionAmount() <= sourceWallet.getBalance()) {
                String destinationUserId = cashOutDto.getDestinationUserId();
                if (userRepository.existsUserByUserId(destinationUserId)) {
                    WalletTransaction walletTransaction = cashOutMapper.CashOutDtoToWalletTransaction(cashOutDto);
                    createWalletTransaction(walletTransaction, walletId);
                    walletTransaction.setTransactionType(TransactionType.CASH_OUT);
                    walletTransaction.setTransactionAmount(cashOutDto.getTransactionAmount());
                    User user = userRepository.findUserByUserId(destinationUserId);
                    debitTransactionService.deposit(walletTransaction, user.getIban());
                    walletTransactionRepository.save(walletTransaction);
                    walletService.updateBalance(walletId, (double) walletTransaction.getTransactionAmount(),
                            walletTransaction.getTransactionType());
                } else
                    throw new UserNotExistException("The entered user is not exist");
            } else
                throw new InsufficientBalanceException("The balance of " + sourceWallet.getName() +
                        " wallet is not sufficient. you can increase the wallet balance by using \"cash in\" api");
        } else
            throw new WalletInactiveException(sourceWallet.getName() + " is inactive so you cannot do any transaction." +
                    " to activate it use the \"enable wallet\" api :>");

    }

    @Transactional
    @Override
    public void walletTransfer(String walletId, WalletTransferDto walletTransferDto) throws
            WalletInactiveException, WalletNotFoundException, UserNotExistException, InsufficientBalanceException {

        Wallet sourceWallet = walletRepository.findWalletByWalletId(walletId);
        if (sourceWallet.getStatus().equals(WalletStatus.ACTIVE)) {
            if (walletTransferDto.getTransactionAmount() <= sourceWallet.getBalance()) {
                String destinationUserId = walletTransferDto.getDestinationUserId();
                if (userRepository.existsUserByUserId(destinationUserId)) {
                    String selectedUserWalletToTransfer = walletTransferDto.getDestinationWalletName();
                    WalletTransaction walletTransaction = walletTransferMapper.walletTransferDtoToWalletTransaction(walletTransferDto);
                    createWalletTransaction(walletTransaction, walletId);
                    walletTransaction.setTransactionType(TransactionType.WALLET_TRANSFER);
                    walletTransaction.setTransactionAmount(walletTransferDto.getTransactionAmount());

                    Stream<String> stringStream = walletService.displayUserWallets(destinationUserId).stream().map(n -> n.getName());
                    if (stringStream.anyMatch(s -> s.matches(selectedUserWalletToTransfer))) {

                    } else
                        throw new WalletNotFoundException("This user doesn't have " + selectedUserWalletToTransfer + " wallet");
                    String userNationalCode = userRepository.findUserByUserId(destinationUserId).getNationalCode();
                    Wallet destinationWallet = walletRepository.findWalletByUserNationalCodeAndName
                            (userNationalCode, selectedUserWalletToTransfer);
                    walletService.updateBalance(destinationWallet.getWalletId(), walletTransaction.getTransactionAmount(),
                            TransactionType.WALLET_TRANSFER);
                    sourceWallet.setBalance(sourceWallet.getBalance() - (double) walletTransaction.getTransactionAmount());
                    walletTransaction.setSourceWallet(sourceWallet);
                    walletTransaction.setDestinationWallet(destinationWallet);
                    walletTransactionRepository.save(walletTransaction);
                } else
                    throw new UserNotExistException("The entry user is not exist");
            } else
                throw new InsufficientBalanceException("The balance of " + sourceWallet.getName() +
                        " wallet is not sufficient. you can increase the wallet balance by using \"cash in\" api");
        } else
            throw new WalletInactiveException(sourceWallet.getName() + " is inactive so you cannot do any transaction." +
                    " to activate it use the \"enable wallet\" api :>");
    }

    @Override
    public List<WalletTransaction> getAllWalletTransactions(String walletId) throws
            WalletTransactionNotFoundException {
        Wallet wallet = walletRepository.findWalletByWalletId(walletId);
        List<WalletTransaction> walletTransactions = walletTransactionRepository
                .findWalletTransactionsBySourceWalletId(walletId);
        if (walletTransactions.size() > 0) {
            return walletTransactions;
        } else
            throw new WalletTransactionNotFoundException("for the " + wallet.getName() + " wallet, there isn't " +
                    "any transaction record");
    }
}