package com.digipay.digitalwallet.mapper;

import com.digipay.digitalwallet.model.dto.WalletTransferDto;
import com.digipay.digitalwallet.model.dto.WalletTransferRequest;
import com.digipay.digitalwallet.model.entity.WalletTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface WalletTransferMapper {

    WalletTransferDto walletTransferRequestToWalletTransferDto(WalletTransferRequest walletTransferRequest);
    WalletTransaction walletTransferDtoToWalletTransaction(WalletTransferDto walletTransferDto);
}
