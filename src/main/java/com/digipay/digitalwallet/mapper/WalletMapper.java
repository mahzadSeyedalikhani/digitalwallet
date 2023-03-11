package com.digipay.digitalwallet.mapper;

import com.digipay.digitalwallet.model.dto.WalletDto;
import com.digipay.digitalwallet.model.dto.WalletRequest;
import com.digipay.digitalwallet.model.entity.Wallet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface WalletMapper {

    WalletDto walletRequestToWalletDto(WalletRequest walletRequest);

    Wallet walletDtoToWallet(WalletDto walletDto);

    List<WalletDto> walletToWalletDto(List<Wallet> wallets);

}
