package com.digipay.digitalwallet.controller;

import com.digipay.digitalwallet.exception.DuplicateWalletException;
import com.digipay.digitalwallet.exception.WalletAlreadyActiveException;
import com.digipay.digitalwallet.exception.WalletInactiveException;
import com.digipay.digitalwallet.exception.WalletNotFoundException;
import com.digipay.digitalwallet.mapper.WalletMapper;
import com.digipay.digitalwallet.model.dto.WalletDto;
import com.digipay.digitalwallet.model.dto.WalletRequest;
import com.digipay.digitalwallet.model.entity.Wallet;
import com.digipay.digitalwallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;
    private final WalletMapper walletMapper;

    public WalletController(WalletService walletService, WalletMapper walletMapper) {
        this.walletService = walletService;
        this.walletMapper = walletMapper;
    }

    @PostMapping("{userId}")
    public void createWallet(@PathVariable String userId,@RequestBody @Valid WalletRequest walletRequest)
                                                                            throws DuplicateWalletException {
        WalletDto walletDto = walletMapper.walletRequestToWalletDto(walletRequest);
        walletService.createWallet(userId, walletDto);
    }

    @PatchMapping("/disable-wallet/{walletId}")
    public void disableWallet(@PathVariable String walletId) throws WalletInactiveException {
        walletService.disableWallet(walletId);
    }

    @PatchMapping("/enable-wallet/{walletId}")
    public void enableWallet(@PathVariable String walletId) throws WalletAlreadyActiveException {
        walletService.enableWallet(walletId);
    }

    @GetMapping("{userId}")
    public List<WalletDto> displayUserWallets(@PathVariable String userId) throws WalletNotFoundException {
        List<Wallet> wallets = walletService.displayUserWallets(userId);
        return walletMapper.walletToWalletDto(wallets);
    }
}
