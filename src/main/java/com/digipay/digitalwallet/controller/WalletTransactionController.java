package com.digipay.digitalwallet.controller;

import com.digipay.digitalwallet.exception.*;
import com.digipay.digitalwallet.mapper.CashInMapper;
import com.digipay.digitalwallet.mapper.CashOutMapper;
import com.digipay.digitalwallet.mapper.WalletTransactionMapper;
import com.digipay.digitalwallet.mapper.WalletTransferMapper;
import com.digipay.digitalwallet.model.dto.*;
import com.digipay.digitalwallet.model.entity.WalletTransaction;
import com.digipay.digitalwallet.service.WalletTransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/wallet_transactions")
public class WalletTransactionController {

    private final WalletTransactionService walletTransactionService;
    private final WalletTransactionMapper walletTransactionMapper;
    private final CashInMapper cashInMapper;
    private final CashOutMapper transactionMapper;
    private final WalletTransferMapper walletTransferMapper;

    public WalletTransactionController(WalletTransactionService walletTransactionService,
                                       WalletTransactionMapper walletTransactionMapper,
                                       CashOutMapper cashOutMapper, CashInMapper cashInMapper,
                                       WalletTransferMapper walletTransferMapper) {
        this.walletTransactionService = walletTransactionService;
        this.walletTransactionMapper = walletTransactionMapper;
        this.transactionMapper = cashOutMapper;
        this.walletTransferMapper = walletTransferMapper;
        this.cashInMapper = cashInMapper;
    }

    @PutMapping("/cash-in/{walletId}")
    void cashIn(@PathVariable String walletId, @RequestBody @Valid CashInRequest cashInRequest) throws
            WalletInactiveException {
        CashInDto cashInDto = cashInMapper.cashInRequestToCashInDto(cashInRequest);
        walletTransactionService.cashIn(walletId, cashInDto);
    }

    @PutMapping("/cash-out/{walletId}")
    void cashOut(@PathVariable String walletId,
                 @RequestBody @Valid CashOutRequest cashOutRequest) throws WalletInactiveException,
            UserNotExistException, InsufficientBalanceException {
        CashOutDto cashOutDto = transactionMapper.cashOutRequestToCashOutDto(cashOutRequest);
        walletTransactionService.cashOut(walletId, cashOutDto);
    }

    @PutMapping("/wallet-transfer/{walletId}")
    void walletTransfer(@PathVariable String walletId,
                        @RequestBody @Valid WalletTransferRequest walletTransferRequest) throws WalletNotFoundException,
            UserNotExistException, WalletInactiveException, InsufficientBalanceException {
        WalletTransferDto walletTransferDto = walletTransferMapper.walletTransferRequestToWalletTransferDto(walletTransferRequest);
        walletTransactionService.walletTransfer(walletId, walletTransferDto);
    }

    @GetMapping("{walletId}")
    List<WalletTransactionDto> getAllWalletTransactions (@PathVariable String walletId) throws
            WalletTransactionNotFoundException {
        List<WalletTransaction> WalletTransactions = walletTransactionService.getAllWalletTransactions(walletId);
        return walletTransactionMapper.walletTransactionToWalletTransactionDto(WalletTransactions);
    }
}
